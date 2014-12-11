/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 *
 * @author dk1109
 * Defines plate properties. 
 * Reads plate properties from XPLT files
 * For now, implement as singleton...
 * 
 */
public class PlateProperties {
    // for now, minimal list of properties - more present in XPLT...
    
    String plateName = "Greiner uClear";
    int rows = 8;
    int cols = 12;
    String wellShape = "Square";
    
    double wellSizeUm = 6500;
    double wellSpacingVUm = 9000;
    double wellSpacingHUm = 9000;
    
    double topLeftCentreOffsetHUm = 14380;
    double topLeftCentreOffsetVUm = 11240;
    
     public PlateProperties(){
    
    }
     
    public PlateProperties loadProperties(File fXmlFile){
        // source: http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        // more general than necessary, but useful as reference...
        try {
            
//	File fXmlFile = new File("C:/Program Files (x86)/Micro-Manager-1.4-32 20 Oct 2014 build/mmplugins/OpenHCAFLIM/XPLT/Greiner uClear.xplt");
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();
	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	NodeList nList = doc.getElementsByTagName("Plate");
 
	for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);
		 
		if (nNode.getNodeType() == Node.ELEMENT_NODE & "Plate".equals(nNode.getNodeName())) {
			Element eElement = (Element) nNode;
                        plateName = eElement.getAttribute("name");
                        rows = Integer.parseInt(eElement.getAttribute("rows"));
                        cols = Integer.parseInt(eElement.getAttribute("columns"));
		}
	}
        
        nList = doc.getElementsByTagName("WellParameters");
        for (int temp  = 0; temp < nList.getLength(); temp++){
        
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE & "WellParameters".equals(nNode.getNodeName())) {
			Element eElement = (Element) nNode;
                        wellShape = eElement.getAttribute("shape");
                        wellSizeUm = Double.parseDouble(eElement.getAttribute("size"));
                        if ("mm".equals(eElement.getAttribute("unit")))
                            wellSizeUm = wellSizeUm * 1000;
                        
		}
        }
        
        nList = doc.getElementsByTagName("WellSpacing");
        for (int temp  = 0; temp < nList.getLength(); temp++){
        
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE & "WellSpacing".equals(nNode.getNodeName())) {
			Element eElement = (Element) nNode;
                        wellSpacingHUm = Double.parseDouble(eElement.getAttribute("horizontal"));
                        wellSpacingVUm = Double.parseDouble(eElement.getAttribute("vertical"));
                        if ("mm".equals(eElement.getAttribute("unit"))){
                            wellSpacingHUm = wellSpacingHUm * 1000;
                            wellSpacingVUm = wellSpacingVUm * 1000;
                        }               
		}
        }
        
        nList = doc.getElementsByTagName("TopLeftWellCenterOffset");
        for (int temp  = 0; temp < nList.getLength(); temp++){
        
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE & "TopLeftWellCenterOffset".equals(nNode.getNodeName())) {
			Element eElement = (Element) nNode;
                        topLeftCentreOffsetHUm = Double.parseDouble(eElement.getAttribute("horizontal"));
                        topLeftCentreOffsetVUm = Double.parseDouble(eElement.getAttribute("vertical"));
                        if ("mm".equals(eElement.getAttribute("unit"))){
                            topLeftCentreOffsetHUm = topLeftCentreOffsetHUm * 1000;
                            topLeftCentreOffsetVUm = topLeftCentreOffsetVUm * 1000;
                        }               
		}
        }
    } catch (Exception e) {
	e.printStackTrace();
    }
        return this;
    }
    
    // potentially setters and getters for all fields...?
    public String getPlateName(){return plateName;}
    
    public int getPlateRows(){return rows;}
    
    public int getPlateColumns(){return cols;}
    
    public String getWellShape(){return wellShape;}
    
    public double getWellSize(){return wellSizeUm;}
    
    public double getWellSpacingV(){return wellSpacingVUm;}
    
    public double getWellSpacingH(){return wellSpacingHUm;}
    
    public double getTopLeftWellOffsetH(){return topLeftCentreOffsetHUm;}
    
    public double getTopLeftWellOffsetV(){return topLeftCentreOffsetVUm;}
}
