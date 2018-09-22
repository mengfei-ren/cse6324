package edu.uta.cse.proggen.packageLevelElements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.uta.cse.proggen.classLevelElements.Field;
import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.MethodSignature;
import edu.uta.cse.proggen.classLevelElements.Type;
import edu.uta.cse.proggen.classLevelElements.Variable;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.configurationParser.ConfigurationXMLParser;
import edu.uta.cse.proggen.expressions.Literal;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * Represents a class in the generated application.
 * 
 * @author balamurugan
 *
 */
public class ClassGenerator 
{
	private String 	fileName;
	private int 	numOfVars;
	private int 	percent;
	private int 	loc;
	private int 	nestedIfCounter = 0;
	private int 	maxNestedIfs = 30;
	private int 	maxAllowedMethodCalls = ProgGenUtil.maxNoOfMethodCalls;
	private int 	methCounter = 0;
	private int 	methCalledCounter = 0;
	private int 	locPerMethod = 0;
	private boolean	preGenerate = false;
	
	/** Holds actual class body*/
	private String 	program = "";
	private int 	numberOfMethods = 1;
	
	private Random 							rand = new Random(System.currentTimeMillis());
	
	private Set<Field> 						usedFields	= new HashSet<Field>();
	private ArrayList<Field> 				fields = new ArrayList<Field>();
	
	private ClassGenerator 					superClass = null;
	private HashSet<ClassGenerator> 		subClasses = new  HashSet<ClassGenerator>();
	
	private ArrayList<Method> 				methodList = new ArrayList<Method>();
	private ArrayList<MethodSignature> 		overriddenMethods = new ArrayList<MethodSignature>();
	private ArrayList<MethodSignature>		methodSignatures = new ArrayList<MethodSignature>();
	
	private ArrayList<InterfaceGenerator> 	interfaceList = new ArrayList<InterfaceGenerator>();
	private Set<Primitives>					returnTypeSet = new HashSet<Primitives>();
	
	
	/**
	 * @param fileName
	 * @param loc
	 * @param superClass
	 */
	public ClassGenerator(String fileName, 
			int loc, 
			ClassGenerator superClass) 
	{
		super();
		this.fileName = fileName;
		//Atleast one variable per class. Added in response to Ishtiaque's comment.
		int maxNoOfVars = ConfigurationXMLParser.getPropertyAsInt("maxNoOfClassFields");
		int minNoOfFields = ConfigurationXMLParser.getPropertyAsInt("minNoOfClassFields");
		
		// to avoid Illegal argument exception
		if(maxNoOfVars != 0)
			this.numOfVars = new Random().nextInt(maxNoOfVars);
		else
			this.numOfVars = 0;
		
		if(numOfVars < minNoOfFields)
		{
			numOfVars = minNoOfFields;
		}
		this.numberOfMethods = 0;
		this.loc = loc;
		this.percent = ConfigurationXMLParser.getPropertyAsInt("probability");
		this.maxNestedIfs = ConfigurationXMLParser.getPropertyAsInt("maxNestedIfs");
		this.maxAllowedMethodCalls = ConfigurationXMLParser.getPropertyAsInt("maxAllowedMethodCalls");
		this.superClass = superClass;
	}

	/**
	 * pre-generation determines the class members, variables and method signatures
	 * @param classList
	 * @param preGeneratedClasses
	 */
	public void preGenerateForMethodSignature(ArrayList<ClassGenerator> classList,
			HashSet<String> preGeneratedClasses) 
	{
		if(preGenerate)
		{
			//pre generation should happen only once.
			return;
		}
		// superclass should have its methods signature ready first
		if(this.getSuperClass() != null &&
				(!preGeneratedClasses.contains(this.getSuperClass().getFileName())))
		{
			this.getSuperClass().preGenerateForMethodSignature(classList, preGeneratedClasses);
		}
		
		// add class fields
		//classList is used all the way down to "new Type()"
		generateClassFields(classList);
		
		System.out.println("calculating number of methods...");
		//calculate number of methods
		calculateNumberOfMethods();
		
		System.out.println("overriding interface methods...");
		//first override methods of implemented interfaces
		overrideInterfaceMethods(classList);
		
		System.out.println("generating member method signatures...");
		generateMethodSignatures(classList);
		
		preGeneratedClasses.add(fileName);
		preGenerate = true;
	}
	
