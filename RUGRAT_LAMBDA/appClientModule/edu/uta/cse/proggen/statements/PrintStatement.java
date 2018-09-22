package edu.uta.cse.proggen.statements;

import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Method;

/**
 * Creates a print statement to be appended to the method body.
 * 
 * @author balamurugan
 *
 */
public class PrintStatement 
{
	private Method method;
	
	public PrintStatement(Method method)
	{
		this.method = method;
	}
	
	public String toString()
	{
		String stmt = "";
		Random rand = new Random();
		int option = rand.nextInt(5);

		for (int i = 0; i <= option; i++) 
		{
			stmt = "System.out.println(\""+ method.getAssociatedClass().getFileName()
			+ " - " + method.getName() + "- LineInMethod: " + method.getLoc() + "\");" + "\n";
		}

		method.setLoc(method.getLoc() + option + 1);
		return stmt;
	}
}
