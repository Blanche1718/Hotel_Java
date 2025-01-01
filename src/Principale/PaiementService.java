/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principale;

/**
 *
 * @author DELL
 */
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class PaiementService {
    private static final String API_URL = "https://sandbox-api.fedapay.com";

    public String initierPaiement(String idReservation, double montant, String methode, String statut) {
        try {
            // Créer la connexion à l'API
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer sk_sandbox_WlupxmH5A23162p7E02gd1SO"); 
            conn.setDoOutput(true);

            // Corps de la requête JSON
            String jsonInputString = String.format(
                "{" +
                "\"id_reservation\": \"%s\"," +
                "\"montant\": %s," +
                "\"methode\": \"%s\"," +
                "\"statut\": \"%s\"" +
                "}",
                idReservation, montant, methode, statut
            );

            // Écrire dans le flux de sortie
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Lire la réponse
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString(); // Réponse JSON
                }
            } else {
                return "Erreur : " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'initialisation du paiement : " + e.getMessage();
        }
    }
}
