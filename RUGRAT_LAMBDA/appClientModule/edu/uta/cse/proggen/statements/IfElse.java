package edu.uta.cse.proggen.statements;

import java.util.ArrayList;
import java.util.Set;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.expressions.BooleanExpression;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * It's if(e){
 * 		...stmt
 * 		}else {stmt..}
 * @author Ishtiaque_Hussain
 *
 */
public class IfElse extends Statement {

	private BooleanExpression boolExpr = null;
	private Statement thenStmt = null;
	private Statement elseStmt =  null;
	private Method method;

	public IfElse(Method method,
			ArrayList<ClassGenerator> classList)
	{
		this.method = method;
		method.setLoc(method.getLoc()+4);
		// 'if' and 'else' themselves contribute 4 lines in the loc
		Set<Primitives> primitiveSet = ProgGenUtil.getPrimitivesOfVariables(method);
		boolExpr = new BooleanExpression(method, ProgGenUtil.getRandomizedPrimitiveForBooleanExpression(primitiveSet));
		thenStmt = Statement.getRandomizedStatement(method, classList);
		elseStmt = Statement.getRandomizedStatement(method, classList);
	}

	public String toString()
	{
		if(boolExpr.toString() == null)
		{
			//failed to construct a variable based expression
			return new PrintStatement(method).toString();
		}
		String output = "if"+ "(" + boolExpr.toString() + "){"+"\n";
		output = output + thenStmt.toString()+"}\n"+ "else{\n " + elseStmt.toString()+"}\n" ;
		return output;
	}
}
