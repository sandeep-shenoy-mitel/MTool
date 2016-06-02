package com.mavenir.resource;

import java.io.File;
import java.io.IOException;



public class Utility {

	private static final String UPLOAD_DIR = File.separator+"uploads";
	private static final String regex = "[-<:>|&\"?/#\\$+%!`{'=}/@]";
	
	public static String getDirectoryPath(String product, String release){
		String path="";
		
		product = removeSpecialChars(product);
		release = removeSpecialChars(release);
		
		path = UPLOAD_DIR +File.separator+product+File.separator+release;
		
		
		return path;
	}
	
	
	public static String removeSpecialChars(String variable){
		variable = variable.replaceAll(" ", "");
		//System.out.println("After removing space char :  "+variable);
		variable = variable.replaceAll(regex, "_");
		//System.out.println("After removing regex char :  "+variable);
		return variable;
	}
	
	
	public static void deleteDirectory(String product, String release){
		   String FileDirectory = Utility.getDirectoryPath(product, "");
	        
	        System.out.println(" FileDirectory  : "+FileDirectory);
	        File directory = new File(FileDirectory);
	        
	        if(!directory.exists()){
	        	 
	            System.out.println("Directory does not exist.");
	           return;
	  
	         }else{
	  
	            try{
	         	   
	                delete(directory);
	                
	                
	         	
	            }catch(IOException e){
	                e.printStackTrace();
	                System.exit(0);
	            }
	         }
	}
	
	
	public static void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	    			
	    		   file.delete();
	    		   System.out.println("Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	    			
	    		}else{
	    			
	    		   //list all the directory contents
	        	   String files[] = file.list();
	     
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	        		 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	        		
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     System.out.println("Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	    		
	    		
	    		
	    		
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
	
	public static void main(String args[]){
		String product = "TAS-10.1";
		String release = "183838#SIP Redirection support added to 10.1";
		
		String path = Utility.getDirectoryPath(product, release);
		
		System.out.println("path : "+path);
		
		Utility.deleteDirectory(product, release);
	}
	
}
