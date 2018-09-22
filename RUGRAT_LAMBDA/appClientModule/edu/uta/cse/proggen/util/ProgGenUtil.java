package edu.uta.cse.proggen.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.uta.cse.proggen.classLevelElements.Field;
import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.MethodSignature;
import edu.uta.cse.proggen.classLevelElements.Type;
import edu.uta.cse.proggen.classLevelElements.Variable;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.configurationParser.ConfigurationXMLParser;
import edu.uta.cse.proggen.expressions.FieldGenerator;
import edu.uta.cse.proggen.expressions.Literal;
import edu.uta.cse.proggen.expressions.VariableGenerator;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;
import edu.uta.cse.proggen.statements.PrintStatement;

/**
 * Class containing utility APIs.
 * 
 * @author balamurugan
 *
 */
public class ProgGenUtil 
{
	public static final int maxNoOfParameters = ConfigurationXMLParser.getPropertyAsInt("maxNoOfParametersPerMethod");
	public static final int minNoOfParameters = ConfigurationXMLParser.getPropertyAsInt("minNoOfParametersPerMethod"); 
	public static final int maxNoOfMethodsPerClass = ConfigurationXMLParser.getPropertyAsInt("maxNoOfMethodsPerClass");
	public static final int maxNoOfMethodCalls = ConfigurationXMLParser.getPropertyAsInt("maxAllowedMethodCalls");
	public static final int maxRecursionDepth = ConfigurationXMLParser.getPropertyAsInt("maxRecursionDepth");
	public static final int recursionProbability = ConfigurationXMLParser.getPropertyAsInt("recursionProbability");
	public static final int integerMaxValue = ConfigurationXMLParser.getPropertyAsInt("intMaxValue");
	
	//minInheritanceDepth
	public static final int minInheritanceDepth = ConfigurationXMLParser.getPropertyAsInt("minInheritanceDepth");
		
	public static final ArrayList<String> allowedTypes;
	public static final HashMap<String, Primitives> primitivesMap = new HashMap<String, Primitives>();
	public static final int maxLoopControllerValue = ConfigurationXMLParser.getPropertyAsInt("maxValueForLoop"); //1000;
	public static final String dbUserName = ConfigurationXMLParser.getProperty("dbUsername");
	public static final String password = ConfigurationXMLParser.getProperty("password");
	public static final String db = ConfigurationXMLParser.getProperty("dbName");
	public static final String driver = ConfigurationXMLParser.getProperty("dbDriver");
	public static final String dbUrl = "jdbc:mysql:///" + db;
	public static boolean useQueries = new Boolean(
			ConfigurationXMLParser.getProperty("useQueries")).booleanValue();
	
	//Using arrays as class field
	public static String allowArray = ConfigurationXMLParser.getProperty("allowArray");
	
	/**
	 * Determines the call type for the method calls generated within
	 * a method body.
	 * 
	 * @author balamurugan
	 */
	public enum CallType
	{
		localWithoutRecursionLimit, localWithRecursionLimit, crossClassWithoutRecursionLimit, crossClassWithRecursionLimit;
	}
	public static CallType methodCallType;
	
	private static int maximumArraySize = 2;
	private static String injectContents = "";
	
	//read config information needed for code generation.
	static
	{
		allowedTypes = getAllowedTypesAsList();
		primitivesMap.put("char", Primitives.CHAR);
		primitivesMap.put("byte", Primitives.BYTE);
		primitivesMap.put("short", Primitives.SHORT);
		primitivesMap.put("int", Primitives.INT);
		primitivesMap.put("long", Primitives.LONG);
		primitivesMap.put("float", Primitives.FLOAT);
		primitivesMap.put("double", Primitives.DOUBLE);
		primitivesMap.put("String", Primitives.STRING);
		primitivesMap.put("Object", Primitives.OBJECT);
		readInjectContents();
		maximumArraySize = ConfigurationXMLParser.getPropertyAsInt("maximumArraySize");
		if(ConfigurationXMLParser.getProperty("useQueries").equalsIgnoreCase("true"))
		{
			useQueries = true;
		}
		
		String callType = ConfigurationXMLParser.getProperty("callType");
		if(callType.equalsIgnoreCase("MCO2_2"))
		{
			methodCallType = CallType.crossClassWithoutRecursionLimit;
		}
		else if(callType.equalsIgnoreCase("MCO2_1"))
		{
			methodCallType = CallType.crossClassWithRecursionLimit;
		}
		else if(callType.equalsIgnoreCase("MCO1_1"))
		{
			methodCallType = CallType.localWithRecursionLimit;
		}
		else
		{
			methodCallType = CallType.localWithoutRecursionLimit;
		}
	}
	
