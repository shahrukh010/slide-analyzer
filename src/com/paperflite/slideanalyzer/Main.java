package com.paperflite.slideanalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ooxml.POIXMLProperties.CoreProperties;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.json.JSONArray;
import org.json.JSONObject;

import com.paperflite.slideanalyzer.services.ExtractPPT;
import com.paperflite.slideanalyzer.services.ExtractPPTImages;
import com.paperflite.slideanalyzer.services.SaveToJSON;
import com.paperflite.slideanalyzer.services.impl.ExtractAndSaveImages;
import com.paperflite.slideanalyzer.services.impl.ExtractText;

public class Main {

	public static void main(String... strings) throws FileNotFoundException, IOException, InvalidFormatException,
			org.apache.poi.openxml4j.exceptions.InvalidFormatException {

		ExtractPPT extract = new ExtractPPT();

		String fileName = "/tmp/Presentation.pptx";
		File file = new File(fileName);

		FileInputStream inputStream;

		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		XMLSlideShow ppt;

		try {
			ppt = new XMLSlideShow(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ExtractText extractText = new ExtractText();
		// ExtractText extractText = new ExtractText();
//		List<Map<String, Object>> dataList = extractText.extract(ppt);
//		ExtractAndSaveImages images = new ExtractAndSaveImages();
//		List<Map<String, Object>> imageList = images.extract(ppt);
//		dataList.addAll(imageList);
		

		List<Map<String, Object>> boxList = new ArrayList<>();
		 for (XSLFSlide slide : ppt.getSlides()) {
	                	boxList = extractText.extractTextboxes(slide);
	            }
		
		
		// Save the extracted data to a JSON file
		File jsonFile = new File("textbox.json");
		SaveToJSON saveToJSON = new SaveToJSON(jsonFile);
		saveToJSON.saveToJSON(boxList,"data.json");


//####################################################################################################		
//####################################################################################################		
//        ExtractPPT extract = new ExtractPPT();
//        ExtractPPTImages images = new ExtractPPTImages();
		// ExtractAndSaveImages images = new ExtractAndSaveImages();
//
		// String fileName = "/tmp/Presentation.pptx";
		// File file = new File(fileName);

		// String outputFolder = "ppt-images";

//        File folder = new File(outputFolder);
//
//        if (!folder.exists()) {
//            folder.mkdir();
//        }

//        
//        File filePath = new File(fileName);
//       InputStream inputStream1 = new FileInputStream(filePath);
//        XMLSlideShow ppt1 = new XMLSlideShow(inputStream1);
//
//  List<Map<String, Object>> dataList = null;
//        for (int i = 1; i <= ppt.getSlides().size(); i++) {
//            images.extractImagesFromPPT(fileName, outputFolder);
//        	dataList = images.extract(ppt1);
//        			  
//	  //    Save the extracted data to a JSON file
//	        File jsonFile = new File("image.json");
//	       SaveToJSON saveToJSON = new SaveToJSON(jsonFile);
//	       System.out.println(dataList);
//	      saveToJSON.saveToJSON(dataList);
//        }
//        System.out.println(dataList);
//        String string = "Images have been saved successfully";
//        System.out.println(string);
	}

}