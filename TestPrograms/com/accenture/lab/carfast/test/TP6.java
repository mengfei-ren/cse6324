package com.accenture.lab.carfast.test;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
public class TP6 implements TPInterface1 {
byte f0;
int f1;
static int f2;
byte f3;
static int f4;
static byte f5;
static byte f6;


public byte TPInterface1Method0(byte var0, byte var1, int var2, String var3, String var4, String var5, int var6, int var7, byte var8, byte var9){
  new Thread(()->{System.out.println("New thread created");}).start(); switch((var6*f1)){
case 0:
System.out.println("TP6 - TPInterface1Method0- LineInMethod: 2");
 break;
case 1:
TP6method4(var0,var4,var8,var6); break;
case 2:
var1 = TP6method4(var8,var3,var8,var7);
 break;
case 3:
var3 = (String)((var4+var5)+(var5+var3));
 break;
case 4:
System.out.println("TP6 - TPInterface1Method0- LineInMethod: 16");
 break;
default :
f3 = (byte)((var1+var8)-(var0-var1));
}
return (byte)var1;

}

public static String TP6method0(int var0, int var1, int var2, int var3){
 if( ((var3*var1)==(var2/(int)(29)))){
System.out.println("TP6 - TP6method0- LineInMethod: 2");
}
if(((((var0-(int)(5))+(var3/(int)(43)))<=(var0*var2))&&((f2-f4)<((var2*(int)(10))%(int)(43))))){
var3 = (int)((var3/(int)(41))-((var2-(int)(40))/(int)(49)));
}
else{
 var3 = (int)(var1-var2);
}
return (String)"irsedibqqymthaklbquwlnernsamdnagqqxivlmuqbcinupzbxttczgiovrlijalcomiiswfzfrxmhdmchlaaqygel";

}

public int TP6method1(byte var0, byte var1, int var2, byte var3){
 if( ((var3+var1)>=(var1+(byte)(111)))){
if( ((f0*f3)<=(f0+(byte)(116)))){
if( ((var3-var1)<(var1-var0))){
f3 = (byte)((var3*var1)+(var1+var0));
}
}
}
switch((var2*f1)){
case 0:
var0 = (byte)(var3%(byte)(44));
 break;
case 1:
System.out.println("TP6 - TP6method1- LineInMethod: 12");
 break;
case 2:
var2 = (int)((var2-(int)(25))*(var2*(int)(16)));
 break;
case 3:
System.out.println("TP6 - TP6method1- LineInMethod: 20");
 break;
case 4:
System.out.println("TP6 - TP6method1- LineInMethod: 24");
 break;
case 5:
System.out.println("TP6 - TP6method1- LineInMethod: 30");
 break;
default :
System.out.println("TP6 - TP6method1- LineInMethod: 37");
}
return (int)var2;

}

public int TP6method2(int var0, int var1, String var2, byte var3, byte var4, String var5, int var6, int var7, int var8, int var9, int var10, String var11, byte var12){
 if( (((f1-(int)(2))+(f1*(int)(5)))!=(var8+var10))){
if( ((var7/(int)(12))<((var6-var8)*(var0-var1)))){
if( ((((var3*var12)!=((var12+(byte)(119))*(var12*var3)))&&(((var4*(byte)(6))<(var12/(byte)(40)))&&((var4+var3)>=((var3+var12)+(var3%(byte)(-77))))))||((var4+var3)==(var4-var12)))){
var9 = (int)(13);
}
}
}
if( ((var11+"chuvpbsekkuhcptjpfqsvkjmwjk")==(var11+"yqmpfixluylkhw"))){
System.out.println("TP6 - TP6method2- LineInMethod: 8");
}
return (int)var10;

}

public int TP6method3(String var0, String var1, byte var2, byte var3){
  List<String> words = Arrays.asList("Java","JavaProgram","abc","XYZ","RUGRAT"); 
 Predicate<String> pr = (s)->s.startsWith("J");
for (String word:words)
        {
            if (pr.test(word))
                System.out.println(word);
        } for(int i = 0; i < 3; i++){
 if( ((var2-var3)==(f0+(byte)(-90)))){
if( (((var2%(byte)(29))+(var2%(byte)(19)))!=(var3*var2))){
System.out.println("TP6 - TP6method3- LineInMethod: 6");
}
}
}
return (int)(int)(15);

}

public byte TP6method4(byte var0, String var1, byte var2, int var3){
 if( (((var3*(int)(14))*(var3*(int)(13)))>(var3-(int)(2)))){
var2 = (byte)(var2-(byte)(14));
}
if(((f1*(int)(34))<(var3/(int)(26)))){
f1 = (int)(var3%(int)(19));
}
else{
 System.out.println("TP6 - TP6method4- LineInMethod: 8");
}
return (byte)var0;

}


public static void main(String args[]){
TP6 obj = new TP6();
obj.TPInterface1Method0((byte)(-106));
obj.TPInterface1Method0((byte)(-106),(byte)(-47));
obj.TPInterface1Method0((byte)(-106),(byte)(-47),(int)(13));
obj.TPInterface1Method0((byte)(-106),(byte)(-47),(int)(13),"gaxdvnrmahonibxusexfoahqyswmhucgwzesoalbqppgquscauwldxavysvjtrwyylrilu");
obj.TPInterface1Method0((byte)(-106),(byte)(-47),(int)(13),"gaxdvnrmahonibxusexfoahqyswmhucgwzesoalbqppgquscauwldxavysvjtrwyylrilu","klqfsakvqpcixydmbgtoiytafjftdzwgdpmigeysmxmbaznnrqkqlnqoiunrijvexkplqkqizcyifccebumodhtttpslxgjvnjhy");
obj.TPInterface1Method0((byte)(-106),(byte)(-47),(int)(13),"gaxdvnrmahonibxusexfoahqyswmhucgwzesoalbqppgquscauwldxavysvjtrwyylrilu","klqfsakvqpcixydmbgtoiytafjftdzwgdpmigeysmxmbaznnrqkqlnqoiunrijvexkplqkqizcyifccebumodhtttpslxgjvnjhy","tncbiktfrzohxdaeqbgvmknlnlxocrgmueiawsahrowjsxyyjxgdbmsetkvnensihtxreknihtxuzfdmsiljlg");
obj.TPInterface1Method0((byte)(-106),(byte)(-47),(int)(13),"gaxdvnrmahonibxusexfoahqyswmhucgwzesoalbqppgquscauwldxavysvjtrwyylrilu","klqfsakvqpcixydmbgtoiytafjftdzwgdpmigeysmxmbaznnrqkqlnqoiunrijvexkplqkqizcyifccebumodhtttpslxgjvnjhy","tncbiktfrzohxdaeqbgvmknlnlxocrgmueiawsahrowjsxyyjxgdbmsetkvnensihtxreknihtxuzfdmsiljlg",(int)(5));
obj.TPInterface1Method0((byte)(-106),(byte)(-47),(int)(13),"gaxdvnrmahonibxusexfoahqyswmhucgwzesoalbqppgquscauwldxavysvjtrwyylrilu","klqfsakvqpcixydmbgtoiytafjftdzwgdpmigeysmxmbaznnrqkqlnqoiunrijvexkplqkqizcyifccebumodhtttpslxgjvnjhy","tncbiktfrzohxdaeqbgvmknlnlxocrgmueiawsahrowjsxyyjxgdbmsetkvnensihtxreknihtxuzfdmsiljlg",(int)(5),(int)(6));
obj.TPInterface1Method0((byte)(-106),(byte)(-47),(int)(13),"gaxdvnrmahonibxusexfoahqyswmhucgwzesoalbqppgquscauwldxavysvjtrwyylrilu","klqfsakvqpcixydmbgtoiytafjftdzwgdpmigeysmxmbaznnrqkqlnqoiunrijvexkplqkqizcyifccebumodhtttpslxgjvnjhy","tncbiktfrzohxdaeqbgvmknlnlxocrgmueiawsahrowjsxyyjxgdbmsetkvnensihtxreknihtxuzfdmsiljlg",(int)(5),(int)(6),(byte)(15));
obj.TPInterface1Method0((byte)(-106),(byte)(-47),(int)(13),"gaxdvnrmahonibxusexfoahqyswmhucgwzesoalbqppgquscauwldxavysvjtrwyylrilu","klqfsakvqpcixydmbgtoiytafjftdzwgdpmigeysmxmbaznnrqkqlnqoiunrijvexkplqkqizcyifccebumodhtttpslxgjvnjhy","tncbiktfrzohxdaeqbgvmknlnlxocrgmueiawsahrowjsxyyjxgdbmsetkvnensihtxreknihtxuzfdmsiljlg",(int)(5),(int)(6),(byte)(15),(byte)(93));
TP6method0((int)(2));
TP6method0((int)(2),(int)(10));
TP6method0((int)(2),(int)(10),(int)(35));
TP6method0((int)(2),(int)(10),(int)(35),(int)(6));
obj.TP6method1((byte)(120));
obj.TP6method1((byte)(120),(byte)(45));
obj.TP6method1((byte)(120),(byte)(45),(int)(43));
obj.TP6method1((byte)(120),(byte)(45),(int)(43),(byte)(-124));
obj.TP6method2((int)(3));
obj.TP6method2((int)(3),(int)(23));
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl");
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10));
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107));
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107),"repsjvzhrvesmsnzfjgxoflcswxxmwrqjrkfgpnffwyomldpepbwvgqycfpgmrkfezkxzuhpzfnt");
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107),"repsjvzhrvesmsnzfjgxoflcswxxmwrqjrkfgpnffwyomldpepbwvgqycfpgmrkfezkxzuhpzfnt",(int)(49));
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107),"repsjvzhrvesmsnzfjgxoflcswxxmwrqjrkfgpnffwyomldpepbwvgqycfpgmrkfezkxzuhpzfnt",(int)(49),(int)(30));
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107),"repsjvzhrvesmsnzfjgxoflcswxxmwrqjrkfgpnffwyomldpepbwvgqycfpgmrkfezkxzuhpzfnt",(int)(49),(int)(30),(int)(23));
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107),"repsjvzhrvesmsnzfjgxoflcswxxmwrqjrkfgpnffwyomldpepbwvgqycfpgmrkfezkxzuhpzfnt",(int)(49),(int)(30),(int)(23),(int)(33));
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107),"repsjvzhrvesmsnzfjgxoflcswxxmwrqjrkfgpnffwyomldpepbwvgqycfpgmrkfezkxzuhpzfnt",(int)(49),(int)(30),(int)(23),(int)(33),(int)(29));
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107),"repsjvzhrvesmsnzfjgxoflcswxxmwrqjrkfgpnffwyomldpepbwvgqycfpgmrkfezkxzuhpzfnt",(int)(49),(int)(30),(int)(23),(int)(33),(int)(29),"muudbsqgdakqtntszjknyhvqmexhjyxsjffytkglvlhlnfiwqrneilvhdjgiwcdqovubalonnuyfjcebtcdoskzrqrhetihpe");
obj.TP6method2((int)(3),(int)(23),"phwkxhouyveobgeigokhobebjiwrruvqrqpbicexvoqnepzbselvcwgl",(byte)(-10),(byte)(107),"repsjvzhrvesmsnzfjgxoflcswxxmwrqjrkfgpnffwyomldpepbwvgqycfpgmrkfezkxzuhpzfnt",(int)(49),(int)(30),(int)(23),(int)(33),(int)(29),"muudbsqgdakqtntszjknyhvqmexhjyxsjffytkglvlhlnfiwqrneilvhdjgiwcdqovubalonnuyfjcebtcdoskzrqrhetihpe",(byte)(-15));
obj.TP6method3("qhpfhmjzhbuczksrqckfkvckynhsiqzbzdfuousrnhdiqechjggh");
obj.TP6method3("qhpfhmjzhbuczksrqckfkvckynhsiqzbzdfuousrnhdiqechjggh","lyypeentxivozymheqeelrqnxudvjditygdpmxdngbwwkeeelhwidfmhrlxutcmswxmhtajuxfizrejrehxv");
obj.TP6method3("qhpfhmjzhbuczksrqckfkvckynhsiqzbzdfuousrnhdiqechjggh","lyypeentxivozymheqeelrqnxudvjditygdpmxdngbwwkeeelhwidfmhrlxutcmswxmhtajuxfizrejrehxv",(byte)(-116));
obj.TP6method3("qhpfhmjzhbuczksrqckfkvckynhsiqzbzdfuousrnhdiqechjggh","lyypeentxivozymheqeelrqnxudvjditygdpmxdngbwwkeeelhwidfmhrlxutcmswxmhtajuxfizrejrehxv",(byte)(-116),(byte)(105));
obj.TP6method4((byte)(-68));
obj.TP6method4((byte)(-68),"fuvymqxwmzymiej");
obj.TP6method4((byte)(-68),"fuvymqxwmzymiej",(byte)(30));
obj.TP6method4((byte)(-68),"fuvymqxwmzymiej",(byte)(30),(int)(28));
}

