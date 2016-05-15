/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import Database.CardData;
import Database.Card_Interaction;
import Database.Database_connector_sqlite;
//import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.io.FilenameUtils.separatorsToSystem;
import org.json.JSONObject;
/**
 *
 * @author rofler
 */
@WebServlet(name = "UserPage_servlet", urlPatterns = {"/UserPage_servlet"})
public class UserPage_servlet extends HttpServlet {

    //@EJB
    //private CurrentCardFacadeREST currentCardFacadeREST;
    //@EJB
    //private InteractionFacadeREST interactionFacadeREST;
    //@EJB
    //private PersonFacadeREST personFacadeREST;
    private static String baseDirectory = System.getProperty("user.home");
    private static String databasePath = separatorsToSystem(baseDirectory + "/" + "es_module_tests.db");

    int login_attempts = 0;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {

        //CC_IO cc_io = new CC_IO();
        //String test = "test";
        //CardData card_inserted = new CardData(test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test);
        Database_connector_sqlite db = new Database_connector_sqlite();
        db.connect(databasePath);
        String numBI_current = db.get_current_user();
        db.connection_close();
        String pass = request.getParameter("pass");
        String pass_conf = request.getParameter("pass_conf");
        boolean nocookies = false;
        String cookie_pass = "";

        //System.err.println("new session");
        //System.err.println("parameter value: " + pass);

        if (pass_conf != null && pass != null && pass_conf.equals(pass)) {
            /*System.err.println("entrou no if");
            System.err.println("new pass: " + pass);
            System.err.println("person: " + numBI_current);*/
            db.connect(databasePath);
            db.update_auth(pass, numBI_current);
            db.connection_close();
            /*Cookie cooki1 = new Cookie("pass", pass);
            cooki1.setMaxAge(1);
            response.addCookie(cooki1);
            Cookie cooki2 = new Cookie("pass_conf", pass_conf);
            cooki2.setMaxAge(1);
            response.addCookie(cooki2);*/
            Cookie cooki = new Cookie("pass", pass);
            cooki.setMaxAge(1);
            response.addCookie(cooki);
            response.sendRedirect("UserPage_servlet");
            return;
        } else if (pass != null) {
            /*System.err.println("entrou no else");*/
            Cookie cooki = new Cookie("pass", pass);
            cooki.setMaxAge(1);
            response.addCookie(cooki);
            response.sendRedirect("UserPage_servlet");

            return;
        }

        try {
            Cookie[] cookies = request.getCookies();
            int numberOfCookies = cookies.length;
            System.err.println("number of cookies: " + numberOfCookies);
            
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals("pass"))
                {
                    cookie_pass = cookie.getValue();
                }
            }
            
