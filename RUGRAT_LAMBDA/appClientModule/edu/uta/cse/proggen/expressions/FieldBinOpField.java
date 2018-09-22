package edu.uta.cse.proggen.expressions;

import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.operators.BinaryOperator;

public class FieldBinOpField extends Operand {
	private Operand 		leftOprnd = null;
	private BinaryOperator 	binOp = null;
	private Operand 		rightOprnd = null;

	public FieldBinOpField(Method method, Primitives primitive){

		Random rand = new Random();
		int nested = rand.nextInt(100);

		//nested expression
		if(nested < method.getAssociatedClass().getPercent())
		{
			leftOprnd = new FieldBinOpField(method, primitive);
			binOp = new BinaryOperator(primitive);
			rightOprnd = new FieldBinOpField(method, primitive);
		}
		else
		{
			leftOprnd = FieldGenerator.getRandomField(method.getAssociatedClass(), primitive, method.isStatic());
			binOp = new BinaryOperator(primitive);
			rightOprnd = FieldGenerator.getRandomField(method.getAssociatedClass(), primitive, method.isStatic());
		}

		// removing expressions of the form: (i5-i5) to avoid {i19%(i5-i5)}expr.
		if(leftOprnd.toString().equals(rightOprnd.toString()))
		{
			rightOprnd = new Literal(primitive);
		}

		// for division and modulo operations, keeping only literals in the right expr.
		// i5%i3 => i5%constantValue OR f2/f4 => f2/constantValue

		if(binOp.toString().equals("/") || binOp.toString().equals("%"))
			do{//FIXME: only handles int for now.
				rightOprnd = new Literal(primitive);
			}while(rightOprnd.toString().contains("(0)"));  //avoiding divide by (0) 

	}

	public String toString(){
		return "(" + leftOprnd.toString() + binOp.toString()+ rightOprnd.toString()+ ")";
	}

}
