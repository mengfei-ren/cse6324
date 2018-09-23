package edu.uta.cse.proggen.expressions;

import java.util.ArrayList;
import java.util.List;

import edu.uta.cse.proggen.classLevelElements.Field;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Expression;

public class LambdaExpression extends Expression{

	private List<Field> variableList = null;
	private int numberOfParams = 0;
	private String output="";

	public LambdaExpression(Field var1, Field var2) {
		variableList = new ArrayList<Field>();
		if (null != var1 && null != var2) {
			variableList.add(var1);
			variableList.add(var2);
		} else if (null == var1 && null == var2) {
			return;
		} else {
			Field v = (null == var1) ? var2 : var1;
			variableList.add(v);
		}
		numberOfParams = variableList.size();

	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		
		switch (numberOfParams) {
		case 0:
			str.append("()->");
			str.append("System.out.println(\"No argument expression\");");
			break;
		case 1:
			
			str.append("(");
			Primitives primitive = variableList.get(0).getType().getType();
			str.append(variableList.get(0).toString());
			str.append(")->");
			str.append("System.out.println(\"Value=");
			str.append("\"+");
			str.append(variableList.get(0).toString());
			if(primitive == Primitives.OBJECT ||primitive == Primitives.STRING) {
				str.append(".toString()");
			}
			str.append(");");

			break;
		case 2:
			Primitives primitive1 = variableList.get(0).getType().getType();
			Primitives primitive2 = variableList.get(1).getType().getType();

			str.append("(");
			str.append(variableList.get(0).toString());
			str.append(",");
			str.append(variableList.get(0).toString());
			str.append(")->");
			str.append("System.out.println(\"Values = v1 :");
			str.append("\"+");
			str.append(variableList.get(0).toString());
			if(primitive1 == Primitives.OBJECT ||primitive1 == Primitives.STRING) {
				str.append(".toString()");
			}
			str.append("+\", v2:");
			str.append("\"+");
			str.append(variableList.get(1).toString());
			if(primitive2 == Primitives.OBJECT ||primitive2 == Primitives.STRING) {
				str.append(".toString()");
			}
			str.append(");");

			break;

		}
		output=str.toString();
		System.out.println("created lambda expression :" + output);
		return output;
	}

}
