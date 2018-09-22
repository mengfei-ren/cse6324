package edu.uta.cse.proggen.classLevelElements;

import java.util.ArrayList;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.nodes.Operand;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * This class represents a class variable in the generated class
 * 
 * @author balamurugan
 *
 */
public class Field
	extends Operand
{
	protected 	Type 			type;
	protected 	String			name;
	protected	boolean 		isStatic;
	protected	boolean			isArray;
	protected	int				arraySize;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		
		if(! (obj instanceof Field) )
			return false;
		
		Field obj1 = (Field)obj;
		
		if(!this.type.equals(obj1.type))
			return false;
		
		if(!this.name.equals(obj1.name))
			return false;
		
		if(this.isStatic != obj1.isStatic)
			return false;
		
		if(this.isArray != obj1.isArray)
			return false;
		
		if(this.arraySize != obj1.arraySize)
			return false;
		
		return true;
	}

	/**
	 * creates a random field with the given name. the field can be 
	 * of a user-specified type or one of the generated types.
	 * 
	 * @param name - of the field
	 * @param classList - list of generated types
	 */
	protected Field(String name, ArrayList<ClassGenerator> classList)
	{	
		this.name = name;
		this.arraySize = -1;
		Primitives primitive = ProgGenUtil.getRandomizedPrimitive();
		String classname = "";
		
		if(primitive == Primitives.OBJECT)
		{
			int randomIndex = new Random().nextInt(classList.size());
			classname = classList.get(randomIndex).getFileName();
		}
		
		type = new Type(primitive, classname);
		randomizeIsStatic();
		randomizeIsArray();
	}
	
	/**
	 * Constructs a field of the specified primitive type
	 * @param name : of the field
	 * @param primitive : type of the field
	 */
	protected Field(String name, Primitives primitive)
	{
		this.name = name;
		this.type = new Type(primitive, "");
	}
	
	private void randomizeIsStatic()
	{
		isStatic = ProgGenUtil.coinFlip();
	}
	
	private void randomizeIsArray()
	{
		if(ProgGenUtil.allowArray.equals("yes"))
			isArray = ProgGenUtil.coinFlip();
		else
			isArray = false;
	}
	
	/**
	 * Randomly selects the type of the field from { CHAR, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, STRING, OBJECT }.
	 * If it's OBJECT type; only then it selects one class from the 'classList' as the field's type.
	 * @param name Name of the field.
	 * @param classList Helper list containing all the declared classes.
	 * @return A field of primitive type or of a declared class type.
	 */
	public static Field generateField(String name, ArrayList<ClassGenerator> classList)
	{
		return new Field(name, classList);
	}
	
	public Type getType() 
	{
		return type;
	}
	
	public boolean isStatic() 
	{
		return isStatic;
	}

	/**
	 * returns the field declaration.
	 * 
	 * eg: int i;
	 *     char str[] = new char[26];
	 *     
	 * @return : the field declaration String
	 */
	public String getFieldDeclaration()
	{
		String str = "";
		
		if(isStatic)
		{
			str += "static ";
		}
		
		if(!isArray)
		{
			str+= type.toString() + " " + name;
		}
		else
		{
			this.arraySize = ProgGenUtil.getRandomArraySize(); 
			str += type.toString() + "[] " + name + "= new " + type.toString() +"[" 
			+ this.arraySize + "]"; 
		}
		
		return str;
	}
	
	/**
	 * Returns field name.
	 * e.g.:
	 * Array type: a[5]
	 * Non array type: a 
	 */
	public String toString()
	{
		this.literal = "";
		
		if(!isArray)
		{
			literal += name;
		}
		else
		{
			int arrayIndex = ProgGenUtil.getRandomIntInRange(arraySize);
			literal += name + "[" + arrayIndex + "]"; 
		}
		
		return literal;
	}
	
	public int getArraySize()
	{
		return arraySize;
	}

	public String getName() {
		return name;
	}
}
