package edu.uta.cse.proggen.expressions;

import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Expression;
import edu.uta.cse.proggen.operators.BinaryOperator;

public class VariableBinOpField extends Expression {
	private Expression leftOprnd = null;
	private BinaryOperator binOp = null;
	private Expression rightOprnd = null;

	public VariableBinOpField(Method method, Primitives primitive){

		Random rand = new Random();


		int option = rand.nextInt(100)%2;

		int nested = 0;

		switch(option){
		case 0: //var binOp field
			nested = rand.nextInt(100);
			if(nested < method.getAssociatedClass().getPercent()){
				leftOprnd = new VariableBinOpField(method, primitive);
				binOp = new BinaryOperator(primitive);
				rightOprnd = new VariableBinOpField(method, primitive);
			}
			else{
				leftOprnd = VariableGenerator.getRandomizedVariable(method, primitive);
				binOp = new BinaryOperator(primitive);
				rightOprnd = FieldGenerator.getRandomField(method.getAssociatedClass(), primitive, method.isStatic());
			}
			break;
			
		case 1: // field binOp var
			nested = rand.nextInt(100);
			if(nested < method.getAssociatedClass().getPercent()){
				leftOprnd = new VariableBinOpField(method, primitive);
				binOp = new BinaryOperator(primitive);
				rightOprnd = new VariableBinOpField(method, primitive);
			}
			else
			{
				leftOprnd = VariableGenerator.getRandomizedVariable(method, primitive);
				binOp = new BinaryOperator(primitive);
				rightOprnd = FieldGenerator.getRandomField(method.getAssociatedClass(), primitive, method.isStatic());
			}
			break;
		}

		// for division and modulo operations, keeping only literals in the right expr.
		// i5%i3 => i5%constantValue OR f2/f4 => f2/constantValue

		if(binOp.toString().equals("/") || binOp.toString().equals("%")){
				do{//FIXME: only handles int for now.
					rightOprnd = new Literal(primitive);
				}while(rightOprnd.toString().contains("(0)"));  //avoiding divide by (0) 
		}

	}

	public String toString(){
		return "(" + leftOprnd.toString() + binOp.toString()+ rightOprnd.toString()+ ")";
	}

}
