package com.reviewapp.reviews;

import org.bson.Document;


public class Review{
    
    private String reviewText;
    private String reviewScore;
    private String reviewID;
    private String reviewLength;
    private String reviewTime;
    private String sentiment;

    public Review(String reviewText, String reviewScore, String reviewID, String reviewLength, String reviewTime, String sentiment) {
        this.reviewText = reviewText;
        this.reviewScore = reviewScore;
        this.reviewID = reviewID;
        this.reviewLength = reviewLength;
        this.reviewTime = reviewTime;    
        this.sentiment = sentiment;
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

    public String getSentiment() {
        return sentiment;
    }

    public Review(Document document){
        this.reviewText = document.getString("Text");
        this.reviewScore = document.getString("Score");
        this.reviewID = document.getString("_id");
        this.reviewLength = document.getString("Length");
        this.reviewTime = document.getString("Time"); 
        this.sentiment = document.getString("Sentiment");
    }


    public Document getDocument() {
        Document document = new Document("_id",reviewID);
        document.append("Text", reviewText);
        document.append("Score", reviewScore);
        document.append("Length", reviewLength);
        document.append("Time", reviewTime);
        document.append("Sentiment", sentiment);
        return document;
    }
}