	/**
	 * Generates the actual body or content of the class 
	 * and updates the set  'generatedClasses' Set of 
	 * @param classList List of created class objects
	 * @param generatedClasses Set of already generated class names
	 */	
	public void generate(ArrayList<ClassGenerator> classList, 
			HashSet<String> generatedClasses, 
			HashSet<String> preGeneratedClasses)
	{
		program = "";
		
		if(this.getSuperClass() != null &&
				(!generatedClasses.contains(this.getSuperClass().getFileName())))
		{
			this.getSuperClass().generate(classList, generatedClasses, preGeneratedClasses);
		}
		
		if(!preGenerate)
		{
			preGenerateForMethodSignature(classList, preGeneratedClasses);
		}
		
		// append package name
		appendPackageName();
		
		//append import statements
		if(ProgGenUtil.useQueries)
		{
			appendImportStatements();
		}

		System.out.println("appending classname...");
		// append class name
		appendClassName();
		
		System.out.println("Injecting contents...");
		//append injected contents from external file
		appendInjectedContents();

		System.out.println("appending field names...");
		// append field names
		appendFieldNames();
		
		System.out.println("generating class methods...");
		//generate methods
		generateMethods(classList);
		
		System.out.println("writing main...");
		generateMain();
		
		//generate SingleEntry for CarFast
		System.out.println("writing singleEntry.....");
		generateSingleEntry();
		
		System.out.println("writing end of class...");
		//write end of class
		generateEndOfClass();
		
		generatedClasses.add(this.getFileName());
	}
	
	private void appendImportStatements()
	{
		program += "import java.sql.ResultSet;\n";
		program += "import java.util.Random;\n\n\n";
	}
	
	private void appendInjectedContents() 
	{
		program += ProgGenUtil.getInjectContents();
	}

	private void overrideInterfaceMethods(ArrayList<ClassGenerator> classList) 
	{
		for(InterfaceGenerator interfaceGen : interfaceList)
		{
			for(MethodSignature signature : interfaceGen.getMethodSignatures())
			{
				Primitives returnType = signature.getReturnType();
				if( returnType != Primitives.OBJECT )
				{
					returnTypeSet.add(returnType);
				}
			}
			methodSignatures.addAll(interfaceGen.getMethodSignatures());
		}
	}
	
	private void generateMethods(ArrayList<ClassGenerator> classList)
	{
		for(MethodSignature signature : methodSignatures)
		{
			Method method = Method.getMethod(signature, 
								this, 
								classList,
								locPerMethod,
								this.maxNestedIfs,
								this.maxAllowedMethodCalls);
			program += method;
			methodList.add(method);
		}
	}

	private void calculateNumberOfMethods() 
	{
		int totalNumberOfMethods = 0;
		numberOfMethods = 0;
		
		//atleast two methods
		if(ProgGenUtil.maxNoOfMethodsPerClass < 2)
		{
			System.out.println("Setting number of methods per class as 2.");
			numberOfMethods = 2;
		}
		else if(ProgGenUtil.maxNoOfMethodsPerClass == 2)
		{
			numberOfMethods = 2;
		}
		else
		{
			numberOfMethods = rand.nextInt(ProgGenUtil.maxNoOfMethodsPerClass - 2) + 2;
		}
		
		totalNumberOfMethods += numberOfMethods;
		//add the number of methods from the implemented interfaces.
		for(InterfaceGenerator interfaceGen : interfaceList)
		{
			totalNumberOfMethods += interfaceGen.getNumOfMethods();
		}

		locPerMethod = this.loc/totalNumberOfMethods;
	}

	public int getMaxNestedIfs() {
		return maxNestedIfs;
	}

	public void setMaxNestedIfs(int maxNestedIfs) {
		this.maxNestedIfs = maxNestedIfs;
	}

	public ArrayList<MethodSignature> getMethodSignatures() {
		return methodSignatures;
	}

	public Set<Primitives> getReturnTypeSet() {
		return returnTypeSet;
	}

	public int getMaxAllowedMethodCalls() {
		return maxAllowedMethodCalls;
	}

	public void setMaxAllowedMethodCalls(int maxAllowedMethodCalls) {
		this.maxAllowedMethodCalls = maxAllowedMethodCalls;
	}

