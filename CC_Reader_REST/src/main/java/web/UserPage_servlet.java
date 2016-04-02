/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import cardIO.CC_IO;
import cardIO.CardData;
import entities.Person;
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
import service.PersonFacadeREST;

/**
 *
 * @author rofler
 */
@WebServlet(name = "UserPage_servlet", urlPatterns = {"/UserPage_servlet"})
public class UserPage_servlet extends HttpServlet {

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
        String numBI_current = PersonFacadeREST.getCurrentCardId();

        if (numBI_current == null) {

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

                out.println(" <b>" + person.getNumBI() + " </b><br />");
                out.println(" <b>" + person.getCardNumber() + "<br /> ");

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
