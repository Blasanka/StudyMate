package com.mad.studymate.cardview.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    private List<String> correctAnswers;
    private List<String> allAnswers;
//    private List<Boolean> allBoleanAnswers;

    public Question() {
        question = "";
        correctAnswers = new ArrayList<>();
        allAnswers = new ArrayList<>();
//        allBoleanAnswers = new ArrayList<>();
    }

    public Question(String question) {
        this.question = question;
        correctAnswers = new ArrayList<>();
        allAnswers = new ArrayList<>();
//        allBoleanAnswers = new ArrayList<>();
    }

    public Question(String question, List<String> correctAnswers, List<String> allAnswers) {
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.allAnswers = allAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setCorrectAnswer(String answer) {
        this.correctAnswers.add(answer);
    }

    public List<String> getAllCorrectAnswers() {
        return correctAnswers;
    }

    public List<String> getAllAnswers() {
        return allAnswers;
    }

    public void setAllAnswers(List<String> allAnswers) {
        this.allAnswers = allAnswers;
    }


    public void setAnswer(String answer) {
        this.allAnswers.add(answer);
    }

//
//    public List<Boolean> getAllBoleanAnswers() {
//        return allBoleanAnswers;
//    }
//
//    public void setAllBooleanAnswers(List<Boolean> allAnswers) {
//        this.allBoleanAnswers = allAnswers;
//    }

//    public void setAnswer(boolean answer) {
//        this.allBoleanAnswers.add(answer);
//    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", correctAnswers=" + correctAnswers +
                ", allAnswers=" + allAnswers +
//                ", allBoleanAnswers=" + allBoleanAnswers +
                '}';
    }
}
