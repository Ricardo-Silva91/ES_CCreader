/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cardIO.CC_IO;
import entities.Person;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.stream.JsonParser;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author rofler
 */
@Stateless
@Path("entities.person")
public class PersonFacadeREST extends AbstractFacade<Person> {
    
    private static String currentCard_path = "/home/rofler/current_card.json";
    
    @PersistenceContext(unitName = "com.mycompany_CustomerDB_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public PersonFacadeREST() {
        super(Person.class);
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Person find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public static String getCurrentCardId() {
        
        String res = null;
        
        try {
            JSONParser parser = new JSONParser();
            
            JSONObject json_o;
            json_o = (JSONObject) parser.parse(new FileReader(currentCard_path));
            res = json_o.get("numBI").toString();
            
        } catch (FileNotFoundException ex) {
            System.err.println("File not Found");
        } catch (IOException ex) {
            System.err.println("IO Exception");
        } catch (ParseException ex) {
            System.err.println("Parse Exception");
        }
                
        return res;
                
    }
    
}
