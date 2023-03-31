package com.paperflite.slideanalyzer.services;

import java.util.List;
import java.util.Map;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

public interface Extractable {
	
	 List<Map<String,Object>> extract(XMLSlideShow ppt);

}
