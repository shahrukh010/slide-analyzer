package com.paperflite.slideanalyzer.services.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSimpleShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;

import com.paperflite.slideanalyzer.services.Extractable;


public class ExtractText implements Extractable{
	
    private static final Logger logger = LogManager.getLogger(ExtractText.class);


	 @Override
	    public List<Map<String, Object>> extract(XMLSlideShow ppt) {
	        List<Map<String, Object>> dataList = new ArrayList<>();

	        System.out.println(ppt.getSlides().size());
	        for (XSLFSlide slide : ppt.getSlides()) {
	            logger.info("Starting slide...");
//	            dataList.addAll(extractShapes(slide));
//	            dataList.addAll(extractTextboxes(slide));
	            dataList.addAll(extractEllipseShapes(slide));
//	            List<XSLFShape> shapes = slide.getShapes();
//	            for (XSLFShape shape : shapes) {
//	                if (shape instanceof XSLFTextShape) {
//	                    readTextShape((XSLFTextShape) shape, dataList);
//	                }
//	            }
	        }
	        return dataList;
	    }
	
	
	
//	 private void readTextShape(XSLFTextShape textShape, List<Map<String, Object>> dataList) {
//	        for (XSLFTextParagraph paragraph : textShape) {
//	            for (XSLFTextRun textRun : paragraph) {
//	                String text = textRun.getRawText();
//	                String font = textRun.getFontFamily();
//	                double fontSize = textRun.getFontSize();
//	                if (!text.equals("\n")) {
//	                    Map<String, Object> data = new HashMap<>();
//	                    data.put("text", text);
//	                    data.put("font", font);
//	                    data.put("fontSize", fontSize);
//	                    dataList.add(data);
//	                    logger.debug("Added data: " + data);
//	                }
//	            }
//	        }
//	        logger.debug("Text shapes extracted successfully.");
//	    }


//	 private void readTextShape(XSLFTextShape textShape, List<Map<String, Object>> dataList) {
//	 	Rectangle2D shapeAnchor = textShape.getAnchor();
	 /**
	  * refactor this code  
	  */
//		    double shapeX = shapeAnchor.getX();
//		    double shapeY = shapeAnchor.getY();
//		    double shapeRotation = 0.0; // default rotation value
//		    if (textShape.getTextRotation() != null) {
//		        shapeRotation = textShape.getTextRotation();
//		    }
//
//	        for (XSLFTextParagraph paragraph : textShape) {
//	            for (XSLFTextRun textRun : paragraph) {
//	                String text = textRun.getRawText();
//	                String font = textRun.getFontFamily();
//	                double fontSize = textRun.getFontSize();
//		            // Get the offset of the text run
//		            double textRunOffset = textRun.getParagraph().getTextRuns().get(0).getCharacterSpacing();
//		            
//		            // Calculate the absolute x-coordinate of the text run
//		            double textRunX = shapeX + textRunOffset;
//		         // Get text width
//		            Font awtFont = new Font(font, Font.PLAIN, (int)fontSize);
//		            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
//		            Graphics2D g2d = img.createGraphics();
//		            g2d.setFont(awtFont);
//		            FontMetrics fm = g2d.getFontMetrics();
//		            int textWidth = fm.stringWidth(text);
//		            g2d.dispose();
//	                if (!text.equals("\n")) {
//	                    Map<String, Object> data = new HashMap<>();
//	                    data.put("text", text);
//	                    data.put("font", font);
//	                    data.put("fontSize", fontSize);
//	                    data.put("textWidth", textWidth);
//	                    data.put("x", textRunX);
//	                    data.put("y", shapeY);
//	                    data.put("rotation", shapeRotation);
//	                    dataList.add(data);
//	                    logger.debug("Added data: " + data);
//	                }
//	            }
//	        }
//	        logger.debug("Text shapes extracted successfully.");
//	    }
	 
	 private void readTextShape(XSLFTextShape textShape, List<Map<String, Object>> dataList) {
		    Rectangle2D shapeAnchor = textShape.getAnchor();
		    double shapeX = shapeAnchor.getX();
		    double shapeY = shapeAnchor.getY();
		    double shapeRotation = getShapeRotation(textShape);
		    for (XSLFTextParagraph paragraph : textShape) {
		        for (XSLFTextRun textRun : paragraph) {
		            if (!textRun.getRawText().equals("\n")) {
		                Map<String, Object> data = createDataMap(textRun, shapeX, shapeY, shapeRotation);
		                dataList.add(data);
		                logger.debug("Added data: " + data);
		            }
		        }
		    }
		    logger.debug("Text shapes extracted successfully.");
		}

		private double getShapeRotation(XSLFTextShape textShape) {
		    Double rotation = textShape.getTextRotation();
		    return rotation != null ? rotation : 0.0;
		}
	
