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

public class Delete{
    public static void main(String[] args) {
            System.out.println("Hello, Which review would you like to delete\"");
            Scanner choice = new Scanner(System.in);  // Create a Scanner object
            String target = choice.nextLine();
            try (MongoClient mongoClient = MongoClients.create(System.getProperty("mongodb.uri"))) {
            MongoDatabase uberReviewDB = mongoClient.getDatabase("uberReviewData");
            MongoCollection<Document> gradesCollection = uberReviewDB.getCollection("reviews");
            // delete one document
            Bson filter = eq("id", target);
            DeleteResult result = gradesCollection.deleteOne(filter);
            System.out.println(result);
            //take the input, either looking for id or the entire line

            //using the update function found on mongodb, either use remove to nuke the entire line or delete by parts
  
  }

}
}