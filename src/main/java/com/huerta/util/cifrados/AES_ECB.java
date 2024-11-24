package com.huerta.util.cifrados;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

// Cifrado AES ECB
public class AES_ECB {

    private AES_ECB() {        
    }

    public static String cifrar(String encryptText, String key) {

        if (encryptText == null || key == null) {
            throw new IllegalArgumentException("No se puede cifrar valores nulos");
        }

        try {
            // Clave de 16 bytes (128 bits para AES)
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

            // Crear instancia del cifrador AES en modo ECB con PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            // Inicializar el cifrador en modo cifrado (ENCRYPT_MODE)
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            // Cifrar el texto
            byte[] bytesCifrados = cipher.doFinal(encryptText.getBytes(Charset.forName("UTF-8")));

            // Retornar el texto cifrado en formato Base64
            return Base64.getEncoder().encodeToString(bytesCifrados);

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException("El cifrado falló", e);
        }
    }

    public static String descifrar(String decryptText, String key) {

        if (decryptText == null || key == null) {
            throw new IllegalArgumentException("No se puede descifrar valores nulos");
        }

        try {
            // Clave de 16 bytes (128 bits para AES)
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

            // Crear instancia del cifrador AES en modo ECB con PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            // Inicializar el cifrador en modo descifrado (DECRYPT_MODE)
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            // Decodificar el texto cifrado (Base64) y descifrarlo
            byte[] bytesDescifrados = cipher.doFinal(Base64.getDecoder().decode(decryptText));

            // Retornar el texto descifrado
            return new String(bytesDescifrados, Charset.forName("UTF-8"));

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException("El descifrado falló", e);
        }
    }

    public static void main(String[] args) {
        String texto = "123";
        String clave = "FujimoriLibertad"; // Clave de 16 bytes para AES

        // Cifrar el texto
        String textoCifrado = AES_ECB.cifrar(texto, clave);
        System.out.println("Texto cifrado: " + textoCifrado);

        // Descifrar el texto
        String textoDescifrado = AES_ECB.descifrar(textoCifrado, clave);
        System.out.println("Texto descifrado: " + textoDescifrado);
    }
}
