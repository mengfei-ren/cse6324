package edu.uta.cse.proggen.classLevelElements;

/**
 * This class represents the type for every entity(class variables, local variables,
 * parameters, return values) in the generated class.
 * 
 * @author balamurugan
 *
 */
public class Type 
{
	/**
	 * The primitives enum class encapsulates the allowed primitives in RUGRAT which
	 * also includes OBJECT.
	 * 
	 * OBJECT can be any one of the generated types.
	 * 
	 * @author balamurugan
	 *
	 */
	public enum Primitives
	{ 
		CHAR, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, STRING, OBJECT;
		
		public String toString()
		{
			switch (this) 
			{
				case CHAR:
					return "char";

				case BYTE:
					return "byte";

				case SHORT:
					return "short";

				case INT:
					return "int";

				case LONG:
					return "long";

				case FLOAT:
					return "float";

				case DOUBLE:
					return "double";

				case STRING:
					return "String";

				default:
					return "Object";
			}
		}
	}
	
	Primitives	p;
	String 		name;
		
	public Type(Primitives primitive, 
			String name)
	{
		this.p = primitive;
		this.name = name;
	}
	
	public Primitives getType()
	{
		return p;
	}
	
	public String toString()
	{
		//if its an object return the classname.
		if(p.equals(Primitives.OBJECT))
		{
			return name;
		}
		
		return p.toString();
	}
	
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		
		if(! (obj instanceof Type) )
			return false;
		
		Type obj1 = (Type)obj;
		
		if(this.toString() != obj1.toString())
			return false;
		
		return true;
	}
	
	public static Primitives reverseLookup(String primitive)
	{
		if(primitive.equalsIgnoreCase("char"))
		{
			return Primitives.CHAR;
		}
		else if(primitive.equalsIgnoreCase("byte"))
		{
			return Primitives.BYTE;
		}
		else if(primitive.equalsIgnoreCase("short"))
		{
			return Primitives.SHORT;
		}
		else if(primitive.equalsIgnoreCase("int"))
		{
			return Primitives.INT;
		}
		else if(primitive.equalsIgnoreCase("long"))
		{
			return Primitives.LONG;
		}
		else if(primitive.equalsIgnoreCase("float"))
		{
			return Primitives.FLOAT; 
		}
		else if(primitive.equalsIgnoreCase("double"))
		{
			return Primitives.DOUBLE;
		}
		else if(primitive.equals("String"))
		{
			return Primitives.STRING;
		}
		else
		{
			return Primitives.OBJECT;
		}
	}
}
