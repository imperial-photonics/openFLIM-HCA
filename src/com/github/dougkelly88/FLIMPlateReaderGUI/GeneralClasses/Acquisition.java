/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FOV;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FilterSetup;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.SeqAcqSetup;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.TimePoint;
import java.util.ArrayList;
import mmcorej.CMMCore;
import org.micromanager.MMStudio;
import loci.formats.ome.OMEXMLMetadata;
import loci.formats.services.OMEXMLServiceImpl;
import loci.common.services.ServiceException;
import loci.formats.ImageWriter;
import loci.common.DataTools;
import loci.formats.CoreMetadata;
import ome.xml.model.enums.DimensionOrder;
import ome.xml.model.enums.PixelType;
import ome.xml.model.primitives.PositiveInteger;
import ome.xml.model.primitives.PositiveFloat;
import ome.xml.model.primitives.NonNegativeInteger;
import java.io.IOException;
import com.quirkware.guid.PlatformIndependentGuidGen;
import loci.formats.FormatException;
import loci.formats.IFormatWriter;
import mmcorej.TaggedImage;
import org.micromanager.api.ImageCache;

/**
 *
 * @author dk1109
 */
public class Acquisition {
    MMStudio gui_;
    CMMCore core_;
    CoreMetadata cm;
    private DisplayImage DisplayImage_;
    private HCAFLIMPluginFrame frame_;


    public Acquisition() {
        gui_ = MMStudio.getInstance();
        core_ = gui_.getCore();
        DisplayImage_ =DisplayImage.getInstance();
        frame_= HCAFLIMPluginFrame.getInstance();
    }

    public void snapFLIMImage(String path, ArrayList<Integer> delays, SeqAcqSetup sas) {

        try{

            if (gui_.isLiveModeOn() | gui_.isAcquisitionRunning()){
                gui_.enableLiveMode(false);
                gui_.closeAllAcquisitions();
            }

            OMEXMLMetadata m = setBasicMetadata(delays, sas);
            IFormatWriter writer = generateWriter(path, m);

            for (Integer delay : delays) {
                int del=delays.indexOf(delay);
                
                core_.setProperty("Delay box", "Delay (ps)", delay);
                

                // EITHER
//                core_.snapImage();
                long dim = core_.getImageWidth() * core_.getImageHeight();
//                Object img = core_.getImage();
                int[] accImg = new int[(int)dim];
                for (int fr = 0; fr < sas.getFilters().getAccFrames(); fr++){
                    core_.snapImage();
                    Object img = core_.getImage();
//                    DisplayImage_.display(img);
                    // this bit c.f. FrameAverager
                    if (core_.getBytesPerPixel() == 2){
                        short[] pixS = (short[]) img;
                        for (int j = 0; j < dim; j++) {
                            accImg[j] = (int) (accImg[j] + (int) (pixS[j] & 0xffff));
                        }
                    } else if (core_.getBytesPerPixel() == 1){
                        byte[] pixB = (byte[]) img;
                        for (int j = 0; j < dim; j++) {
                            accImg[j] = (int) (accImg[j] + (int) (pixB[j] & 0xff));
                        }
                    }

                }
//                core_.snapImage();
                saveLayersToOMETiff(writer, accImg, delays.indexOf(delay));
                ////

                
                // OR
//                gui_.snapAndAddImage(acq, delays.indexOf(delay), 0, 0, 0);
                
                ////
                if(frame_.singleImage==1){
                    frame_.progressBar_.stepIncrement(del, delays.size());
                }
            }
            // OR
//            saveAcqToOMETiff(writer, acq, delays.size());
            ////
            // clean up writer when finished...
            writer.close();
       } catch (Exception e) {
           // byte[] imgDemo =null;
           // try {
             //   core_.snapImage();
             //   imgDemo = (byte[]) core_.getImage();
           // } catch (Exception ex) {
              //  System.out.print("no picture snaped");
           // }
            System.out.println(e.getMessage());
           // DisplayImage_.display(imgDemo);
        }

    }
    
    private void saveAcqToOMETiff(IFormatWriter writer, String acq, int length)
            throws Exception {
        ImageCache imgCache = gui_.getAcquisitionImageCache(acq);
        for (int ind = 0; ind < length; ind++) {
            TaggedImage timg = imgCache.getImage(ind, 0, 0, 0); // returns null if no image exists at these coordinates
            Object img = timg.pix;
            if (img instanceof byte[]) {
                System.out.println("Img is in bytes");
                writer.saveBytes(ind, (byte[]) img);
            } else if (img instanceof short[]) {
//                byte[] bytes = DataTools.shortsToBytes((short[]) img, true);
                short[] img2 = (short[]) img;
                byte[] bytes = DataTools.shortsToBytes(img2, false);
                System.out.println("Img is short[], converting to bytes, i = " + ind);
//                writer.savePlane(ind,img);
                writer.saveBytes(ind, bytes);
            } else {
                System.out.println("I don't know what type img is!");
            }
        }

    }

    private void saveLayersToOMETiff(IFormatWriter writer, Object img, int layer)
            throws Exception {
//        Object img = core_.getImage();
        if (img instanceof byte[]) {
            System.out.println("Img is in bytes");
            writer.saveBytes(layer, (byte[]) img);
        } else if (img instanceof short[]) {
            byte[] bytes = DataTools.shortsToBytes((short[]) img, true);
//            System.out.println("Img is short[], converting to bytes, i = " + layer);
            writer.saveBytes(layer, bytes);
        } else  if (img instanceof int[]){
            byte[] bytes = DataTools.intsToBytes((int[]) img, true);
            writer.saveBytes(layer, bytes);
        } else
        {
            System.out.println("I don't know what type img is!");
        }
    }

