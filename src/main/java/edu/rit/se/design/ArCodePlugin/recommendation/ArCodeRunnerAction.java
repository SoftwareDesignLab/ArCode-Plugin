package edu.rit.se.design.ArCodePlugin.recommendation;

import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import edu.rit.se.design.ArCodePlugin.settings.ArCodePersistentStateComponent;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizV8Engine;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Ali Shokri (as8308@rit.edu)
 */

public class ArCodeRunnerAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Graphviz.useEngine(new GraphvizV8Engine());

//        new ArCodeRunnerDialogWrapper(e.getProject(), true).showAndGet();
        ArCodePersistentStateComponent arCodePersistentStateComponent = ArCodePersistentStateComponent.getInstance();
        String framework = arCodePersistentStateComponent.getState().getFramework();
        String frameworkJarPath = arCodePersistentStateComponent.getState().getFrameworkJarPath();
        String frameworkPackage = arCodePersistentStateComponent.getState().getFrameworkPackage();
        String trainProjectsPath = arCodePersistentStateComponent.getState().getTrainingProjectsPath();
        String minerType = "FROM_JAR";
        String exclusionFilePath = arCodePersistentStateComponent.getState().getExclusionFilePath();
        String testProjectsPath = arCodePersistentStateComponent.getState().getProjectJarPath();

        JFrame arCodeRecomFrame = null;
        try {
            arCodeRecomFrame = new ArCodeRecomFrameBuilder().createFrame(framework, frameworkJarPath, frameworkPackage, trainProjectsPath, minerType, exclusionFilePath, testProjectsPath, trainProjectsPath);
        } catch (ClassHierarchyException classHierarchyException) {
            classHierarchyException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        arCodeRecomFrame.pack();
        arCodeRecomFrame.setLocationRelativeTo(null);
        arCodeRecomFrame.setVisible(true);


    }

/*    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Graphviz.useEngine(new GraphvizV8Engine());
        new ArCodeRecomDialogWrapper(e.getProject(), true).showAndGet();
    }*/



