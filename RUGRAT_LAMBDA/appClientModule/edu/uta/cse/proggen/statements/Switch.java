package edu.uta.cse.proggen.statements;

import java.util.ArrayList;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.expressions.VariableBinOpField;
import edu.uta.cse.proggen.expressions.VariableBinOpVariable;
import edu.uta.cse.proggen.nodes.Expression;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;


/**
 *  This creates the switch statement.
 * @author Ishtiaque_Hussain
 *
 */
public class Switch extends Statement{

	Expression operand = null;
	ArrayList<String> cases = new ArrayList<String>();

	public Switch(Method method, ArrayList<ClassGenerator> classList)
	{
		Random rand = new Random();
		//introducing fields in the switch statement
		if(rand.nextInt(2) == 0)
			operand = new VariableBinOpField(
					method, Primitives.INT);
		else
			operand = new VariableBinOpVariable(method, 
					Primitives.INT);

		method.setLoc( method.getLoc() + 2 );

		int option = rand.nextInt(100)%10; //switches will have maximum of 10 cases.

		//We don't want switch to have 0, 1 or only 2 cases
		if(option <= 2)
			option += 2;

		for(int i = 0; i < option ; i++){
			cases.add("case "+ i+ ":\n"+ Statement.getRandomizedStatement(method, classList).toString()+ " break;\n");
			method.setLoc(method.getLoc() + 2);
			//Start.loc_per_meth_counter += 2; // each case adds 2 lines 'case:' and 'break'
		}
		cases.add("default :\n"+ Statement.getRandomizedStatement(method, classList).toString());
	}


	public String toString(){
		String output = "switch(" +operand.toString()+ "){\n";
		for(int i =0; i < cases.size(); i++){
			output += cases.get(i);
		}
		output += "}\n";

		return output;
	}
}