		private List<Map<String, Object>> extractEllipseShapes(XSLFSlide slide) {
		    List<Map<String, Object>> dataList = new ArrayList<>();

		    for (XSLFShape shape : slide.getShapes()) {

		    	System.out.println(shape.getShapeName());

		        if (shape instanceof XSLFAutoShape && shape.getShapeName().equals("Isosceles Triangle 3") || shape.getShapeName().equals("Cylinder 2")) {
		            Rectangle2D shapeAnchor = shape.getAnchor();
		            double shapeX = shapeAnchor.getX();
		            double shapeY = shapeAnchor.getY();
		            double shapeWidth = shapeAnchor.getWidth();
		            double shapeHeight = shapeAnchor.getHeight();

		            Map<String, Object> data = new HashMap<>();
		            data.put("shape name", shape.getShapeName());
		            data.put("type", "Ellipse");
		            data.put("x", shapeX);
		            data.put("y", shapeY);
		            data.put("width", shapeWidth);
		            data.put("height", shapeHeight);

		            dataList.add(data);
		        }
		    }

		    return dataList;
		}

//		public List<Map<String, Object>> extractTextboxes(XSLFSlide slide) {
//		    List<Map<String, Object>> dataList = new ArrayList<>();
//
//		    for (XSLFShape shape : slide.getShapes()) {
//		    	System.out.println(shape.getShapeName());
//		         if (shape instanceof XSLFTextBox) {
//		            XSLFTextBox textBox = (XSLFTextBox) shape;
//		            String content = textBox.getText();
//		            Rectangle2D anchor = textBox.getAnchor();
//		            double x = anchor.getX();
//		            double y = anchor.getY();
//		            double width = anchor.getWidth();
//		            double height = anchor.getHeight();
//		            Map<String, Object> data = new HashMap<>();
//		            data.put("x", x);
//		            data.put("y", y);
//		            data.put("width", width);
//		            data.put("height", height);
//		            data.put("content", content);
//		            dataList.add(data);
//		            logger.debug("Added data: " + data);
//		        }
//		    }
//
//		    logger.debug("Textboxes extracted successfully.");
//		    return dataList;
//		}

		public List<Map<String, Object>> extractTextboxes(XSLFSlide slide) {
		    List<Map<String, Object>> dataList = new ArrayList<>();

		    for (XSLFShape shape : slide.getShapes()) {
		        if (shape instanceof XSLFTextBox) {
		            XSLFTextBox textBox = (XSLFTextBox) shape;
		            String content = textBox.getText();
		            double rotation = textBox.getRotation();
		            if(!content.isEmpty()) {
		            Rectangle2D anchor = textBox.getAnchor();
		            double x = anchor.getX();
		            double y = anchor.getY();
		            double width = anchor.getWidth();
		            double height = anchor.getHeight();
		            Map<String, Object> data = new HashMap<>();
		            data.put("x", x);
		            data.put("y", y);
		            data.put("width", width);
		            data.put("height", height);
		            data.put("content", content);
		            data.put("rotation", rotation);

		            // Get font information
		            XSLFTextParagraph paragraph = textBox.getTextParagraphs().get(0);
		            XSLFTextRun textRun = paragraph.getTextRuns().get(0);
		            String fontName = textRun.getFontFamily();
		            double fontSize = textRun.getFontSize();
		            boolean bold = textRun.isBold();
		            boolean italic = textRun.isItalic();
		            boolean underline = textRun.isUnderlined();
		            boolean strike = textRun.isStrikethrough();
		            
		            // Add font information to data map
		            data.put("fontName", fontName);
		            data.put("fontSize", fontSize);
		            data.put("bold", bold);
		            data.put("italic", italic);
		            data.put("underline", underline);
		            data.put("strike", strike);

		            dataList.add(data);
		            logger.debug("Added data: " + data);
		            }
		        }
		    }

		    logger.debug("Textboxes extracted successfully.");
		    return dataList;
		}



		private Map<String, Object> createDataMap(XSLFTextRun textRun, double shapeX, double shapeY, double shapeRotation) {
		    String text = textRun.getRawText();
		    String font = textRun.getFontFamily();
		    double fontSize = textRun.getFontSize();
		    double textRunOffset = textRun.getParagraph().getTextRuns().get(0).getCharacterSpacing();
		    double textRunX = shapeX + textRunOffset;
		    Font awtFont = new Font(font, Font.PLAIN, (int)fontSize);
		    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2d = img.createGraphics();
		    g2d.setFont(awtFont);
		    FontMetrics fm = g2d.getFontMetrics();
		    int textWidth = fm.stringWidth(text);
		    g2d.dispose();
		    Map<String, Object> data = new HashMap<>();
		    data.put("text", text);
		    data.put("font", font);
		    data.put("fontSize", fontSize);
		    data.put("textWidth", textWidth);
		    data.put("x", textRunX);
		    data.put("y", shapeY);
		    data.put("rotation", shapeRotation);
		    return data;
		}


}
