
/**
 *
 * @author CONG THUONG
 */
import java.util.*;
import java.io.*;
import java.util.regex.*;
 class ProjectAnalysis {
   private File projectPath;
   /*public void getProjectPath(){
       try{
           System.out.println("Enter the path to project:");
           InputStreamReader input = new InputStreamReader(System.in);
           BufferedReader br = new BufferedReader(input);
           projectPath = br.readLine();
       }catch(IOException e){
           System.out.println("Error path");
       }
   }*/
   private void getAllFiles(File curDir, ArrayList<File> path){
       File[] filesList = curDir.listFiles();
       for(File f : filesList){
           if(f.isDirectory()){
               getAllFiles(f, path);
           }
           if(f.isFile()){
               path.add(f);
           }
       }
   }
   
   private ArrayList<File> findJavaTag(File folderPath){
        ArrayList<File> allFiles = new ArrayList();
        ArrayList<File> javaFile = new ArrayList();
        getAllFiles(folderPath, allFiles);
        ArrayList<String> paths = new ArrayList<String>();
        for(File temp: allFiles){
            paths.add(temp.toString());
        }
        String tempName = ".java";
        Pattern p = Pattern.compile(tempName);
        for(String temp : paths){
            Matcher m = p.matcher(temp);
            if(m.find()){
                javaFile.add(new File(temp));
            }
        }
        return javaFile;
   }
   
  
   
   public void sortClass(ArrayList<String> nameClass, ArrayList<ArrayList<String>> variables, ArrayList<ArrayList<String>> methods,ArrayList<Integer> numberPerRow){
       ArrayList<String> tempClassName = new ArrayList<>();
       ArrayList<ArrayList<String>> tempAttributeName = new ArrayList<>();
       ArrayList<ArrayList<String>> tempMethodName = new ArrayList<>();
     //  System.out.println(nameClass);
      // System.out.println("before of before "  + nameClass.size());
       for(int i = 0; i < nameClass.size(); i++){
           String s = nameClass.get(i);
           s = s.replaceAll(", ", " implement");
           if((!s.contains(" extends ")) && (!s.contains(" implements "))){
               tempClassName.add(s);
               tempAttributeName.add(variables.get(i));
               tempMethodName.add(methods.get(i));
               //System.out.println(s);
               nameClass.remove(i);
               variables.remove(i);
               methods.remove(i);
               i--;
           }
       }
       int d = 0;
       numberPerRow.add(tempClassName.size());
       int temp = 0, temp1 = numberPerRow.get(0);
       while(!nameClass.isEmpty()){
           int oldSize = temp1;
           for(int i = temp; i < temp1; i++){
               d = 0;
               Iterator<String> nameIterator = nameClass.iterator();
               Iterator<ArrayList<String>> variableIterator = variables.iterator();
               Iterator<ArrayList<String>> methodIterator = methods.iterator();
               
               
               while(nameIterator.hasNext()){
                   String s1 = nameIterator.next();
                   ArrayList<String> s2 = variableIterator.next();
                   ArrayList<String> s3 = methodIterator.next();
                   if(s1.contains(" " + getOnlyClassName(tempClassName.get(i)))){
                       tempClassName.add(s1);
                       
                       tempAttributeName.add(s2);
                       tempMethodName.add(s3);
                       nameIterator.remove();
                       variableIterator.remove();
                       methodIterator.remove();
                   }
               }
           }
           
           temp = temp1;
           temp1 = tempClassName.size();
           numberPerRow.add(tempClassName.size() - oldSize);
       }
       nameClass.clear();
       variables.clear();
       methods.clear();
       for(String t: tempClassName){
           nameClass.add(t);
       }
       for(ArrayList<String> t: tempAttributeName){
           variables.add(t);
       }
       for(ArrayList<String> t: tempMethodName){
           methods.add(t);
       }
       
   }
   
    public void analyseProject(ArrayList<String> nameClass, ArrayList<ArrayList<String>> variables, ArrayList<ArrayList<String>> methods,ArrayList<Integer> numberPerRow){
       File fileSource = (projectPath);
       
       ArrayList<File> allFile = new ArrayList<File>();
       allFile = findJavaTag(fileSource);
       
       for(File temp: allFile){
           Class tempClass = new Class();
           tempClass.processClass(temp);
           nameClass.add(tempClass.getClassName());
           variables.add(tempClass.getVariables());
           methods.add(tempClass.getMethods());
       }
      sortClass(nameClass, variables, methods, numberPerRow);
   }
   
   private String getOnlyClassName(String s){
       String[] temp = s.split(" ");
       for(int i = 0; i < temp.length; i++){
           if(temp[i].equals("class")){
               return temp[i + 1];
           }
       }
       return "";
   }
   public void setProjectPath(File path) {
	   projectPath = path;
   }
   
}
