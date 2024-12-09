package com.reviewapp.menu;
import java.util.Scanner;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.reviewapp.database.Database;
import com.reviewapp.reviews.Review;

import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.bson.BsonValue;

public class Menu extends Delete{

    public void startUp(Database reviewDatabase) {

        reviewDatabase.createCollection();

        // Parse UberReviewsTestData.csv
        String csvFile = "src/main/resources/UberReviewsTestData.csv";
        String line;
        String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

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

                Review reviewObject = new Review(reviewText, reviewScore, reviewID, reviewLength, reviewTime);
                reviewDatabase.addToDatabase(reviewObject.getDocument());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
public static void main(String[] args) {

    System.out.println("Initializing the Uber Review app...");
    System.out.println("Hello! Welcome to the Uber Review app!");
    Scanner scanner = new Scanner(System.in);
    // Create a collection in the database to store Review objects
    Database reviewDatabase = new Database("uberReviews", "reviews");
    Menu menu = new Menu();
    menu.startUp(reviewDatabase);
    int choice = 0;
    int deletion = 0;
    while (choice != 6) {

        System.out.println("Please select one of the following options ");
        System.out.println("1. Add a Uber review to the database.");
        System.out.println("2. Remove A Uber review from database.");
        System.out.println("3. Find specific rating reviews.");
        System.out.println("4. Classify Uber review.");
        System.out.println("5. Edit Uber review."); 
        System.out.println("6. Exit.");

        System.out.print("Enter your choice: ");
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
            continue;
        }
        switch (choice) {
            case 1:
                System.out.println("Adding a review to the database");
                System.out.print("Enter review text: ");
                String reviewText = scanner.nextLine();
                System.out.print("Would you like to enter a review score of 1-5 (y/n))");
                String reviewScore = "";
                if ("y" == scanner.next()){
                    reviewScore = scanner.nextLine();
                }
                String reviewLength = String.valueOf(reviewText.length());
                System.out.println("The length of the review is " + reviewLength);
                String reviewTime = "12/10/2024";
                String reviewID = Long.toString(reviewDatabase.getCount()+1);
                System.out.println("The ID of the review is " + reviewID);
                Review reviewObject = new Review(reviewText, reviewScore, reviewID, reviewLength, reviewTime);
                reviewDatabase.addToDatabase(reviewObject.getDocument());
                System.out.println("Review added to the database");
                break;

            case 2:
                    Remove();
                    System.out.println("Removing a review from the database");
                
                    break;

            case 3: 
                System.out.println("Ok, let's find you some reviews!");
                System.out.print("Enter the ID of the review you want to see: ");
                String revID = scanner.nextLine();
                Document chosenReview = reviewDatabase.getDocumentByID(revID);
                System.out.println(chosenReview);

                break;
            case 4:
                System.out.println("Let's try to classify a review");
                
                break;
            case 5:
                System.out.println("Let's try to edit this review");

                break;
            case 6:
                System.out.println("Exiting the movie app...");
                reviewDatabase.deleteCollection();
                break;

            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    scanner.close();
}
}