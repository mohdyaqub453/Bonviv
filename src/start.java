import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.*;
import org.apache.jena.query.*;

public class start {

	public static void main(String[] args) {
		
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
		model.write(System.out,"JSONLD");
		
		
		
		String querystring = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "prefix owl: <http://www.w3.org/2002/07/owl#>\r\n"
				+ "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "prefix ab: <http://learningsparql.com/ns/addressbook#>\r\n"
				+ "prefix d:<http://learningsparql.com/ns/data#>\r\n"
				+ "SELECT ?Title ?Genre ?Author_Name ?Author_age\r\n"
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
		QueryExecution qexec =QueryExecutionFactory.create(query, model);
		
		try 
		{
			ResultSet result =qexec.execSelect();
			while(result.hasNext()) {
				QuerySolution soln = result.nextSolution();
				RDFNode Title = soln.get("?Title");
				RDFNode author = soln.get("?Author_Name");
				System.out.println("Title "+ Title + "   Author "+ author);
			}
		}
		finally {
			qexec.close();
		}
		
		
		//query from fuseki server
		/*
		String service = "http://localhost:3030/first";
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
		}*/
	}

}
