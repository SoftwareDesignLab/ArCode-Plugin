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
}