	public int getMethCounter() {
		return methCounter;
	}

	public void setMethCounter(int methCounter) {
		this.methCounter = methCounter;
	}

	public ClassGenerator getSuperClass() {
		return superClass;
	}

	public void setSuperClass(ClassGenerator superClass) {
		this.superClass = superClass;
	}

	public ArrayList<InterfaceGenerator> getInterfaceList() {
		return interfaceList;
	}

	public void setInterfaceList(ArrayList<InterfaceGenerator> interfaceList) {
		this.interfaceList = interfaceList;
	}

	public int getPercent() 
	{
		return percent;
	}
	
	public ArrayList<Method> getMethodList() {
		return this.methodList;
	}

	public void setMethodList(ArrayList<Method> methodList) {
		this.methodList = methodList;
	}

	public HashSet<ClassGenerator> getSubClasses() {
		return subClasses;
	}

	public int getMethCalledCounter() {
		return methCalledCounter;
	}

	public void setMethCalledCounter(int methCalledCounter) {
		this.methCalledCounter = methCalledCounter;
	}

	public Set<Field> getUsedFields() {
		return usedFields;
	}

	public void setUsedFields(Set<Field> usedFields) {
		this.usedFields = usedFields;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() 
	{
		return program;
	}
	
	private void generateMain()
	{
		program += "\npublic static void main(String args[]){\n";
		program += this.fileName + " obj = new " + this.fileName +"();\n";
		for(Method method : this.methodList)
		{
			StringBuilder builder = new StringBuilder();
			
			MethodSignature signature = method.getMethodSignature();
			if(!signature.isStatic())
			{
				builder.append("obj."); 
			}
			
			builder.append(signature.getName() + "(");
			
			for(Variable variable : signature.getParameterList())
			{
				Type type = variable.getType();
				if( type.getType() == Primitives.OBJECT)
				{
					builder.append("new " + type.toString() + "()");
					builder.append(",");
				}
				else
				{
					if(variable.getName().equals("recursionCounter"))
					{
						builder.append(new Random().nextInt(ProgGenUtil.maxRecursionDepth));
					}
					else
					{
						builder.append(new Literal(type.getType()));
					}
					builder.append(",");
				}
			}
			String str = builder.toString();
			str = str.substring(0, str.length() - 1);
			str += ");\n";
			
			program += str;
		}
		program += "}\n";
	}
	
	
	//Single Entry point for CarFast
	//TODO: support other parameter types
	private void generateSingleEntry()
	{
		program += "\npublic static void singleEntry(";
		
		StringBuilder param = new StringBuilder();
		
		for(int i =0; i< ProgGenUtil.maxNoOfParameters; i++)
			param.append("int i"+i+",");
		
		String st = param.toString();
		st = st.substring(0, st.length()-1);
		st += "){\n";
		
		program += st;
		
		
		program += this.fileName + " obj = new " + this.fileName +"();\n";
		for(Method method : this.methodList)
		{
			HashSet<Integer> set = new HashSet<Integer>(); 
			StringBuilder builder = new StringBuilder();
			
			MethodSignature signature = method.getMethodSignature();
			if(!signature.isStatic())
			{
				builder.append("obj."); 
			}
			
			builder.append(signature.getName() + "(");
			
			for(Variable variable : signature.getParameterList())
			{
				Type type = variable.getType();
				if( type.getType() == Primitives.OBJECT)
				{
					builder.append("new " + type.toString() + "()");
					builder.append(",");
				}
				else
				{
					if(variable.getName().equals("recursionCounter"))
					{
						builder.append(new Random().nextInt(ProgGenUtil.maxRecursionDepth));
					}
					else
					{
						if(type.getType() != Primitives.INT )
							builder.append(new Literal(type.getType()));
						else{
							boolean addedToSet = false;
							do{
								int var = new Random().nextInt(ProgGenUtil.maxNoOfParameters);
								addedToSet = set.add(var);
								if(addedToSet){
									builder.append("i"+var);
								}
							}while(!addedToSet);
							
						}
					}
					builder.append(",");
				}
			}
			String str = builder.toString();
			str = str.substring(0, str.length() - 1);
			str += ");\n";
			
			program += str;
		}
		program += "}\n";
	}
	
	private void generateEndOfClass() 
	{
		// closing brace of the class
		program += "\n}"; 
	}

	private MethodSignature overRiddenMethod(ArrayList<ClassGenerator> classList,
			int loc)
	{
		ArrayList<MethodSignature> superClassMethodSignatures = this.superClass.methodSignatures;
		MethodSignature methodToOverride = superClassMethodSignatures.get(rand.nextInt(superClassMethodSignatures.size()));
		int counter = this.superClass.numberOfMethods;
		
		while( ( overriddenMethods.contains(methodToOverride) 
				|| methodSignatures.contains(methodToOverride) )
				&& counter > 0 )
		{
			methodToOverride = superClassMethodSignatures.get(rand.nextInt(superClassMethodSignatures.size()));
			counter--;
		}
		
		if(counter == 0)
		{
			System.out.println("overriddenMethod()::returning null due to lack of methods in super class.");
			return null;
		}
		
		overriddenMethods.add(methodToOverride);
		Primitives returnType = methodToOverride.getReturnType();
		if(returnType != Primitives.OBJECT)
		{
			returnTypeSet.add(returnType);
		}
		return methodToOverride;
	}


	private void generateMethodSignatures(ArrayList<ClassGenerator> classList) 
	{
		for (int i = 0; i<numberOfMethods; i++) 
		{
			if(this.superClass != null)
			{
				//flip a coin(1 out of 3) to decide for method override
				int choiceOverride = rand.nextInt(3);
				if(choiceOverride == 0)
				{
					MethodSignature overridenMethod = overRiddenMethod(classList, locPerMethod);
					if(overridenMethod != null)
					{
						methodSignatures.add(overridenMethod);
						continue;
					}
					//else fallthrough and generate normal class method
				}
			}
			
			//Basically creating MethodSignature
			Method method =  Method.generateMethod(
						this,
						ProgGenUtil.maxNoOfParameters, 
						classList, 
						this.fileName + "method" + i,
						locPerMethod,
						this.maxNestedIfs,
						this.maxAllowedMethodCalls,
						false);
				
			methodSignatures.add(method.getMethodSignature());
			Primitives returnType = method.getReturnType();
			if(returnType != Primitives.OBJECT)
			{
				returnTypeSet.add(returnType);
			}
		}
	}
	
	/**
	 * Gives the corresponding class name
	 * @return Name of the Class
	 */
	public String getFileName() 
	{
		return fileName;
	}

	private void appendFieldNames() 
	{
		for (int i = 0; i < fields.size(); i++) 
		{
			program += fields.get(i).getFieldDeclaration() + ";\n";
		}
		program += "\n\n";
	}

	private void appendClassName() 
	{
		program += "public class " + fileName;
		
		if(this.superClass != null)
		{
			program += " extends " + superClass.getFileName();
			superClass.addSubClass(this);
		}
		
		if(this.interfaceList.size()>0)
		{
			program += " implements ";
			for(InterfaceGenerator interfaceGen : interfaceList)
			{
				System.out.println("implementing interface : " + interfaceGen.getName());
				program += interfaceGen.getName() + ", ";
			}
			
			program = program.substring(0, program.length()-2);
		}
		
		program += " {\n";
	}

	private void addSubClass(ClassGenerator classGenerator) 
	{
		this.subClasses.add(classGenerator);
	}

	private void appendPackageName() 
	{
		program += "package com.accenture.lab.carfast.test;\n\n\n";
	}

	private void generateClassFields(ArrayList<ClassGenerator> classList) 
	{
		for (int i = 0; i < numOfVars; i++) 
		{
			fields.add( Field.generateField("f" + i, classList) );
		}
	}

	public void setNestedIfCounter(int nestedIfCounter) 
	{
		this.nestedIfCounter = nestedIfCounter;
	}

	public int getNestedIfCounter() 
	{
		return nestedIfCounter;
	}

	public ArrayList<MethodSignature> getMethodSignatures(Type returnType) 
	{
		ArrayList<MethodSignature> list = new ArrayList<MethodSignature>();
		for(MethodSignature signature : methodSignatures)
		{
			if(signature.getReturnType().equals(returnType.getType()))
			{
				list.add(signature);
			}
		}
		return list;
	}
}
