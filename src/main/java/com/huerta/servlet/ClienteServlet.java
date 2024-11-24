package com.huerta.servlet;

import com.google.gson.Gson;
import com.huerta.dao.ClienteJpaController;
import com.huerta.dto.Cliente;
import com.huerta.util.UtilValidarTiposToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ClienteServlet", urlPatterns = {"/clienteServlet"})
public class ClienteServlet extends HttpServlet {
    
    private ClienteJpaController clienteJpaController; 

    public ClienteServlet() {
        clienteJpaController = new ClienteJpaController(); 
    }
    
    
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String authHeader = request.getHeader("Authorization");
            String tipoToken = request.getParameter("tipo"); 
            String usuario = request.getParameter("usuario"); 

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Extraer el token
                
                if (UtilValidarTiposToken.valida(token, tipoToken, usuario)) {
                    
                    List<Cliente> clientes = clienteJpaController.findClienteEntities(); 
                    out.println("{\"respuesta\":\"OK\", \"lista\":"+ new Gson().toJson(clientes)+"}");
                }else {
                    out.println("{\"respuesta\":\"Usted no tiene los permisos para acceder a este servlet\"}");
                }
                
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado");
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
