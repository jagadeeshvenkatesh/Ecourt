package Advocate_Module;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Advocate_Map_Text_File {

    String get_title(String location){
        try {
            location = location.replace("?", "\\");
            location = "D:\\study material\\java program\\ecourtfinalproject\\files\\" + location;
            System.out.println(location);
            File myObj = new File(location);
            Scanner myReader = new Scanner(myObj);
            String data = "";
            data = data + myReader.nextLine();
            System.out.println(data);
            return data;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    String get_document(String location){
        try {
            location = location.replace("?", "\\");
            location = "D:\\study material\\java program\\ecourtfinalproject\\files\\" + location;
            System.out.println(location);
            File myObj = new File(location);
            Scanner myReader = new Scanner(myObj);
            String data = "";
            while(myReader.hasNextLine()) {
                data = data + myReader.nextLine()+"\n";
            }
            return data;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
