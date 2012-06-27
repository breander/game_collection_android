package com.davenport.gamecollector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class ParsePlatforms extends DefaultHandler{

	private List<Platform> elements;
	
	private StringBuilder tempVal;
	private String XML;
	
	//to maintain context
	private Platform element;
	
	public ParsePlatforms(String xml){
		elements = new ArrayList<Platform>();
		XML = xml;
	}

	public void parseDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			InputStream xmlStream = new ByteArrayInputStream(XML.getBytes("UTF-8"));
			sp.parse(xmlStream , this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = new StringBuilder();//"";
		if(qName.equalsIgnoreCase("platform")) {
			element = new Platform();
			//tempEmp.setType(attributes.getValue("type"));
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		//tempVal = new String(ch,start,length);
		tempVal.append(new String(ch,start,length));
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(qName.equalsIgnoreCase("platform")) {
			//add it to the list
			elements.add(element);
		}else if (qName.equalsIgnoreCase("abbreviation")) {
			element.abbreviation = tempVal.toString();
		}else if (qName.equalsIgnoreCase("api_detail_url")) {
			element.api_detail_url = tempVal.toString();
		}else if (qName.equalsIgnoreCase("company")) {
			element.company = tempVal.toString();
		}else if (qName.endsWith("name")){
			element.name = tempVal.toString();
		}else if (qName.endsWith("deck")){
			element.deck = tempVal.toString();
		}else if (qName.endsWith("description")){
			element.description = tempVal.toString();
		}else if (qName.endsWith("id")){
			element.id = tempVal.toString();
		}else if (qName.endsWith("image")){
			element.image = tempVal.toString();
		}else if (qName.endsWith("install_base")){
			element.install_base = tempVal.toString();
		}else if (qName.endsWith("online_support")){
			element.online_support = tempVal.toString();
		}else if (qName.endsWith("original_support")){
			element.original_price = tempVal.toString();
		}else if (qName.endsWith("release_date")){
			element.release_date = tempVal.toString();
		}else if (qName.endsWith("site_detail_url")){
			element.site_detail_url = tempVal.toString();
		}
	}
	
	public List<Platform> ReturnList()
	{
		return elements;
	}	
}
