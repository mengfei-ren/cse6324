package com.accenture.lab.carfast.test;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
public class TP9 extends TP11 implements TPInterface2 {
static String f0;
static int f1;
String f2;
String f3;
byte f4;
byte f5;
static byte f6;


public byte TPInterface2Method0(int var0, int var1, int var2, int var3){
 for(int i = 0; i < 4; i++){
 f2 = (String)(("perlhuvejrhnfwqkjiloicmagwhsfqwkxtohdkzkf"+"qdsctzuqaswjkzabh")+("dlytqgimilztlawgmamxpkamavjtkaqpkoqyshcwbvxyxayzjgrxdenzlfqqwwbztspnyihftqsehue"+"kfwaromdqdjxvsi"));
}
for(int i = 0; i < 2; i++){
 System.out.println("TP9 - TPInterface2Method0- LineInMethod: 6");
}
return (byte)(byte)(42);

}

public int TP9method0(String var0, int var1, byte var2, int var3, String var4, int var5, byte var6, String var7){
 if( (((var6/(byte)(60))-(var2-var6))<=(var2*(byte)(121)))){
f2 = (String)(var4+var7);
}
switch((var3%(int)(16))){
case 0:
TP8method2(var2,var4,var3,var7); break;
case 1:
System.out.println("TP9 - TP9method0- LineInMethod: 8");
 break;
case 2:
System.out.println("TP9 - TP9method0- LineInMethod: 13");
 break;
case 3:
var0 = (String)((var7+var4)+((var4+var7)+(var0+var4)));
 break;
case 4:
System.out.println("TP9 - TP9method0- LineInMethod: 22");
 break;
default :
System.out.println("TP9 - TP9method0- LineInMethod: 28");
}
return (int)var5;

}

public static byte TP8method1(int var0, byte var1, String var2, int var3, byte var4, int var5){
 for(int i = 0; i < 3; i++){
 if( (((f0+"fchebllsakjqhgnnvzyvlejnsogvmulqsjefjemknclpipikxlczpzdjkblfjrokyezzgddoluojgoanucylslitrhytnaxnzdx")!=(var2+"etetlazftfybsshrmamgewavjfogjrcuwgwlezlkvidwbvkukkvjsqiumingd"))&&((f0+"sesumujeadajcmuxmc")!=(f0+"gzpjkglmahqvxwzmdvtejtoajkkrzgh")))){
if( ((f6*(byte)(67))>=(var4*var1))){
if( ((var3/(int)(43))>=(f1+(int)(5)))){
System.out.println("TP9 - TP8method1- LineInMethod: 8");
}
}
}
}
return (byte)var1;

}

public static int TP8method2(byte var0, String var1, int var2, String var3){
 switch((var2+(int)(42))){
case 0:
System.out.println("TP9 - TP8method2- LineInMethod: 2");
 break;
case 1:
var2 = (int)(0);
 break;
case 2:
System.out.println("TP9 - TP8method2- LineInMethod: 9");
 break;
case 3:
System.out.println("TP9 - TP8method2- LineInMethod: 12");
 break;
case 4:
System.out.println("TP9 - TP8method2- LineInMethod: 17");
 break;
case 5:
System.out.println("TP9 - TP8method2- LineInMethod: 22");
 break;
default :
System.out.println("TP9 - TP8method2- LineInMethod: 28");
}
return (int)var2;

}

public static String TP11method0(int var0, byte var1, int var2, byte var3, String var4, String var5, byte var6, byte var7, String var8, byte var9, int var10, int var11, byte var12){
  List<String> words = Arrays.asList("Java","JavaProgram","abc","XYZ","RUGRAT"); 
 Predicate<String> pr = (s)->s.startsWith("J");
for (String word:words)
        {
            if (pr.test(word))
                System.out.println(word);
        } if( ((var1+var12)<=(var3%(byte)(-37)))){
System.out.println("TP9 - TP11method0- LineInMethod: 2");
}
if( (((var5+var8)+(var4+"xsqzcxaporcleufabvoyvwoccoqkmaxzlmgfkmgyfcidmnyufdaraknlelpnurpe"))==(var5+var4))){
if( ((f1+(int)(9))!=((var2-var0)/(int)(20)))){
if( ((var10/(int)(37))<(var10*var2))){
TP8method1(var0,var7,var5,var11,var6,var2);}
}
}
return (String)var8;

}

public byte TP8method4(byte var0, String var1, String var2, String var3, int var4, int var5, byte var6, String var7){
 if(((var6*var0)>=(f5*f4))){
var4 = (int)((var5-var4)/(int)(33));
}
else{
 f3 = (String)((var3+"qutqlcnprraioecaiopddicwamukgedcafxizmlbjji")+(var7+var2));
}
 List<String> words = Arrays.asList("Java","JavaProgram","abc","XYZ","RUGRAT"); 
 Predicate<String> pr = (s)->s.startsWith("J");
for (String word:words)
        {
            if (pr.test(word))
                System.out.println(word);
        }  new Thread(()->{System.out.println("New thread created");}).start();  new Thread(()->{System.out.println("New thread created");}).start();  new Thread(()->{System.out.println("New thread created");}).start(); for(int i = 0; i < 1; i++){
 System.out.println("TP9 - TP8method4- LineInMethod: 8");
}
return (byte)var0;

}


public static void main(String args[]){
TP9 obj = new TP9();
obj.TPInterface2Method0((int)(5));
obj.TPInterface2Method0((int)(5),(int)(34));
obj.TPInterface2Method0((int)(5),(int)(34),(int)(17));
obj.TPInterface2Method0((int)(5),(int)(34),(int)(17),(int)(7));
obj.TP9method0("xacysrpxoxtpwne");
obj.TP9method0("xacysrpxoxtpwne",(int)(18));
obj.TP9method0("xacysrpxoxtpwne",(int)(18),(byte)(-52));
obj.TP9method0("xacysrpxoxtpwne",(int)(18),(byte)(-52),(int)(39));
obj.TP9method0("xacysrpxoxtpwne",(int)(18),(byte)(-52),(int)(39),"stupwhqrhaltesoegzibjchbnfajlkydbbrwlkwtxwnlcwmj");
obj.TP9method0("xacysrpxoxtpwne",(int)(18),(byte)(-52),(int)(39),"stupwhqrhaltesoegzibjchbnfajlkydbbrwlkwtxwnlcwmj",(int)(34));
obj.TP9method0("xacysrpxoxtpwne",(int)(18),(byte)(-52),(int)(39),"stupwhqrhaltesoegzibjchbnfajlkydbbrwlkwtxwnlcwmj",(int)(34),(byte)(121));
obj.TP9method0("xacysrpxoxtpwne",(int)(18),(byte)(-52),(int)(39),"stupwhqrhaltesoegzibjchbnfajlkydbbrwlkwtxwnlcwmj",(int)(34),(byte)(121),"ekzzeysjttamxehnkewjlldnch");
TP8method1((int)(37));
TP8method1((int)(37),(byte)(71));
TP8method1((int)(37),(byte)(71),"evsnzwlbrtcmwvcujgqnwwtffkefsdfgjxrvyjzvotwgkcghfxyhgvsyqcesdhjncqmyowjgcadnbqmwvciuzwbtetkgf");
TP8method1((int)(37),(byte)(71),"evsnzwlbrtcmwvcujgqnwwtffkefsdfgjxrvyjzvotwgkcghfxyhgvsyqcesdhjncqmyowjgcadnbqmwvciuzwbtetkgf",(int)(45));
TP8method1((int)(37),(byte)(71),"evsnzwlbrtcmwvcujgqnwwtffkefsdfgjxrvyjzvotwgkcghfxyhgvsyqcesdhjncqmyowjgcadnbqmwvciuzwbtetkgf",(int)(45),(byte)(-68));
TP8method1((int)(37),(byte)(71),"evsnzwlbrtcmwvcujgqnwwtffkefsdfgjxrvyjzvotwgkcghfxyhgvsyqcesdhjncqmyowjgcadnbqmwvciuzwbtetkgf",(int)(45),(byte)(-68),(int)(40));
TP8method2((byte)(-94));
TP8method2((byte)(-94),"vkndjgwnysgsvdtvffngiltkmjyzpulj");
TP8method2((byte)(-94),"vkndjgwnysgsvdtvffngiltkmjyzpulj",(int)(11));
TP8method2((byte)(-94),"vkndjgwnysgsvdtvffngiltkmjyzpulj",(int)(11),"bklrezdwgypluzq");
TP11method0((int)(33));
TP11method0((int)(33),(byte)(109));
TP11method0((int)(33),(byte)(109),(int)(39));
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118));
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn");
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn","cqjwefguqqxczlmpqdqlrrimpozevcbxz");
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn","cqjwefguqqxczlmpqdqlrrimpozevcbxz",(byte)(-13));
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn","cqjwefguqqxczlmpqdqlrrimpozevcbxz",(byte)(-13),(byte)(73));
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn","cqjwefguqqxczlmpqdqlrrimpozevcbxz",(byte)(-13),(byte)(73),"jfubgcfhfyvwqwvltnhjlqfflsqjsmsfqvcyqycuevduuoagcetzefkmwsqpalfxrcoucr");
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn","cqjwefguqqxczlmpqdqlrrimpozevcbxz",(byte)(-13),(byte)(73),"jfubgcfhfyvwqwvltnhjlqfflsqjsmsfqvcyqycuevduuoagcetzefkmwsqpalfxrcoucr",(byte)(-13));
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn","cqjwefguqqxczlmpqdqlrrimpozevcbxz",(byte)(-13),(byte)(73),"jfubgcfhfyvwqwvltnhjlqfflsqjsmsfqvcyqycuevduuoagcetzefkmwsqpalfxrcoucr",(byte)(-13),(int)(14));
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn","cqjwefguqqxczlmpqdqlrrimpozevcbxz",(byte)(-13),(byte)(73),"jfubgcfhfyvwqwvltnhjlqfflsqjsmsfqvcyqycuevduuoagcetzefkmwsqpalfxrcoucr",(byte)(-13),(int)(14),(int)(49));
TP11method0((int)(33),(byte)(109),(int)(39),(byte)(-118),"cgfvshotrtvvoktwoyvgbldjiacadphzuvxvafgwiacskgbotzopoocatcdfkknvjxmwmn","cqjwefguqqxczlmpqdqlrrimpozevcbxz",(byte)(-13),(byte)(73),"jfubgcfhfyvwqwvltnhjlqfflsqjsmsfqvcyqycuevduuoagcetzefkmwsqpalfxrcoucr",(byte)(-13),(int)(14),(int)(49),(byte)(-17));
obj.TP8method4((byte)(12));
obj.TP8method4((byte)(12),"dqmstrtqwocqwtczuqokrbqcrynjbcwxytcbtugkvouzncpfgebnphgeebtykznbdytsoytrcvacjfgulnjnsqpkabai");
obj.TP8method4((byte)(12),"dqmstrtqwocqwtczuqokrbqcrynjbcwxytcbtugkvouzncpfgebnphgeebtykznbdytsoytrcvacjfgulnjnsqpkabai","euboooxuzchcvwfjhkixhaudhtaynxshombhbhlavgkatqcbga");
obj.TP8method4((byte)(12),"dqmstrtqwocqwtczuqokrbqcrynjbcwxytcbtugkvouzncpfgebnphgeebtykznbdytsoytrcvacjfgulnjnsqpkabai","euboooxuzchcvwfjhkixhaudhtaynxshombhbhlavgkatqcbga","pghnldzewogwqiescfmtmlnlovksupyixicgnfzhtiubqgiyuhgwvv");
obj.TP8method4((byte)(12),"dqmstrtqwocqwtczuqokrbqcrynjbcwxytcbtugkvouzncpfgebnphgeebtykznbdytsoytrcvacjfgulnjnsqpkabai","euboooxuzchcvwfjhkixhaudhtaynxshombhbhlavgkatqcbga","pghnldzewogwqiescfmtmlnlovksupyixicgnfzhtiubqgiyuhgwvv",(int)(12));
obj.TP8method4((byte)(12),"dqmstrtqwocqwtczuqokrbqcrynjbcwxytcbtugkvouzncpfgebnphgeebtykznbdytsoytrcvacjfgulnjnsqpkabai","euboooxuzchcvwfjhkixhaudhtaynxshombhbhlavgkatqcbga","pghnldzewogwqiescfmtmlnlovksupyixicgnfzhtiubqgiyuhgwvv",(int)(12),(int)(40));
obj.TP8method4((byte)(12),"dqmstrtqwocqwtczuqokrbqcrynjbcwxytcbtugkvouzncpfgebnphgeebtykznbdytsoytrcvacjfgulnjnsqpkabai","euboooxuzchcvwfjhkixhaudhtaynxshombhbhlavgkatqcbga","pghnldzewogwqiescfmtmlnlovksupyixicgnfzhtiubqgiyuhgwvv",(int)(12),(int)(40),(byte)(-28));
obj.TP8method4((byte)(12),"dqmstrtqwocqwtczuqokrbqcrynjbcwxytcbtugkvouzncpfgebnphgeebtykznbdytsoytrcvacjfgulnjnsqpkabai","euboooxuzchcvwfjhkixhaudhtaynxshombhbhlavgkatqcbga","pghnldzewogwqiescfmtmlnlovksupyixicgnfzhtiubqgiyuhgwvv",(int)(12),(int)(40),(byte)(-28),"tolnbwsyqbvynigdvsoianeyrzkmweitmnulxoaksdzsegyikg");
}

