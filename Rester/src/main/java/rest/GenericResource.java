/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import Database.CardData;
import Database.Database_connector_sqlite;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import static org.apache.commons.io.FilenameUtils.separatorsToSystem;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author rofler
 */
@Path("ES_REST_API")
public class GenericResource {

    @Context
    private UriInfo context;

    private static String baseDirectory = System.getProperty("user.home");
    private static String databasePath = separatorsToSystem(baseDirectory + "/" + "es_module.db");

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    @GET
    @Path("hello")
    @Produces("text/html")
    public String getHtml() {
        return "<html><body><h1>Hello, World!!</body></h1></html>";
    }

    /**
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("find_person")
    @Produces("application/json")
    public String getJson_no_user(@PathParam("id") String id) {

        String res = "";

        JSONObject jo = new JSONObject();
        jo.put("error", "user not found");
        res = jo.toString();

        return res;
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
    }

    /**
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("find_person/{id}")
    @Produces("application/json")
    public String getJson(@PathParam("id") String id) {

        Database_connector_sqlite db = new Database_connector_sqlite();

        String res = "";

        db.connect(databasePath);
        CardData card = db.getInfoByIID(id);
        db.connection_close();

        if (id == null || id.equals("") || card == null) {
            JSONObject jo = new JSONObject();
            jo.put("error", "user not found");
            res = jo.toString();
        } else {
            res = card.getJson_brief();
        }

        return res;
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    /*@PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }*/
    /**
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
    /*@GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
     */
}
