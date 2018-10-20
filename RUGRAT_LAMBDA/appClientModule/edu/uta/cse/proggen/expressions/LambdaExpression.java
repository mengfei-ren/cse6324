package edu.uta.cse.proggen.expressions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.classLevelElements.Variable;
import edu.uta.cse.proggen.nodes.Expression;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.packageLevelElements.FunctionalInterfaceGenerator;

public class LambdaExpression extends Expression {

	private List<Variable> variableList = null;
	private int numberOfParams = 0;
	private String output = "";
	FunctionalInterfaceGenerator functionalInterface;

	public LambdaExpression(FunctionalInterfaceGenerator functionalInterface) {
		ArrayList<Variable> abstractMethodParameterList = functionalInterface.getMethodSignatures().getParameterList();
		this.variableList = new ArrayList<Variable>();
		this.variableList = abstractMethodParameterList;
		this.numberOfParams = variableList.size();
		this.functionalInterface = functionalInterface;

	}

	public LambdaExpression() {
		super();
	}

	public String generateStandardLambdaExpressions(String typeLambdaExpressions) {
		StringBuffer str = new StringBuffer();
		
		switch (typeLambdaExpressions) {
		case "thread":

			str.append(" new Thread(()->{System.out.println(\"New thread created\");}).start(); ");
			break;
		case "predicate interface":
			str.append(" List<String> words = Arrays.asList(\"Java\",\"JavaProgram\",\"abc\",\"XYZ\",\"RUGRAT\"); \n");
			str.append(" Predicate<String> pr = (s)->s.startsWith(\"J\");\n");
			str.append("for (String word:words)\n");
			str.append("        {\n");
			str.append("            if (pr.test(word))\n");
			str.append("                System.out.println(word);\n");
			str.append("        } ");
			break;

		case "binary operator":
			str.append("BinaryOperator<Integer> sum = (n1, n2) -> n1 + n2;\n");
			str.append("System.out.println(sum.apply(" + new Random().nextInt(100) + ", " + new Random().nextInt(100)
					+ "));\n");
			break;
		default:
			str.append("\n");
		}
		return str.toString();
	}

	
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(functionalInterface.getName()+" ");
		str.append(functionalInterface.getName()+"obj = ");
		StringBuilder namesStr = new StringBuilder();
		StringBuilder valuesStr = new StringBuilder();


		if (numberOfParams == 0) {
			str.append("()->");
		} else {
			str.append("\n(");

			for (Iterator iterator = variableList.iterator(); iterator.hasNext();) {
				Variable variable = (Variable) iterator.next();

				namesStr = namesStr.length() > 0 ? namesStr.append(",").append(variable.getName())
						: namesStr.append(variable.getName());

			}
			str.append(namesStr);
			str.append(")->");

		}

		str.append("{");
		str.append("\nSystem.out.print(\"This method has " + variableList.size() + " number of arguments\");");
		if (functionalInterface.getMethodSignatures().getReturnType() != null) {
			str.append(generateReturnStatement(functionalInterface.getMethodSignatures().getReturnType()));
		}

		str.append("\n};");
		str.append("\n"+functionalInterface.getName()+"obj."+functionalInterface.getMethodSignatures().getName()+"(");
		if (numberOfParams == 0) {
			str.append(");");
		}else {
			for (int i = 0; i < numberOfParams; i++) {
				namesStr = namesStr.length() > 0 ? namesStr.append(",").append("null")
						: namesStr.append("null");
				
			}
		}

		output = str.toString();
		System.out.println("created lambda expression :" + output);
		return output;
	}
	
	/**
	 * Generates an appropriate return statement for the Method.
	 * @return 
	 */
	private String generateReturnStatement(Primitives  returntype) {
		StringBuilder builder = new StringBuilder("\nreturn ");
		builder.append("(");
		builder.append(returntype);
		builder.append(")");

		
		Operand operand;

		operand = new Literal(returntype);
		

		builder.append(operand);
		builder.append(";\n");
		return builder.toString();
	}

}
