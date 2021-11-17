package com.Lua;
import com.*;
public class InterperateLua{
    private static boolean ArrayContains(String[] a,String b){
    boolean tmp= false;
    for(int i =0; i< a.length; i++){
        if(a[i].equals(b)){
            return true;
        }
    }return tmp;
}
private static int ArrayWhere(String[] a,String b){
    
    for(int i =0; i< a.length; i++){
        if(a[i].equals(b)){
            return i;
        }
    }return -1;
}
private static int getValue(String input,Sand sand){
    try{
        return Integer.parseInt(input);
    }catch(Exception e){
        String[] intCode = input.split("\\(|\\)");
        
        if(intCode[0].equals("sand.check")){
            
            if(intCode[1].equals("up")){
                return sand.sand[sand.selected[0]][sand.selected[1]+1].type;
            }else if(intCode[1].equals("down")){
                return sand.sand[sand.selected[0]][sand.selected[1]-1].type;
            }else if(intCode[1].equals("left")){
                return sand.sand[sand.selected[0]-1][sand.selected[1]].type;
            }else if(intCode[1].equals("right")){
                return sand.sand[sand.selected[0]+1][sand.selected[1]].type;
            }
        }
    }
    return 0;
}
/*
if == ♳
elseif == ♴

*/

private static void checkCommand(String input, Sand sand){

    String[] intCode = input.split("\\(|\\)");
    if(intCode[0].equals("sand.copy")){
        if(intCode[1].equals("up")){
            sand.addtype(sand.selected[0], sand.selected[1]+1, 10);
            //sand.sand[sand.selected[0]][sand.selected[1]+1].type = sand.sand[sand.selected[0]][sand.selected[1]].type;
        }else if(intCode[1].equals("down")){
            sand.sand[sand.selected[0]][sand.selected[1]-1].type= sand.sand[sand.selected[0]][sand.selected[1]].type;
        }else if(intCode[1].equals("left")){
            sand.sand[sand.selected[0]-1][sand.selected[1]].type= sand.sand[sand.selected[0]][sand.selected[1]].type;
        }else if(intCode[1].equals("right")){
            sand.addtype(sand.selected[0]+1, sand.selected[1], 10);
        }
    }else if(intCode[0].equals("sand.move")){
        if(intCode[1].equals("up")){
            sand.addtype(sand.selected[0], sand.selected[1]+1, 10);
            sand.addtype(sand.selected[0], sand.selected[1], 0);
            //sand.sand[sand.selected[0]][sand.selected[1]+1].type = sand.sand[sand.selected[0]][sand.selected[1]].type;
        }else if(intCode[1].equals("down")){
            sand.addtype(sand.selected[0], sand.selected[1]-1, 10);
            sand.addtype(sand.selected[0], sand.selected[1], 0);
        }else if(intCode[1].equals("left")){
            sand.addtype(sand.selected[0]-1, sand.selected[1], 10);
            sand.addtype(sand.selected[0], sand.selected[1], 0);
        }else if(intCode[1].equals("right")){
            sand.addtype(sand.selected[0]+1, sand.selected[1], 10);
            sand.addtype(sand.selected[0], sand.selected[1], 0);
        }
    }
}
public static void interperate(String data,Sand sand){
    boolean doCode = true;
    String[] luaCode = data.split("\n");
    for(int line=0; line<luaCode.length; line++){
        String[] lineCode = luaCode[line].split(" ");
        if(ArrayContains(lineCode,"if")){
            int tmp = ArrayWhere(lineCode, "if");
           
            if(lineCode[tmp+2].equals("=")){
                
                if(getValue(lineCode[tmp+1],sand) == getValue(lineCode[tmp+3],sand)){
                    doCode = true;
                 
                }else{
                    doCode = false;
                }
            }
        }else if(ArrayContains(lineCode,"elseif")){
            if (!doCode){
                int tmp = ArrayWhere(lineCode, "elseif");
               
                if(lineCode[tmp+2].equals("=")){
                    if(getValue(lineCode[tmp+1],sand) == getValue(lineCode[tmp+3],sand)){

                        doCode = true;
                    }else{
                        doCode = false;
                    }
                }
            }
        }else if(ArrayContains(lineCode, "else")){
            if (!doCode){
                doCode = true;
            }
        }else if(ArrayContains(lineCode,"for")){

        }else if(ArrayContains(lineCode,"end")){
            doCode = true;

        }else{
            //check what the code is
            
            if(doCode){
                checkCommand(lineCode[0],sand);
            }
            

        }
        
        //for(int i =0; i< lineCode.length; i++){
          //  System.out.println(lineCode[i]);
       // }
    }
}
}
