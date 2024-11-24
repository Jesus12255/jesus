package com.huerta.servlet;

import com.huerta.util.facebook.UtilValidarTokenFacebook;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name = "AuthServletFacebook", urlPatterns = {"/authServletFacebook"})
public class AuthServletFacebook extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el token desde la solicitud
        String accessToken = request.getParameter("token");

        try {
            JSONObject jsonResp = UtilValidarTokenFacebook.validar(accessToken);
            response.setContentType("application/json");
            response.getWriter().write(jsonResp.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
