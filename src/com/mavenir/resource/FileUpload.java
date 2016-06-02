package com.mavenir.resource;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



public class FileUpload extends HttpServlet {
  
    private static final long serialVersionUID = 205242440643911308L;
     
    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    
      
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	System.out.println("Reached file Upload ...");
    	
    	

         
         
    	String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        
        System.out.println("ipAddress : "+ipAddress);
        
        System.out.println("URL1 : "+request.getRequestURI());
        
        System.out.println("URL2 : "+request.getRequestURL());
        
        System.out.println("URL3 : "+request.getServletPath());
        
        String product = "";
        if(request.getParameter("productName") != null)
        		product = request.getParameter("productName");
        
        String release = "";
        if(request.getParameter("releaseName") != null)
        	release = request.getParameter("releaseName");
        
        System.out.println(" releaseName  : "+release);
        
        
        
       
        String FileDirectory = Utility.getDirectoryPath(product, release);
        
        System.out.println(" FileDirectory  : "+FileDirectory);
        
   	 File uploadDir = new File(FileDirectory);
     if (!uploadDir.exists()) {
         /*uploadDir.mkdirs();
         uploadDir.setExecutable(true);
         uploadDir.setReadable(true);
         uploadDir.setWritable(true);*/
    	 Set<PosixFilePermission> perms =
    			    PosixFilePermissions.fromString("rwxrwxrwx");
    			FileAttribute<Set<PosixFilePermission>> attr =
    			    PosixFilePermissions.asFileAttribute(perms);
    			
    	 Files.createDirectories(Paths.get(FileDirectory), attr);
     }
        
     
     
        request.setCharacterEncoding("utf-8");
        if (!"OPTIONS".equalsIgnoreCase(request.getMethod()) && request.getParameter("errorCode") != null) {
        	System.out.println(" ! OPTIONS");
        	response.sendError(Integer.parseInt(request.getParameter("errorCode")), request.getParameter("errorMessage"));
            return;
        }
        
        StringBuilder sb = new StringBuilder("{\"result\": [");
        
        System.out.println(" multipart content : "+ServletFileUpload.isMultipartContent(request));
        
        System.out.println(" content Type : "+request.getHeader("Content-Type"));
        
  
        
        
    	 if(ServletFileUpload.isMultipartContent(request)){
            
    		 try {
                 List<FileItem> multiparts = new ServletFileUpload(
                                          new DiskFileItemFactory()).parseRequest(request);
                 

                 System.out.println(" Size : "+multiparts.size());
                 
                 int count = 1;
                 for(FileItem item : multiparts){
                     if(!item.isFormField()){
                    	 String name = new File(item.getName()).getName();
                         
                    	 sb.append("{");
                         sb.append("\"fieldName\":\"").append(item.getFieldName()).append("\",");
                         
                         System.out.println(" name : "+name);
                         
                         if (item.getName() != null) {
                             sb.append("\"name\":\"").append(item.getName()).append("\",");
                         }
                         if (item.getName() != null) {
                             sb.append("\"size\":\"").append(" few Bytes ").append("\"");
                         } else {
                             sb.append("\"value\":\"").append(" few Bytes ").append("\"");
                         }
                         sb.append("}");
                         if (count < multiparts.size()) {
                             sb.append(",");
                         }
                         
                         
                         
                         item.write( new File(FileDirectory + File.separator + name));
                         
                         count++;
                     }
                 }
            
                
            
                 
                 
             } catch (Exception ex) {
            	 System.out.println("Error whilewriting the file ..");
            	 ex.printStackTrace();
            	 throw new ServletException(ex);
            	 
             }          
          
         }else{
        	 
        	 System.out.println("Not a Multipart content");
        	 sb.append("{\"size\":\"" + " few Bytes" + "\"}");
         }
    	 sb.append("]");
    	 
         sb.append("}");
        
         
         System.out.println(" Final Response : "+sb.toString());
         response.setCharacterEncoding("utf-8");
         response.getWriter().write(sb.toString());
         
    }
  

    
}
