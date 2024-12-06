package com.uber_reviews.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Database {

    private String connectionString, databaseName, collectionName;

    public Database(String dbName, String collectionName) {
        // Write your own connection string here!
        this.connectionString = "mongodb+srv://username:password@uberReviewData.ebpvq.mongodb.net/?retryWrites=true&w=majority&appName=uberReviewData";
        this.databaseName = dbName;
        this.collectionName = collectionName;
    }

    public Database(String connectionString, String dbName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = dbName;
        this.collectionName = collectionName;

    }

    public void addToDatabase(Document document) {

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

}