package edu.uta.cse.proggen.start;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;

import edu.uta.cse.proggen.classLevelElements.Method;
import edu.uta.cse.proggen.configurationParser.ConfigurationXMLParser;
import edu.uta.cse.proggen.packageLevelElements.ClassGenerator;
import edu.uta.cse.proggen.packageLevelElements.DBUtilGenerator;
import edu.uta.cse.proggen.packageLevelElements.InterfaceGenerator;
import edu.uta.cse.proggen.packageLevelElements.SingleEntryGenerator;
import edu.uta.cse.proggen.packageLevelElements.TreeOfSingleEntryGenerator;
import edu.uta.cse.proggen.util.ProgGenUtil;

/**
 * Starting point of the ProgGen tool.
 *
 * @author Ishtiaque_Hussain
 *
 */
public class Start 
{
	
	private static String pathToDir = "";
	
	public static String getPathToDir(){
		return pathToDir;
	}
	
	public static void main(String[] args) 
	{
		/* For ant script: specify 'config.xml' file and output directory */		
		if(args.length == 1){
			pathToDir = args[0] + File.separator;			
		}
		
		
		/* List of generated class objects: ClassGenerators */		
		ArrayList<ClassGenerator> 		list 			= new ArrayList<ClassGenerator>();
		ArrayList<InterfaceGenerator>	interfaceList	= new ArrayList<InterfaceGenerator>(); 
		int numberOfClasses = 0;
		int maxInheritanceDepth = 0;
		int noOfInheritanceChains = 0;
		int noOfInterfaces = 0;
		int maxInterfacesToImplement = 0;
		
		/* Set of generated classes, it's updated in ClassGenerator.generate() */
		HashSet<String> generatedClasses = new HashSet<String>();
		HashSet<String> preGeneratedClasses = new HashSet<String>();
		
			try
			{
				String className = ConfigurationXMLParser.getProperty("classNamePrefix");
				int totalLoc = ConfigurationXMLParser.getPropertyAsInt("totalLOC");
																
				numberOfClasses = ConfigurationXMLParser.getPropertyAsInt("noOfClasses");
				maxInheritanceDepth = ConfigurationXMLParser.getPropertyAsInt("maxInheritanceDepth"); // e.g. 3
				noOfInheritanceChains = ConfigurationXMLParser.getPropertyAsInt("noOfInheritanceChains"); // 2 => "A-B-C" ; "E-F-G"
				noOfInterfaces = ConfigurationXMLParser.getPropertyAsInt("noOfInterfaces");
				maxInterfacesToImplement = ConfigurationXMLParser.getPropertyAsInt("maxInterfacesToImplement");
				
				if(numberOfClasses < (maxInheritanceDepth * noOfInheritanceChains)) 
				{
					System.out.println("Insufficent number of classes. Should be atleast: " 
							+ maxInheritanceDepth*noOfInheritanceChains);
					System.exit(1);
				}
				
				LinkedHashSet<String> classList = new LinkedHashSet<String>();
								
				for(int i=0; i<numberOfClasses; i++)
				{
					classList.add(className+i);
				}
				//E.g., {[2,5,6], [0,1,4]}
				ArrayList<ArrayList<Integer>> inheritanceHierarchies = new ArrayList<ArrayList<Integer>>();
				
				inheritanceHierarchies = ProgGenUtil.getInheritanceList(
						noOfInheritanceChains, 
						maxInheritanceDepth, 
						numberOfClasses
						);
				
				for(int i=0; i<numberOfClasses; i++)
				{
					//Ishtiaque: All classes have equal number of variables, methods, etc. Should we change it?	
					// classes are like A1, A2, etc where A=<UserDefinedName> 
					//Bala: All such cases are handled in the ClassGenerator. It generates arbitrary number of
					// fields, methods. Only constraint is it should override all the methods of the interfaces
					// it implements.
					list.add( new ClassGenerator(
							className + i, 
							totalLoc/numberOfClasses,
							null) 
					);
				}
				
				File directory = new File( pathToDir+ 
						"TestPrograms" + File.separator +
						"com" + File.separator +
						"accenture" + File.separator + "lab" + 
						File.separator + "carfast"
						+ File.separator + "test");					
				
				if(!directory.exists())
				{
					System.out.println(directory.mkdirs());
				}
				
				for(int i=0; i<noOfInterfaces; i++)
				{
					InterfaceGenerator generator = new InterfaceGenerator(
							className+"Interface"+i, 
							list);
					interfaceList.add(generator);
					writeToFile(generator);
				}
				
				establishClassRelationships(inheritanceHierarchies, list);
				
				establishClassInterfaceRelationships(interfaceList, list);
			}
			catch (NumberFormatException e) 
			{
				System.out.println("Please enter integer values for arguments that expect integers!!!");
				e.printStackTrace();
				System.exit(1);
			}
			
			//do pre-generation for classes
			//pre-generation determines the class members variables and method signatures
			for(ClassGenerator generator : list)
			{
				generator.preGenerateForMethodSignature(list, preGeneratedClasses);
			}
			
			for(ClassGenerator generator : list)
			{
				//Ishtiaque: How can 'generatedClasses' contain any of the ClassGenerator objects from the list? 
				//Bala: classes are generated semi-recursively. The classes will invoke class generation on the
				//super class. The current class will be generated only after all its ancestor classes are generated.
				//We do not want to regenerate the ancestor classses and make stale the information used by its sub-classes
				//based on the earlier version.
				if(!generatedClasses.contains(generator.getFileName()))
				{
					//call generate to construct the class contents
					generator.generate(list, generatedClasses, preGeneratedClasses);
				}
				writeToFile(generator);
			}
			
			//generate DBUtil only if useQueries is TRUE
			if(ProgGenUtil.useQueries)
			{
				DBUtilGenerator dbUtilGenerator = new DBUtilGenerator();
				writeToFile(dbUtilGenerator);
			}
			
			/* writing SingleEntry class */
			
//			SingleEntryGenerator singleEntryGen = new SingleEntryGenerator(list);
//			String className = ConfigurationXMLParser.getProperty("classNamePrefix")+"Start";
//			write(className, singleEntryGen.toString());
			
			TreeOfSingleEntryGenerator treeSingleEntryGen = new TreeOfSingleEntryGenerator(list, pathToDir);
			treeSingleEntryGen.generateTreeOfSingleEntryClass();
			
			//write the reachability matrix
			
			if(ConfigurationXMLParser.getProperty("doReachabilityMatrix").equals("no"))
				return;
			
			
			ArrayList<Method> methodListAll = new ArrayList<Method>();
			for(ClassGenerator generator : list)
			{
				methodListAll.addAll(generator.getMethodList());
			}

			StringBuilder builder = new StringBuilder();
			builder.append("Name, ");
			
			for(Method method : methodListAll)
			{
				builder.append(
						method.getAssociatedClass().getFileName() + "." + method.getName());
				builder.append(", ");
			}
			builder.append("\n");
			
			for(Method method : methodListAll)
			{
				builder.append(method.getAssociatedClass().getFileName() 
						+ "." + method.getName());
				builder.append(", ");
				for(Method calledMethod : methodListAll)
				{
					if(method.getCalledMethodsWithClassName().contains(
							calledMethod.getAssociatedClass().getFileName() + "." + calledMethod.getName()))
					{
						builder.append("1, ");
					}
					else
					{
						builder.append("0, ");
					}
				}
				builder.append("\n");
			}
			writeReachabilityMatrix(builder.toString());
	}
	
