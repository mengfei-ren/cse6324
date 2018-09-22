package edu.uta.cse.proggen.configurationParser;

import java.util.ArrayList;
import java.util.Set;

import edu.uta.cse.proggen.classLevelElements.Type.Primitives;

/**
 * This class represents a <query> element in the QueryFile fed into
 * RUGRAT.
 * 
 * @author balamurugan
 *
 */
public class Query 
{
	private String 					queryString;
	private ArrayList<QueryResult>	results;
	private Set<Primitives>			resultTypes;
	
	public Query(String queryString, 
			ArrayList<QueryResult> results,
			Set<Primitives> resultTypes) 
	{
		super();
		this.queryString = queryString;
		this.results = results;
		this.resultTypes = resultTypes;
	}
	
	public String getQueryString() 
	{
		return queryString;
	}

	public ArrayList<QueryResult> getResults() 
	{
		return results;
	}

	public Set<Primitives> getResultTypes() 
	{
		return resultTypes;
	}
}
