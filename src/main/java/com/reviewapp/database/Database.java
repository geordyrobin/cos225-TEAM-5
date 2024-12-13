package com.reviewapp.database;

import static com.mongodb.client.model.Filters.eq;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Flow.Publisher;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.reviewapp.reviews.Review;

public class Database {

    private String connectionString, databaseName, collectionName;

    public Database(String dbName, String collectionName) {
        // connection string
        this.connectionString = "mongodb+srv://zocarroll:aUM6rPmPoN9m9PyF@uberReviewData.ebpvq.mongodb.net/?retryWrites=true&w=majority&appName=uberReviewData";
        this.databaseName = dbName;
        this.collectionName = collectionName;
    } // Database constructor with connection string for the database

    public Database(String connectionString, String dbName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = dbName;
        this.collectionName = collectionName;

    }

    public void addOneToDatabase(Document document) {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase reviewDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> reviewCollection = reviewDatabase.getCollection(this.collectionName);

            reviewCollection.insertOne(document);

        }

    }

    public void createCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase reviewDatabase = mongoClient.getDatabase(this.databaseName);
            reviewDatabase.createCollection(this.collectionName);

        }

    }

    public void deleteCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase reviewDatabase = mongoClient.getDatabase(this.databaseName);
            reviewDatabase.getCollection(this.collectionName).drop();

        }

    }

    public void deleteAllDocuments() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase reviewDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> reviewCollection = reviewDatabase.getCollection(this.collectionName);

            reviewCollection.deleteMany(new Document());

        }

    }

    public Review getDocumentByID(String id) {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase reviewDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> reviewCollection = reviewDatabase.getCollection(this.collectionName);

            Review foundReview = new Review(reviewCollection.find(new Document("_id", id)).first());
            return foundReview;
        }
    }

    public void updateDocumentByID(String id) {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase reviewDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> reviewCollection = reviewDatabase.getCollection(this.collectionName);
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            int updates = 0;
            String text = "", score = "";
            while (choice != 3) {

                System.out.println("Which field would you like to update?");
                System.out.println("1. Review text.");
                System.out.println("2. Review score.");
                System.out.println("3. Exit.");

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
                        System.out.print("Enter the new review text : ");
                        text = scanner.nextLine();
                        if (updates % 2 == 0) updates += 1;
                        break;
                
                    case 2:
                        System.out.print("Enter the new review score (1-5): ");
                        score = scanner.nextLine();
                        if (updates / 2 == 0) updates += 2;
                        break;

                    case 3:
                        System.out.println("Finishing updates.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
            switch (updates) {
                case 0:
                    System.out.println("No updates made.");
                    break;

                case 1:
                reviewCollection.updateOne(eq("_id", id), combine(set("Text", text), set("Length",String.valueOf(text.length())), currentDate("Time")));
                System.out.println("Updated review text, length, and date.");
                    break;
                
                case 2:
                reviewCollection.updateOne(eq("_id", id), combine(set("Score", score), currentDate("Time")));
                System.out.println("Updated review score and date.");
                    break;

                case 3:
                reviewCollection.updateOne(eq("_id", id), combine(set("Text", text), set("Length",String.valueOf(text.length())), set("Score", score), currentDate("Time")));
                System.out.println("Updated review text, length, score, and date.");
                    break;
                
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
            //scanner.close();            
        }
    }

    public Long getCount(){
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase reviewDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> reviewCollection = reviewDatabase.getCollection(this.collectionName);

            return reviewCollection.countDocuments();
        }
    }
}