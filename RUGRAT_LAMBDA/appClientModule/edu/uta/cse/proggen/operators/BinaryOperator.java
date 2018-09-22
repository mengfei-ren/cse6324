package edu.uta.cse.proggen.operators;

import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Operator;

/**
 * Binary Operator : +, -, *, /, %
 * @author Ishtiaque_Hussain
 *
 */

public class BinaryOperator extends Operator {

	private String 	symbol = null;

	public BinaryOperator(Primitives primitive){
		
		if(primitive == Primitives.STRING)
		{
			symbol = "+";
		}
		else
		{
			Random rand = new Random();
			int option = rand.nextInt(8);
			switch(option){
				case 0: symbol = "%"; break;
				case 1: symbol = "-"; break;
				case 2: symbol = "*"; break;
				case 3: symbol = "+"; break;
				case 4: symbol = "-"; break;
				case 5: symbol = "*"; break;
				case 6: symbol = "+"; break;
				case 7: symbol = "/"; break;
			}
		}
	}

	public String toString(){
		return symbol;
	}

}
