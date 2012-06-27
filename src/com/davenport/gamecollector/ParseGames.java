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

public class ParseGames extends DefaultHandler {
private List<game> elements;
	
	private StringBuilder tempVal;
	private String XML;
	
	//to maintain context
	private game element;
	
	public ParseGames(String xml){
		elements = new ArrayList<game>();
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
		if(qName.equalsIgnoreCase("game")) {
			element = new game();
			//tempEmp.setType(attributes.getValue("type"));
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		//tempVal = new String(ch,start,length);
		tempVal.append(new String(ch,start,length));
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(qName.equalsIgnoreCase("game")) {
			//add it to the list
			elements.add(element);
		}else if (qName.equalsIgnoreCase("name")) {
			element.name = tempVal.toString();
		}
	}
	
	public List<game> ReturnList()
	{
		return elements;
	}	
}