	private static void writeReachabilityMatrix(String matrix)
	{
		FileOutputStream fos = null;
		BufferedOutputStream outstream = null;
		
		try
		{
			fos = new FileOutputStream(new File(
					"ReachabilityMatrix.csv"));
			outstream = new BufferedOutputStream(fos);
			System.out.println("Writing reachability matrix...");
			outstream.write(matrix.getBytes());
		}
		catch(Exception e)
		{
			System.out.println("Error writing reachability matrix!");
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if (outstream != null) 
				{
					outstream.flush();
					outstream.close();
				}
			} 
			catch (IOException e) 
			{
				System.out.println("Error closing output filestream");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	private static void writeToFile(DBUtilGenerator generator)
	{
		if(generator == null)
		{
			System.out.println("DBUtil generator null");
			return;
		}
		write("DBUtil", generator.toString());
	}
	
	private static void writeToFile(ClassGenerator generator)
	{
		if(generator == null)
		{
			return;
		}
		String filename = generator.getFileName();
		write(filename, generator.toString());
	}
	
	private static void writeToFile(InterfaceGenerator generator)
	{
		if(generator == null)
		{
			return;
		}
		write(generator.getName(), generator.toString());
	}
	
	private static void write(String filename, String contents)
	{
		FileOutputStream fos = null;
		BufferedOutputStream outstream = null;

		try {
			fos = new FileOutputStream(new File( pathToDir+
					"TestPrograms" + File.separator +
					"com" + File.separator +
					"accenture" + File.separator + "lab" + 
					File.separator + "carfast"
					+ File.separator + "test"
					+ File.separator + filename + ".java"));
			
			outstream = new BufferedOutputStream(fos);

			// To let the user know RUGRAT is working...
			System.out.println("Writing to file...." + filename);

			outstream.write(contents.getBytes());

			// Successfully written
			System.out.println("Successfully written.");
		} catch (FileNotFoundException e) {
			System.out.println("Error writing out class to .java file : "
							+ filename);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out
					.println("Error writing out class to .java file : "
							+ filename);
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
					if (outstream != null) {
						outstream.flush();
						outstream.close();
					}
				} catch (IOException e) {
				System.out.println("Error closing output filestream");
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private static void establishClassInterfaceRelationships(
			ArrayList<InterfaceGenerator> interfaceList,
			ArrayList<ClassGenerator> list)
	{
		int numberOfInterfaces = interfaceList.size(); 
		int maxInterfacesToImplement = ConfigurationXMLParser.getPropertyAsInt("maxInterfacesToImplement");
		if(numberOfInterfaces == 0)
		{
			System.out.println("No interfaces generated!");
			return;
		}
		
		for(ClassGenerator generator : list)
		{
			ArrayList<InterfaceGenerator> generatorInterfaceList = generator.getInterfaceList();
			Random random = new Random();
			
			//A class can implement 0 or more interfaces.
			int numberOfInterfacesToImplement = random.nextInt(maxInterfacesToImplement);
			
			for(int j=0; j<numberOfInterfacesToImplement; j++)
			{
				InterfaceGenerator interfaceGenerator = interfaceList.get(random.nextInt(interfaceList.size()));
				if(generatorInterfaceList.contains(interfaceGenerator))
				{
					continue;
				}
				generatorInterfaceList.add(interfaceGenerator);
			}
		}
	}
	
	/*
	 * First class has no superclass.
	 * E.g., A-B-C
	 * A has no superclass. But A is superclass of B,...
	 */

	private static void establishClassRelationships(
			ArrayList<ArrayList<Integer>> inheritanceHierarchies, // E.g., <{2,3,7}, {4, 5, 1},...>
			ArrayList<ClassGenerator> classes) 
	{
		for(ArrayList<Integer> hierarchy: inheritanceHierarchies)
		{
			int superClassIndex = -1;
			for(int index : hierarchy)
			{
				if(superClassIndex == -1)
				{
					superClassIndex = index;
					continue;
				}
				
				classes.get(index).setSuperClass(classes.get(superClassIndex));
				superClassIndex = index;
			}
		}
	}
}
