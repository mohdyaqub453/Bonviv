import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.jena.query.*;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.tdb.TDBFactory;

public class dataset {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("jdo");
		Model model = ModelFactory.createDefaultModel();
		model.read("example.jsonld") ;
		model.write(System.out,"turtle");
		
		String querystring = "SELECT * WHERE{?subject ?predicate ?object}";
		Query query = QueryFactory.create(querystring);
		QueryExecution qexec =QueryExecutionFactory.create(query, model);
		
		try 
		{
			ResultSet result =qexec.execSelect();
			ResultSetFormatter.out(System.out, result);
		}
		finally {
			qexec.close();
		}
		
		/*
		String service = "http://localhost:3030/first";
		String query = "\r\n"
				+ "SELECT ?subject ?predicate ?object\r\n"
				+ "WHERE {\r\n"
				+ "  ?subject ?predicate ?object\r\n"
				+ "}\r\n"
				+ "LIMIT 25";
		try (QueryExecution qe = QueryExecutionFactory.sparqlService(service, query)) {
		    ResultSet rs = qe.execSelect();
		    ResultSetFormatter.outputAsTSV(rs);
		}
		
		
		model.read("https://fragments.dbpedia.org/2016-04/en");
		model.write(System.out,"TURTLE");
		/*HttpClientContext httpContext = new HttpClientContext();
		String querystring ="SELECT * WHERE { ?s ?p ?o } LIMIT 10";
		Query query = QueryFactory.create(querystring);
		QueryEngineHTTP qEngine = QueryExecutionFactory.createServiceRequest("http://fragments.dbpedia.org/2015/en", query);
		qEngine.setHttpContext(httpContext);
		
		
		try 
		{
			ResultSet result = qEngine.execSelect();
			ResultSetFormatter.out(result);
		}
		finally {
			qEngine.close();
		}
		 /* String directory = "MyDatabases/dataset" ;
		  Dataset dataset = TDBFactory.createDataset(directory) ;
		
		  dataset.begin(ReadWrite.READ) ;
		  // Get model inside the transaction
		  Model model = dataset.getDefaultModel() ;
		  
		  model.write(System.out);
		  
		  dataset.end() ;
		  
		  dataset.begin(ReadWrite.WRITE) ;
		  model = dataset.getDefaultModel() ;
		  model.commit(); 
		  dataset.end() ;
		  
		/*String szStoreName  = args[0];
		String szHostName   = args[1];
		String szHostPort   = args[2];
		int iBatchSize  = Integer.parseInt(args[3]);
		int iDOP = Integer.parseInt(args[4]);

		System.out.println("Create Oracle NoSQL connection");
		OracleNoSqlConnection conn 
		= OracleNoSqlConnection.createInstance(szStoreName,
		                                       szHostName, 
		                                       szHostPort);
		     
		System.out.println("Create Oracle NoSQL datasetgraph");
		OracleGraphNoSql graph = new OracleGraphNoSql(conn);
		DatasetGraphNoSql datasetGraph = DatasetGraphNoSql.createFrom(graph);
		   
		// Close graph, as it is no longer needed
		graph.close();
		    
		// Clear datasetgraph
		datasetGraph.clearRepository();
		    
		// Load data from file into the Oracle NoSQL Database
		DatasetGraphNoSql.load("example.nt", Lang.NQUADS, conn, 
		                        "http://example.org",
		                        iBatchSize, // batch size
		                        iDOP); // degree of parallelism
		    
		// Create dataset from Oracle NoSQL datasetgraph to execute
		Dataset ds = DatasetImpl.wrap(datasetGraph);
		   
		String szQuery = "select * where { graph ?g { ?s ?p ?o }  }";
		System.out.println("Execute query " + szQuery);
		    
		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, ds);
		    
		try {
		      ResultSet results = qexec.execSelect();
		      ResultSetFormatter.out(System.out, results, query);
		    }
		    
		finally {
		      qexec.close();
		    }

		ds.close();
		conn.dispose();*/

	}

}