/*    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ArCodePersistentStateComponent arCodePersistentStateComponent = ArCodePersistentStateComponent.getInstance();

//        Messages.showMessageDialog(e.getProject(),"FSpec Path: " + arCodePersistentStateComponent.getState().getfSpecPath(), "ArCode Settings", Messages.getInformationIcon() );

        PluginState settings = arCodePersistentStateComponent.getState();



       try {

           StringBuilder dot = new StringBuilder(
                   "digraph \"DirectedGraph\" {\n" +
                           "graph [label=\"JaasBasedProject.jar\"];center=true;fontsize=10;node [ color=black,shape=\"box\"fontsize=10,fontcolor=black,fontname=Arial];edge [ color=black,fontsize=10,fontcolor=black,fontname=Arial]; \n" +
                           "\t\"CallbackHandler.<init>()V\"[fillcolor =\"#CCFFCC\", shape=\"box\", style=\"rounded,filled\", color=\"black\" ];\n" +
                           "\t\"LoginContext.getSubject()Subject;\"[fillcolor =\"#CCFFFF\", shape=\"box\", style=\"rounded,filled\", color=\"black\" ];\n" +
                           "\t\"LoginContext.<init>(String;CallbackHandler;)V\"[fillcolor =\"#CCFFCC\", shape=\"box\", style=\"rounded,filled\", color=\"black\" ];\n" +
                           "\t\"END_NODE\"[fillcolor =\"yellow\", shape=\"oval\", style=\"rounded,filled\", color=\"black\" ];\n" +
                           "\t\"LoginContext.login()V\"[fillcolor =\"#CCFFFF\", shape=\"box\", style=\"rounded,filled\", color=\"black\" ];\n" +
                           "\t\"LoginContext.logout()V\"[fillcolor =\"#CCFFFF\", shape=\"box\", style=\"rounded,filled\", color=\"black\" ];\n" +
                           "\t\"START_NODE\"[fillcolor =\"yellow\", shape=\"oval\", style=\"rounded,filled\", color=\"black\" ];\n" +
                           "\t\"CallbackHandler.<init>()V\" -> \"LoginContext.<init>(String;CallbackHandler;)V\"[color=\"black\",style=dotted, label=\"\" ];\n" +
                           "\t\"LoginContext.getSubject()Subject;\" -> \"END_NODE\"[color=\"green\",style=dotted, label=\"\" ];\n" +
                           "\t\"LoginContext.<init>(String;CallbackHandler;)V\" -> \"LoginContext.getSubject()Subject;\"[color=\"black\",style=dotted, label=\"\" ];\n" +
                           "\t\"LoginContext.<init>(String;CallbackHandler;)V\" -> \"LoginContext.login()V\"[color=\"black\",style=dotted, label=\"\" ];\n" +
                           "\t\"LoginContext.<init>(String;CallbackHandler;)V\" -> \"LoginContext.logout()V\"[color=\"black\",style=dotted, label=\"\" ];\n" +
                           "\t\"LoginContext.login()V\" -> \"LoginContext.getSubject()Subject;\"[color=\"green\",style=dotted, label=\"\" ];\n" +
                           "\t\"LoginContext.login()V\" -> \"LoginContext.logout()V\"[color=\"green\",style=dotted, label=\"\" ];\n" +
                           "\t\"LoginContext.logout()V\" -> \"END_NODE\"[color=\"green\",style=dotted, label=\"\" ];\n" +
                           "\t\"START_NODE\" -> \"CallbackHandler.<init>()V\"[color=\"green\",style=dotted, label=\"\" ];\n" +
                           "\n" +
                           "}");

           JFrame frame = new JFrame("Dot Graph");
           frame.add( new DotImagePanel( dot ) );

*//*
           GraphSplitEditorProvider graphSplitEditorProvider = new GraphSplitEditorProvider();
           VirtualFile virtualFile = new MockVirtualFile( "MockDotFile", dotgraph );
           graphSplitEditorProvider.createEditor( e.getProject(), virtualFile );


             MutableGraph g = new Parser().read( dotgraph );
            BufferedImage bi = Graphviz.fromGraph( g ).render(Format.PNG ).toImage();
            frame.add(new JLabel(new ImageIcon(bi)));
            frame.add(new JLabel("new ImageIcon(bi)"));*//*

           String dotGraphFilePath = "/Users/as8308/Desktop/ArCode/DataRepository/JAAS/Train/GRAAMsDotGraph/JaasBasedProject.jar.dot";
*//*
           DotViewer dotViewer = new DotViewer();
           dotViewer.setDotFilePath( dotGraphFilePath );

           frame.add( dotViewer );
*//*

           frame.setVisible(true);
           frame.pack();
//           frame.setLocationRelativeTo(null);



*//*            SpecMiner trainProjsSpecMiner = new SpecMiner(settings.getFramework(), settings.frameworkJarPath, settings.frameworkPackage, settings.getTrainingProjectsPath(), "FROM_JAR", settings.getExclusionFilePath());
            CommonConstants.LOGGER.log(Level.INFO, "Analyzing training projects");

            trainProjsSpecMiner.mineFrameworkSpecificationFromScratch(true);
            trainProjsSpecMiner.saveFSpecToFile(settings.getfSpecPath().substring(0, settings.getfSpecPath().lastIndexOf("/")));

            CommonConstants.LOGGER.log(Level.INFO, "Analyzing " + settings.getProjectJarPath());

            SpecMiner testProjsSpecMiner = new SpecMiner(settings.getFramework(), settings.frameworkJarPath, settings.frameworkPackage, settings.getProjectJarPath().substring(0, settings.getProjectJarPath().lastIndexOf("/")), "FROM_JAR", settings.getExclusionFilePath());
            testProjsSpecMiner.mineFrameworkSpecificationFromScratch(false);

            MutableGraph g = new Parser().read( "graph {\n" +
                    "    { rank=same; white}\n" +
                    "    { rank=same; cyan; yellow; pink}\n" +
                    "    { rank=same; red; green; blue}\n" +
                    "    { rank=same; black}\n" +
                    "\n" +
                    "    white -- cyan -- blue\n" +
                    "    white -- yellow -- green\n" +
                    "    white -- pink -- red\n" +
                    "\n" +
                    "    cyan -- green -- black\n" +
                    "    yellow -- red -- black\n" +
                    "    pink -- blue -- black\n" +
                    "}" );

            Graph g = GraphvizLoader.loadAsString( "" );

            List<GRAAM> testGRAAMs = GRAAMBuilder.loadGRAAMsFromSerializedFolder( testProjsSpecMiner.getSerializedGRAAMsFolder() );

            List<GraphEditDistanceInfo> recommendations = FSpec2Recom.fspec2Recom(trainProjsSpecMiner.getMinedFSpec(), testGRAAMs.get(0));*//*

        } catch (Exception exception) {

        }
//        filePickup(e);
// /Users/as8308/Desktop/ArCode/arcode-1.0-SNAPSHOT-jar-with-dependencies.jar
*//*        if( new SettingsDialogWrapper(e.getProject(), true).showAndGet() ) {

        }*//*

     *//*
        if(  ) {
            // Information provided
        }
*//*


    }

    private void filePickup(@NotNull AnActionEvent e) {
        FileChooserDescriptor f = new FileChooserDescriptor(false, true, false, false, false, false);
        f.setTitle("Please choose FSpec file");
        f.setDescription("FSpec file is a previously mined Framework API Specification model.");
        FileChooser.chooseFile(f, e.getProject(), null, virtualFile -> {
            Messages.showMessageDialog(e.getProject(), virtualFile.getPath(), "ArCode", Messages.getInformationIcon());
        });
    }*/

}
