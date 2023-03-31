package com.paperflite.slideanalyzer.services.impl;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import com.paperflite.slideanalyzer.services.Extractable;


public class ExtractAndSaveImages implements Extractable {
	
		private final String DEFAULT_DIR_NAME = "IMG";
	    private String outputDirPath;

	    public ExtractAndSaveImages(String outputDirPath) {
	        this.outputDirPath = outputDirPath;
	    }
	    
	    public ExtractAndSaveImages() {}

	    @Override
	    public List<Map<String, Object>> extract(XMLSlideShow ppt) {
	        List<Map<String, Object>> dataList = new ArrayList<>();

	        for (XSLFSlide slide : ppt.getSlides()) {
	            for (XSLFShape shape : slide.getShapes()) {
	                if (shape instanceof XSLFPictureShape) {
	                    readPictureShape((XSLFPictureShape) shape, dataList);
	                }
	            }
	        }

	        try {
	            extractImagesFromPPT(ppt);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return dataList;
	    }

//	    private void readPictureShape(XSLFPictureShape pictureShape, List<Map<String, Object>> dataList) {
//	        XSLFPictureData pictureData = pictureShape.getPictureData();
//			byte[] pictureBytes = pictureData.getData();
//			String pictureName = pictureData.getFileName();
//			String pictureType = pictureData.getContentType();
//
//			Map<String, Object> data = new HashMap<>();
//			data.put("name", pictureName);
//			data.put("type", pictureType);
//			data.put("data", pictureBytes);
//			dataList.add(data);
//	    }
//	    private void readPictureShape(XSLFPictureShape pictureShape, List<Map<String, Object>> imageList) {
//	        XSLFPictureData pictureData = pictureShape.getPictureData();
//			byte[] pictureBytes = pictureData.getData();
//			String pictureName = pictureData.getFileName();
//			String pictureType = pictureData.getContentType();
//
//			// Encode the byte array using base64
//			String encodedBytes = Base64.getEncoder().encodeToString(pictureBytes);
//
//			Map<String, Object> image = new HashMap<>();
//			image.put("name", pictureName);
//			image.put("type", pictureType);
//			image.put("data", encodedBytes);
//			imageList.add(image);
//	    }
	    
	    private void readPictureShape(XSLFPictureShape pictureShape, List<Map<String, Object>> dataList) {
	        XSLFPictureData pictureData = pictureShape.getPictureData();
			byte[] pictureBytes = pictureData.getData();
			String pictureName = pictureData.getFileName();
			String pictureType = pictureData.getContentType();

			Map<String, Object> image = new HashMap<>();
			image.put("name", pictureName);
			image.put("type", pictureType);
//			image.put("data", Arrays.toString(pictureBytes));

			// Get properties of the picture shape
			Rectangle2D bounds = pictureShape.getAnchor();
			image.put("x", bounds.getX());
			image.put("y", bounds.getY());
			image.put("width", bounds.getWidth());
			image.put("height", bounds.getHeight());

			// Get properties of the picture data
			PictureType type = pictureData.getType();
//	            image.put("typeCode", type.ctype);
			image.put("extension", type.extension);
			image.put("widthPx", pictureData.getImageDimension().width);
			image.put("heightPx", pictureData.getImageDimension().height);

			dataList.add(image);
	    }


	    private void extractImagesFromPPT(XMLSlideShow ppt) throws IOException {
	    	 File folder = new File(DEFAULT_DIR_NAME);

	         if (!folder.exists()) {
	             folder.mkdir();
	         }
	        for (XSLFSlide slide : ppt.getSlides()) {
	            for (XSLFShape shape : slide.getShapes()) {
	                if (shape instanceof XSLFPictureShape) {
	                    XSLFPictureShape picture = (XSLFPictureShape) shape;
	                    XSLFPictureData pictureData = picture.getPictureData();
	                    byte[] pictureBytes = pictureData.getData();
	                    String pictureFileName = pictureData.getFileName();
	                    FileOutputStream outputStream = new FileOutputStream(DEFAULT_DIR_NAME + File.separator + pictureFileName);
	                    outputStream.write(pictureBytes);
	                    outputStream.close();
	                }
	            }
	        }
	    }
	}
