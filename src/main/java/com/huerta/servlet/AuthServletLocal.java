
package com.huerta.servlet;

import com.huerta.app.util.cifrados.DES_ECB;
import com.huerta.dao.UsuarioJpaController;
import com.huerta.util.cifrados.AES_ECB;
import com.huerta.util.jwt.JwtUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "AuthServletLocal", urlPatterns = {"/authServletLocal"})
public class AuthServletLocal extends HttpServlet {

    
    private UsuarioJpaController usuJpa; 

    public AuthServletLocal() {
        usuJpa = new UsuarioJpaController(); 
    }
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            //recibiendo parámetros
            String login = request.getParameter("login"); 
            String pass = request.getParameter("pass"); 
            
            
            //trayendo la clave secreta compartida
            HttpSession miSession = request.getSession(false);
            String claveSecretaCompartida = (String) miSession.getAttribute("ClaveSecretaCompartidaDH");
            
            //DECIFRAR PASSWORD
            pass = AES_ECB.descifrar(pass, claveSecretaCompartida);
            //pass = DES_ECB.decifrar(pass, claveSecretaCompartida);
            
            //validando
            int resultado =  usuJpa.validar(login, pass); 
            
            if (resultado > 0) {
                String token = JwtUtil.generateToken(login);
                System.out.println(token);
                out.println("{\"resultado\":\"OK\",\"token\":\"" + token + "\"}");
            }else {
                out.println("{\"resultado\":\"ERROR\"}");
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
