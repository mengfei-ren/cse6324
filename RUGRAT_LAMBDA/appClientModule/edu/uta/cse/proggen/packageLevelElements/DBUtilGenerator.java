package edu.uta.cse.proggen.packageLevelElements;

import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * Class that generates the contents of DBUtil in the generated application.
 * 
 * @author balamurugan
 *
 */
public class DBUtilGenerator 
{
	private String packageString = "package com.accenture.lab.carfast.test;\n\n\n";
	private String importString = "import java.sql.Connection;\nimport java.sql.DriverManager;\nimport java.sql.PreparedStatement;\n" +
			"import java.sql.ResultSet;\nimport java.sql.SQLException;\nimport java.util.Properties;\n\n\n\n";

	private String classnameString = "public class DBUtil{\n";
	private String membersString = "private static	DBUtil	dbUtil;\nprivate Connection	connection;\n";	
	String constructorString = "//singleton implementation for database resources.\n"+
				"private DBUtil(){\n" +	
					"try{\n" +
						"Class.forName(\"" +  ProgGenUtil.driver + "\").newInstance();\n" +
						"Properties props = new Properties();\n" +
						"props.put(\"user\",\"" +  ProgGenUtil.dbUserName + "\");\n" +
						"props.put(\"password\",\"" + ProgGenUtil.password + "\");\n" +
						"connection = DriverManager.getConnection(\"" + ProgGenUtil.dbUrl + "\", props);\n}\n" +
				"catch (Exception e) {\n" +
					"e.printStackTrace(); System.exit(1);\n}\n}\n\n";
	
	private String methodsString 
		= "public static DBUtil getDBUtil(){\n"+
			"if(dbUtil == null)	{\n" +
				"dbUtil = new DBUtil();\n}\n" +
			"return dbUtil;\n }\n\n" +
			"public ResultSet executeQuery(String sql){\n" +
				"try {\n" +
					"PreparedStatement ps = connection.prepareStatement(sql);\n" +
					"return ps.executeQuery();\n}\n" +
				"catch (SQLException e) {\n" +
					"e.printStackTrace();\n" +
					"return null;\n}\n}\n\n";
	
	private String endOfClassString = "\n}\n";
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(packageString);
		builder.append(importString);
		builder.append(classnameString);
		builder.append(membersString);
		builder.append(constructorString);
		builder.append(methodsString);
		builder.append(endOfClassString);
		
		return builder.toString();
	}
}
