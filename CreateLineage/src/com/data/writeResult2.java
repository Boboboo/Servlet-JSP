package com.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//directly write into DB
public class writeResult2 {
	
	public static void main(String[] args) {
		  
		  PreparedStatement ps = null;
	      String sql0 = null;
	      
	      
		  String taxid=null;
		  String lineage=null;
		  
		  String taxidCom=null;
		  String name;
		  String type = null;
		  
		  Map<String,ArrayList<Combination>> map= new HashMap<>(); 
		 
	      try {
	         Class.forName("org.postgresql.Driver");
	      } catch(ClassNotFoundException e) {
	         System.out.println("Class not found "+ e);
	      }
	      
	      
	      try {
		         Connection con = DriverManager.getConnection(
		            "jdbc:postgresql://localhost:5432/postgres","postgres", "722722");
			     
		         
			     Statement statement = con.createStatement();
		         ResultSet res = statement.executeQuery("SELECT * FROM mind_lineage_number");
		        
		         while (res.next()) {
		      	        taxid=res.getString("taxid");
		      	        lineage=res.getString("lineage");
		      	        
		      	        String temp[]=lineage.split("\\|\\|");
		      	        String nameList="";
		      	        String nameResult=null;
		      	       
		      	        for(int i=0;i<temp.length;i++) {
			    	        		Statement s = con.createStatement();
			    	        		String sql="SELECT * FROM nodes_all where taxid='"+temp[i]+"'";
			    	        		
			    	        		
			    	        		ResultSet result = s.executeQuery(sql);
			    	        			
		    	        			String resultName=null;
		    	        			String resultTaxid=null;
		    	        			
		    	        			Map<String, String> newMap=new HashMap<>();
			    	        		
		    	        			while (result.next()) {
			    	 	      	      taxidCom=result.getString("taxid");
			    	 	      	      name=result.getString("name");
			    	 	      	      type=result.getString("type");
			    	 	      	      
			    	 	      	      if(!newMap.containsKey(type)) {
			    	 	      	    	  		newMap.put(type, name);
			    	 	      	      }else {
			    	 	      	    	  		newMap.put(type, newMap.get(type));
			    	 	      	      }
			    	        		}

		    	        			if(newMap.get("unique name")!=null) {
		    	        				resultName=newMap.get("unique name");
		    	        				resultTaxid=taxidCom;
		    	        			}
		    	        			else if(newMap.get("scientific name")!=null) {
		    	        				resultName=newMap.get("scientific name");
		    	        				resultTaxid=taxidCom;
		    	        			}
		    	        			else if(newMap.get("genbank common name")!=null) {
		    	        				resultName=newMap.get("genbank common name");
		    	        				resultTaxid=taxidCom;
		    	        			}
		    	        			else if(newMap.get("common name")!=null) {
		    	        				resultName=newMap.get("common name");
		    	        				resultTaxid=taxidCom;
		    	        			}
		    	        			else if(newMap.get("equivalent name")!=null) {
		    	        				resultName=newMap.get("equivalent name");
		    	        				resultTaxid=taxidCom;
		    	        			}
		    	        			else if(newMap.get("synonym")!=null) {
		    	        				resultName=newMap.get("synonym");
		    	        				resultTaxid=taxidCom;
		    	        			}
		    	        			
		    	        			nameList+=resultName+"["+resultTaxid+"]"+"||";
		    	        			nameResult=nameList.substring(0, nameList.length()-2);
		      	        }
		      	        System.out.println(taxid+" ** "+nameResult);
	      	   
		      	        sql0 = "INSERT INTO mind_lineage (taxid,lineage) values ('"+taxid+"','"+nameResult+ "')";
		      		
					    ps = con.prepareStatement(sql0);
				
					    ps.executeUpdate();
		         
		         }
		        System.out.println("done!!!"); 
		      
	      } catch(SQLException e) {
	         System.out.println("SQL exception occured" + e);
	      }
	      
	   }
}