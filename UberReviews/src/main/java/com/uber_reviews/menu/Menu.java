package com.uber_reviews.menu;

import com.uber_reviews.database.Database;
import com.uber_reviews.reviews.Reviews;


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
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the first header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] reviewData = line.split(delimiter);
                String originalTitle = reviewData[1];
                String overview = reviewData[0];
                Float rating = Float.parseFloat(reviewData[reviewData.length - 2]);

                Review reviewObject = new review(originalTitle, overview, rating);
                reviewDatabase.addToDatabase(reviewObject.getDocument());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
