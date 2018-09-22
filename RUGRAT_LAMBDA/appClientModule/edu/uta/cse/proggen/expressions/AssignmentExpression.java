package edu.uta.cse.proggen.expressions;

import java.util.ArrayList;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Field;
import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.classLevelElements.Type.Primitives;
import edu.uta.cse.proggen.configurationParser.Query;
import edu.uta.cse.proggen.configurationParser.QueryFileParser;
import edu.uta.cse.proggen.configurationParser.QueryResult;
import edu.uta.cse.proggen.nodes.Expression;
import edu.uta.cse.proggen.nodes.Operator;
import edu.uta.cse.proggen.operators.BinaryOperator;
import edu.uta.cse.proggen.statements.PrintStatement;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * var = NormanExpr BinOp NormalExpr
 * @author Ishtiaque_Hussain
 *
 */
public class AssignmentExpression extends Expression 
{
	Field lhs;
	Expression expr = null;
	Expression leftExpr;
	Expression rightExpr;
	String output = "";

	public AssignmentExpression(Method method)
	{
		Random rand = new Random();
		
		//should we use field or variable?
		int OptionVarOrField = rand.nextInt(100)%2;

		if(OptionVarOrField == 0)
		{
			// Introducing any variable
			lhs = VariableGenerator.getRandomizedVariable(method);
		}
		else
		{ 
			//Introducing any field
			lhs = FieldGenerator.getRandomField(method.getAssociatedClass(), method.isStatic());
			
			if(lhs == null)
			{
				//if no field is present, introduce variable
				lhs = VariableGenerator.getRandomizedVariable(method);
			}
		}
		
		// if no variable is present, just use print statements
		if(lhs == null){
			output += new PrintStatement(method).toString();
			return;			
		}
			
		
		Primitives primitive = lhs.getType().getType();
		
		//If the primitive is an object, invoke constructor
		if(primitive == Primitives.OBJECT)
		{
			output += lhs + " = new " + ProgGenUtil.getClassToConstruct(lhs.getType().toString(), method.getClassList()) +"();\n";
			return;
		}

		//Randomly choose to nest operation or not
		int option = rand.nextInt(100);
		if(option > method.getAssociatedClass().getPercent())
		{
			leftExpr = new NormalExpression(method, primitive);
			rightExpr = new NormalExpression(method, primitive);
			Operator binOp = new BinaryOperator(primitive);

			// Removing variable from right expr. if binOp is / or %
			// i6=(i3/2)%(i3-5)  => i6=(i3/2)%constant
			if(binOp.toString().equals("/")|| binOp.toString().equals("%"))
				do{//FIXME: only handles int for now.
					rightExpr = new Literal(primitive);
				}while(rightExpr.toString().contains("(0)"));  //avoiding divide by (0) 

			output += lhs + " = ("+ lhs.getType() +")("+ leftExpr + binOp.toString() + rightExpr +");\n";
		}
		else
		{ 
			if(ProgGenUtil.useQueries)
			{
				if(ProgGenUtil.coinFlip())
				{
					expr = new NormalExpression(method, primitive);

					//we don't want assignments statements like this: i8 = i8
					while(lhs.equals(expr.toString()))
					{
						expr = new NormalExpression(method, lhs.getType().getType());
					}
					output += lhs + " = (" + lhs.getType() + ")" + expr.toString()+";\n";
				}
				else
				{
					System.out.println("Trying to fetch literal from database for : " + lhs.getType());
					StringBuilder builder = new StringBuilder();
					Query query = getQueryForType(primitive);
					if(query == null)
					{
						//no query results
						output += lhs + " = (" + lhs.getType() + ")" + new Literal(primitive) + ";\n";
						return;
					}
					
					builder.append("try{");
					builder.append("ResultSet rs = DBUtil.getDBUtil().executeQuery(");
					builder.append("\"" + query.getQueryString() + "\"");
					builder.append(");\n");
					
					builder.append("rs.last();\n");
					builder.append("int rowToSelect = new Random().nextInt(rs.getRow());\n");
					builder.append("rs.first();\n");
					
					builder.append("for(int rowToSelectCounter=0; rowToSelectCounter<rowToSelect;rowToSelectCounter++)");
					builder.append("{ 	rs.next();	}\n");
					
					ArrayList<QueryResult> queryResultsForType = getColumnNumbersForType(query, primitive);
					if(queryResultsForType.size() == 0)
					{
						//no query results present for expected type.
						output += lhs + " = (" + lhs.getType() + ")" + new Literal(primitive) + ";\n";
						return;
					}
					
					int resultToSelect = new Random().nextInt(queryResultsForType.size());
					QueryResult selectedResult = queryResultsForType.get(resultToSelect);
					
					String result = getValueFromResultSet(primitive, selectedResult);
					if(result == null)
					{
						output += lhs + " = (" + lhs.getType() + ")" + new Literal(primitive) + ";\n";
						return;
					}
					builder.append( lhs + " = (" + lhs.getType() + ")" + result + "\n\n" );
					builder.append( "} catch(Exception e) { e.printStackTrace(); }\n\n" );
					builder.append( "System.out.println(" + lhs + ");\n" );
					output += builder.toString(); 
				}
			}
			else
			{
				expr = new NormalExpression(method, primitive);

				//we don't want assignments statements like this: i8 = i8
				while(lhs.equals(expr.toString()))
				{
					expr = new NormalExpression(method, lhs.getType().getType());
				}
				output += lhs + " = (" + lhs.getType() + ")" + expr.toString()+";\n";
			}
		}
	}
	
