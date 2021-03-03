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

package edu.rit.se.design.ArCodePlugin.settings;

import java.io.Serializable;

/**
 * @author Ali Shokri (as8308@rit.edu)
 */

public class PluginState implements Serializable {
    String fSpecPath = "";
    String trainingProjectsPath="";
    String frameworkJarPath = "";
    String frameworkPackage = "";
    String framework = "";
    String arCodeJarPath = "";
    String projectJarPath = "";
    String exclusionFilePath = "";
    Boolean trainFromScratch = Boolean.TRUE;
    Boolean testFromScratch = Boolean.TRUE;


    public String getfSpecPath() {
        return fSpecPath;
    }

    public void setfSpecPath(String fSpecPath) {
        this.fSpecPath = fSpecPath;
    }

    public String getTrainingProjectsPath() {
        return trainingProjectsPath;
    }

    public void setTrainingProjectsPath(String trainingProjectsPath) {
        this.trainingProjectsPath = trainingProjectsPath;
    }

    public String getFrameworkJarPath() {
        return frameworkJarPath;
    }

    public void setFrameworkJarPath(String frameworkJarPath) {
        this.frameworkJarPath = frameworkJarPath;
    }

    public String getFrameworkPackage() {
        return frameworkPackage;
    }

    public void setFrameworkPackage(String frameworkPackage) {
        this.frameworkPackage = frameworkPackage;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getArCodeJarPath() {
        return arCodeJarPath;
    }

    public void setArCodeJarPath(String arCodeJarPath) {
        this.arCodeJarPath = arCodeJarPath;
    }

    public String getProjectJarPath() {
        return projectJarPath;
    }

    public void setProjectJarPath(String projectJarPath) {
        this.projectJarPath = projectJarPath;
    }

    public String getExclusionFilePath() {
        return exclusionFilePath;
    }

    public void setExclusionFilePath(String exclusionFilePath) {
        this.exclusionFilePath = exclusionFilePath;
    }

    public Boolean getTrainFromScratch() {
        return trainFromScratch;
    }

    public void setTrainFromScratch(Boolean trainFromScratch) {
        this.trainFromScratch = trainFromScratch;
    }

    public Boolean getTestFromScratch() {
        return testFromScratch;
    }

    public void setTestFromScratch(Boolean testFromScratch) {
        this.testFromScratch = testFromScratch;
    }
}
