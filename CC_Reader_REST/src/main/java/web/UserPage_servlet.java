/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entities.CurrentCard;
import entities.Person;
import entities.Interaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.CurrentCardFacadeREST;
import service.InteractionFacadeREST;
import service.PersonFacadeREST;

/**
 *
 * @author rofler
 */
@WebServlet(name = "UserPage_servlet", urlPatterns = {"/UserPage_servlet"})
public class UserPage_servlet extends HttpServlet {

    @EJB
    private CurrentCardFacadeREST currentCardFacadeREST;

    @EJB
    private InteractionFacadeREST interactionFacadeREST;

    @EJB
    private PersonFacadeREST personFacadeREST;

    
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
            throws ServletException, IOException {

        //CC_IO cc_io = new CC_IO();
        //String test = "test";
        //CardData card_inserted = new CardData(test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test, test);
        List current_cards = currentCardFacadeREST.findAll();
        CurrentCard current_card =(CurrentCard) current_cards.get(0);
        String numBI_current = current_card.getPersonId();
        System.err.println(numBI_current);

        if (numBI_current.equals("dummy")) {

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error Page</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Sorry, but there is no card in the reader</h1>");

                out.println(" <b> Please insert Citizen card in the reader and try again </b><br />");

                out.println(" <b> If the card is properly inserted and this message appears, wait a couple seconds and try again</b><br />");
                
                out.println("</body>");
                out.println("</html>");
            }
        } else {

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {

                //System.err.println(numBI_current);
                Person person = personFacadeREST.find(numBI_current);
                
                
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Welcome Page</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Welcome " + person.getFirstname() + "</h1>");
                out.println("<h2>Your Data: </h2>");

                out.println(" <b>numBI: </b><a>" + person.getNumBI() + " </a><br />");
                out.println(" <b>BirthDate: </b><a>" + person.getBirthDate() + " </a><br />");
                out.println(" <b>CardNumber: </b><a>" + person.getCardNumber() + " </a><br />");
                out.println(" <b>CardNumberPAN: </b><a>" + person.getCardNumberPAN() + " </a><br />");
                out.println(" <b>CardVersion: </b><a>" + person.getCardVersion() + " </a><br />");
                out.println(" <b>Country: </b><a>" + person.getCountry() + " </a><br />");
                out.println(" <b>DeliveryDate: </b><a>" + person.getDeliveryDate() + " </a><br />");
                out.println(" <b>DeliveryEntity: </b><a>" + person.getDeliveryEntity() + " </a><br />");
                out.println(" <b>DocumentType: </b><a>" + person.getDocumentType() + " </a><br />");
                out.println(" <b>Firstname: </b><a>" + person.getFirstname() + " </a><br />");
                out.println(" <b>Lastname: </b><a>" + person.getLastname() + " </a><br />");
                out.println(" <b>FirstnameFather: </b><a>" + person.getFirstnameFather() + " </a><br />");
                out.println(" <b>LastnameFather: </b><a>" + person.getLastnameFather() + " </a><br />");
                out.println(" <b>FirstnameMother: </b><a>" + person.getFirstnameMother() + " </a><br />");
                out.println(" <b>LastnameMother: </b><a>" + person.getLastnameMother() + " </a><br />");
                out.println(" <b>Height: </b><a>" + person.getHeight() + " </a><br />");
                out.println(" <b>Locale: </b><a>" + person.getLocale() + " </a><br />");
                out.println(" <b>Mrz1: </b><a>" + person.getMrz1() + " </a><br />");
                out.println(" <b>Mrz2: </b><a>" + person.getMrz2() + " </a><br />");
                out.println(" <b>Mrz3: </b><a>" + person.getMrz3() + " </a><br />");
                out.println(" <b>Nationality: </b><a>" + person.getNationality() + " </a><br />");
                out.println(" <b>Notes: </b><a>" + person.getNotes() + " </a><br />");
                out.println(" <b>NumNIF: </b><a>" + person.getNumNIF() + " </a><br />");
                out.println(" <b>NumSNS: </b><a>" + person.getNumSNS() + " </a><br />");
                out.println(" <b>NumSS: </b><a>" + person.getNumSS() + " </a><br />");
                out.println(" <b>Sex: </b><a>" + person.getSex() + " </a><br />");

                
                out.println("<h2>Your Logged Interactions: </h2>");            
                List interactions = interactionFacadeREST.findAll();
                for (Iterator it = interactions.iterator(); it.hasNext();) {
                    Interaction elem = (Interaction) it.next();
                    if(elem.getPersonId().getNumBI().equals((person.getNumBI()))){
                        out.println(" <b> Action: </b><a>" + elem.getInteraction()+ " </b><br />");
                        out.println(" <b> Room Code: </b><a>" + elem.getRoomCode()+ " </b><br />");
                        out.println(" <b> Timestamp: </b><a>" + elem.getTime()+ " </b><br />");
                    }
                }

                    out.println("</body>");
                    out.println("</html>");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
