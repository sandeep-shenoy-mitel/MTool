package com.mavenir.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.mavenir.entity.Documents;
import com.mavenir.entity.Release;



@Path("/dashboardService")
public class DashBoardResource
{

    static Logger log = Logger.getLogger(DashBoardResource.class);

    static SessionFactory sessionFactory = null;

    

    @GET
    @Path("/heartbeat")
    @Produces({ "application/xml" })
    public Response getNodeStatus()
    {
        log.debug("If the request reached here means node is up and running...");
        Response response = Response.ok().build();
        return response;
    }
    
    @GET
	@Path("/release/list")
    @Produces({ MediaType.APPLICATION_JSON })
	public Response getReleaseList() {
    	
    	 SessionFactory sessionFactory = DashBoardResource.getSessionFactory();
         
         Session session = sessionFactory.getCurrentSession();
         Transaction tx = session.beginTransaction();
         
         ArrayList<Release> list = (ArrayList)session.createCriteria(Release.class).list();

         tx.commit();
         
         //session.close();
         
         
         DashBoardResource.closeSessionFactory();
         
         System.out.println("List Size : "+list.size());
         Iterator<Release> lstItr = list.iterator();
         
         while(lstItr.hasNext()){
       	  Release obj = lstItr.next();
       	  System.out.println(obj.getName() );
         }
         
    	 
    	 
     Response response = Response.ok(list,MediaType.APPLICATION_JSON).
    			 header("Access-Control-Allow-Origin", "*").
    	         header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").
    	          build();
         return response;

        
         
         
	}
    
    

   
	
	@POST
	@Path("/release/add")
	public Response addRelease(String input) {
		System.out.println("Reached POST the method..");
		System.out.println("input : "+input);

   	 SessionFactory sessionFactory = DashBoardResource.getSessionFactory();
   	 	
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        ObjectMapper mapper = new ObjectMapper();
        Release newRelease = null;
        try {
			 newRelease = mapper.readValue(input, Release.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        session.persist(newRelease);
        tx.commit();
		Response response = Response.ok(). header("Access-Control-Allow-Origin", "*").
   	         header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization").header("Accept", "application/json;text/plain; * / *").build();
        return response;
	}
	
	
	
	@POST
	@Path("/release/update")
	public Response updateRelease(String input) {
		System.out.println("Reached POST the method..");
		System.out.println("input : "+input);

   	 SessionFactory sessionFactory = DashBoardResource.getSessionFactory();
   	 	
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        ObjectMapper mapper = new ObjectMapper();
        Release newRelease = null;
        try {
			 newRelease = mapper.readValue(input, Release.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        session.merge(newRelease);
        tx.commit();
		Response response = Response.ok(). header("Access-Control-Allow-Origin", "*").
   	         header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization").header("Accept", "application/json;text/plain; * / *").build();
        return response;
	}
    
    
	@POST
	@Path("/release/delete")
	public Response deleteRelease(String input) {
		System.out.println("Reached POST the method..");
		System.out.println("input : "+input);

   	 SessionFactory sessionFactory = DashBoardResource.getSessionFactory();
   	 	
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        ObjectMapper mapper = new ObjectMapper();
        Release newRelease = null;
        try {
			 newRelease = mapper.readValue(input, Release.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        session.delete(newRelease);
        tx.commit();
		Response response = Response.ok(). header("Access-Control-Allow-Origin", "*").
   	         header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").header("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization").header("Accept", "application/json;text/plain; * / *").build();
		
		Utility.deleteDirectory(newRelease.getProductName(), newRelease.getName());
		
        return response;
	}
	
	
	   @GET
		@Path("/documents/list/{product},{release}")
	    @Produces({ MediaType.APPLICATION_JSON })
		public Response getDocumentList(@PathParam("product") String productName,@PathParam("release") String releaseName) {
	    	
		   	System.out.println("inside getDocumentList method, product Name : "+productName+" release Name : "+releaseName);
		   	List<Documents> docLst = new ArrayList<Documents>();
		   	
		   	String myDirectoryPath = Utility.getDirectoryPath(productName, releaseName);
		   	System.out.println("myDirectoryPath : "+myDirectoryPath);
		   	
		   	File dir = new File(myDirectoryPath);
		    File[] directoryListing = dir.listFiles();
		    if (directoryListing != null) {
		      for (File child : directoryListing) {
		    	  Documents doc = new Documents();
		    	  doc.setProductName(productName);
		    	  doc.setName(releaseName);
		    	  doc.setDocumentName(child.getName());
		    	  doc.setPath(child.getAbsolutePath());
		    	  docLst.add(doc);
		      }
		    } else {
		      System.out.println("No files found under the directory : "+myDirectoryPath);
		    }
	    	 
	    	 
	     Response response = Response.ok(docLst,MediaType.APPLICATION_JSON).
	    			 header("Access-Control-Allow-Origin", "*").
	    	         header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").
	    	          build();
	         return response;

	        
	         
	         
		}
    
    public static void main(String args[]){
    	
         
         
        
 		//StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build(); 
 		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();  
 		Session session = factory.getCurrentSession();
 		
         Transaction tx = session.beginTransaction();
        
         List<Release> list = session.createCriteria(Release.class).list();

         tx.commit();
         String json = "%7B%22productName%22%3A%22RMS-6.9%22%2C%22name%22%3A%22CR-24%22%2C%22status%22%3A%22STARTED%22%2C%22owner%22%3A%22Anirban%22%2C%22startDate%22%3A%222016-05-08T18%3A30%3A00.000Z%22%2C%22endDate%22%3A%222016-05-08T18%3A30%3A00.000Z%22%2C%22mnodeAvailable%22%3A%22YES%22%2C%22comments%22%3A%22test%22%7D";
         ObjectMapper mapper = new ObjectMapper();
         Release newRelease = null;
         try {
 			 newRelease = mapper.readValue(json, Release.class);
 		} catch (JsonParseException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} catch (JsonMappingException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
      //   session.close();
         
         System.out.println("List Size : "+list.size());
      Iterator<Release> lstItr = list.iterator();
      
      while(lstItr.hasNext()){
    	  Release obj = lstItr.next();
    	  System.out.println(obj.getName() );
      }
         
         
         if(sessionFactory != null && !sessionFactory.isClosed()){
     		System.out.println("Closing sessionFactory");
             sessionFactory.close();
         }
    }
    
    public static SessionFactory getSessionFactory(){
    	 
    	if(sessionFactory == null){
    		sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();  

         
         return sessionFactory;
         
    	}else{
    		return sessionFactory;
    	}
		
         
    }
    
    public static void closeSessionFactory(){
    	if(sessionFactory != null && !sessionFactory.isClosed()){
    		System.out.println("Closing sessionFactory");
            sessionFactory.close();
            sessionFactory = null;
        }
    }

}