public static void singleEntry(int i0,int i1,int i2,int i3,int i4,int i5,int i6,int i7,int i8,int i9,int i10,int i11,int i12,int i13,int i14){
TP6 obj = new TP6();
obj.TPInterface1Method0((byte)(-121),(byte)(-6),i1,"ojvkstlxxgwxekewrzvzggmznszhtdwwuuetulsddjihmsdxeksolswftnoajhady","enfeyzephmxorkwinjtfpbcviwu","pgrbpbezmxxutt",i0,i2,(byte)(-83),(byte)(-42));
TP6method0(i12,i14,i4,i13);
obj.TP6method1((byte)(92),(byte)(48),i9,(byte)(8));
obj.TP6method2(i13,i10,"llhanjcksugpuflixbhocmmk",(byte)(90),(byte)(-65),"akoecppmuqqztqpbwbkehfpyjqiiyugpdhwootrtvsplrwhaesirpxhfzhhphtfqibpqksmsfbcpihjwrtcevanffk",i6,i2,i9,i8,i3,"rmpococfaenrdhamtqdebbbcysdfepdvxhszthvrpqiigapykjhnbdwafirnmfcef",(byte)(51));
obj.TP6method3("gasbrzsppoywqgmugldlrlfhanzbwkrjxfmgigmojgdtttaysvgumqmlawtjishmbkmjjmiulbzengxpvjxkvancpx","bksgmcsskjnwlehycuyvsshgdycmlcuguduufqnyktlnqbyczvqxknrvscxrlduktryiuvvdk",(byte)(106),(byte)(31));
obj.TP6method4((byte)(18),"gqccjiwlllrjcructsxo",(byte)(-16),i6);
}

}