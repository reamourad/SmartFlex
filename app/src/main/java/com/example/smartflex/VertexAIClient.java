package com.example.smartflex;

import static com.example.smartflex.Database.amountNeeds;
import static com.example.smartflex.Database.amountSavings;
import static com.example.smartflex.Database.amountWants;
import static com.example.smartflex.Database.budgetFrequency;
import static com.example.smartflex.Database.income;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;

import java.io.IOException;
import java.io.InputStream;


public class VertexAIClient {
    public GenerateContentResponse aiAnswer(Context context, String question){
        // Open the asset as an InputStream
        try {
            InputStream inputStream = context.getAssets().open("smart-flex-3f2a29f1ed9d.json");
            // Use the InputStream to create your GoogleCredentials object
            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
            credentials = credentials.createScoped("https://www.googleapis.com/auth/cloud-platform");
            try (VertexAI vertexAI = new VertexAI("smart-flex", "us-central1", credentials)) {
                GenerativeModel model = new GenerativeModel("gemini-1.0-pro", vertexAI);
                GenerateContentResponse response = model.generateContent(getPrompt() + question);
                return response;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            // Handle the IOException if the file is not found
            e.printStackTrace();
        }
        return null;
    }

    public String getPrompt() {
        if (income == 0) {
            return ""; // No prompt if income is zero
        }

        // Check if any financial goals have been set
        boolean hasGoals = amountNeeds > 0 || amountWants > 0 || amountSavings > 0;

        // Build the prompt
        StringBuilder promptBuilder = new StringBuilder();

        promptBuilder.append("You are a financial advisor and your client has an income of $")
                .append(income)
                .append(" ")
                .append(budgetFrequency.toString())
                .append(". ");

        if (hasGoals) {
            promptBuilder.append("The client has set their budget for needs (total: $")
                    .append(amountNeeds)
                    .append("), wants (total: $")
                    .append(amountWants)
                    .append("), and savings (total: $")
                    .append(amountSavings)
                    .append("). ");
        }
        promptBuilder.append("Use this information if it is relevant to the following question: ");


        return promptBuilder.toString();
    }
}
