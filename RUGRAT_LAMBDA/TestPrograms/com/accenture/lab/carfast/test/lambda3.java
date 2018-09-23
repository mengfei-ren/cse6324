package com.accenture.lab.carfast.test;


public class lambda3 extends lambda2 {
String f0;
static int f1;
static char f2;
String f3;
String f4;
int f5;
lambda2 f6;
String f7;
static char f8;


public int lambda3method0(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (int)(int)(2);

}
else
{
 recursionCounter--; 
}
for(int i = 0; i < 1; i++){
 ()->System.out.println("No argument expression");}
return (int)(int)(1);

}

public char lambda3method1(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (char)'y';

}
else
{
 recursionCounter--; 
}
System.out.println("lambda3 - lambda3method1- LineInMethod: 7");
return (char)'b';

}


public static void main(String args[]){
lambda3 obj = new lambda3();
obj.lambda3method0(1);
obj.lambda3method1(0);
}

public static void singleEntry(int i0,int i1,int i2){
lambda3 obj = new lambda3();
obj.lambda3method0(0);
obj.lambda3method1(1);
}

}