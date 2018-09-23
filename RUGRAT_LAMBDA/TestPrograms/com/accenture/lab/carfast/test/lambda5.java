package com.accenture.lab.carfast.test;


public class lambda5 extends lambda4 implements lambdaInterface2, lambdaInterface3 {
static String f0;
static String f1;
String f2;
char f3;
static String f4;
int f5;
static int f6;


public String lambdaInterface2Method0(int recursionCounter, int var1){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (String)"atapmzdanvmxaeusejdfixwqa";

}
else
{
 recursionCounter--; 
}
return (String)"bfonoqtkivjjijkrxrzhyuldbtzdcfz";

}

public char lambdaInterface3Method0(int recursionCounter, char var1){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (char)var1;

}
else
{
 recursionCounter--; 
}
return (char)var1;

}

public int lambda5method0(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (int)(int)(4);

}
else
{
 recursionCounter--; 
}
return (int)(int)(1);

}

public static Object lambda5method1(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (Object)null;

}
else
{
 recursionCounter--; 
}
return (Object)null;

}


public static void main(String args[]){
lambda5 obj = new lambda5();
obj.lambdaInterface2Method0(0,(int)(0));
obj.lambdaInterface3Method0(0,'b');
obj.lambda5method0(0);
lambda5method1(1);
}

public static void singleEntry(int i0,int i1,int i2){
lambda5 obj = new lambda5();
obj.lambdaInterface2Method0(0,i2);
obj.lambdaInterface3Method0(1,'b');
obj.lambda5method0(1);
lambda5method1(1);
}

}