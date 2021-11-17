package com.Lua;
import com.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
public class LuaInter{
    private static String dir;
    private static void CreateFile(String dir) {
          try {
            File myObj = new File(dir);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (Exception e) {
            
          }
        }
   
    private static String Read(String dir){
        String data="";
        try {
            File myObj = new File(dir);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
                data += "\n";
              //System.out.println(data);
            }
            myReader.close();
          } catch (Exception e) {
            
          }
          return data;
    }
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
        }
    }
    public static void interperate(int id,Sand sand){
        dir = String.valueOf(id) + ".lua";
        boolean doCode = true;
        CreateFile(dir);
        String data = Read(dir);
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
                   // System.out.print(getValue(lineCode[tmp+1],sand) +lineCode[tmp+2]+lineCode[tmp + 3]); 
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
                //System.out.println("end");
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