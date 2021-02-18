package edu.rit.se.design.ArCodePlugin.recommendation;

import com.ibm.wala.ipa.cha.ClassHierarchyException;
import edu.rit.se.design.ArCodePlugin.recommendation.ArCodeRecomPanel;
import edu.rit.se.design.arcode.fspec2code.ClassHierarchyUtil;
import edu.rit.se.design.arcode.fspec2recom.CodeGeneratorUtil;
import edu.rit.se.design.arcode.fspec2recom.FSpec2Recom;
import edu.rit.se.design.arcode.fspec2recom.GraphEditDistanceInfo;
import edu.rit.se.design.arcode.fspec2recom.SubFSpecVisualizer;
import edu.rit.se.design.arcode.fspecminer.SpecMiner;
import edu.rit.se.design.arcode.fspecminer.graam.GRAAM;
import edu.rit.se.design.arcode.fspecminer.graam.GRAAMBuilder;
import edu.rit.se.design.arcode.fspecminer.graam.GRAAMVisualizer;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizV8Engine;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArCodeRecomFrameBuilder {
    static int ST_WIDTH = 1750;
    static int ST_HEIGHT = 900;

    static StringBuilder projectGRAAMDot;
    static StringBuilder projectCodeSummery;

    static List<GraphEditDistanceInfo> recommendations;


    public static JFrame createFrame(String framework, String frameworkJarPath, String frameworkPackage,
                                     String trainProjectsPath, String minerType, String exclusionFilePath, String testProjectsPath, String fspecPath) throws ClassHierarchyException, IOException {
        JFrame frame = new JFrame("ArCode");
        Graphviz.useEngine(new GraphvizV8Engine());
        ArCodeRecomPanel mainPanel = createArCodeRecomPanel( framework,  frameworkJarPath,  frameworkPackage,
                 trainProjectsPath,  minerType,  exclusionFilePath,  testProjectsPath,  fspecPath);
        frame.getContentPane().add( mainPanel, BorderLayout.CENTER );
        frame.setPreferredSize(new Dimension(ST_WIDTH, ST_HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo( null);
        frame.setVisible(true);


        return frame;
    }

    static ArCodeRecomPanel createArCodeRecomPanel(String framework, String frameworkJarPath, String frameworkPackage,
     String trainProjectsPath, String minerType, String exclusionFilePath, String testProjectsPath, String fspecPath) throws ClassHierarchyException, IOException {
        try {
            SpecMiner trainProjsSpecMiner = new SpecMiner(framework, frameworkJarPath, frameworkPackage, trainProjectsPath, minerType, exclusionFilePath);
            trainProjsSpecMiner.mineFrameworkSpecificationFromSerializedGRAAMs();

            SpecMiner testProjsSpecMiner = new SpecMiner(framework, frameworkJarPath, frameworkPackage, testProjectsPath, minerType, exclusionFilePath);
            testProjsSpecMiner.mineFrameworkSpecificationFromSerializedGRAAMs();
            List<GRAAM> loadedTestGRAAMs = GRAAMBuilder.loadGRAAMsFromSerializedFolder( testProjsSpecMiner.getSerializedGRAAMsFolder() );
            GRAAM projectGRAAM = loadedTestGRAAMs.get(0);
            projectGRAAMDot = new GRAAMVisualizer( projectGRAAM ).dotOutput();
            projectCodeSummery = new StringBuilder( "public void yourCodeSummery (String str) {\n" +
                    "    CallbackHandler callbackHandler = new MyCallbackHandler();\n" +
                    "    try {\n" +
                    "        LoginContext loginContext = new LoginContext( str, callbackHandler);\n" +
                    "        loginContext.getSubject();\n" +
                    "        loginContext.logout();\n" +
                    "    } catch (LoginException e) {\n" +
                    "        e.printStackTrace();\n" +
                    "    }\n" +
                    "}" );
            recommendations = FSpec2Recom.fspec2Recom( trainProjsSpecMiner.getMinedFSpec(), projectGRAAM );
        }
        catch ( Exception e ){
            e.printStackTrace();
        }

        Map<Double, Map.Entry<StringBuilder, StringBuilder>> scoreRecommendationMap = new HashMap<>();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        int maxDistance = recommendations.stream().max( (o1, o2) -> o1.getDistance() - o2.getDistance() ).get().getDistance();

        if( maxDistance == 0 )
            maxDistance++;

//        ClassHierarchyUtil classHierarchyUtil = new ClassHierarchyUtil(frameworkJarPath, exclusionFilePath);


        int finalMaxDistance = maxDistance;
        recommendations.stream().sorted( (o1, o2) -> o2.getDistance() - o1.getDistance() ).forEach(graphEditDistanceInfo -> {
            StringBuilder recomDot = new SubFSpecVisualizer( graphEditDistanceInfo.getDistSubFSpec() ).dotOutput();
            StringBuilder recomCode = null;
            try {
                recomCode = new StringBuilder(""); /*new StringBuilder(CodeGeneratorUtil.SubFSpec2Code(graphEditDistanceInfo.getDistSubFSpec(), "Automatically generated code", "recommendedCode", classHierarchyUtil) )*/;
                Double score =  (finalMaxDistance + 1 - new Double( graphEditDistanceInfo.getDistance() ) ) / (finalMaxDistance + 1) ;

                System.out.println( graphEditDistanceInfo.getDistance() );


                scoreRecommendationMap.put( Double.parseDouble( decimalFormat.format( score ) ), new AbstractMap.SimpleEntry<>( recomDot, recomCode ) );

            }
            catch (Exception e){

            }
        } );

        ArCodeRecomPanel arCodeRecomPanel = new ArCodeRecomPanel(projectCodeSummery, projectGRAAMDot, scoreRecommendationMap, ST_WIDTH - 20, ST_HEIGHT - 20 );


        arCodeRecomPanel.setEnabled(true);
        return arCodeRecomPanel ;

    }

}
