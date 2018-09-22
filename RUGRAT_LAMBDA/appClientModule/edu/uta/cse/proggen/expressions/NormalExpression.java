package edu.uta.cse.proggen.expressions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.uta.cse.proggen.classLevelElements.Field;
import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Expression;

/**
 * normalExpr:= operand binOp operand | operand | methodCall
 *
 * @author Ishtiaque_Hussain
 *
 */
public class NormalExpression 
	extends Expression 
{
	private Expression subExpr = null;

	public NormalExpression(Method method, Primitives primitive) 
	{
		Set<Primitives> fieldPrimitivesList = getFieldPrimitiveList(method.getAssociatedClass().getFields(), method.isStatic());
		
		if(fieldPrimitivesList.contains(primitive))
		{
			Random rand = new Random();
			int option = rand.nextInt(100)%5;
			switch (option) 
			{
				case 4:
					subExpr = new FieldBinOpField(method, primitive);
					break;
				
				default:
					subExpr = new VariableBinOpVariable(method, primitive);
					break;
			}
		}
		else
		{
			subExpr = new VariableBinOpVariable(method, primitive);
		}
	}
	
	private HashSet<Primitives> getFieldPrimitiveList(ArrayList<Field> fieldList, boolean isStatic)
	{
		HashSet<Primitives> primitivesSet = new HashSet<Primitives>();
		for(Field field : fieldList)
		{
			if(field.isStatic() == isStatic)
			{
				primitivesSet.add(field.getType().getType());
			}
		}
		return primitivesSet;
	}

	public String toString() 
	{
		return subExpr.toString();
	}
}
