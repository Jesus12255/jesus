package com.huerta.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.huerta.util.facebook.UtilValidarTokenFacebook;
import com.huerta.util.google.UtilValidarTokenGoogle;
import com.huerta.util.jwt.JwtUtil;
import org.json.JSONObject;

public class UtilValidarTiposToken {

    public static boolean valida(String token, String tipoToken, String usuario) {

        boolean valido = false;
        switch (tipoToken) {
            case "local":
                valido = validarTokenLocal(token, usuario);
                break;
            case "google":

                valido = validaTokenGoogle(token);
                break;
            case "facebook":

                valido = validarTokenFacebook(token);
                break;
            default:
                valido  = false;
                break;
        }
        return valido; 
    }
    
    //también verifica si el token ya expiró o no
    private static boolean validarTokenLocal(String token, String usuario) {
        return JwtUtil.validateToken(token, usuario);
    }

    //también verifica si el token ya expiró o no
    private static boolean validaTokenGoogle(String token) {
        UtilValidarTokenGoogle validarTokenGoogle = new UtilValidarTokenGoogle();
        GoogleIdToken.Payload payload = validarTokenGoogle.validateToken(token);
        return null != payload; 
    }

    //también verifica si el token ya expiró o no
    private static boolean validarTokenFacebook(String token) {
        JSONObject jsonResp =  UtilValidarTokenFacebook.validar(token); 
        return "OK".equals(jsonResp.getString("resultado"));
    }
}
