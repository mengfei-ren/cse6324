package edu.uta.cse.proggen.classLevelElements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.expressions.FieldGenerator;
import edu.uta.cse.proggen.expressions.Literal;
import edu.uta.cse.proggen.expressions.VariableGenerator;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;
import edu.uta.cse.proggen.statements.ForLoop;
import edu.uta.cse.proggen.statements.IfElse;
import edu.uta.cse.proggen.statements.IfStmtIfStmt;
import edu.uta.cse.proggen.statements.Switch;
import edu.uta.cse.proggen.util.ProgGenUtil;
import edu.uta.cse.proggen.util.ProgGenUtil.CallType;

/**
 * This class represents a method in the generated class.
 * 
 * @author balamurugan
 *
 */
public class Method 
{
	private boolean 					isStatic;
	private int 						numberOfParams;
	private ClassGenerator				associatedClass;
	private	ArrayList<ClassGenerator> 	classList;
	private String						name;
	private	int							loc;
	private int							locPerMethod;
	private	Primitives 					returnType 	= ProgGenUtil.getRandomizedPrimitive();
	
	private	ArrayList<Variable>			parameterList = new ArrayList<Variable>();
	private HashSet<Variable>  			usedParameterList = new HashSet<Variable>();
	
	private String						methodBody;
	private	String						returnStatement;
	private int							maxNestedIfs;
	private int							nestedIfCounter;
	private int							maxAllowedMethodCalls;
	private int							methodCallCounter;
	private MethodSignature				methodSignature;
	private	HashSet<MethodSignature>   	calledMethods = new HashSet<MethodSignature>();
	private HashSet<String>				calledMethodsWithClassName = new HashSet<String>();
	
	
	
	/**
	 * Private constructor to create the method.
	 * 
	 * @param signature : signature of the method to be constructed
	 * @param associatedClass : class to which the method belongs
	 * @param classList : list of generated types
	 * @param locPerMethod : lines of code for this method
	 * @param maxNestedIfs : upper limit on the nested ifs.
	 * @param maxAllowedMethodCalls : upper limit on the method calls.
	 */
	private Method( MethodSignature signature, 
			ClassGenerator associatedClass,
			ArrayList<ClassGenerator> classList,
			int locPerMethod,
			int maxNestedIfs,
			int maxAllowedMethodCalls )
	{
		System.out.println("Constructing method..." + signature.getName());
		this.isStatic = signature.isStatic();
		this.numberOfParams = signature.getParameterList().size();
		this.associatedClass = associatedClass;
		this.classList = classList;
		this.name = signature.getName();
		this.loc = 0;
		this.nestedIfCounter = 0;
		this.locPerMethod = locPerMethod;
		this.maxNestedIfs = maxNestedIfs;
		this.maxAllowedMethodCalls = maxAllowedMethodCalls;
		this.methodCallCounter = 0;
		
		this.methodSignature = signature;
		this.parameterList = signature.getParameterList();
		this.returnType = signature.getReturnType();
		generateMethodBody();
		generateReturnStatement();
	}
	
	/**
	 * Static API to get a generated method.
	 * 
	 * @param signature
	 * @param associatedClass
	 * @param classList
	 * @param loc
	 * @param maxNestedIfs
	 * @param maxMethodCalls
	 * @return
	 */
	public static Method getMethod(MethodSignature signature, 
			ClassGenerator associatedClass,
			ArrayList<ClassGenerator> classList,
			int loc,
			int maxNestedIfs,
			int maxMethodCalls)
	{
		 return new Method(signature,
				associatedClass,
				classList,
				loc,
				maxNestedIfs,
				maxMethodCalls);
	}
		
	public Primitives getReturnType() 
	{
		return returnType;
	}

	/**
	 * Constructor that creates a method with/without
	 * generating the method body based on the generateMethodBody
	 * parameter.
	 * 
	 * @param isStatic
	 * @param numberOfParams
	 * @param associatedClass
	 * @param classList
	 * @param name
	 * @param locPerMethod
	 * @param maxNestedIfs
	 * @param maxAllowedMethodCalls
	 * @param generateMethodBody
	 */
	private Method(boolean isStatic, 
			int numberOfParams, 
			ClassGenerator associatedClass,
			ArrayList<ClassGenerator> classList,
			String name,
			int locPerMethod,
			int maxNestedIfs,
			int maxAllowedMethodCalls,
			boolean generateMethodBody)
	{
		System.out.println("Constructing method..." + name);
		this.isStatic = isStatic;
		this.numberOfParams = numberOfParams;
		this.associatedClass = associatedClass;
		this.classList = classList;
		this.name = name;
		this.loc = 0;
		this.nestedIfCounter = 0;
		this.locPerMethod = locPerMethod;
		this.maxNestedIfs = maxNestedIfs;
		this.maxAllowedMethodCalls = maxAllowedMethodCalls;
		this.methodCallCounter = 0;
		
		generateParams();
		
		this.methodSignature = new MethodSignature(this.isStatic, this.returnType, 
				this.name, this.parameterList);
		
		if(generateMethodBody)
		{
			generateMethodBody();
			generateReturnStatement();
		}
	}
	
