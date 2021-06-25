import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdfconnection.*;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.vocabulary.*;
//import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;

//@RestController
public class fusekiServer {
    private RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
            .destination("http://localhost:3030/first");
    private RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ;
   
    public Boolean insert() {

        // In this variation, a connection is built each time.
       
            UpdateRequest request = UpdateFactory.create();
            request.add("PREFIX example: <http://example/> INSERT DATA {example:anne example:age 30 . example:peter example:birthyear 1982  } ;");
            conn.update(request);
        return true;
    }

 
    public Boolean delete() {

        // In this variation, a connection is built each time.
        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {
            UpdateRequest request = UpdateFactory.create();

            request.add("PREFIX example: <http://example/> DELETE DATA {example:anne example:age 30 . example:peter example:birthyear 1982  } ;");
            conn.update(request);
        }
        return true;
    }

  
    public Boolean update() {
        // In this variation, a connection is built each time.
        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) {
            UpdateRequest request = UpdateFactory.create();

            // The idea is SPARQL is not for relational data! Its for graph data
            // So here we are just deleting the old record and inserting new one
            request.add("PREFIX example: <http://example/> DELETE DATA { example:anne example:age 30 }; INSERT DATA { example:anne example:age 12 . };");
            conn.update(request);
        }
        return true;
    }

   // @GetMapping("/select")
    public  String select() {
        // In this variation, a connection is built each time.
       

            QueryExecution qExec = conn.query("prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> prefix owl: <http://www.w3.org/2002/07/owl#> SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object }");
            ResultSet rs = qExec.execSelect() ;

            // Converting results into JSON
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(outputStream, rs);
            return new String(outputStream.toByteArray());
        
    }
    
    public static void main(String[] args) {
    	fusekiServer fs = new fusekiServer();
    	 System.out.println(fs.insert());
    	System.out.println(fs.select());
       
    }
    }
