package edu.uta.cse.proggen.expressions;

import java.util.ArrayList;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Field;
import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;

/**
 * Class with utility methods for fetching fields from a generated class.
 * 
 * @author balamurugan
 *
 */
public class FieldGenerator
{
	/**
	 * Returns a random field for a given class.
	 * 
	 * @param generator
	 * @param isStatic
	 * @return
	 */
	public static Field getRandomField(ClassGenerator generator, boolean isStatic){
		Field field;
		Random rand = new Random();
		
		// if no fields for this class is available
		if(generator.getFields().size()== 0) 
			return null;
		
		field = generator.getFields().get(
				rand.nextInt(generator.getFields().size()));
		int count = 5000;
		
		while(field.isStatic() != isStatic
				&& count > 0)
		{
			field = generator.getFields().get(
				rand.nextInt(generator.getFields().size()));
			count--;
		}
		
		if(field.isStatic() == isStatic
				&& count > 0)
		{
			// adding to the used variable Set
			generator.getUsedFields().add(field);
			return field;
		}
		
		return null;
	}
	
	/**
	 * returns a random field of a given type for a generated class.
	 * 
	 * @param generator
	 * @param primitive
	 * @param isStatic
	 * @return
	 */
	public static Operand getRandomField(ClassGenerator generator, 
			Primitives primitive, boolean isStatic)
	{
		Field field;
			
		field = getField(generator.getFields(), primitive, isStatic);
		
		if(field == null)
		{
			return new Literal(primitive);
		}
		
		generator.getUsedFields().add(field);
		return field;
	}
	
	private static Field getField(ArrayList<Field> fields, 
			Primitives primitive, 
			boolean isStatic) 
	{
		ArrayList<Field> typedFieldList = new ArrayList<Field>();
		
		for(Field var : fields)
		{
			if(var.getType().getType() == primitive
					&& var.isStatic() == isStatic)
			{
				typedFieldList.add(var);
			}
		}
		
		if(typedFieldList.isEmpty())
		{
			return null;
		}
		
		int index = new Random().nextInt(typedFieldList.size());
		return typedFieldList.get(index);
	}

	/**
	 * Returns a randomized object of a given type belonging
	 * to a generated class.
	 * 
	 * @param generator
	 * @param type
	 * @return
	 */
	public static Operand getRandomizedObjectForType(
			ClassGenerator generator, 
			Type type) 
	{
		ArrayList<Field> fieldList = generator.getFields();
		ArrayList<Field> typedFieldList = new ArrayList<Field>();
		
		for(Field field : fieldList)
		{
			if(field.getType().equals(type))
			{
				typedFieldList.add(field);
			}
		}
		
		if(typedFieldList.size() == 0)
		{
			return new Literal(Primitives.OBJECT);
		}
		
		int index = new Random().nextInt(typedFieldList.size());
		return typedFieldList.get(index);
	}
}
