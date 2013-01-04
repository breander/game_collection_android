package com.davenport.gamecollector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class ParsePlatform extends DefaultHandler{

	private Platform _platform;
	
	private StringBuilder tempVal;
	private String XML;
	
	public ParsePlatform(String xml){
		_platform = new Platform();
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
		tempVal = new StringBuilder();
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal.append(new String(ch,start,length));
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(qName.equalsIgnoreCase("id")) {
			_platform.id = tempVal.toString();
		}else if (qName.equalsIgnoreCase("overview")) {
			_platform.overview = tempVal.toString();
		}else if (qName.equalsIgnoreCase("Platform")) {
			if(tempVal != null)
				_platform.platform = tempVal.toString();
		}
		
	}
	
	public Platform Return()
	{
		return _platform;
	}	
}
