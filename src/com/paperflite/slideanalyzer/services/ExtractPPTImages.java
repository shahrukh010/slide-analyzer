package com.paperflite.slideanalyzer.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;

public class ExtractPPTImages {

	private static final Logger logger = LogManager.getLogger(ExtractPPT.class);

	private static final Logger logger1 = LogManager.getLogger(ExtractPPT.class);

	public static void extractImagesFromPPT(String pptFilePath, String outputDirPath) throws IOException {
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
