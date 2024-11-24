package com.huerta.util.facebook;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class UtilValidarTokenFacebook {

    private static final String FB_APP_ID = "1552402655664137"; // Tu App ID
    private static final String FB_APP_SECRET = "a7b4df6c60fc8f6314b038f06dff1600"; // Tu App Secret

    public static JSONObject validar(String accessToken) {

        String fbValidationUrl = String.format(
                "https://graph.facebook.com/debug_token?input_token=%s&access_token=%s|%s",
                accessToken, FB_APP_ID, FB_APP_SECRET
        );

        try {
            URL url = new URL(fbValidationUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Leer la respuesta
            Scanner sc = new Scanner(url.openStream());
            StringBuilder jsonResponse = new StringBuilder();
            while (sc.hasNext()) {
                jsonResponse.append(sc.nextLine());
            }
            sc.close();

            // Convertir respuesta a JSON
            JSONObject fbResponse = new JSONObject(jsonResponse.toString());
            JSONObject data = fbResponse.getJSONObject("data");
            System.out.println("Response: " + data.toString());

            // Validar si el token es válido
            if (data.getBoolean("is_valid")) {
                // Obtener el user_id
                String userId = data.getString("user_id");

                // Llamar a la API de Graph para obtener información del usuario
                String userInfoUrl = String.format("https://graph.facebook.com/%s?fields=id,name&access_token=%s", userId, accessToken);
                URL userInfoRequestUrl = new URL(userInfoUrl);
                HttpURLConnection userInfoConn = (HttpURLConnection) userInfoRequestUrl.openConnection();
                userInfoConn.setRequestMethod("GET");

                // Leer la respuesta del usuario
                Scanner userInfoScanner = new Scanner(userInfoRequestUrl.openStream());
                StringBuilder userInfoResponse = new StringBuilder();
                while (userInfoScanner.hasNext()) {
                    userInfoResponse.append(userInfoScanner.nextLine());
                }
                userInfoScanner.close();

                // Convertir respuesta de usuario a JSON
                JSONObject userInfoJson = new JSONObject(userInfoResponse.toString());
                String userName = userInfoJson.getString("name"); // Obtener el nombre del usuario

                // Obtener la fecha de expiración
                long expirationTime = data.getLong("expires_at");

                // Crear respuesta JSON para el cliente
                JSONObject jsonResp = new JSONObject();
                jsonResp.put("resultado", "OK");
                jsonResp.put("usuario", userName); // Agregar el nombre del usuario
                jsonResp.put("expiracion", expirationTime); // Agregar la fecha de expiración
                jsonResp.put("token", accessToken); // Incluir el token en la respuesta

                return jsonResp;
            } else {
                // Token no válido
                JSONObject jsonResp = new JSONObject();
                jsonResp.put("resultado", "ERROR");
                return jsonResp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
