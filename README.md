This is the home of ArCode Plugin, a tool that facilitates architectural concerns comprehension and implementation. 

# What is ArCode Plugin?
ArCode Plugin implements [ArCode](https://arxiv.org/abs/2102.08372) approach in an interactive IntelliJ IDEA plugin.

# Obtaining ArCode Plugin
In this section, we provide a step-by-step instruction to walk you through the process of building and using ArCode Plugin.

## Use a released version
Whenever we reach a stable version, we build the plugin and release it as a zip file. You can easily download the latest release from [here](../../releases/). 

## Building ArCode Plugin from source code
In order to build the plugin from source code, you need to have gradle installed on your machine. The link to Gradle can be found from [here](https://gradle.org/).
Please use below command to build the ArCode Plugin:
```
gradle buildPlugin
```

If the process finishes with success, ArCode Plugin zip file is created in build\distributions directory.

To install the plugin on your IDE open IntelliJ IDEA, go to Plugins, and click on "Install Plugin from Disk...". Then select the built ArCode Plugin zip file.
You may need to restart your IDE to be able to see ArCode menue under Tools menu.

# Using ArCode Plugin
Once the plugin is installed on your IDE, you may find the ArCode Plugin menu from tools->ArCode menu. There are two sub-menus available. The first sub-menu, Settings, let you config ArCode Plugin.
Second menu, ArCode Runner, will actually run ArCode. There are three main inputs to ArCode Plugin:

1. Framework information
2. Training projects' information
3. Program under development information

ArCode leverages static analysis techniques to extract some facts about API dependencies and usage constraint from the framework. It then analyzes programs in the training repository to identify programs that are not violating API usage constraint. Mining these programs, it builds an API usage model, called Framework API Specification (FSpec) model. Finally, it analyzes program under development to create its API usage model. Leveraging the built model alongside with created FSpec, ArCode Plugin finds deviations from correct implementation. It provides recommendations on how to fix the found issues in an interactive communication with programmer.

# Tutorial
A link to ArCode Plugin's tutorial will be added soon.

# Contact
Please do not hasitate to reach out to us should you have any question about ArCode. You may get in contact with us throught as8308 at rit dot edu.
