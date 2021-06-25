import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.vocabulary.*;
import org.apache.jena.rdfconnection.*;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import java.io.ByteArrayOutputStream;
import org.apache.jena.query.*;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.atlas.json.JsonValue;
import java.util.List;

public class starts {

	public static void main(String[] args) {
		System.out.println("jdo");
		String URI = "https://abc/animal";
		Model m = ModelFactory.createDefaultModel();
        
		Resource animal1 = m.createResource(URI);
		animal1.addProperty(VCARD.FN,"ELEPHANT");
		//m.write(System.out,"RDF/XML");*/
		
		Model model = ModelFactory.createDefaultModel() ;
		model.read("book.ttl") ;
		
		Model model1 = ModelFactory.createDefaultModel();
		model.read("example.jsonld") ;
		model.add(model1);
		//model.write(System.out,"JSONLD");
		
		
		
		String querystring ="\r\n"
				+"prefix ab: <http://learningsparql.com/ns/addressbook#>\r\n"
				+ "prefix d:<http://learningsparql.com/ns/data#>\r\n"
				+ "select ?subject  ?Title\r\n"
				+ "WHERE {\r\n"
				+ "  ?subject ab:Title ?Title\r\n"
				+ "}\r\n"
				+ "LIMIT 5";
		
		Query query = QueryFactory.create(querystring);
		QueryExecution qexec =QueryExecutionFactory.create(query, model);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try 
		{
			
			JsonArray results = qexec.execJson();
			//results.write(outputStream, "JSONLD");
			System.out.println("strinfg");
		
		
		}
		finally {
			qexec.close();
		}
		
		String service = "http://localhost:3030/first";
		
		//query from fuseki server
		
	
		String query1 = "\r\n"
				+"prefix ab: <http://learningsparql.com/ns/addressbook#>\r\n"
				+ "prefix d:<http://learningsparql.com/ns/data#>\r\n"
				+ "SELECT ?subject ?predicate ?object\r\n"
				+ "WHERE {\r\n"
				+ "  ?subject ?predicate ?object\r\n"
				+ "}\r\n"
				+ "LIMIT 5";
		try (QueryExecution qe = QueryExecutionFactory.sparqlService(service, query1)) {
		    ResultSet rs = qe.execSelect();
		    ResultSetRewindable results = ResultSetFactory.makeRewindable(rs);

		    
		    System.out.println("---- XML ----");
            ResultSetFormatter.outputAsXML(System.out, results);
            results.reset();

            System.out.println("---- Text ----");
            ResultSetFormatter.out(System.out, results);
            results.reset();

            System.out.println("\n---- CSV ----");
            ResultSetFormatter.outputAsCSV(System.out, results);
            results.reset();

            System.out.println("\n---- TSV ----");
            ResultSetFormatter.outputAsTSV(System.out, results);
            results.reset();
            
            System.out.println("\n---- JSON ----");
            ResultSetFormatter.outputAsJSON(System.out, results);
            results.reset();
		}
		
		//update via jena in fuseqi
		
		 
		
		System.out.println(outputStream);
	}

}
