package com.uber_reviews.menu;

import com.uber_reviews.database.Database;
import com.uber_reviews.reviews.Review;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menu {
    


        public void startUp() {

        // Create a collection in the database to store Review objects
        Database reviewDatabase = new Database("uberReviews", "reviews");
        reviewDatabase.createCollection();

        // Parse UberReviewsData.csv
        String csvFile = "src/main/resources/UberReviewsData.csv";
        String line;
        String delimiter = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)\")";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the first header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] reviewData = line.split(delimiter, -1);
                String reviewText = reviewData[0];
                String reviewScore = reviewData[1];
                String reviewID = reviewData[2];
                String reviewLength = reviewData[3];
                String reviewTime = reviewData[4];

                Review reviewObject = new review(reviewText, reviewScore, reviewID, reviewLength, reviewTime);
                reviewDatabase.addToDatabase(reviewObject.getDocument());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
