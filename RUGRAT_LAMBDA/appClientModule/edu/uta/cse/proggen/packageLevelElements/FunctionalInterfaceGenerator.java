package edu.uta.cse.proggen.packageLevelElements;

import java.util.ArrayList;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.MethodSignature;
import edu.uta.cse.proggen.util.ProgGenUtil;

public class FunctionalInterfaceGenerator extends Generator {
	private MethodSignature methodSignature;
	private String name;

	public FunctionalInterfaceGenerator(String name, ArrayList<ClassGenerator> classList) {
		this.name = name;
		Method method = Method.generateMethodForInterface(ProgGenUtil.maxNoOfParameters, classList,
				name + "AbstractMethod");
		methodSignature = method.getMethodSignature();

	}

	public MethodSignature getMethodSignatures() {
		return methodSignature;
	}

	public String getName() {
		return name;
	}

	public String getPackageName() {
		return "package com.accenture.lab.carfast.test;\n\n\n";
	}

	public String toString() {
		StringBuilder builder = new StringBuilder(getPackageName());
		builder.append("public interface");
		builder.append(" ");
		builder.append(name);
		builder.append("\n{\n");
		builder.append(methodSignature);
		builder.append(";\n");

		builder.append("}");
		return builder.toString();
	}
}
