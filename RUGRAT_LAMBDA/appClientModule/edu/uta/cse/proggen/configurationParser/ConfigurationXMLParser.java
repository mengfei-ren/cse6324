package edu.uta.cse.proggen.configurationParser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.uta.cse.proggen.start.Start;

/**
 * Parser class to get information from config.xml.
 *  * 
 * @author balamurugan
 *
 */

/*
 * Parses config.xml and puts all the key-value pairs in 'properties
 * HashMap. For AllowedTypes, puts the key-value pairs in 'typeList' LinkedHashSet.
 * It has also public methods to access these maps. 
 */
public class ConfigurationXMLParser 
{
	
	private static Document document = null;
	private static HashMap<String, String> properties = new HashMap<String, String>();
	private static LinkedHashSet<String> typeList = new LinkedHashSet<String>();
	
	//read information from the XML as soon as the class is loaded into the JVM
	static
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try 
		{
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(new File(Start.getPathToDir() + "config.xml"));
			try
			{
				parseProperties();
			}
			catch(Exception e)
			{
				System.out.println("Error parsing properties from XML!");
				e.printStackTrace();
				System.exit(1);
			}
		} 
		catch (ParserConfigurationException e) 
		{
			System.out.println("error parsing XML configuration!");
			e.printStackTrace();
			System.exit(1);
		} 
		catch (SAXException e) 
		{
			System.out.println("error parsing XML configuration!");
			e.printStackTrace();
			System.exit(1);
		} 
		catch (IOException e) 
		{
			System.out.println("error processing XML configuration file!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static Node getRootNode()
	{
		return document.getDocumentElement();
	}
	
	public static void parseProperties()
		throws Exception
	{
		Node root = getRootNode();
		NodeList propertyNodes = root.getChildNodes();
		
		int numberOfPropertyNodes = propertyNodes.getLength();
		
		for(int i=0; i<numberOfPropertyNodes; i++)
		{
			Node node = propertyNodes.item(i);
			String name = node.getNodeName();
			if(name.equals("allowedTypes"))
			{
				parseAllowedTypes(node);
				continue;
			}
			String value = node.getTextContent();			
			properties.put(name, value);
		}
	}
	
	private static void parseAllowedTypes(Node node)
		throws Exception
	{
		if(!node.getNodeName().equals("allowedTypes"))
		{
			throw new Exception("Invalid node allowedTypes");
		}
		
		NodeList typeNodes = node.getChildNodes();
		int noOfTypes = typeNodes.getLength();
		
		for(int i=0; i < noOfTypes; i++)
		{
			String str = typeNodes.item(i).getTextContent().trim();
			if(str.equals(""))
			{
				continue;
			}
			typeList.add(str);
		}
	}
	
	public static LinkedHashSet<String> getTypeList()
	{
		return typeList;
	}

	public static String getProperty(String property)
	{
		return properties.get(property);
	}
	
	/**
	 * returns an int value or -1 in case of error
	 * 
	 * @param property - to be fetched
	 * @return int value
	 */
	public static int getPropertyAsInt(String property)
	{
		try
		{
			return Integer.parseInt(getProperty(property));
		}
		catch (NumberFormatException e) 
		{
			System.out.println("Error converting value to int for property : " + property  );
			System.exit(1);
		}
		return -1;
	}

}
