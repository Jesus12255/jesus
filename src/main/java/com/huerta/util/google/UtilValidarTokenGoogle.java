
package com.huerta.util.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GooglePublicKeysManager;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Collections;


public class UtilValidarTokenGoogle {
    
    private static final String CLIENT_ID = "914158457144-qd1sdimd2ntcmf9krft1rgqafdlsh2bg.apps.googleusercontent.com";

    
    private final GoogleIdTokenVerifier verifier;

    public UtilValidarTokenGoogle() {
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GooglePublicKeysManager manager = new GooglePublicKeysManager(new NetHttpTransport(), jsonFactory);
        
        // Configura el verificador de tokens
        this.verifier = new GoogleIdTokenVerifier.Builder(manager)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
    }

    public GoogleIdToken.Payload validateToken(String idTokenString) {
        try {
            // Verifica el ID token
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                //modificable
                mostrandoContenidoTocken(idToken.getPayload());
                return idToken.getPayload();
            } else {
                throw new Exception("Invalid ID token.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }
    
    private void mostrandoContenidoTocken (GoogleIdToken.Payload payload) {
        String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            
            
            System.out.println("Email: " + email);
            System.out.println("Name: " + name);
            System.out.println("pictureUrl: " + pictureUrl);
            System.out.println("emailVerified: " + emailVerified);
            System.out.println("locale: " + locale);
            System.out.println("familyName: " + familyName);
            System.out.println("givenName: " + givenName);
    }

 
    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImM4OGQ4MDlmNGRiOTQzZGY1M2RhN2FjY2ZkNDc3NjRkMDViYTM5MWYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI5MTQxNTg0NTcxNDQtcWQxc2RpbWQybnRjbWY5a3JmdDFyZ3FhZmRsc2gyYmcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5MTQxNTg0NTcxNDQtcWQxc2RpbWQybnRjbWY5a3JmdDFyZ3FhZmRsc2gyYmcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTI0MjUwMTMxNzM2OTQwMTQ0OTMiLCJoZCI6InVuamZzYy5lZHUucGUiLCJlbWFpbCI6IjAzMzMyMjEwMDVAdW5qZnNjLmVkdS5wZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYmYiOjE3Mjk5NTk2NzcsIm5hbWUiOiJIVUVSVEEgRkxPUkVOVElOTyBKRVNVUyBNQU5VRUwiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jS1BvbDYycGRXWXNPNjB4eDlMdTh0dU9sYlVRVkZfeHNKdXBmbUlyVzJRbk03NnBRPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IkhVRVJUQSBGTE9SRU5USU5PIiwiZmFtaWx5X25hbWUiOiJKRVNVUyBNQU5VRUwiLCJpYXQiOjE3Mjk5NTk5NzcsImV4cCI6MTcyOTk2MzU3NywianRpIjoiOGFlZjdhOWRiZjUxZGE3Yjg2OWJiYzAwOTIwZDdlNjllZWZiMWJiMSJ9.MzwIcav9wdiuU1XXI8iNdWnUnQrc7GLv_tKzpwueeETruxDqPgwVJtmYnP4H7obzVt6iYJpN9UsIphXhChygfDXgswW63xyctin2GBfzYoPrII6N8HTJ0K--iYUEeDCZ1GUoJArYbHBSu-_0cXUclX25hX2QEiNPS74UhZb-L4evjGpCURxwKNyqKcLJ2FpwyA6VClz4-DM_rtApaDb04RcafPZLrMXMldr3TpXMzIGUJbmeRoKy--cbIgmtcH-VjQFxbEbKZ2e3ZVW9Xaob8w0S9Cn4POVCF6lgT4eaeMMak1h-ok0vurIebqdgZGn0BPGPWVRhQXiEUro0gWbgGw";
        String a = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImM4OGQ4MDlmNGRiOTQzZGY1M2RhN2FjY2ZkNDc3NjRkMDViYTM5MWYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI5MTQxNTg0NTcxNDQtcWQxc2RpbWQybnRjbWY5a3JmdDFyZ3FhZmRsc2gyYmcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5MTQxNTg0NTcxNDQtcWQxc2RpbWQybnRjbWY5a3JmdDFyZ3FhZmRsc2gyYmcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTI3NjM4NTIwMTQwNTg3MzQ5ODYiLCJlbWFpbCI6ImpoOTQ5NzQyQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYmYiOjE3Mjk5ODMwMDIsIm5hbWUiOiJqZXN1cyBodWVydGEiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jSTJEd1F1V0FQMHYyRmc2V2cybFo5NzBhQ0FzUktyNjlJa0pySkFfVW9ObVJNcjJ1VUU9czk2LWMiLCJnaXZlbl9uYW1lIjoiamVzdXMiLCJmYW1pbHlfbmFtZSI6Imh1ZXJ0YSIsImlhdCI6MTcyOTk4MzMwMiwiZXhwIjoxNzI5OTg2OTAyLCJqdGkiOiI4OTQ3MmJhZWIxNzIzNTE3NTMwOWY2N2RjZmZiMjk3NzRhZGNiOGFmIn0.b0Y3LzI99Ybo6gSumC7TdCD13sFdUMnFrQLZsEm2DZOJDctwBXgVvj2c98AL-Db7ZsYZhzYdc-FgDYSuysIGQcqHOAt-chkokvtgGS4DMJU5OCrj3osvzKp0ckwn7fNBckw2v2k3zTFrZuBprV_AONtcgDsNRy3DtZZdoCQosglbavzxe90xa8fUHspd1JNlU6AGpqtsDdN6eBdjkcqmRn6R42hP-Ml4AwXAztX1UFYEveGbD9ZTKrYFR0cTSJh3VgL4S9Vh7eScDMfvne1XmDfoUXhSm1ce67ENBJRX3FNJ8yUpKaemeKaFiWkGlOWNVxSFrGXksXsekRPNqpVU7A";
        UtilValidarTokenGoogle valida = new UtilValidarTokenGoogle(); 
        valida.validateToken(token); 
    }
    
}
