/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author rofler
 */
public class Runner {
    
    static String RestServer_URL = "http://localhost:3390/Rester-1/rest/ES_REST_API/find_person/";
    
    public static void main(String[] args) {
        System.err.println("name = " + getUserNameFromRest());
    }
    
    private static String getUserNameFromRest() {
        String name = "";

        try {
            URL url;
            url = new URL(RestServer_URL + "3");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                JSONObject jo = new JSONObject(output);
                JSONObject person_info = (JSONObject) jo.getJSONObject("person_info");
                name = person_info.getString("firstname");
            }

            conn.disconnect();

        } catch (MalformedURLException ex) {
           // Logger.getLogger(googleCalcStepDefinition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
           // Logger.getLogger(googleCalcStepDefinition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(googleCalcStepDefinition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            //Logger.getLogger(googleCalcStepDefinition.class.getName()).log(Level.SEVERE, null, ex);
        }

        return name;
    }
    
}