            //System.err.println("cookie value: " + request.getCookies()[0].getValue());
            //System.err.println("cookie2 value: " + request.getCookies()[1].getValue());
            //System.err.println("cookie time: " + request.getCookies()[0].getMaxAge());

        } catch (Exception err) {
            System.err.println("exc");
            nocookies = true;
        }
        //CurrentCardFacadeREST currentCardFacadeREST = (CurrentCardFacadeREST) InitialContext.doLookup("java:module/CurrentCardFacadeREST");//= getLocalEJB(CurrentCardFacadeREST.CURRENTCARD_EJB);
        //List current_cards = currentCardFacadeREST.findAll();
        //CurrentCard current_card = (CurrentCard) current_cards.get(0);
        //String numBI_current = current_card.getPersonId();
        //System.err.println(numBI_current);
        if (numBI_current.equals("dummy")) {

            login_attempts = 0;
            
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error Page</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1 id=\"greets\" >Sorry, but there is no card in the reader</h1>");

                out.println(" <b> Please insert Citizen card in the reader and try again </b><br />");

                out.println(" <b> If the card is properly inserted and this message appears, wait a couple seconds and try again</b><br />");

                out.println("</body>");
                out.println("</html>");
            }
        } else {

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                //System.err.println(numBI_current);
                //Person person = personFacadeREST.find(numBI_current);
                db.connect(databasePath);
                CardData card = db.get_person_info(numBI_current);
                db.connection_close();

                if (card.getAuthentication() == null) {

                    //System.err.println(request.getParameter("pass"));
                    response.setContentType("text/html;charset=UTF-8");

                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>CC Card Reader - First log</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1 id=\"greets\" >Hello, " + card.getFirstname() + " this is your first log</h1>");

                    out.println(" <b> Please insert your future password </b><br />");

                    out.println("<form>");
                    out.println("<b> password:</b><br /><input type='password' name='pass'></input> <br />");
                    out.println("<b>confirm password: </b><br /><input type='password' name='pass_conf' ></input> <br />");
                    out.println("<input type='submit'><br/>");
                    out.println("</form>");

                    out.println("</body>");
                    out.println("</html>");

                } else if (nocookies || !cookie_pass.equals(card.getAuthentication())) {
                    response.setContentType("text/html;charset=UTF-8");


                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>CC Card Reader - log</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1 id=\"greets\" >Hello, " + card.getFirstname() + " please log in</h1>");

                    out.println("<form>");
                    out.println("<b> password:</b><br /><input type='password' name='pass'></input> <br />");
                    out.println("<input type='submit'><br/>");

                    if (login_attempts > 0) {
                        out.println("<b>login attemts: " + login_attempts + "<br />");
                    }

                    login_attempts++;

                    out.println("</form>");

                    out.println("</body>");
                    out.println("</html>");

                } else {

                    login_attempts = 0;

                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>CC Card Reader - User Page</title>");
                    out.println("</head>");
                    out.println("<body>");

                    out.println("<a href='/CC_Reader_REST/' type='submit' name='logout'>log out</a><br/>");
                    out.println("<h1>Welcome " + card.getFirstname() + "</h1>");
                    out.println("<h2>Your Data: </h2>");

                    out.println(" <b>numBI: </b><a>" + card.getNumBI() + " </a><br />");
                    out.println(" <b>BirthDate: </b><a>" + card.getBirthDate() + " </a><br />");
                    out.println(" <b>CardNumber: </b><a>" + card.getCardNumber() + " </a><br />");
                    out.println(" <b>CardNumberPAN: </b><a>" + card.getCardNumberPAN() + " </a><br />");
                    out.println(" <b>CardVersion: </b><a>" + card.getCardVersion() + " </a><br />");
                    out.println(" <b>Country: </b><a>" + card.getCountry() + " </a><br />");
                    out.println(" <b>DeliveryDate: </b><a>" + card.getDeliveryDate() + " </a><br />");
                    out.println(" <b>DeliveryEntity: </b><a>" + card.getDeliveryEntity() + " </a><br />");
                    out.println(" <b>DocumentType: </b><a>" + card.getDocumentType() + " </a><br />");
                    out.println(" <b>Firstname: </b><a>" + card.getFirstname() + " </a><br />");
                    out.println(" <b>Lastname: </b><a>" + card.getLastname() + " </a><br />");
                    out.println(" <b>FirstnameFather: </b><a>" + card.getFirstnameFather() + " </a><br />");
                    out.println(" <b>LastnameFather: </b><a>" + card.getLastnameFather() + " </a><br />");
                    out.println(" <b>FirstnameMother: </b><a>" + card.getFirstnameMother() + " </a><br />");
                    out.println(" <b>LastnameMother: </b><a>" + card.getLastnameMother() + " </a><br />");
                    out.println(" <b>Height: </b><a>" + card.getHeight() + " </a><br />");
                    out.println(" <b>Locale: </b><a>" + card.getLocale() + " </a><br />");
                    out.println(" <b>Mrz1: </b><a>" + card.getMrz1() + " </a><br />");
                    out.println(" <b>Mrz2: </b><a>" + card.getMrz2() + " </a><br />");
                    out.println(" <b>Mrz3: </b><a>" + card.getMrz3() + " </a><br />");
                    out.println(" <b>Nationality: </b><a>" + card.getNationality() + " </a><br />");
                    out.println(" <b>Notes: </b><a>" + card.getNotes() + " </a><br />");
                    out.println(" <b>NumNIF: </b><a>" + card.getNumNIF() + " </a><br />");
                    out.println(" <b>NumSNS: </b><a>" + card.getNumSNS() + " </a><br />");
                    out.println(" <b>NumSS: </b><a>" + card.getNumSS() + " </a><br />");
                    out.println(" <b>Sex: </b><a>" + card.getSex() + " </a><br />");

                    //System.err.println(card.getAuthentication());

                    out.println("<h2>Your Logged Interactions: </h2>");
                    //List interactions = interactionFacadeREST.findAll();

                    db.connect(databasePath);
                    List<Card_Interaction> interactions = db.get_user_interactions(numBI_current);
                    db.connection_close();

                    for (Iterator it = interactions.iterator(); it.hasNext();) {
                        //Interaction elem = (Interaction) it.next();
                        Card_Interaction elem = (Card_Interaction) it.next();

                        long unixSeconds = Long.valueOf(elem.getTime()).longValue();
                        Date date = new Date(unixSeconds); // *1000 is to convert seconds to milliseconds
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date

                        out.println(" <b> Room code: </b><a>" + elem.getRoomCode() + "</a><b>&nbsp&nbsp&nbsp&nbspTimeStamp: </b><a>" + sdf.format(date) + "</a><b>&nbsp&nbsp&nbsp&nbsp&nbspAction: </b><a>" + elem.getInteraction() + "</a><br />");
                        out.println("<p></p>");
                        //out.println(" <b> Room Code: </b><a>" + elem.getRoomCode() + " </b><br />");

                        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//sdf.format(date)
                        //out.println(" <b> Timestamp: </b><a>" + sdf.format(date) + " </b><br />");
                    }

                    out.println("</body>");
                    out.println("</html>");
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(UserPage_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(UserPage_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
