package com.uber_reviews.reviews;

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

    public Document getDocument() {
        Document document = new Document();
        document.append("Text", reviewText);
        document.append("Score", reviewScore);
        document.append("ID", reviewID);
        document.append("Length", reviewLength);
        document.append("Time", reviewTime);
        return document;
    }
}
