package com.mavenir.resource;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class DownloadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		 String product = "";
	        if(request.getParameter("productName") != null)
	        		product = request.getParameter("productName");
	        
	        String release = "";
	        if(request.getParameter("releaseName") != null)
	        	release = request.getParameter("releaseName");
	        
	        String fileName = "";
	        if(request.getParameter("fileName") != null)
	        	fileName = request.getParameter("fileName");
	        
	        
	        System.out.println(" productName  : "+product);
	        
	        System.out.println(" releaseName  : "+release);
	        
	        System.out.println(" fileName  : "+fileName);
		
		response.setContentType("text/html");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		PrintWriter out = response.getWriter();
		
		String filePath = Utility.getDirectoryPath(product, release)+File.separator+fileName;
		
		 System.out.println(" filePath  : "+filePath);
		
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");

		FileInputStream fileInputStream = new FileInputStream(filePath);

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out.write(i);
		}
		fileInputStream.close();
		out.close();
	}

}