	/**
	 * Generates an appropriate return statement for the Method.
	 */
	private void generateReturnStatement() 
	{
		StringBuilder builder = new StringBuilder("return ");
		builder.append("(");
		builder.append(this.returnType);
		builder.append(")");
		
		int choiceVarOrLiteral = new Random().nextInt(1);
		Operand operand;
		
		if(choiceVarOrLiteral == 0)
		{
			operand = VariableGenerator.getRandomizedVariable(this, this.returnType);
		}
		else
		{
			operand = new Literal(this.returnType);
		}
		
		builder.append(operand);
		builder.append(";\n");
		this.returnStatement = builder.toString();
	}

	private void generateMethodBody()
	{
		String output = "";
		Random rand = new Random();
		
		if(loc < locPerMethod)
		{
			//for the case of indirect recursion.
			//this helps to stop the chain of method calls within
			//the configured limit.
			if(ProgGenUtil.methodCallType == CallType.crossClassWithRecursionLimit
					|| ProgGenUtil.methodCallType == CallType.localWithRecursionLimit)
			{
				output += generateBaseCaseForRecursion();
			}
		
			if(rand.nextInt(100) < ProgGenUtil.recursionProbability)
			{
				loc += 1;
				//method to include recursive calls.
				output += getRecursiveMethodCall();
			}
		
			//introduce a instance of the current class
			if(ProgGenUtil.methodCallType == CallType.crossClassWithoutRecursionLimit
					|| ProgGenUtil.methodCallType == CallType.crossClassWithRecursionLimit)
			{
				String classname = getAssociatedClass().getFileName(); 
				output += classname + " classObj = new "
				               + classname + "();\n";
				loc += 1;
			}
		}
		
		while (loc < locPerMethod ) 
		{
				int option = 0;
				if(ProgGenUtil.getAllowedTypes().contains("int"))
				{
					if(ProgGenUtil.getValidPrimitivesInScope(this).contains(Primitives.INT))
					{
						option = rand.nextInt(4);
					}
					else
					{
						//can contain for-loops but not switch statements
						option = rand.nextInt(3);
					}
				}
				else
				{
					option = rand.nextInt(2);
				}
				
				switch (option)
				{
					case 0:
						output += new IfStmtIfStmt(this, classList).toString();
						break;
					case 1:
						output += new IfElse(this, classList).toString();
						break;
						/*
						 * cases after this not valid if INT is not 
						 * a user-specified type.
						 */
					case 2:
						output += new ForLoop(this, classList).toString();
						break;
					case 3:
						output += new Switch(this, classList).toString();
						break;
				}
			}
			this.methodBody = output;
	}
	
	private String generateBaseCaseForRecursion() 
	{
		//generate a return statement for the base case
		generateReturnStatement();
		
		StringBuilder builder = new StringBuilder(
				"if(! (recursionCounter > 0 && recursionCounter < " + ProgGenUtil.maxRecursionDepth + ") )");
		builder.append("\n{\n");
		builder.append(this.returnStatement);
		builder.append("\n}\n");
		builder.append("else\n{\n recursionCounter--; \n}\n");
		//add 4 to lines of count.
		loc += 4;
		return builder.toString();
	}
	
	private String getRecursiveMethodCall()
	{
		System.out.println("Inside recursive method call for : " + methodSignature);
		String parameters = "(";
		
		for(Variable var : parameterList)
		{
			if(var.getName().equals("recursionCounter"))
			{
				parameters += "recursionCounter,";
				continue;
			}
			
			Operand operand;
			Primitives primitive = var.getType().getType();
			int optionVariableOrField = new Random().nextInt(1);
			if(optionVariableOrField == 0)
			{
				if(primitive == Primitives.OBJECT)
				{
					operand = VariableGenerator.getRandomizedObjectForType(this, var.getType());
				}
				else
				{
					operand = VariableGenerator.getRandomizedVariable(this, primitive);
				}
			}
			else
			{
				if(primitive == Primitives.OBJECT)
				{
					operand = FieldGenerator.getRandomizedObjectForType(this.getAssociatedClass(), var.getType());
				}
				else
				{
					operand = FieldGenerator.getRandomField(this.getAssociatedClass(), primitive, this.isStatic());
				}
			}
			parameters += operand + ",";
		}

		parameters = parameters.substring(0, parameters.length()-1);
		parameters += ")";
		
		return this.getName() + parameters + ";\n\n";
	}

