package edu.uta.cse.proggen.nodes;

/**
 * Represents an operand node in a stochastic parse tree.
 * 
 * @author balamurugan
 *
 */
public class Operand 
	extends Expression
{
	protected String literal = "";
	
	public String toString()
	{
		return literal;
	}
}
