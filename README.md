This is the home of ArCode Plugin, a tool that facilitates architectural concerns comprehension and implementation. 

# What is ArCode Plugin?
ArCode Plugin implements [ArCode](https://arxiv.org/abs/2102.08372) approach in an interactive IntelliJ IDEA plugin.

# Obtaining ArCode Plugin
In this section, we provide a step-by-step instruction to walk you through the process of building and using ArCode Plugin.

## Use a released version
Whenever we reach a stable version, we build the plugin and release it as a zip file. You can easily download the latest release from [here](../../releases/). 

## Building ArCode Plugin from source code
In order to build the plugin from source code, you need to have gradle installed on your machine. The link to Gradle can be found from [here](https://gradle.org/). Also, you need to download or build the jar file of [ArCode](https://github.com/SoftwareDesignLab/ArCode). Then, in the build.gradle file of ArCode Plugin, you need to set the path to this jar file throw the following line:
```
implementation files('/Users/ali/Academic/RIT/Research/Projects/ArCode/Implementation/arcode/target/arcode-1.1.1-SNAPSHOT-jar-with-dependencies.jar')
```

Lastly, please use below command to build the ArCode Plugin:
```
gradle buildPlugin
```

If the process finishes with success, ArCode Plugin zip file is created in build\distributions directory.

To install the plugin on your IDE open IntelliJ IDEA, go to Plugins, and click on "Install Plugin from Disk...". Then select the built ArCode Plugin zip file.
You may need to restart your IDE to be able to see ArCode menue under Tools menu.

# Using ArCode Plugin
Once the plugin is installed on your IDE, you may find the ArCode Plugin menu from tools->ArCode menu. There are two sub-menus available. The first sub-menu, Settings, let you config ArCode Plugin.
Second menu, ArCode Runner, will actually run ArCode. There are five input types to ArCode Plugin:

1. **ArCode jar file:** Path to the jar file of [ArCode](https://github.com/SoftwareDesignLab/ArCode). ArCode Plugin uses ArCode jar file as an underlying framework to fulfil its job. You may either download the [latest releas](https://github.com/SoftwareDesignLab/ArCode/releases) of ArCode or clone and build it. To build ArCode you need to have maven installed on your machine. Then, by running the following command you can create the needed jar file:
```
mvn clean package
```

2. **Framework information:** ArCode plugin needs to have the path to jar file of the framework of interest. For instance, you may find JAAS jar file from [here](https://github.com/SoftwareDesignLab/ArCode/blob/main/Frameworks/JAAS.jar). Also, ArCode Plugin needs to know the **packaging of the framework of interest**. In case of JAAS, this packaging would be: javax/security/auth
3. **Training projects' information:** Path to the folder that contains training projects' jar file. Also, ArCode Plugin needs to know the path to a folder that it can dump the created **framework API specification model (FSpec)** from training projects. It could be any folder.
4. **Program under development information:** Path to the folder that the jar file of the program under development is placed in. Normally this is a sub-folder of your build folder.
5. **Exclusion file:** This is a file entitled as "JAASJavaExclusions.txt" in the config folder of ArCode source code. In the current version of ArCode, you need to have that file on your system and introduce its path to ArCode. This file specifies packages to be excluded from program analysis process. We need to introduce these packages to avoid explosion of call-graph creation in that process.

ArCode leverages static analysis techniques to extract some facts about API dependencies and usage constraint from the framework. It then analyzes programs in the training repository to identify programs that are not violating API usage constraint. Mining these programs, it builds an API usage model, called Framework API Specification (FSpec) model. Finally, it analyzes program under development to create its API usage model. Leveraging the built model alongside with created FSpec, ArCode Plugin finds deviations from correct implementation. It provides recommendations on how to fix the found issues in an interactive communication with programmer. 

# Tutorial
A link to ArCode Plugin's tutorial will be added shortly.

# Contact
Please do not hasitate to reach out to us should you have any question about ArCode Plugin. You may get in contact with us throught as8308 at rit dot edu.
