
package com.huerta.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.KeyAgreement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DiffieHellmanServerChat", urlPatterns = {"/diffieHellmanServerChat"})
public class DiffieHellmanServerChat extends HttpServlet {

    private PrivateKey bobPrivateKey;
    private PublicKey bobPublicKey;
    private String claveCompartida;

    public DiffieHellmanServerChat() {
        try {
            // Generar un par de claves ECDH para Bob (el servidor)
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC");
            keyPairGen.initialize(256);  // Usar curva P-256
            
            KeyPair bobKeyPair = keyPairGen.generateKeyPair();
            bobPrivateKey = bobKeyPair.getPrivate(); // Clave privada de Bob
            bobPublicKey = bobKeyPair.getPublic();   // Clave pública de Bob
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Enviar la clave pública de Bob al cliente
            byte[] bobPubKeyBytes = bobPublicKey.getEncoded();
            String bobPubKeyBase64 = Base64.getEncoder().encodeToString(bobPubKeyBytes);
            out.println("{\"bobPubKeyLength\":\"" + bobPubKeyBytes.length + "\", \"bobPubKeyBytes\":\"" + bobPubKeyBase64 + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                // Recibir la clave pública de Alice
                int alicePubKeyLength = Integer.parseInt(request.getParameter("alicePubKeyLength"));
                String alicePubKeyBase64 = request.getParameter("alicePubKeyBytes");
                byte[] alicePubKeyBytes = Base64.getDecoder().decode(alicePubKeyBase64);

                // Reconstruir la clave pública de Alice
                KeyFactory keyFactory = KeyFactory.getInstance("EC");
                X509EncodedKeySpec aliceKeySpec = new X509EncodedKeySpec(alicePubKeyBytes);
                PublicKey alicePublicKey = keyFactory.generatePublic(aliceKeySpec);

                // Realizar el acuerdo de claves usando la clave privada de Bob y la clave pública de Alice
                KeyAgreement bobKeyAgree = KeyAgreement.getInstance("ECDH");
                bobKeyAgree.init(bobPrivateKey);
                bobKeyAgree.doPhase(alicePublicKey, true);
                byte[] bobSharedSecret = bobKeyAgree.generateSecret();

                // Convertir la clave compartida a formato hexadecimal
                claveCompartida = new BigInteger(1, bobSharedSecret).toString(16);
                
                //VALIDAR CLAVE SECRETA COMPARTIDA
                claveCompartida = ajustarLongitudClaveAES(claveCompartida);
                //claveCompartida = ajustarLongitudClaveDES(claveCompartida);
                
                System.out.println("Clave secreta compartida: " + claveCompartida);

                
                //guardando la clave secreta compartida
                HttpSession misessiSession = request.getSession(true);
                misessiSession.setAttribute("ClaveSecretaCompartidaDHChat", claveCompartida);
                
                // Enviar la clave compartida al cliente
                out.println("{\"resultado\":\"OK\"}");
                
            } catch (Exception e) {
                e.printStackTrace();
                out.println("{\"error\":\"Error en la generación de la clave compartida\"}");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para el intercambio de claves usando ECDH";
    }
    
     private String ajustarLongitudClaveAES(String claveCompartida) {
        // Convertir la cadena a un array de bytes
        byte[] claveBytes = claveCompartida.getBytes();

        // Si la clave es mayor a 16 bytes, truncar a 16 bytes
        if (claveBytes.length > 16) {
            return new String(claveBytes, 0, 16);
        }

        // Si la clave es menor a 16 bytes, rellenar con ceros
        StringBuilder claveAjustada = new StringBuilder(16);
        claveAjustada.append(claveCompartida);
        while (claveAjustada.length() < 16) {
            claveAjustada.append('\0'); // Rellenar con caracteres nulos
        }
        return claveAjustada.toString();
    }

    private String ajustarLongitudClaveDES(String claveCompartida) {
        // Convertir la clave a bytes
        byte[] claveBytes = claveCompartida.getBytes();

        // Si la longitud es mayor a 8 bytes, truncar a 8 bytes
        if (claveBytes.length > 8) {
            return new String(claveBytes, 0, 8);
        }

        // Si la longitud es menor a 8 bytes, rellenar con ceros
        byte[] claveAjustada = new byte[8];
        System.arraycopy(claveBytes, 0, claveAjustada, 0, claveBytes.length);

        return new String(claveAjustada);
    }
}