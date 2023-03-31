package com.paperflite.slideanalyzer.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import com.paperflite.slideanalyzer.services.Extractable;


public class ExtractImages implements Extractable{

	@Override
	public List<Map<String, Object>> extract(XMLSlideShow ppt) {
		
		return null;
	}
private  void extractImagesFromPPT(String pptFilePath, String outputDirPath) throws IOException {
	    FileInputStream inputStream = new FileInputStream(pptFilePath);
	    XMLSlideShow ppt = new XMLSlideShow(inputStream);
	    inputStream.close();
	    
	    for (XSLFSlide slide : ppt.getSlides()) {
	        for (XSLFShape shape : slide.getShapes()) {
	            if (shape instanceof XSLFPictureShape) {
	                XSLFPictureShape picture = (XSLFPictureShape) shape;
	                XSLFPictureData pictureData = picture.getPictureData();
	                byte[] pictureBytes = pictureData.getData();
	                String pictureFileName = pictureData.getFileName();
	                FileOutputStream outputStream = new FileOutputStream(outputDirPath + File.separator + pictureFileName);
	                outputStream.write(pictureBytes);
	                outputStream.close();
	            }
	        }
	    }
	}
	
}
