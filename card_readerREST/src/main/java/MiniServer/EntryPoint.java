/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// https://nikolaygrozev.wordpress.com/2014/10/16/rest-with-embedded-jetty-and-jersey-in-a-single-jar-step-by-step/
package MiniServer;
 
import Database.CardData;
import Database.Database_connector_sqlite;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.FilenameUtils;
 
@Path("/api")
public class EntryPoint {
 
    private static final String baseDirectory = System.getProperty("user.home");
    private static final String databasePath = FilenameUtils.separatorsToSystem(baseDirectory + "/" + "es_module.db");
    Database_connector_sqlite db = new Database_connector_sqlite();
    
    ObjectMapper mapper = new ObjectMapper();
    /*
    implementacao baseada nisto:
    http://www.mkyong.com/webservices/jax-rs/jax-rs-pathparam-example/
    http://www.mkyong.com/webservices/jax-rs/json-example-with-jersey-jackson/
    */
    
    /**
     * Gets the person card info for a given ID
     * Example usage: GET request to the URI: /api/cc/423423
     * @param user_id
     */
    @GET
    @Path("/cc/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonData(@PathParam("id") String id) {

        db.connect(databasePath);
        CardData card = db.getInfoByIID(id);
        db.connection_close();
        
        String res;
        if(card!=null){
            try {
                res = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(card);
                return Response.status(200).entity(res).build();
             } catch (JsonProcessingException ex) {
                  ex.printStackTrace();
             }
        }
        res="Person does not exist in the database";
        return Response.status(404).entity(res).build();

    }
}


