package com.reviewapp.menu;



import com.reviewapp.database.Database;
import com.reviewapp.nlp.TFIDF;
import com.reviewapp.reviews.Review;
import com.reviewapp.nlp.ReviewClassifier;
import com.reviewapp.nlp.Processor;

import java.util.Scanner;
import com.mongodb.client.result.InsertOneResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.bson.BsonValue;


public class Menu extends Delete{


    private static Processor processor = new Processor("src/main/resources/listOfStopWords.txt");
    private static TFIDF tfidf = new TFIDF(processor);
    private  static ReviewClassifier classifier = new ReviewClassifier(processor);


    public void startUp() {
        Database reviewDatabase = new Database("uberReviews", "reviews");
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

                //sentiment set for testing
                String sentiment = "None";

                //sentiment set for training
                if(Integer.parseInt(reviewScore) >= 3){
                    sentiment = "positive";
                } else { sentiment = "negative";}
                Review reviewObject = new Review(reviewText, reviewScore, reviewID, reviewLength, reviewTime, sentiment);
               // List<? extends Document> reviewDocument.addTo(reviewObject.getDocument());
                InsertOneResult result = reviewDatabase.addOneToDatabase(reviewObject.getDocument());
                BsonValue id = result.getInsertedId();
                tfidf.addSample(id, reviewObject.getReviewText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Database trainingDatabase = new Database("uberTraining", "reviews");
        reviewDatabase.createCollection();
        // Parse the UberReviewsTrainingData.csv
        String reviewCSVFile = "src/main/resources/UberReviewsTrainingData.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(reviewCSVFile))) {
            // Skip the first header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] reviewData = line.split(delimiter, -1);
                String reviewText = reviewData[0];
                String reviewScore = reviewData[1];
                String reviewID = reviewData[2];
                String reviewLength = reviewData[3];
                String reviewTime = reviewData[4];
                String sentiment = "none";
                if(Integer.parseInt(reviewScore) >= 3){
                    sentiment = "positive";
                } else { sentiment = "negative";}
                Review reviewObject = new Review(reviewText, reviewScore, reviewID, reviewLength, reviewTime, sentiment);
                trainingDatabase.addOneToDatabase(reviewObject.getDocument());
                classifier.addSample(reviewObject);
            }
            classifier.train();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void shutDown(){
        Database reviewDatabase = new Database("uberReviews", "reviews");
        Database trainingDatabase = new Database("uberTraining", "reviews");
        reviewDatabase.deleteCollection();
        trainingDatabase.deleteCollection();
    }
    public static String classifyReview(String review){
        String sentiment = classifier.classify(review);
        return sentiment;
    }

public static void main(String[] args) {

    System.out.println("Initializing the Uber Review app...");
    System.out.println("Hello! Welcome to the Uber Review app!");
    Scanner scanner = new Scanner(System.in);
    // Create a collection in the database to store Review objects
    Menu menu = new Menu();
    menu.startUp();
    Database reviewDatabase = new Database("uberReviews", "reviews");
    reviewDatabase.createCollection();
    int choice = 0;
    String revID = "";
    String choiceTwo = "";
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
                System.out.print("Would you like to enter a review score of 1-5 (1 for yes, 2 for no) ");
                String reviewScore = "";
                if (scanner.hasNextLine()) {
                    choiceTwo = scanner.nextLine();
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                }
                if ("1" == choiceTwo){
                    reviewScore = scanner.nextLine();
                }
                String reviewLength = String.valueOf(reviewText.length());
                System.out.println("The length of the review is " + reviewLength);
                String reviewTime = Instant.now().atZone(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);
                String reviewID = Long.toString(reviewDatabase.getCount()+2001);
                System.out.println("The ID of the review is " + reviewID);
                String sentiment = classifyReview(reviewText);
                System.out.println("The sentiment of the review is " + sentiment);
                Review reviewObject = new Review(reviewText, reviewScore, reviewID, reviewLength, reviewTime, "blank for sentiment later");
                reviewDatabase.addOneToDatabase(reviewObject.getDocument());
                System.out.println("Review added to the database");
                break;

            case 2:
                    Remove();
                    System.out.println("Removing a review from the database");
                
                    break;

            case 3: 
                System.out.println("Ok, let's find you some reviews!");
                System.out.print("Enter the ID of the review you want to see: ");
                revID = scanner.nextLine();
                Review chosenReview = reviewDatabase.getDocumentByID(revID);
                System.out.println("The review you found was\n\tId: " + chosenReview.getReviewID() +
                    "\n\tReview Text: " + chosenReview.getReviewText() + "\n\tScore: " + chosenReview.getReviewScore() +
                    "\n\tLength of: " + chosenReview.getReviewLength() + "\n\tDate of Review: " +chosenReview.getReviewTime());

                break;
            case 4:
                    System.out.println("Let's try to classify a review");
                    System.out.println("Please enter the review of the movie");
                    String review = scanner.nextLine();
                    System.out.println("The sentiment of the review is: " + classifyReview(review));
                break;
            case 5:
                System.out.println("Let's try to edit this review");
                System.out.print("Enter the ID of the review you want to change: ");
                revID = scanner.nextLine();
                reviewDatabase.updateDocumentByID(revID);
                break;
            case 6:
                System.out.println("Exiting the movie app...");
                menu.shutDown();
                break;

            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    scanner.close();
}
}