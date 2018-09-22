package edu.uta.cse.proggen.classLevelElements;

import java.util.ArrayList;
import java.util.HashSet;

import edu.uta.cse.proggen.classLevelElements.Type.Primitives;

/**
 * This class represents the signature of a generated method.
 * 
 * @author balamurugan
 *
 */
public class MethodSignature
{
	private boolean 				isStatic;
	private Primitives				returnType;
	private	String					name;
	private	ArrayList<Variable>		parameterList;
	
	// To stop indirect recursion
	private HashSet<MethodSignature> callerMethods = new HashSet<MethodSignature>();
	
	public MethodSignature(
			boolean isStatic, 
			Primitives returnType,
			String name, 
			ArrayList<Variable> parameterList) 
	{
		super();
		this.isStatic = isStatic;
		this.returnType = returnType;
		this.name = name;
		this.parameterList = parameterList;
	}
	
	/**
	 * returns the String form of the MethodSignature
	 * eg: int add(int x, int y)
	 */
	public String toString()
	{
		String str = "public ";
			
		if(isStatic)
		{
			str += "static ";
		}
			
		str += returnType.toString() + " " + name + "(";
		
		for(Variable parameter : parameterList)
		{
			str += parameter.getFieldDeclaration() + ", ";
		}
		
		str = str.substring(0, str.length()-2);
		str += ")";
		
		return str;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) 
	{
		/*
		 * not necessary to override hashcode as
		 * we are not persisting these objects in 
		 * hash based structures.
		 */
		if(obj == null)
			return false;
		
		if(! (obj instanceof MethodSignature) )
			return false;
		
		MethodSignature obj1 = (MethodSignature)obj;
		
		if(this.isStatic != obj1.isStatic)
			return false;
		
		if(this.returnType != obj1.returnType)
			return false;
		
		if(this.name != obj1.name)
			return false;
		
		int size = this.parameterList.size();
		for(int i=0; i<size; i++)
		{
			if(!this.parameterList.get(i).equals(
					obj1.parameterList.get(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isStatic() 
	{
		return isStatic;
	}

	public Primitives getReturnType() 
	{
		return returnType;
	}

	public String getName() 
	{
		return name;
	}

	public ArrayList<Variable> getParameterList() 
	{
		return parameterList;
	}
	
	public HashSet<MethodSignature> getCallerMethodSignature(){
		return callerMethods;
	}
}
