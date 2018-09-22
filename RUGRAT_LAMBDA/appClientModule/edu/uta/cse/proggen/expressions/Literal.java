package edu.uta.cse.proggen.expressions;

import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * This returns a literal except 0 {}
 * @author Ishtiaque_Hussain
 *
 */
public class Literal 
	extends Operand 
{
	final char[] alphabets = { 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public Literal(Primitives primitive)
	{
		assignLiteralForType(primitive);
	}

	private void assignLiteralForType(Primitives primitive)
	{
		Random random = new Random();
		
		switch(primitive)
		{
			case CHAR:
				literal = "'" +
						new Character(alphabets[random.nextInt(26)]).toString() +
						"'";
				break;
				
			case BYTE:
				byte[] bytes = new byte[100];
				random.nextBytes(bytes);
				byte b = bytes[random.nextInt(99)];
				literal = "(byte)(" + new Byte(b).toString() + ")";
				break;
				
			case SHORT:
				short shortVal = (short)random.nextInt(32768);
				literal = "(short)(" + shortVal + ")";
				break;
			
			case INT:
				literal = "(int)(" + (new Integer(random.nextInt(ProgGenUtil.integerMaxValue)).toString()) + ")";
				break;
			
			case LONG:
				//long literals can cause out-of-range exceptions. using compatible int instead
				literal = "(long)(" + new Long(random.nextInt(ProgGenUtil.integerMaxValue)).toString() + ")";
				break;
				
			case FLOAT:
				literal = "(float)(" + new Float(random.nextFloat()).toString() + ")";
				break;
				
			case DOUBLE:
				literal = "(double)(" + new Double(random.nextDouble()).toString() + ")";
				break;
				
			case STRING:
				StringBuilder builder = new StringBuilder();
				int max = random.nextInt(100)+2;				
				for (int i = 0; i < max; i++)
				{
					builder.append(alphabets[random.nextInt(26)]);
				}
				literal = "\"" + builder.toString() + "\"";
				break;
			
			default:
				literal = "null";
		}
	}
}
