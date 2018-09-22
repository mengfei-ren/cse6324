package edu.uta.cse.proggen.statements;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.expressions.BooleanExpression;
import edu.uta.cse.proggen.nodes.Expression;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * Creates :
 * if(cond1){
 *     stmt
 *     stmt
 *     ...
 *     if(cond2){
 *       stmt
 *       stmt
 *       ....
 *     }
 *  }
 * @author Ishtiaque_Hussain
 *
 */
public class IfStmtIfStmt extends Statement {

	private Expression cond = null;
	private String output = "";
	private String body = "";
	private Method method;
	

	public IfStmtIfStmt(Method method, ArrayList<ClassGenerator> classList)
	{
		this.method = method;
		
		Set<Primitives> primitiveSet = ProgGenUtil.getPrimitivesOfVariables(method);
		cond = new BooleanExpression(method, ProgGenUtil.getRandomizedPrimitiveForBooleanExpression(primitiveSet));

		//adding two extra lines for each nested loop
		method.setLoc(method.getLoc()+2);

		Random rand = new Random();
		int option = rand.nextInt(100);
		//We want more nested if's but not more than the MAX.
		if((option < method.getAssociatedClass().getPercent() + 40) && (method.getNestedIfCounter() < method.getMaxNestedIfs()))
		{
			// counts the nested ifs
			method.setNestedIfCounter(method.getNestedIfCounter()+1);
			//Start.nestedIf_counter++; 
			body += new IfStmtIfStmt(method, classList).toString();
		}
		else
		{
			body = Statement.getRandomizedStatement(method, classList).toString();
		}
	}

	public String toString()
	{
		if(cond.toString() == null)
		{
			//unable to construct a variable based expression
			return new PrintStatement(method).toString();
		}
		
		output = "if( "+ cond.toString()+ "){\n";
		output += body + "}\n";
		return output;
	}
}
