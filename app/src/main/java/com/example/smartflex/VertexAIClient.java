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
                GenerateContentResponse response = null;
                if(question == "Hello"){
                    response = model.generateContent(question);
                }
                else{
                    response = model.generateContent(getPrompt() + question);
                }

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

        // Build the prompt
        StringBuilder promptBuilder = new StringBuilder();

        promptBuilder.append("You are a financial advisor. Here's the financial overview for the person you're talking to:\n\n");

// Income and budget frequency
        promptBuilder.append("Income: $")
                .append(income)
                .append(" ")
                .append(Database.incomeFrequency.toString())
                .append("\n");
        promptBuilder.append("Budget Frequency: ")
                .append(budgetFrequency.toString())
                .append("\n\n");

// Budget breakdown
        promptBuilder.append("Budget Breakdown:\n");
        promptBuilder.append("- Needs: $")
                .append(amountNeeds)
                .append(" (")
                .append(Database.percentageNeeds)
                .append("% spent, $")
                .append(Database.remainingNeeds)
                .append(" remaining)\n");
        promptBuilder.append("- Wants: $")
                .append(amountWants)
                .append(" (")
                .append(Database.percentageWants)
                .append("% spent, $")
                .append(Database.remainingWants)
                .append(" remaining)\n");
        promptBuilder.append("- Savings: $")
                .append(amountSavings)
                .append(" (")
                .append(Database.percentageSavings)
                .append("% saved, $")
                .append(Database.remainingSavings)
                .append(" remaining)\n\n");

        promptBuilder.append("The person you're talking to asks: ");


        return promptBuilder.toString();
    }
}
