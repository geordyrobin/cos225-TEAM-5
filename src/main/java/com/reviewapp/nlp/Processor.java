package com.reviewapp.nlp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


// Contains various functions to process text for use by other functions

public class Processor {


    private Set<String> stopWords;

    public Processor(String stopWordsFilePath) { //adds stop words to hashset stopWords from file using loadstopwords function.
        stopWords = new HashSet<>();
        loadStopWords(stopWordsFilePath);
    }

    private void loadStopWords(String stopWordsFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(stopWordsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //text standardation functions

    public String lowerAllCases(String text) {
        return text.toLowerCase();
    }   

    public String removePunctuation(String text) {
        return text.replaceAll("[^a-zA-Z0-9]", " ");
    }

    public String[] tokenizeWords(String text) {
        return text.split("\\s+");
    }


    /**
     * Removes words that are present in listOfStopWords from the arrayOfText
     * @param arrayOfText - Array of words after they do not have any punctuation
     * @return arrayOfText - Array of words that do not contain any stop words
     */

    public String[] removeStopWords(String[] arrayOfText) {
        Set<String> words = new HashSet<>();
        for (String word : arrayOfText) {
            if (!stopWords.contains(word)) {
                words.add(word);
            }
        }
        return words.toArray(new String[0]);
    }

    //calls the text standardization / formatting functions above in sequence to put text in readable format for TFIDF use
    public String[] processText(String text) {
        text = lowerAllCases(text);
        text = removePunctuation(text);
        String[] words = tokenizeWords(text);
        words = removeStopWords(words);
        return words;
    }

    //for processing one sentence at a time
    public String[] splitTextIntoSentences(String text) {
        return text.split("\\.");
    }

}

