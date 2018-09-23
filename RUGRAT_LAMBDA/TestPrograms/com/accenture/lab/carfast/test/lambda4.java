package com.accenture.lab.carfast.test;


public class lambda4 {
static char f0;
int f1;
int f2;
static char f3;
int f4;


public static Object lambda4method0(int recursionCounter, int var1){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (Object)null;

}
else
{
 recursionCounter--; 
}
switch((var1%(int)(5))){
case 0:
var1 = (int)(4);
 break;
case 1:
lambda4method1(recursionCounter,'u'); break;
case 2:
(var1,var1)->System.out.println("Values = v1 :"+var1+", v2:"+var1); break;
case 3:
System.out.println("lambda4 - lambda4method0- LineInMethod: 13");
 break;
case 4:
var1 = (int)((var1-(int)(6))/(int)(1));
 break;
case 5:
var1 = (int)((var1*(int)(8))+(var1%(int)(8)));
 break;
case 6:
f3 = (char)(('x'*'t')-('o'-'m'));
 break;
case 7:
(var1,var1)->System.out.println("Values = v1 :"+var1+", v2:"+var1); break;
case 8:
System.out.println("lambda4 - lambda4method0- LineInMethod: 29");
 break;
default :
(var1,var1)->System.out.println("Values = v1 :"+var1+", v2:"+var1);}
return (Object)null;

}

public static char lambda4method1(int recursionCounter, char var1){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (char)var1;

}
else
{
 recursionCounter--; 
}
if( ((var1-'g')==(var1%'t'))){
var1 = (char)((var1*'n')*((var1+'u')+(var1+'h')));
}
return (char)var1;

}


public static void main(String args[]){
lambda4 obj = new lambda4();
lambda4method0(0,(int)(1));
lambda4method1(1,'x');
}

public static void singleEntry(int i0,int i1,int i2){
lambda4 obj = new lambda4();
lambda4method0(0,i0);
lambda4method1(1,'k');
}

}