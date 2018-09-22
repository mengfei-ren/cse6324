package edu.uta.cse.proggen.packageLevelElements;

import java.util.ArrayList;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.MethodSignature;
import edu.uta.cse.proggen.configurationParser.ConfigurationXMLParser;
import edu.uta.cse.proggen.util.ProgGenUtil;


/**
 * Class to generate an interface.
 * 
 * Note: No inheritance among interfaces.
 * 
 * @author balamurugan
 *
 */
public class InterfaceGenerator 
{
	private ArrayList<MethodSignature> methodSignatures = new ArrayList<MethodSignature>();
	private String name;
	private int	numOfMethods;
	
	public InterfaceGenerator(String name, 
			ArrayList<ClassGenerator> classList)
	{
		this.name = name;
						
		//we need atleast one method per interface. not a marker interface.
		int maxNumOfMethods = ConfigurationXMLParser.getPropertyAsInt("maxNoOfMethodsPerInterface");
		int numOfMethods;
		if(maxNumOfMethods < 1)
		{
			numOfMethods = 1;
		}
		else
		{
			numOfMethods = new Random().nextInt(maxNumOfMethods);
		}
		
		if(numOfMethods == 0)
		{
			numOfMethods = 1;
		}
		this.numOfMethods = numOfMethods;
		
		for(int i=0; i<numOfMethods; i++)
		{
			//use this method only to extract the signature and discard them
			Method method = Method.generateMethodForInterface(
					ProgGenUtil.maxNoOfParameters, 
					classList, 
					name+"Method"+i);
			methodSignatures.add(method.getMethodSignature());
		}
	}
	
	public ArrayList<MethodSignature> getMethodSignatures() 
	{
		return methodSignatures;
	}
	
	public int getNumOfMethods() {
		return numOfMethods;
	}

	public String getName() {
		return name;
	}
	
	public String getPackageName()
	{
		return "package com.accenture.lab.carfast.test;\n\n\n";
	}

	public String toString()
	{
		StringBuilder builder = new StringBuilder(getPackageName());
		builder.append("public interface");
		builder.append(" ");
		builder.append(name);
		builder.append("\n{\n");
		for(MethodSignature signature : methodSignatures)
		{
			builder.append(signature);
			builder.append(";\n");
		}
		builder.append("}");
		return builder.toString();
	}
}