	public HashSet<String> getCalledMethodsWithClassName() {
		return calledMethodsWithClassName;
	}

	public ArrayList<Variable> getParameterList() 
	{
		return parameterList;
	}
	
	public ClassGenerator getAssociatedClass() 
	{
		return associatedClass;
	}

	public HashSet<Variable> getUsedParameterList() 
	{
		return usedParameterList;
	}

	/**
	 * Generate randomized parameters for this method.
	 */
	private void generateParams()
	{
		if(ProgGenUtil.methodCallType == CallType.crossClassWithRecursionLimit
				|| ProgGenUtil.methodCallType == CallType.localWithRecursionLimit)
		{
			parameterList.add(Variable.generateRecursionCounterVariable());
			for(int i = 1; i<numberOfParams; i++)
			{	//classList is passed because: if Variable's type is Object, then to select type
				parameterList.add(Variable.generateVariable("var"+i, classList));
			}
		}
		else
		{
			for(int i = 0; i<numberOfParams; i++)
			{
				parameterList.add(Variable.generateVariable("var"+i, classList));
			}
		}
	}
	
	public int getLoc() 
	{
		return loc;
	}

	public void setLoc(int loc) 
	{
		this.loc = loc;
	}

	public MethodSignature getMethodSignature() 
	{
		return methodSignature;
	}

	public String getName() 
	{
		return name;
	}

	public int getMethodCallCounter() 
	{
		return methodCallCounter;
	}

	public void setMethodCallCounter(int methodCallCounter) 
	{
		this.methodCallCounter = methodCallCounter;
	}

	public int getNestedIfCounter() 
	{
		return nestedIfCounter;
	}

	public void setNestedIfCounter(int nestedIfCounter) 
	{
		this.nestedIfCounter = nestedIfCounter;
	}
	
	public boolean isStatic() 
	{
		return isStatic;
	}

	public int getMaxAllowedMethodCalls() 
	{
		return maxAllowedMethodCalls;
	}

	public void setMaxAllowedMethodCalls(int maxAllowedMethodCalls) 
	{
		this.maxAllowedMethodCalls = maxAllowedMethodCalls;
	}

	public ArrayList<ClassGenerator> getClassList() 
	{
		return classList;
	}

	public int getMaxNestedIfs() 
	{
		return maxNestedIfs;
	}

	/**
	 * API to generate a method for a class.
	 * 
	 * @param generator
	 * @param maxNumberOfParams
	 * @param classList
	 * @param name
	 * @param maxLocPerMethod
	 * @param maxNestedIfs
	 * @param maxAllowedMethodCalls
	 * @param generateMethodBody
	 * @return
	 */
	public static Method generateMethod(ClassGenerator generator,
			int maxNumberOfParams,
			ArrayList<ClassGenerator> classList,
			String name,
			int maxLocPerMethod,
			int maxNestedIfs,
			int maxAllowedMethodCalls,
			boolean generateMethodBody)
	{
		boolean isStatic = ProgGenUtil.coinFlip();
		
		//we need minimum of two parameters
		int numberOfParams = ProgGenUtil.getRandomIntInRange(maxNumberOfParams);
		
//		if(numberOfParams<2)
		if(numberOfParams < ProgGenUtil.minNoOfParameters)
		{
			numberOfParams = ProgGenUtil.minNoOfParameters;
		}
		
		return new Method(isStatic, 
				numberOfParams, 
				generator, 
				classList,
				name, 
				maxLocPerMethod,
				maxNestedIfs,
				maxAllowedMethodCalls,
				generateMethodBody);
	}
	
	public String toString()
	{
		String str = "";
		
		str += this.methodSignature;
		str+= "{\n "+ methodBody + returnStatement + "\n}\n\n";
		
		return str;
	}

	/**
	 * API to generate methods for an interface. Generated methods will not have 
	 * a body generated. Such methods are used only for extracting signatures.
	 * 
	 * @param maxNoOfParameters
	 * @param classList
	 * @param name
	 * @return
	 */
	public static Method generateMethodForInterface(
			int maxNoOfParameters,
			ArrayList<ClassGenerator> classList, 
			String name) 
	{
		int numberOfParams = ProgGenUtil.getRandomIntInRange(maxNoOfParameters);
		
//		if(numberOfParams < 2)
		if(numberOfParams < ProgGenUtil.minNoOfParameters)
		{
			numberOfParams = ProgGenUtil.minNoOfParameters;
		}
		//TODO: So, interface methods will have at least 2 parameters, but why?
		return new Method(
				false, 
				numberOfParams, 
				null, 
				classList, 
				name, 
				0, 
				0, 
				0, 
				false);
	}
	
	public HashSet<MethodSignature> getCalledMethods() 
	{
		return calledMethods;
	}	
	
}