    private IFormatWriter generateWriter(String path, OMEXMLMetadata m)
            throws FormatException, IOException {
        IFormatWriter writer = new ImageWriter().getWriter(path);
        writer.setWriteSequentially(true);
        writer.setMetadataRetrieve(m);
        writer.setCompression("LZW");

        writer.setId(path);

        return writer;
    }

    private PositiveFloat checkPixelPitch() {

        PositiveFloat pitch = new PositiveFloat(1.0);

        try {
            String binningStr = core_.getProperty(core_.getCameraDevice(), "Binning");
            float binning = 1;
            if (binningStr.equals("1")) {
                binning = 1;
            } else if (binningStr.equals("2")) {
                binning = 2;
            } else if (binningStr.equals("4")) {
                binning = 4;
            } else if (binningStr.equals("8")) {
                binning = 8;
            }

            pitch = new PositiveFloat(binning * 6.45);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return pitch;
    }

    private OMEXMLMetadata setBasicMetadata(ArrayList<Integer> delays, SeqAcqSetup sas)
            throws ServiceException {

        OMEXMLServiceImpl serv = new OMEXMLServiceImpl();
        OMEXMLMetadata m = serv.createOMEXMLMetadata();

        int no_delays = delays.size();
        // delays must be parsed as strings for metadata
        String[] delArrayStr = new String[no_delays];
        for (int ind = 0; ind < no_delays; ind++) {
            delArrayStr[ind] = String.valueOf(delays.get(ind));
        }

        try {
            m.createRoot();
            m.setImageID("Image:0", 0);
            m.setPixelsID("Pixels:0", 0);
            m.setPixelsDimensionOrder(DimensionOrder.XYZCT, 0);
            m.setChannelID("Channel:0:0", 0, 0);
            m.setChannelSamplesPerPixel(new PositiveInteger(1), 0, 0);
            m.setPixelsBinDataBigEndian(Boolean.FALSE, 0, 0);
//            m.setPixelsType(PixelType.UINT8, 0);
            m.setImageDescription(sas.toString(), 0);

            long bpp = core_.getBytesPerPixel();

//            if (sas.getFilters().getAccFrames() == 1){
//                if (bpp == 1) {
//                    m.setPixelsType(PixelType.UINT8, 0);
//                }
//                if (bpp == 2) {
//                    m.setPixelsType(PixelType.UINT16, 0);
//                }
//            }
//            else if (sas.getFilters().getAccFrames() > 0){ 
                System.out.println("setting pixeltype to 32");
                m.setPixelsType(PixelType.UINT32, 0);
                
//            }

            PositiveInteger w1 = new PositiveInteger((int) core_.getImageWidth());
            PositiveInteger h1 = new PositiveInteger((int) core_.getImageHeight());
            PositiveInteger g1 = new PositiveInteger(no_delays);

            m.setPixelsSizeX((w1), 0);
            m.setPixelsSizeY((h1), 0);
            m.setPixelsSizeZ(new PositiveInteger(1), 0);
            m.setPixelsSizeC(new PositiveInteger(1), 0);
            m.setPixelsSizeT(g1, 0);

            PositiveFloat pitch = checkPixelPitch();

            m.setPixelsPhysicalSizeX(pitch, 0);
            m.setPixelsPhysicalSizeY(pitch, 0);
            m.setPixelsPhysicalSizeZ(new PositiveFloat(1.0), 0);

            PlatformIndependentGuidGen p = PlatformIndependentGuidGen.getInstance();

            for (int ii = 0; ii < no_delays; ii++) {

                m.setUUIDFileName(delArrayStr[ii], 0, ii);
                m.setUUIDValue(p.genNewGuid(), 0, ii);
                m.setTiffDataPlaneCount(new NonNegativeInteger(0), 0, ii);
                m.setTiffDataIFD(new NonNegativeInteger(0), 0, ii);
                m.setTiffDataFirstZ(new NonNegativeInteger(0), 0, ii);
                m.setTiffDataFirstC(new NonNegativeInteger(0), 0, ii);
                m.setTiffDataFirstT(new NonNegativeInteger(0), 0, ii);
                m.setPlaneTheC(new NonNegativeInteger(0), 0, ii);
                m.setPlaneTheZ(new NonNegativeInteger(0), 0, ii);
                m.setPlaneTheT(new NonNegativeInteger(ii), 0, ii);
                m.setTiffDataPlaneCount(new NonNegativeInteger(ii), 0, ii);
                System.out.println("done loop ind " + ii);
            }

            // deal FLIMfit issue loading single plane images with moduloAlongT
            if (no_delays > 2){ 
                CoreMetadata cm = new CoreMetadata();

                cm.moduloT.labels = delArrayStr;
                cm.moduloT.unit = "ps";
                cm.moduloT.typeDescription = "Gated";
                cm.moduloT.type = loci.formats.FormatTools.LIFETIME;
                serv.addModuloAlong(m, cm, 0);
                System.out.println("did addModulo");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return m;
    }

    public void startHCASequence(ArrayList<FOV> fovs, ArrayList<TimePoint> tc, ArrayList<FilterSetup> filts, ArrayList<String> order) {
        // TODO: pass an instance of a single class containing all the input vars, also some stuff regarding autofocus?

        // go through order arraylist and build a 2D arraylist containing all vars in appropriate order...
    }
    

    
    public void showImage(){
        //  DisplayImage_.display();
    }
}
