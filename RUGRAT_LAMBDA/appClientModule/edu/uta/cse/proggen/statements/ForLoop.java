package edu.uta.cse.proggen.statements;

import java.util.ArrayList;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * This creates: for(int i=0; i<var; i++){ stmt..}
 * @author Ishtiaque_Hussain
 *
 */
public class ForLoop extends Statement {

	Operand 	var = null;
	Statement 	stmt = null;
	
	private String positiveInteger()
	{
		Integer integer = new Integer(new Random().nextInt(ProgGenUtil.maxLoopControllerValue));
		int val = integer.intValue();
		
		if(val < 0)
		{
			val = val * -1;
		}
		
		return val+"";
	}

	public ForLoop(Method method,
			ArrayList<ClassGenerator> classList){
		// adding 2 lines per for loop
		method.setLoc(method.getLoc() + 2);

		Random rand = new Random();
		int option = rand.nextInt(2);
		if(option == 0)
		{
			stmt = new IfStmtIfStmt(method, classList);
		}
		else
		{
			stmt = Statement.getRandomizedStatement(method, classList);

			// We don't want method calls in the for loop, allow print statements that has 'method' keyword in it
			while(stmt != null && stmt.toString().contains("method") && !stmt.toString().contains("System.out.println"))
			{
				stmt = Statement.getRandomizedStatement(method, classList);
			}
		}
	}

	public String toString(){
		String output = "";
		output  += "for(int i = 0; i < "+ positiveInteger() +"; i++){\n ";
		output += stmt.toString() + "}\n";
		return output;
	}
}
