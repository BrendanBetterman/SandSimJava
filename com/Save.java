package com;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import com.NumJa;
import com.Sand;

import java.io.FileWriter;   // Import the FileWriter class
public class Save{
    private String dir;
    public Save(String dir){
        this.dir = dir;
        CreateFile();
    }
    private void CreateFile() {
          try {
            File myObj = new File(this.dir);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (Exception e) {
            
          }
        }
    private void Write(String data){
        try {
            FileWriter myWriter = new FileWriter(this.dir);
            myWriter.write(data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (Exception e) {
            
          }
    }
    private String Read(){
        String data="";
        try {
            File myObj = new File(this.dir);
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
    public void SaveGame(Sand[] sandArray){
        String data="";
        int[][] c;
        for(int i=0; i<sandArray.length; i++){
            c=NumJa.findHCluster(sandArray[i].sand, 0);
            for(int u=0; u<c.length; u++){
                for(int k=0; k<c[0].length; k++){
                    if(k!=2){
                        data+=String.valueOf(c[u][k]);
                        data+=":"; 
                    }
                    
                }data+=" ";
            }
            data+="\n";
        }
        Write(data);
    }
    public Sand[] GetSave(Sand[] sandArray,float gridSize){
        String data = Read();
         
        Sand[] tmp = new Sand[sandArray.length];
        for (int i=0; i<sandArray.length; i++){
            tmp[i] = new Sand(new SandObj[sandArray[0].sand.length][sandArray[0].sand[0].length]);
        }
        int column;
        int row = 0;
        String[] SSand = data.split("\n");
        System.out.println("Read Files");
        
        for(int i=0; i<sandArray.length-1; i++){
            column =-1;
            
            String[] clusters = SSand[i].split(" ");
            
            for(int u=0; u<clusters.length-1; u++){
                String[] pattern = clusters[u].split(":");
                
                if(Integer.parseInt(pattern[0]) == 0){
                    column++;
                    row=0;
                }
                    
                for(int k=Integer.parseInt(pattern[0]); k<Integer.parseInt(pattern[1])+Integer.parseInt(pattern[0])+1; k++){
                    //System.out.println(pattern[1]);
                    
                    try{
                        tmp[i].sand[column][k].type = Integer.parseInt(pattern[2]);
                        
                        //System.out.println(row+" "+pattern[2]);
                    }catch(Exception e){}
                    
                }
            }
        }
        //for(int i=0; i<tmp[0].sand.length; i++){
           // for(int u=0; u<tmp[0].sand[0].length; u++){
         //       System.out.print(tmp[0].sand[i][u].type);
         //   }
         //   System.out.print("\n");
      //  }
        return tmp;
    }
}