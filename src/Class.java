//package oopproject;

/**
 *
 * @author CONG THUONG
 */
import java.util.*;
import java.util.regex.*;
import java.io.*;


public class Class {

    /**
     * @param args the command line arguments
     */
    private String fileString = "";
    private ArrayList<String> methods = new ArrayList<String>();
    private ArrayList<String> variables = new ArrayList<String>();
    private String nameClass;
    
    private void replace(String regex){
        Pattern deleteString = Pattern.compile(regex);
        Matcher m = deleteString.matcher(fileString);
        fileString = m.replaceAll("");
        
    }
    
    private void standardizeFileString(){
        String stringPattern;
        
        stringPattern = "\\\\\"";  
        replace(stringPattern);
        
        stringPattern = "(\"[^(\")]*\")|(//.*)";
        replace(stringPattern);
        
        stringPattern = " (\".*\") ";
        replace(stringPattern);
        
        stringPattern = "(\\s*|^)(import|return|package)(\\s+)[^;]*(\\s*);";
        replace(stringPattern);
        
        
        stringPattern = "(/\\*([\\S\\s]+?)\\*/)";
        replace(stringPattern);
         
        stringPattern = "(?m)^\\s+";
        replace(stringPattern);
        fileString = fileString.trim().replaceAll("\\s+"," ");
       
        System.out.println(fileString);
        deleteInsideMethods();
        System.out.println(fileString);
    }
    
    public void setMethods(){
        Pattern pattern = Pattern.compile("(\\s+|^|\\{)?(abstract\\s+)?((public|private|protected)\\s+)?(static\\s+)?(abtract\\s+)?(\\w+||ArrayList\\<\\s*\\w+\\s*\\>||\\w+\\s*\\[\\])\\s+(\\w+)s*\\([^\\(]*\\)\\s*");
        Matcher m = pattern.matcher(fileString);
        while(m.find()){
            methods.add(m.group());
        }
    }
    
    public void setVariables(){
        Pattern pattern = Pattern.compile("(\\s+|^|\\{)((public|private|protected)\\s+)?((static|final)\\s+)?(\\w+)\\s+(\\w+)s*[^(\\()(\\{)(\\;)]*;");
        Matcher m = pattern.matcher(fileString);
        while(m.find()){
           variables.add(m.group());
        }
    }
    
    public void setClassOrInterfaceName(){
        Pattern pattern = Pattern.compile("(\\s*|^)(abstract\\s+)?((class|interface)\\s+)(\\w+)\\s*(((extends|implements))\\s+((\\w+))\\s*)*");
        Matcher m = pattern.matcher(fileString);
        while(m.find()){
            nameClass = m.group();
        }
    }
    
    public void getFile(File fileName){
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){ 
                fileString = fileString + line + "\n";
            }  
           
        }catch(IOException e){
            e.printStackTrace();
        }
       
         standardizeFileString();
    }
    
    public String getClassName(){
        return nameClass;
    }
    
    public ArrayList<String> getMethods(){
        return methods;
    }
    
    public ArrayList<String> getVariables(){
        return variables;
    }
  
    
   private void deleteInsideMethods(){
       StringBuilder string = new StringBuilder(fileString);
       Stack container = new Stack();
       int index = string.indexOf("{");
       container.push(index);
       int from, to;
       for(int i = index + 1; i < string.length(); i++){
           if(string.charAt(i) == '{'){
                container.push(i);
           }
            if(string.charAt(i) == '}'){
                to = i;
                from = (int)container.pop();
                if(container.isEmpty()){
                    break;
                }
                for(int k = from + 1; k < to; k++){
                    string.setCharAt(k, '*');
                }
 
            }
            
       }
       StringBuilder temp = new StringBuilder();
       for(int i = 0; i < string.length(); i++){
           if(string.charAt(i) != '*'){
               temp.append(string.charAt(i));
           }
           
       }
       fileString = temp.toString();
        
   }
    public void processClass(File fileName){
        getFile(fileName);
        setMethods();
        setVariables();
        setClassOrInterfaceName();
    }
    
}
