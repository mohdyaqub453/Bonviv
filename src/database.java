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


public class database {
	
	public static void main(String[] args) {

	
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
					+ "SELECT ?Title ?Genre ?Author_Name \r\n"
					+ "WHERE {\r\n"
					+ "  \r\n"
					+ "		?subject  ab:Title ?Title;\r\n"
					+ "                  ab:Genre ?Genre;\r\n"
					+ "                  ab:authors ?author.\r\n"
					+ "         ?author ab:Author_Name ?Author_Name;\r\n"
					+ "                 ab:Age ?Author_age.\r\n"
					+ "                  \r\n"
					+ "                  \r\n"
					+ "\r\n"
					+ "          \r\n"
					+ "}";
			
			
		  Query query = QueryFactory.create(querystring);
			//QueryExecution qexec =QueryExecutionFactory.create(query, dataset);
			
			  
			
		        JsonArray jsonArray = new JsonArray() ;
		        //queryIterator = qexec.execSelect();
		        
		        List<String> resultVars = query.getResultVars() ; 
		        System.out.println(resultVars);
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
			         
			          System.out.println(jsonArray);
				/*while(result.hasNext()) {
					QuerySolution soln = result.nextSolution();
					RDFNode Title = soln.get("?Title");
					RDFNode author = soln.get("?Author_Name");
					System.out.println("Title "+ Title + "   Author "+ author);
				}
			}
			finally {
				qexec.close();
			}*/
			}

	

}
