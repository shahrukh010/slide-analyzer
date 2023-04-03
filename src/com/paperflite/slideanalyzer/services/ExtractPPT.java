package com.paperflite.slideanalyzer.services;

import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ooxml.POIXMLProperties.CoreProperties;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSimpleShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class ExtractPPT {

    File file = new File("data.json");
	public void extractPPT(XMLSlideShow ppt) {
		String title = getTitle(ppt);
		System.out.println("Title: " + title);

		for (XSLFSlide slide : ppt.getSlides()) {
			System.out.println("Starting slide...");
			List<XSLFShape> shapes = slide.getShapes();
			for (XSLFShape shape : shapes) {
				if (shape instanceof XSLFTextShape) {
					readTextShape((XSLFTextShape) shape);
				}
			}
		}
	}

	private String getTitle(XMLSlideShow ppt) {
		CoreProperties props = ppt.getProperties().getCoreProperties();
		return props.getTitle();
	}
	
	private void readTextShape(XSLFTextShape textShape) {
	    List<Map<String, Object>> dataList = new ArrayList<>();
	    for (XSLFTextParagraph paragraph : textShape) {
	        for (XSLFTextRun textRun : paragraph) {
	            String text = textRun.getRawText();
	            String font = textRun.getFontFamily();
	            double fontSize = textRun.getFontSize();
	            if (!text.equals("\n")) {
	                Map<String, Object> data = new HashMap<>();
	                data.put("text", text);
	                data.put("font", font);
	                data.put("fontSize", fontSize);
	                dataList.add(data);
	            }
	        }
	    }

	    if(!dataList.isEmpty()) {
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String json = gson.toJson(dataList);
	    System.out.println("JSON: " + json);

	    System.out.println("File exists: " + file.exists());

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
	        writer.write(json);
	        writer.flush();
	        System.out.println("Data saved successfully to data.json file.");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    }
	}
	
	private List<Map<String, Object>> extractShapes(XSLFSlide slide) {
	    List<Map<String, Object>> dataList = new ArrayList<>();

	    for (XSLFShape shape : slide.getShapes()) {
	        if (shape instanceof XSLFSimpleShape) {
	            Rectangle2D shapeAnchor = shape.getAnchor();
	            double shapeX = shapeAnchor.getX();
	            double shapeY = shapeAnchor.getY();
	            double shapeWidth = shapeAnchor.getWidth();
	            double shapeHeight = shapeAnchor.getHeight();
	            String shapeType = shape.getShapeName();

	            Map<String, Object> data = new HashMap<>();
	            data.put("x", shapeX);
	            data.put("y", shapeY);
	            data.put("width", shapeWidth);
	            data.put("height", shapeHeight);
	            data.put("type", shapeType);

	            dataList.add(data);
//	            logger.debug("Added data: " + data);
	        }
	    }

//	    logger.debug("Shapes extracted successfully.");
	    return dataList;
	}

	
	
	
	
	
	
}