	/**
	 * returns the user-configured allowed types.
	 * 
	 * @return
	 */
	private static ArrayList<String> getAllowedTypesAsList()
	{
		ArrayList<String> allowedTypesList = new ArrayList<String>();
		Set<String> allowedTypes = ConfigurationXMLParser.getTypeList();
		Object[] array = allowedTypes.toArray();
		for(Object o : array)
		{
			allowedTypesList.add((String)o);
		}
		return allowedTypesList;
	}
	
	/**
	 * read the contents to be injected into every generated class.
	 */
	private static void readInjectContents() 
	{
		String injectFileName = ConfigurationXMLParser.getProperty("injectFilename");
		
		File injectFile = new File(injectFileName);
		
		if(!injectFile.exists())
		{
			System.out.println("Unable to locate Inject File. Skipping content injection!");
		}
		
		try
		{
			StringBuffer buffer = new StringBuffer();
			FileReader reader = new FileReader(injectFile);
			BufferedReader buffReader = new BufferedReader(reader);
			String str = "";
			while( (str = buffReader.readLine()) != null)
			{
				buffer.append(str);
				buffer.append("\n");
			}
			injectContents = buffer.toString();
		}
		catch(FileNotFoundException fe)
		{
			System.out.println("Unable to locate Inject File. Skipping content injection!");
			injectContents = "";
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to read Inject File. Skipping content injection!");
			injectContents = "";
		}
	}

	public static String getInjectContents() 
	{
		return injectContents;
	}

	public static ArrayList<String> getAllowedTypes()
	{
		return allowedTypes;
	}

	public static int getRandomArraySize()
	{
		if(maximumArraySize < 2)
		{
			System.out.println("Array should be atleast of size 2! Config.xml has value: "
					+ maximumArraySize);
			System.out.println("Setting array size to 2");
			return 2;
		}
		else if(maximumArraySize == 2)
		{
			return maximumArraySize;
		}
		//array size should be atleast 2
		return new Random().nextInt(maximumArraySize-2) + 2;
	}
	
	public static int getRandomIntInRange(int range)
	{
		return new Random().nextInt(range);
	}
	
