package com.accenture.lab.carfast.test;


public class lambda9 {
static String f0;
lambda6 f1;
lambda1 f2;
int f3;
static char f4;
static String f5;
char f6;


public char lambda9method0(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (char)'s';

}
else
{
 recursionCounter--; 
}
System.out.println("lambda9 - lambda9method0- LineInMethod: 11");
return (char)'u';

}

public char lambda9method1(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (char)'z';

}
else
{
 recursionCounter--; 
}
System.out.println("lambda9 - lambda9method1- LineInMethod: 13");
return (char)'m';

}


public static void main(String args[]){
lambda9 obj = new lambda9();
obj.lambda9method0(1);
obj.lambda9method1(1);
}

public static void singleEntry(int i0,int i1,int i2){
lambda9 obj = new lambda9();
obj.lambda9method0(1);
obj.lambda9method1(1);
}

}