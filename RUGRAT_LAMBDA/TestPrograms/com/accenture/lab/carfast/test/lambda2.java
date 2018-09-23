package com.accenture.lab.carfast.test;


public class lambda2 {
static String f0;
static lambda1 f1;
static int f2;
String f3;
static char f4;


public static int lambda2method0(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (int)(int)(5);

}
else
{
 recursionCounter--; 
}
for(int i = 0; i < 1; i++){
 ()->System.out.println("No argument expression");}
return (int)(int)(3);

}

public static Object lambda2method1(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (Object)null;

}
else
{
 recursionCounter--; 
}
System.out.println("lambda2 - lambda2method1- LineInMethod: 10");
return (Object)null;

}


public static void main(String args[]){
lambda2 obj = new lambda2();
lambda2method0(1);
lambda2method1(1);
}

public static void singleEntry(int i0,int i1,int i2){
lambda2 obj = new lambda2();
lambda2method0(0);
lambda2method1(0);
}

}