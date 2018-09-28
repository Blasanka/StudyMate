package com.mad.studymate.cardview.model;

public class Quiz {
    private String title;
    private String quizTag;
    private String quizType;
    private int questionCount;
    private int timesTaken;
    private double scoresOfQuiz;

    public Quiz(String title, String quizTag, String quizType, int questionCount, int timesTaken, double scoresOfQuiz) {
        this.title = title;
        this.quizTag = quizTag;
        this.quizType = quizType;
        this.questionCount = questionCount;
        this.timesTaken = timesTaken;
        this.scoresOfQuiz = scoresOfQuiz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuizTag() {
        return quizTag;
    }

    public void setQuizTag(String quizTag) {
        this.quizTag = quizTag;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getTimesTaken() {
        return timesTaken;
    }

    public void setTimesTaken(int timesTaken) {
        this.timesTaken = timesTaken;
    }

    public double getScoresOfQuiz() {
        return scoresOfQuiz;
    }

    public void setScoresOfQuiz(double scoresOfQuiz) {
        this.scoresOfQuiz = scoresOfQuiz;
    }
}
