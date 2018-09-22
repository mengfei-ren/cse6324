package edu.uta.cse.proggen.packageLevelElements;

import java.util.ArrayList;

import edu.uta.cse.proggen.configurationParser.ConfigurationXMLParser;
import edu.uta.cse.proggen.util.ProgGenUtil;

public class SingleEntryGenerator {
	
	private String program ="";
	private ArrayList<ClassGenerator> listOfClasses ;
	private int noOfMethCalls = 0;
	private final double  methCallLimit = 150;
	
	public SingleEntryGenerator(ArrayList<ClassGenerator> list){
		listOfClasses = list;
		double totalClass = listOfClasses.size();
		double temp = Math.ceil(totalClass / methCallLimit);
		noOfMethCalls = (int) temp;
		
		generateSingleEntryClass();
	}
	
	
	private void appendPackageName() 
	{
		program += "package com.accenture.lab.carfast.test;\n\n\n";
	}
	private void appendClassName(){
		program += "public class "+ConfigurationXMLParser.getProperty("classNamePrefix")+"Start {\n";
	}
	
	private void appendFieldNames() 
	{
		for (int i = 0; i < ProgGenUtil.maxNoOfParameters; i++) 
		{
			program += "private static int f"+ i + ";\n";
		}
		program += "\n\n";
	}
	
	private void appendSubMethod(){
		
		StringBuilder fields = new StringBuilder();
		for(int i = 0; i < ProgGenUtil.maxNoOfParameters; i++){
			fields.append("f"+i+",");
		}
		String f = fields.toString();
		f = f.substring(0, fields.length() - 1);
		
//		for(ClassGenerator cls : listOfClasses){
//			program += cls.getFileName()+"." + "singleEntry(" + f + ");\n";
//		}	
		int indx = 0;
		int totalClass = listOfClasses.size();
		for(int i=0; i< noOfMethCalls; i++){
			program += "private static void subEntryMethod"+i+"(){\n";
			for(int k = 0; (k< methCallLimit && indx< totalClass); indx++, k++){
				program += listOfClasses.get(indx).getFileName()+"." + "singleEntry(" + f + ");\n";
			}
			program += "}\n\n";
		}
		
		program += "\n\n";
		
	}
	
	private void appendEntryMethod()
	{
		program += "public static void entryMethod(";
		
		StringBuilder param = new StringBuilder();
		for(int i =0; i< ProgGenUtil.maxNoOfParameters; i++)
			param.append("int i"+i+",");
		
		String st = param.toString();
		st = st.substring(0, st.length()-1);
		st += "){\n";
		
		program += st;
		
		for(int i = 0; i < ProgGenUtil.maxNoOfParameters; i++){
			program += "f"+i+ " = "+ "i"+i+";\n";
		}
		
		program += "\n\n";
		
		// calling class' single-entry method
		
//		StringBuilder fields = new StringBuilder();
//		for(int i = 0; i < ProgGenUtil.maxNoOfParameters; i++){
//			fields.append("f"+i+",");
//		}
//		String f = fields.toString();
//		f = f.substring(0, fields.length() - 1);
//		
//		for(ClassGenerator cls : listOfClasses){
//			program += cls.getFileName()+"." + "singleEntry(" + f + ");\n";
//		}	
		
		for(int i=0; i< noOfMethCalls; i++){
			program += "subEntryMethod"+i+"();\n";
		}
		
		program += "\n}\n";
	}
	
	private void endClass(){
		program += "}";
	}
	
	private void appendMainMethod(){
		program += "public static void main(String[] args){\n entryMethod(";
		
		StringBuilder str = new StringBuilder();
		for(int i =0; i < ProgGenUtil.maxNoOfParameters; i++){
			str.append("Integer.parseInt(args["+ i+ "]),");
		}
		String s = str.toString();
		s = s.substring(0, str.length()-1);
		s += ");\n}";
		
		program += s;	
	
	}
	
	public String toString(){
		return program;
	}
	
	public void generateSingleEntryClass(){
		appendPackageName();
		appendClassName();
		appendFieldNames();
		appendSubMethod();
		appendEntryMethod();
		appendMainMethod();
		endClass();
		
	}
		

}
