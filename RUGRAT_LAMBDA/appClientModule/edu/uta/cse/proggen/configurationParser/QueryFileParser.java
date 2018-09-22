package edu.uta.cse.proggen.configurationParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.uta.cse.proggen.classLevelElements.Type;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;

/**
 * Parser class for the query file XML.
 * 
 * @author balamurugan
 *
 */
public class QueryFileParser 
{
	private static Document document = null;
	public 	static ArrayList<Query> queries = new ArrayList<Query>();
	
	/*
	 * parse and construct query objects in memory as soon as this class is loaded
	 * in the JVM.
	 */
	static
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(ConfigurationXMLParser.getProperty("queryFilename")));
			parseQueryNodes();
		}
		catch(Exception e)
		{
			System.out.println("Error parsing query XML. Continuing...");
			e.printStackTrace();
		}
	}
	
	private static Node getRootNode()
	{
		return document.getDocumentElement();
	}
	
	public static void parseQueryNodes()
	{
		Node root = getRootNode();
		NodeList queryNodes = root.getChildNodes();
		
		int noOfQueryNodes = queryNodes.getLength();
		
		for(int i=0; i<noOfQueryNodes; i++)
		{
			Node node = queryNodes.item(i);
			if(node instanceof Element)
			{
				Element queryNode = (Element)node;
				String queryString = queryNode.getAttribute("value");
								
				NodeList resultNodes = queryNode.getChildNodes();
				int noOfResultNodes = resultNodes.getLength();
				String name	= "";
				int seqNumber = -1;
				Primitives type;
				
				ArrayList<QueryResult> results = new ArrayList<QueryResult>();
				HashSet<Primitives> typeSet = new HashSet<Primitives>();
				for(int j=0; j<noOfResultNodes; j++)
				{
					Node resultNode = resultNodes.item(j);
					if(resultNode instanceof Element)
					{
						Element resultElement = (Element) resultNode;
						name = resultElement.getAttribute("Name");
						seqNumber = Integer.valueOf(resultElement.getAttribute("SeqNumber"));
						type = Type.reverseLookup(resultElement.getAttribute("Type"));
					
						typeSet.add(type);
						QueryResult queryResult = new QueryResult(name, seqNumber, type);
						results.add(queryResult);
					}
				}
				Query query = new Query(queryString, results, typeSet);
				queries.add(query);
			}
		}
	}
}
