package edu.rit.se.design.ArCodePlugin.recommendation;

public class Recommendation {
    double score;
    StringBuilder dotGraph;
    StringBuilder codeSnippet;

    public Recommendation(double score, StringBuilder dotGraph, StringBuilder codeSnippet) {
        this.score = score;
        this.dotGraph = dotGraph;
        this.codeSnippet = codeSnippet;
    }

    public double getScore() {
        return score;
    }

    public StringBuilder getDotGraph() {
        return dotGraph;
    }

    public StringBuilder getCodeSnippet() {
        return codeSnippet;
    }

    public String toString(){
        return String.valueOf(score);
    }
}
