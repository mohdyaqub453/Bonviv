import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.jena.query.*;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.lib.RDFTerm2Json;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.atlas.json.JsonValue;
import java.util.List;
import org.apache.jena.graph.Node;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class database {
	
	
	public JsonArray jsonfromTDB()
	{
		String directory = "MyDatabases/dataset" ;
		  Dataset dataset = TDBFactory.createDataset(directory) ;
		
		  dataset.begin(ReadWrite.READ) ;
		  // Get model inside the transaction
		  //Model model = dataset.getDefaultModel() ;
		 // model.write(System.out ,"TURTLE");
		 /* dataset.end() ;
		  
		  dataset.begin(ReadWrite.WRITE) ;
		  model = dataset.getDefaultModel() ;
		  Model model1 = ModelFactory.createDefaultModel();
		  model.read("example.jsonld") ;
		  model.add(model1);
		  
		  model.commit(); 
		  dataset.end() ;*/
		  
		  String querystring = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
					+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "prefix owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "prefix ab: <http://learningsparql.com/ns/addressbook#>\r\n"
					+ "prefix d:<http://learningsparql.com/ns/data#>\r\n"
					+ "SELECT ?title ?genre ?name ?age\r\n"
					+ "WHERE {\r\n"
					+ "  \r\n"
					+ "		?subject  ab:Title ?title;\r\n"
					+ "                  ab:Genre ?genre;\r\n"
					+ "                  ab:authors ?author.\r\n"
					+ "         ?author ab:Author_Name ?name;\r\n"
					+ "                 ab:Age ?age.\r\n"
					+ "                  \r\n"
					+ "                  \r\n"
					+ "\r\n"
					+ "          \r\n"
					+ "}LIMIT 2";
			
			
		  Query query = QueryFactory.create(querystring);
			//QueryExecution qexec =QueryExecutionFactory.create(query, dataset);
			
			  
			
		        JsonArray jsonArray = new JsonArray() ;
		        //queryIterator = qexec.execSelect();
		        
		        List<String> resultVars = query.getResultVars() ; 
		        //System.out.println(resultVars);
		        Op op = Algebra.compile(query) ;
		        op = Algebra.optimize(op) ;
		        QueryIterator queryIterator = Algebra.exec(op, dataset) ;
		        while (queryIterator.hasNext())
		        {
		            Binding binding = queryIterator.next() ;
		            JsonObject jsonObject = new JsonObject() ;
		            for (String resultVar : resultVars) {
		                Node n = binding.get(Var.alloc(resultVar)) ;
		                JsonValue value = RDFTerm2Json.fromNode(n) ;
		                jsonObject.put(resultVar, value) ;
		            }
		            jsonArray.add(jsonObject) ;
		        }
			         
			    return jsonArray;		
	}
	
	public static void main(String[] args) {

	   database db =  new database();
	   JsonArray output = db.jsonfromTDB();
	   String jsonString = output.toString();
	   
	   ObjectMapper mapper = new ObjectMapper();
	      //map json to student
	      try{
	         Book[] book = mapper.readValue(jsonString, Book[].class);
	         
	         for (Book itr : book) {
	        	   System.out.println("Title of Book is : " + itr.getTitle());
	        	   System.out.println("Genre is : " + itr.getGenre());
	        	    System.out.println("Name of Author is : " + itr.getName());
	        	    System.out.println("Age of Author is: " + itr.getAge());
	        	    
	        	}
	         
	         jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(book);
	         
	        // System.out.println(jsonString);
	      }
	      catch (JsonParseException e) { e.printStackTrace();}
	      catch (JsonMappingException e) { e.printStackTrace(); }
	      catch (IOException e) { e.printStackTrace(); }
}
}



class Book {
	   private String title;
	   private String genre;
	   private String name;
	   private String age;
	   public Book(){}
	   public String getTitle() {
		   return title;
	   }
	   public void setTitle(String title) {
		   this.title = title;
	   }
	   public String getGenre() {
		   return genre;
	   }
	   public void setGenre(String genre) {
		   this.genre = genre;
	   }
	   public String getName() {
	      return name;
	   }
	   public void setName(String name) {
	      this.name = name;
	   }
	   public String getAge() {
	      return age;
	   }
	   public void setAge(String age) {
	      this.age = age;
	   }
	   public String toString(){
		      return "Book [ name: "+name+", age: "+ age+ " ]";
	}
}