public static void singleEntry(int i0,int i1,int i2,int i3,int i4,int i5,int i6,int i7,int i8,int i9,int i10,int i11,int i12,int i13,int i14){
TP9 obj = new TP9();
obj.TPInterface2Method0(i2,i4,i14,i1);
obj.TP9method0("wevkcluqqqbggedqsnepqpqrkgkzgfvcubpxhsjzmerveuggmqrkraopvzexglmevgznbkeepgzgcadnxuyyvij",i7,(byte)(-48),i0,"arqgsooehrvfaayxfppcvtdmirowwsfhmwfbvcjtldeplegbiblykurjbgoresvphsjawidpghy",i10,(byte)(0),"svuyftdbrdcearudgrlaadltkuems");
TP8method1(i12,(byte)(22),"lngpzfltoedejjacnztyitdinunmwflvnalbhimdrlgzqjnifqvpyftlyzobnprhvid",i11,(byte)(-35),i2);
TP8method2((byte)(-100),"afbbfsglzyrtxcgdddnxrqaieuwdblxtwghtthadbzhhecunku",i11,"vtjeialf");
TP11method0(i6,(byte)(35),i12,(byte)(86),"rblyekiyoyxlz","nuuwcpkvzlkpkngqrhagldpygdncoz",(byte)(31),(byte)(-58),"fvazun",(byte)(-30),i9,i5,(byte)(-47));
obj.TP8method4((byte)(-73),"xyymzxriemnllfzeuhbfksvwhxgpcceghgqylxeyxyjykhohnhoqitsstjeignutkdwgqtpvaguqygfbrtiarvduyiuzquurlj","qrxgdxz","uxxqapgfammbcfbbmvacq",i3,i10,(byte)(8),"vwfhjjfmjdvofnhemclmoxpvipyuozls");
}

}