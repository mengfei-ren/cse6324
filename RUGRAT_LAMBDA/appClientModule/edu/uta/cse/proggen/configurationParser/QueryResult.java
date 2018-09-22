package edu.uta.cse.proggen.configurationParser;

import edu.uta.cse.proggen.classLevelElements.Type.Primitives;

/**
 * Represents the <Result> sub-element of <Query> in the query xml.
 *  
 * @author balamurugan
 *
 */
public class QueryResult 
{
	private String 		name;
	private int 		seqNumber;
	private Primitives 	type;
	
	public QueryResult(
			String name, 
			int seqNumber, 
			Primitives type) 
	{
		super();
		this.name = name;
		this.seqNumber = seqNumber;
		this.type = type;
	}

	public String getName() 
	{
		return name;
	}

	public int getSeqNumber() 
	{
		return seqNumber;
	}

	public Primitives getType() 
	{
		return type;
	}
}
