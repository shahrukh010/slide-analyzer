package com.paperflite.slideanalyzer.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paperflite.slideanalyzer.services.impl.HtmlObject;

public class SaveToJSON {

    private static final Logger logger = LogManager.getLogger(SaveToJSON.class);

	private File file;

    public SaveToJSON(File file) {
        this.file = file;
    }
    
   

//    public void saveToJSON(List<Map<String, Object>> dataList) {
//        if (!dataList.isEmpty()) {
//        	String htmlContent = null;
//        	 for (Map<String, Object> data : dataList) {
//                 htmlContent = (String) data.get("htmlContent");
//        	 }
//        	HtmlObject htmlObj = new HtmlObject(htmlContent);
//
////            Gson gson = new GsonBuilder().setPrettyPrinting().create();
////           String json = gson.toJson(dataList);
//
//        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        	String json = gson.toJson(htmlObj);
//        	System.out.println(json);
//            logger.info("JSON: " + json);
//
//            logger.info("File exists: " + file.exists());
//
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//                writer.write(json);
//                writer.flush();
//                logger.info("Data saved successfully to " + file.getName());
//
//            } catch (IOException e) {
//                logger.error("Error saving data to " + file.getName(), e);
//
//            }
//        }
//        else {
//            logger.warn("No data to save.");
//        }
//    }
    
//    public void saveToJSON(List<Map<String, Object>> dataList) {
//        if (!dataList.isEmpty()) {
//            String htmlContent = null;
//            for (Map<String, Object> data : dataList) {
//                htmlContent = (String) data.get("htmlContent");
//            }
//            HtmlObject htmlObj = new HtmlObject(htmlContent);
//
//            Gson gson = new GsonBuilder()
//                    .registerTypeAdapter(HtmlObject.class, new HtmlObjectTypeAdapter())
//                    .setPrettyPrinting()
//                    .create();
//            String json = gson.toJson(htmlObj);
//            System.out.println(json);
//            logger.info("JSON: " + json);
//
//            logger.info("File exists: " + file.exists());
//
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//                writer.write(json);
//                writer.flush();
//                logger.info("Data saved successfully to " + file.getName());
//
//            } catch (IOException e) {
//                logger.error("Error saving data to " + file.getName(), e);
//
//            }
//        } else {
//            logger.warn("No data to save.");
//        }
//    }

    /*
    @SuppressWarnings("deprecation")
	public void saveToJSON(List<Map<String, Object>> dataList) {
        if (!dataList.isEmpty()) {
            String htmlContent = null;
            for (Map<String, Object> data : dataList) {
                htmlContent = (String) data.get("htmlContent");
            }
            HtmlObject htmlObj = new HtmlObject(htmlContent);
            htmlContent = StringEscapeUtils.unescapeHtml4(htmlContent);
            htmlObj.setHtmlContent(htmlContent);
           // Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Gson gson = new GsonBuilder().registerTypeAdapter(HtmlObject.class, new HtmlObjectSerializer())
                    .setPrettyPrinting().create();
            String json = gson.toJson(htmlObj);
            String encodedHtml = "\u003ch6 style\u003d\"font-size:18.0px;\"\u003e\u003cspan style\u003d\"font-size:18.0px;font-family:\u0027Trade Gothic Next Light\u0027;font-weight:400;color:rgb(0,0,0);background-color:transparent;font-style:normal;text-decoration:none solid rgb(0,0,0);\"\u003eText Box\u003cbr\u003eShahrukh@paperflite.com\u003cbr\u003e\u003c/span\u003e\u003c/h6\u003e";
            String decodedHtml = StringEscapeUtils.unescapeJava(encodedHtml);
            System.out.println(decodedHtml);
            System.out.println(json);
            logger.info("JSON: " + json);
            logger.info("File exists: " + file.exists());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(json);
                writer.flush();
                logger.info("Data saved successfully to " + file.getName());
            } catch (IOException e) {
                logger.error("Error saving data to " + file.getName(), e);
            }
        } else {
            logger.warn("No data to save.");
        }
    }
    */

    public  void saveToJSON(List<Map<String, Object>> dataList, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        for (Map<String, Object> data : dataList) {
            if (data.containsKey("htmlContent")) {
                String htmlContent = (String) data.get("htmlContent");
          //      String decodedHtml = StringEscapeUtils.unescapeHtml4(htmlContent);
//                data.put("decodedHtml", decodedHtml);
            }
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(new FileWriter(filePath), dataList);
        String jsonContent = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataList);
        logger.info("Content of JSON file {}: {}", filePath, jsonContent);
        logger.info("Data saved to JSON file: {}", filePath);
    }
}
