package com.accenture.lab.carfast.test;


public class lambda1 implements lambdaInterface3 {
static lambda8 f0;
static int f1;
static String f2;
int f3;
static String f4;


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

public int lambda1method0(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (int)(int)(5);

}
else
{
 recursionCounter--; 
}
return (int)(int)(7);

}

public String lambda1method1(int recursionCounter){
 if(! (recursionCounter > 0 && recursionCounter < 2) )
{
return (String)"khofkbslimntiferzxprlqirpnlvgrwcdoikfhzlzyvfwetdkntzemwfbtcatorofakwrkrmforyhmqj";

}
else
{
 recursionCounter--; 
}
return (String)"wezvqilbtluzxqgtckmcabzyztqehkbdkqejtjoavfmaosekvqkhdhysokxkwqrhzczewpdrlzbdbjparhwkkpwejgwux";

}


public static void main(String args[]){
lambda1 obj = new lambda1();
obj.lambdaInterface3Method0(1,'e');
obj.lambda1method0(1);
obj.lambda1method1(0);
}

public static void singleEntry(int i0,int i1,int i2){
lambda1 obj = new lambda1();
obj.lambdaInterface3Method0(1,'p');
obj.lambda1method0(1);
obj.lambda1method1(0);
}

}