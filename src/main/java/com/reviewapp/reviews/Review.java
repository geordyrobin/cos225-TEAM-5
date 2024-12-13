package com.reviewapp.reviews;

import org.bson.Document;

public class Review{
    
    private String reviewText;
    private String reviewScore;
    private String reviewID;
    private String reviewLength;
    private String reviewTime;

    public Review(String reviewText, String reviewScore, String reviewID, String reviewLength, String reviewTime) {
        this.reviewText = reviewText;
        this.reviewScore = reviewScore;
        this.reviewID = reviewID;
        this.reviewLength = reviewLength;
        this.reviewTime = reviewTime;    
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getReviewScore() {
        return reviewScore;
    }

    public String getReviewID() {
        return reviewID;
    }

    public String getReviewLength() {
        return reviewLength;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public Review(Document document){
        this.reviewText = document.getString("Text");
        this.reviewScore = document.getString("Score");
        this.reviewID = document.getString("_id");
        this.reviewLength = document.getString("Length");
        this.reviewTime = document.getString("Time"); 
    }


    public Document getDocument() {
        Document document = new Document("_id",reviewID);
        document.append("Text", reviewText);
        document.append("Score", reviewScore);
        document.append("Length", reviewLength);
        document.append("Time", reviewTime);
        return document;
    }
}