	private String getValueFromResultSet(Primitives primitive,
			QueryResult result) {
		boolean fetchUsingSeqNumber = ProgGenUtil.coinFlip();
		if (primitive == Primitives.INT) {
			if (fetchUsingSeqNumber) {
				return "rs.getInt(" + result.getSeqNumber() + ");\n";
			}
			return "rs.getInt(\"" + result.getName() + "\");\n";
		} else if (primitive == Primitives.BYTE) {
			if (fetchUsingSeqNumber) {
				return "rs.getByte(" + result.getSeqNumber() + ");\n";
			}
			return "rs.getByte(\"" + result.getName() + "\");\n";
		} else if (primitive == Primitives.CHAR) {
			if (fetchUsingSeqNumber) {
				return "rs.getString(" + result.getSeqNumber() + ");\n";
			}
			return "rs.getString(\"" + result.getName() + "\");\n";
		} else if (primitive == Primitives.DOUBLE) {
			if (fetchUsingSeqNumber) {
				return "rs.getDouble(" + result.getSeqNumber() + ");\n";
			}
			return "rs.getDouble(\"" + result.getName() + "\");\n";
		} else if (primitive == Primitives.FLOAT) {
			if (fetchUsingSeqNumber) {
				return "rs.getFloat(" + result.getSeqNumber() + ");\n";
			}
			return "rs.getFloat(\"" + result.getName() + "\");\n";
		} else if (primitive == Primitives.LONG) {
			if (fetchUsingSeqNumber) {
				return "rs.getLong(" + result.getSeqNumber() + ");\n";
			}
			return "rs.getLong(\"" + result.getName() + "\");\n";
		} else if (primitive == Primitives.SHORT) {
			if (fetchUsingSeqNumber) {
				return "rs.getShort(" + result.getSeqNumber() + ");\n";
			}
			return "rs.getShort(\"" + result.getName() + "\");\n";
		} else if (primitive == Primitives.STRING) {
			if (fetchUsingSeqNumber) {
				return "rs.getString(" + result.getSeqNumber() + ");\n";
			}
			return "rs.getString(\"" + result.getName() + "\");\n";
		}
		return null;
	}
	
	private ArrayList<QueryResult> getColumnNumbersForType(Query query, Primitives primitive)
	{
		ArrayList<QueryResult> list = new ArrayList<QueryResult>();
		
		ArrayList<QueryResult> results = query.getResults();
		
		for(QueryResult result : results)
		{
			if(result.getType().equals(primitive))
			{
				list.add(result);
			}
		}
		return list;
	}
	
	private Query getQueryForType(Primitives primitive)
	{
		ArrayList<Query> queryList = QueryFileParser.queries;
		int noOfQueries = queryList.size();
		Random random = new Random();
		
		int index = random.nextInt(noOfQueries);
		int count = 100;
		Query query = queryList.get(index);
				
		while(!query.getResultTypes().contains(primitive)
				&& count > 0)
		{
			query = queryList.get(random.nextInt(noOfQueries));
			count--;
		}
		
		if(count == 0)
		{
			//no query was found with expected type in its results.
			return null;
		}
		
		return query;
	}
	
	public String toString()
	{
		return output;
	}
}
