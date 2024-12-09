package com.reviewapp.menu;
import java.util.Scanner;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.bson.BsonValue;

public class Menu extends Delete{
public static void main(String[] args) {

    System.out.println("Initializing the Uber Review app...");
    System.out.println("Hello! Welcome to the Uber Review app!");
    Scanner scanner = new Scanner(System.in);

    int choice = 0;
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
                System.out.println("Adding a Review to the database");
                break;

            case 2:
                    Remove(); // calls the remove function in the delete class to remove a single ID from the database
                    System.out.println("Removing a review from the database");
                
                    break;

            case 3: 
                System.out.println("Ok, let's find you some reviews!");
                
                break;
            case 4:
                System.out.println("Let's try to classify a review");
                
                break;
            case 5:
                System.out.println("Let's try to edit this review");

                break;
            case 6:
                System.out.println("Exiting the movie app...");
                
                break;

            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
}
}