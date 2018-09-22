package edu.uta.cse.proggen.operators;

import java.util.Random;

import edu.uta.cse.proggen.nodes.Operator;

/**
 * Conjunct operators: &&, ||, &, |
 * @author Ishtiaque_Hussain
 *
 */
public class ConjunctOperator extends Operator {
	private String symbol = null;

	public ConjunctOperator(){
		Random rand = new Random();
		int option = rand.nextInt(4);
		switch(option){
			case 0: symbol = "&&"; break;
			case 1: symbol = "||"; break;
			case 2: symbol = "&&"; break;
			case 3: symbol = "&&"; break;
		}
	}

	public String toString(){
		return symbol;
	}
}
