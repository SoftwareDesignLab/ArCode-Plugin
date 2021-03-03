/*
 * Copyright (c) 2021 - Present. Rochester Institute of Technology
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
