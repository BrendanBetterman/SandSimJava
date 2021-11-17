package com.Lua;
import com.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
public class ReadLua{
    public static void CreateFile(String dir) {
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
  public static String Read(String dir){
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
}