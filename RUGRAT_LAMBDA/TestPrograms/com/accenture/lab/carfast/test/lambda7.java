package com.accenture.lab.carfast.test;


public class lambda7 {
char f0;
static int f1;
static String f2;
static String f3;
int f4;
String f5;


public static char lambda7method0(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (char)'j';

}
else
{
 recursionCounter--; 
}
for(int i = 0; i < 4; i++){
 System.out.println("lambda7 - lambda7method0- LineInMethod: 9");
}
return (char)'j';

}

public static int lambda7method1(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (int)(int)(0);

}
else
{
 recursionCounter--; 
}
System.out.println("lambda7 - lambda7method1- LineInMethod: 12");
return (int)(int)(8);

}


public static void main(String args[]){
lambda7 obj = new lambda7();
lambda7method0(0);
lambda7method1(1);
}

public static void singleEntry(int i0,int i1,int i2){
lambda7 obj = new lambda7();
lambda7method0(1);
lambda7method1(0);
}

}