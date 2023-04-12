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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveToJSON {

    private static final Logger logger = LogManager.getLogger(SaveToJSON.class);

	private File file;

    public SaveToJSON(File file) {
        this.file = file;
    }
    
   

    public void saveToJSON(List<Map<String, Object>> dataList) {
        if (!dataList.isEmpty()) {
        	
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(dataList);
            logger.info("JSON: " + json);

            logger.info("File exists: " + file.exists());


            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(json);
                writer.flush();
                logger.info("Data saved successfully to " + file.getName());

            } catch (IOException e) {
                logger.error("Error saving data to " + file.getName(), e);

            }
        }
        else {
            logger.warn("No data to save.");
        }
    }
}