	/**
	 * method to return a boolean value based on a coin flip.
	 * 
	 * @return
	 */
	public static boolean coinFlip()
	{
		if( (new Random().nextInt())%2 == 0 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Method to reverse lookup a class by name in a given list.
	 * 
	 * @param classList
	 * @param classname
	 * @return
	 */
	private static ClassGenerator getClassByName(
			ArrayList<ClassGenerator> classList,
			String classname)
	{
		for(ClassGenerator classGenerator : classList)
		{
			if(classGenerator.getFileName().equals(classname))
			{
				return classGenerator;
			}
		}
		return null;
	}
	
	/**
	 * Method to fetch fields which are of OBJECT primitive type.
	 * 
	 * @param variableList
	 * @return
	 */
	private static ArrayList<? extends Field> getObjects(
			ArrayList<? extends Field> variableList)
	{
		ArrayList<Field> objList = new ArrayList<Field>();
		
		for(Field var : variableList)
		{
			if(var.getType().getType() == Primitives.OBJECT)
			{	
				objList.add(var);
			}
		}
		return objList;
	}
	
	private static String getParametersForList(ArrayList<Variable> parameterList, 
			Method method)
	{
		String parameters = "";
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
					operand = VariableGenerator.getRandomizedObjectForType(method, var.getType());
				}
				else
				{
					operand = VariableGenerator.getRandomizedVariable(method, primitive);
				}
			}	
			else
			{
				if(primitive == Primitives.OBJECT)
				{
					operand = FieldGenerator.getRandomizedObjectForType(method.getAssociatedClass(), var.getType());
				}
				else
				{
					operand = FieldGenerator.getRandomField(method.getAssociatedClass(), primitive, method.isStatic());
				}
			}
			parameters += operand + ",";
		}
		parameters = parameters.substring(0, parameters.length()-1);
		return parameters;
	}
	
	private static MethodSignature getMethodToBeInvoked(
			ArrayList<MethodSignature> methodList,
			boolean isStatic,
			Type returnType,
			MethodSignature callingMethod) 
	{
		ArrayList<MethodSignature> list = new ArrayList<MethodSignature>();
		
		for(MethodSignature methodSignature : methodList)
		{
			if(methodSignature.getReturnType().equals(returnType.getType())
					&& !methodSignature.equals(callingMethod))
			{
				list.add(methodSignature);
			}
		}
		
		if(isStatic)
		{
			ArrayList<MethodSignature> staticSignatures = new ArrayList<MethodSignature>();
			for(MethodSignature methodSignature : list)
			{
				if(methodSignature.isStatic())
				{
					staticSignatures.add(methodSignature);
				}
			}
			
			if(staticSignatures.size() == 0)
			{
				return null;
			}
			return staticSignatures.get(new Random().nextInt(staticSignatures.size()));
		}
		
		if(list.size() == 0)
		{
			return null;
		}
		
		return list.get(new Random().nextInt(list.size()));
	}

	private static MethodSignature getMethodToBeInvoked(
			ArrayList<MethodSignature> methodList,
			boolean isStatic,
			MethodSignature callingMethod) 
	{
		if(!isStatic)
		{
			MethodSignature methodSignature = methodList.get(new Random().nextInt(methodList.size()));
			int counter = 300;
			
			while(methodSignature.equals(callingMethod)
					&& counter > 0)
			{
				methodSignature = methodList.get(new Random().nextInt(methodList.size()));
			}
			
			if(counter>0 && !methodSignature.equals(callingMethod))
			{
				return methodSignature;
			}
			return null;
		}
		else
		{
			ArrayList<MethodSignature> staticMethods = new ArrayList<MethodSignature>();
			for(MethodSignature method : methodList)
			{
				if(method.isStatic() && !method.equals(callingMethod))
				{
					staticMethods.add(method);
				}
			}
			
			if(staticMethods.isEmpty())
			{
				return null;
			}
			
			return staticMethods.get(new Random().nextInt(staticMethods.size()));
		}
	}
	
	/**
	 * Return a method call statement based on a given primitive.
	 * 
	 * @param method
	 * @param classList
	 * @param returnType
	 * @param lhs
	 * @return
	 */
	public static String getMethodCallForReturnType(
			Method method, 
			ArrayList<ClassGenerator> classList,
			Type returnType,
			Operand lhs)
	{
		String stmt = "";
		
		if(ProgGenUtil.methodCallType == CallType.localWithoutRecursionLimit
				|| ProgGenUtil.methodCallType == CallType.localWithRecursionLimit)
		{
			//only local method calls.
			ArrayList<MethodSignature> methodList = method.getAssociatedClass().getMethodSignatures();
			if(methodList.size() < 1)
			{
				return lhs + " = " + new Literal(returnType.getType()).toString() + ";";
			}
		
			MethodSignature methodToBeInvoked = getMethodToBeInvoked(methodList, method.isStatic(), 
					returnType, method.getMethodSignature());
			
		
			if(methodToBeInvoked == null)
			{
				return lhs + " = " + new Literal(returnType.getType()).toString() + ";";
			}
			
			//Check if indirect recursion is allowed:
			if(ConfigurationXMLParser.getProperty("allowIndirectRecursion").toLowerCase().equals("no")){
				try{
					String[] tok = methodToBeInvoked.getName().toLowerCase().split("method");
					int calleeMethodID = Integer.parseInt(tok[1]);

					String[] tok2 = method.getMethodSignature().getName().toLowerCase().split("method");
					int callerMethodID = Integer.parseInt(tok2[1]);

					if (callerMethodID >= calleeMethodID) {
						return lhs + " = " + new Literal(returnType.getType()).toString()+ ";";
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}		

		
			ArrayList<Variable> parameterList = methodToBeInvoked.getParameterList();
			String parameters = "(";
			parameters += getParametersForList(parameterList, method);
			parameters += ")";
		
			stmt += lhs + " = " + methodToBeInvoked.getName() + parameters + ";";
			method.getCalledMethods().add(methodToBeInvoked);
			method.getCalledMethodsWithClassName().add(
					method.getAssociatedClass().getFileName() + "." + methodToBeInvoked.getName());
			method.setLoc(method.getLoc()+1);
			return stmt;
		}
		else
		{
			//cross-class method calls.
			Random random = new Random();
			ArrayList<Field> objList = new ArrayList<Field>(); 
				
			objList.addAll(getObjects(method.getAssociatedClass().getFields()));
			objList.addAll(getObjects(method.getParameterList()));
						
			if(objList.size() != 0)
			{
				ArrayList list = getClassByMethodReturnType(objList, returnType.getType(), classList);
				if(list == null)
				{
					return lhs + " = " + new Literal(returnType.getType()).toString() + ";";
				}
				
				Field variable 			= (Field)list.get(0);
				ClassGenerator classObj = (ClassGenerator)list.get(1); 
				
				if(classObj != null)
				{
					ArrayList<MethodSignature> signatures = classObj.getMethodSignatures(returnType);
					if(signatures.size() == 0)
					{
						return lhs + " = " + new Literal(returnType.getType()).toString() + ";";
					}
					
					MethodSignature signature = signatures.get(random.nextInt(signatures.size()));
					String varString = variable.toString();
					String methodCall = signature.getName() + "(" + getParametersForList(
							signature.getParameterList(), method) + ");\n";
					
					if(!signature.isStatic())
					{
						if( method.isStatic() 
								&& !variable.isStatic() )
						{
							if(!variable.getName().startsWith("var"))
							{
								stmt += "classObj.";
							}
						}
						stmt += varString + " = new " + variable.getType() + "();\n";
					}
					
					stmt += lhs + " = "; 
					
					if(method.isStatic() 
							&& !variable.isStatic() 
							&& !variable.getName().startsWith("var")
							&& !signature.isStatic())
					{
						stmt += "classObj.";
					}
					
					if(signature.isStatic())
					{
						stmt += classObj.getFileName() + "." + methodCall;
					}
					else
					{
						stmt += varString + "." + methodCall;
					}
					method.setLoc(method.getLoc()+1);
					method.getCalledMethods().add(signature);
					method.getCalledMethodsWithClassName().add(
							variable.getType() + "." + method.getName());
					return stmt;
				}
			}
		}
		return lhs + " = " + new Literal(returnType.getType()).toString() + ";";
	}
	
	@SuppressWarnings("unchecked")
	private static ArrayList getClassByMethodReturnType(
			ArrayList<Field> objList, 
			Primitives returnType, 
			ArrayList<ClassGenerator> classList) 
	{
		Field 	field;
		int 	counter = 500;
		Random random = new Random();
		
		field = objList.get(random.nextInt(objList.size()));
		ClassGenerator classObj = getClassByName(classList, field.getType().toString());
		
		while(counter > 0 && !classObj.getReturnTypeSet().contains(returnType))
		{
			field = objList.get(random.nextInt(objList.size()));
			classObj = getClassByName(classList, field.getType().toString());
			counter--;
		}
		
		if(counter > 0 && classObj.getReturnTypeSet().contains(returnType))
		{
			ArrayList list = new ArrayList();
			list.add(field);
			list.add(classObj);
			return list;
		}
		
		return null;
	}

	/**
	 * get a random method call.
	 * 
	 * @param method
	 * @param classList
	 * @return
	 */
	public static String getMethodCall(Method method, ArrayList<ClassGenerator> classList)
	{
		String stmt = "";
		if(ProgGenUtil.methodCallType == CallType.localWithoutRecursionLimit
				|| ProgGenUtil.methodCallType == CallType.localWithRecursionLimit)
		{
			//only local method calls.
			ArrayList<MethodSignature> methodList = method.getAssociatedClass().getMethodSignatures();
			if(methodList.size() < 1)
			{
				stmt = new PrintStatement(method).toString();
				return stmt;
			}
		
			MethodSignature methodToBeInvoked = getMethodToBeInvoked(methodList, method.isStatic(), method.getMethodSignature());
		
			if(methodToBeInvoked == null)
			{
				stmt = new PrintStatement(method).toString();
				return stmt;
			}
			
			// Check if indirect recursion is allowed:			
			if(ConfigurationXMLParser.getProperty("allowIndirectRecursion").toLowerCase().equals("no")){			
				//Methods are always named ClassNameM/methodNUMBER
				String[] tok = methodToBeInvoked.getName().toLowerCase().split("method");
				int calleeMethodID = Integer.parseInt(tok[1]);

				String[] tok2 = method.getMethodSignature().getName().toLowerCase().split("method");
				int callerMethodID = Integer.parseInt(tok2[1]);

				// callerID should be lower than calleeID
				if(callerMethodID >= calleeMethodID){
					stmt = new PrintStatement(method).toString();
					return stmt;
				}
			}
			
				
			
					
			ArrayList<Variable> parameterList = methodToBeInvoked.getParameterList();
			String parameters = "(";
			parameters += getParametersForList(parameterList, method);
			parameters += ")";
		
			stmt += methodToBeInvoked.getName() + parameters + ";";
			method.getCalledMethods().add(methodToBeInvoked);
			method.getCalledMethodsWithClassName().add(
					method.getAssociatedClass().getFileName() + "." + methodToBeInvoked.getName());
			method.setLoc(method.getLoc()+1);
			return stmt;
		}
		else
		{
			//cross-class method calls.
			Random random = new Random();
			ArrayList<Field> objList = new ArrayList<Field>(); 
				
			objList.addAll(getObjects(method.getAssociatedClass().getFields()));
			objList.addAll(getObjects(method.getParameterList()));
						
			if(objList.size() != 0)
			{
				Field variable = objList.get(random.nextInt(objList.size()));
				ClassGenerator classObj = getClassByName(classList, variable.getType().toString());
				if(classObj != null)
				{
					String varString = variable.toString();
					ArrayList<MethodSignature> signatures = classObj.getMethodSignatures();
					MethodSignature signature = signatures.get(random.nextInt(signatures.size()));
					String methodCall = signature.getName() + "(" + getParametersForList(
							signature.getParameterList(), method) + ");\n";
					
					if(!signature.isStatic())
					{
						if( method.isStatic() 
								&& !variable.isStatic() )
						{
							if(!variable.getName().startsWith("var"))
							{
								stmt += "classObj.";
							}
						}
						stmt += varString + " = new " + variable.getType() + "();\n";
					}
					
					if(method.isStatic() 
							&& !variable.isStatic() 
							&& !variable.getName().startsWith("var")
							&& !signature.isStatic())
					{
						stmt += "classObj.";
					}
					
					if(signature.isStatic())
					{
						stmt += classObj.getFileName() + "." + methodCall;
					}
					else
					{
						stmt += varString + "." + methodCall;
					}
					method.setLoc(method.getLoc()+1);
					method.getCalledMethods().add(signature);
					method.getCalledMethodsWithClassName().add(
							variable.getType() + "." + signature.getName());
					return stmt;
				}
			}
		}
		return stmt;
	}
	
	
	/**
	 * 
	 * @param inheritanceDepth: Randomly chosen inheritance depth
	 * @param numberOfClasses: User defined total number of classes
	 * @param usedIntegers: So far used integers
	 * @return: a unique integer list of size inheritanceDepth
	 */
	public static ArrayList<Integer> getRandomList(
			int inheritanceDepth, // e.g., 3 
			int numberOfClasses, // e.g., 10
			List<Integer> usedIntegers) // e.g., 1, 2, 5, 7
	{		// may return a list: {3, 4, 9}
		LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
		
		Random random = new Random();
		
		while(set.size() < inheritanceDepth)
		{
			int next = random.nextInt(numberOfClasses);
			if(!usedIntegers.contains(next))
			{
				set.add(next);
				usedIntegers.add(next);
			}
		}
		
		ArrayList<Integer> intList = new ArrayList<Integer>();
		for(int num : set)
		{
			intList.add(num);
		}
		return intList;
	}
	
	/**
	 * 
	 * @return Any of the { CHAR, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, STRING, OBJECT } types
	 */
	public static Primitives getRandomizedPrimitive()
	{
		ArrayList<String> typeList = getAllowedTypes();
		if(typeList.size() == 0)
		{
			System.out.println("No type specified in config.xml!");
			System.exit(1);
		}
		
		String primitiveString = typeList.get(
				new Random().nextInt(typeList.size()));
	
		return primitivesMap.get(primitiveString);
	}
	
	public static Primitives getRandomizedPrimitiveForOperands()
	{
		//returns any Primitive except Object
		Primitives primitive = getRandomizedPrimitive();
		
		while( primitive == Primitives.OBJECT )
		{
			primitive = getRandomizedPrimitive();
		}
		
		return primitive;
	}
	
	/**
	 * 
	 * @param noOfInheritanceChains : User defined desired number of inheritance chains
	 * @param inheritanceDepth : Maximum allowed inheritance depth
	 * @param range: Total number of classes
	 * @return Something like this: {<3,5,1>,<7,2,6>} random but unique list of integers 
	 */	
	public static ArrayList<ArrayList<Integer>> getInheritanceList(
			int noOfInheritanceChains,
			int inheritanceDepth,
			int range) // number of classes
	{
		//Ishtiaque: this is probably already checked
		//Bala: This is a static method which can be reused for Interfaces
		//Even if the main stub is replaced with a GUI, this check will be needed.
		if(range < (noOfInheritanceChains*inheritanceDepth) )
			
		{
			System.out.println("getInheritanceList:: Invalid range");
			return null;
		}
		ArrayList<ArrayList<Integer>> inheritanceList = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> usedList = new ArrayList<Integer>();
		
		for(int i=0; i<noOfInheritanceChains; i++)
		{
			Random random = new Random();
			
			//we don't want a 0 depth inheritance chain.
			int randomizedInheritanceDepth = random.nextInt(inheritanceDepth)+1;
			
			// considering MinInheritanceDepth;  Min. InheritanceDepth should be less than equal to Max.,=> this is checked in GUI
			if (randomizedInheritanceDepth < minInheritanceDepth) 
				randomizedInheritanceDepth = minInheritanceDepth;	
			
			
			
			inheritanceList.add(ProgGenUtil.getRandomList(randomizedInheritanceDepth, range, usedList));
		}
		return inheritanceList;
	}

	public static Primitives getRandomizedPrimitiveForBooleanExpression(Set<Primitives> primitiveSet) 
	{
		if(primitiveSet.isEmpty())
		{
			//return null
			return null;
		}
		
		Object[] primitiveArray = primitiveSet.toArray();
		
		int index = new Random().nextInt(primitiveArray.length);
		return (Primitives)primitiveArray[index]; 
	}

	public static LinkedHashSet<Primitives> getPrimitivesOfVariables(Method method) 
	{
		LinkedHashSet<Primitives> primitiveSet = new LinkedHashSet<Primitives>();
		ArrayList<Variable> parameterList = method.getParameterList();
		
		for(Variable var : parameterList)
		{
			//ignore the recursionCounter
			if(var.getName().equals("recursionCounter"))
			{
				continue;
			}
			
			Primitives primitive = var.getType().getType();
			//we don't want expressions based on Object type
			if(! (primitive == Primitives.OBJECT) )
			{
				primitiveSet.add(primitive);
			}
		}
		return primitiveSet;
	}
	
	public static String getClassToConstruct(String classname, ArrayList<ClassGenerator> classList)
	{
		ClassGenerator lhsClass = getClassByName(classList, classname);
		if(lhsClass == null)
		{
			return classname;
		}
		
		if(ProgGenUtil.coinFlip())
		{
			//return the same class
			return classname;
		}
		
		//else pick one of its subclasses to return
		HashSet<ClassGenerator> directKnownSubClasses = lhsClass.getSubClasses();

		if(directKnownSubClasses.isEmpty())
		{
			//no subclasses
			return classname;
		}
		
		//Using linked hash set for predictable iteration order
		LinkedHashSet<ClassGenerator> knownSubClasses = new LinkedHashSet<ClassGenerator>(directKnownSubClasses);
		
		for(ClassGenerator generator : knownSubClasses)
		{
			knownSubClasses.add(generator);
			HashSet<ClassGenerator> subclasses = generator.getSubClasses();
			for(ClassGenerator subclass : subclasses)
			{
				knownSubClasses.add(subclass);
			}
		}
		
		Object subClassArray[] = knownSubClasses.toArray();
		ClassGenerator chosenOne = (ClassGenerator)subClassArray[new Random().nextInt(subClassArray.length)];
		return chosenOne.getFileName();
	}
	
	public static Set<Primitives> getValidPrimitivesInScope(Method method) 
	{
		HashSet<Primitives> validPrimitivesInScope = new HashSet<Primitives>();
		
		ArrayList<Variable> variableList = method.getParameterList();
		for(Variable var : variableList)
		{
			if(!var.getName().equals("recursionCounter"))
			{
				validPrimitivesInScope.add(var.getType().getType());
			}
		}
		
		if(validPrimitivesInScope.contains(Primitives.OBJECT))
		{
			validPrimitivesInScope.remove(Primitives.OBJECT);
		}
		
		return validPrimitivesInScope;
	}
}
