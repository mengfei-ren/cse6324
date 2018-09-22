package edu.uta.cse.proggen.expressions;

import java.util.ArrayList;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type;
import edu.uta.cse.proggen.classLevelElements.Variable;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.util.ProgGenUtil;
import edu.uta.cse.proggen.util.ProgGenUtil.CallType;

/**
 * Class with utility methods to fetch a variable from a generated class
 * based on various criteria.
 * 
 * @author balamurugan
 *
 */
public class VariableGenerator
{
	public static Variable getRandomizedVariable(Method method)
	{
		Variable variable;
		ArrayList<Variable> parameterList = method.getParameterList();
		if(ProgGenUtil.methodCallType == CallType.crossClassWithRecursionLimit
				|| ProgGenUtil.methodCallType == CallType.localWithRecursionLimit)
		{
			//ignore the first parameter: 'recursionCounter'
			// if 'recursionCounter' is the only parameter, return null (nextInt(0) throws exception)
			if(parameterList.size() == 1)
				return null;
			
			variable = parameterList.get(new Random().nextInt(parameterList.size()-1) + 1);
		}
		else
		{
			if(parameterList.size() == 0)
				return null;
			variable = parameterList.get(new Random().nextInt(parameterList.size()));
		}
				
		method.getUsedParameterList().add(variable); // adding to the used variable Set
		return variable;
	}
	
	public static Operand getRandomizedVariable(Method method, Primitives primitive)
	{
		Variable variable;
		variable = getVariable(method.getParameterList(), primitive);
		
		if(variable == null)
		{
			return new Literal(primitive);
		}
		
		method.getUsedParameterList().add(variable);
		return variable;
	}

	private static Variable getVariable(ArrayList<Variable> variables, Primitives primitive) 
	{
		ArrayList<Variable> typedVariableList = new ArrayList<Variable>();
		
		for(Variable var : variables)
		{
			if(var.getName().equals("recursionCounter"))
			{
				continue;
			}
			
			if(var.getType().getType() == primitive)
			{
				typedVariableList.add(var);
			}
		}
		
		if(typedVariableList.isEmpty())
		{
			return null;
		}
		
		int index = new Random().nextInt(typedVariableList.size());
		return typedVariableList.get(index);
	}

	public static Operand getRandomizedObjectForType(
			Method method, 
			Type type) 
	{
		ArrayList<Variable> variableList = method.getParameterList();
		ArrayList<Variable> typedVariableList = new ArrayList<Variable>();
		for(Variable var : variableList)
		{
			if(var.getType().equals(type))
			{
				typedVariableList.add(var);
			}
		}
		
		if(typedVariableList.size() == 0)
			return new Literal(Primitives.OBJECT);
		
		int index = new Random().nextInt(typedVariableList.size());
		return typedVariableList.get(index);
	}
}
