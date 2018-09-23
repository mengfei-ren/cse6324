package com.accenture.lab.carfast.test;

public class lambdaStart {
private static int f0;
private static int f1;
private static int f2;


public static void entryMethod(int i0,int i1,int i2){
f0 = i0;
f1 = i1;
f2 = i2;
TStart_L0_0.entryMethod(f0,f1,f2);
}

public static void main(String[] args){
 entryMethod(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
}
}