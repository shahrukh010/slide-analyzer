package com.paperflite.slideanalyzer;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.sl.usermodel.PaintStyle;
import org.apache.poi.sl.usermodel.PaintStyle.SolidPaint;
import org.apache.poi.xslf.usermodel.*;

public class PPTXTextColorExtractor {
	
	public static List<String> getFontColor(XSLFSlide slide) {
		
         List<String> colors = new ArrayList<>();
	     // Get all text runs in the slide
        for (XSLFShape shape : slide.getShapes()) {
            if (shape instanceof XSLFTextShape) {
                XSLFTextShape textShape = (XSLFTextShape) shape;
                for (XSLFTextParagraph paragraph : textShape.getTextParagraphs()) {
                    for (XSLFTextRun run : paragraph.getTextRuns()) {
                        // Extract the text color and text content of the run
                        PaintStyle paintStyle = run.getFontColor();
                        String text = run.getRawText();
                        if(!text.equals("\n")) {
                        if (paintStyle instanceof SolidPaint) {
                            SolidPaint solidPaint = (SolidPaint) paintStyle;
                            Color color = solidPaint.getSolidColor().getColor();
                            String colorName = getColorName(color);
                            System.out.println("Text: " + text + ", Text color: " + colorName);
                            colors.add(colorName);
                        }
                        }
                    }
                }
            }
        }
        return colors;
	}
	
	
    public static void main(String[] args) throws Exception {
		String fileName = "/tmp/Presentation.pptx";
		File file = new File(fileName);

        XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(file));
        
		 for (XSLFSlide slide : pptx.getSlides()) {

			 getFontColor(slide);

	            }
        
        
        

     // Get all text runs in the slide
//        for (XSLFShape shape : slide.getShapes()) {
//            if (shape instanceof XSLFTextShape) {
//                XSLFTextShape textShape = (XSLFTextShape) shape;
//                for (XSLFTextParagraph paragraph : textShape.getTextParagraphs()) {
//                    for (XSLFTextRun run : paragraph.getTextRuns()) {
//                        // Extract the text color of the run
//                    	// Extract the text color of the run
//                    	PaintStyle paintStyle = run.getFontColor();
//                    	if (paintStyle instanceof SolidPaint) {
//                    	    SolidPaint solidPaint = (SolidPaint) paintStyle;
//                    	    Color color = solidPaint.getSolidColor().getColor();
//                    	    String colorName = getColorName(color);
//                   	    System.out.println("Text color: " + colorName);
//                    	}
//
//                    }
//                }
//            }
//        }
    }
    
 // Helper method to get the name of a color
    private static String getColorName(Color color) {
        if (color.equals(Color.BLACK)) {
            return "Black";
        } else if (color.equals(Color.WHITE)) {
            return "White";
        } else if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        } else if (color.equals(Color.BLUE)) {
            return "Blue";
        }
         else if (color.equals(Color.ORANGE)) {
            return "Orange";
        }
        else {
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            System.out.println(red+","+green+","+blue);
            if (red == 163 && green == 163 && blue == 0) {
                return "Dark Yellow";
            } else {
                return "Unknown";
            }
        }
    }
}
