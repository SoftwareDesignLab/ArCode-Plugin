package edu.rit.se.design.ArCodePlugin.recommendation;

import com.ibm.wala.ipa.cha.ClassHierarchyException;
import edu.rit.se.design.arcode.fspec2code.ClassHierarchyUtil;
import edu.rit.se.design.arcode.fspec2recom.CodeGeneratorUtil;
import edu.rit.se.design.arcode.fspec2recom.FSpec2Recom;
import edu.rit.se.design.arcode.fspec2recom.GraphEditDistanceInfo;
import edu.rit.se.design.arcode.fspec2recom.SubFSpecVisualizer;
import edu.rit.se.design.arcode.fspecminer.SpecMiner;
import edu.rit.se.design.arcode.fspecminer.graam.GRAAM;
import edu.rit.se.design.arcode.fspecminer.graam.GRAAMBuilder;
import edu.rit.se.design.arcode.fspecminer.graam.GRAAMVisualizer;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArCodeRecomFrameBuilder {
    static int ST_WIDTH = Math.min( 1750, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 20 );
    static int ST_HEIGHT = Math.min( 900, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 20 );;
    static int ST_RECOMMENDATION_CUTOFF = 10;

    static StringBuilder projectGRAAMDot;
    static StringBuilder projectCodeSummery;

    static List<GraphEditDistanceInfo> recommendations;


    public static JFrame createFrame(String arcodeJarPath, String framework, String frameworkJarPath, String frameworkPackage,
                                     String trainProjectsPath, String minerType, String exclusionFilePath, String testProjectsPath, String fspecPath) throws ClassHierarchyException, IOException {
        JFrame frame = new JFrame("ArCode");
        ArCodeRecomPanel mainPanel = createArCodeRecomPanel(arcodeJarPath, framework, frameworkJarPath, frameworkPackage,
                trainProjectsPath, minerType, exclusionFilePath, testProjectsPath, fspecPath);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(ST_WIDTH, ST_HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        return frame;
    }

    static StringBuilder readContentFromFile(String filePath) throws IOException {
        return new StringBuilder(FileUtils.readFileToString(new File(filePath), "UTF-8"));
    }


    static ArCodeRecomPanel createArCodeRecomPanel(String arcodeJarPath, String framework, String frameworkJarPath, String frameworkPackage,
                                                   String trainProjectsPath, String minerType, String exclusionFilePath, String testProjectsPath, String fspecPath) throws ClassHierarchyException, IOException {
        List<Recommendation> recommendationList = new ArrayList<>();

        try {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            String jarPath = arcodeJarPath;//"/Users/as8308/Desktop/Ali/RIT/My Drive/Research/Projects/ArCode/Implementation/arcode/target/arcode-1.1.1-SNAPSHOT-jar-with-dependencies.jar";

            runProcess(new String[]{"java", "-jar", jarPath, "-framework", framework, "-frameworkJarPath", frameworkJarPath, "-frameworkPackage", frameworkPackage,
                    "-trainProjectsPath", trainProjectsPath, "-exclusionFilePath", exclusionFilePath, "-testProjectsPath", testProjectsPath,
                    "-fspecOutputPath", fspecPath, "-mineTrainFromScratch", "False", "-mineTestFromScratch", "True", "-recommendationCutOff", Integer.toString(ST_RECOMMENDATION_CUTOFF)});

            int walkLevel = 1;

            String recommendationsFolderPath = testProjectsPath + File.separator + "Recommendations";
            Stream.of(new File(recommendationsFolderPath).listFiles()).collect(Collectors.toList()).forEach(aCaseFolder -> {
                Stream.of(aCaseFolder.listFiles()).filter(aCaseInstanceFolder -> aCaseInstanceFolder.getName().endsWith("GRAAM")).
                        collect(Collectors.toList()).forEach(graamRelatedFolder -> {
                    Stream.of(graamRelatedFolder.listFiles()).collect(Collectors.toList()).forEach(graamRelatedFile -> {
                        if (graamRelatedFile.getAbsolutePath().endsWith("dot")) {
                            try {
                                projectGRAAMDot = readContentFromFile(graamRelatedFile.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (graamRelatedFile.getAbsolutePath().endsWith("code")) {
                            try {
                                projectCodeSummery = readContentFromFile(graamRelatedFile.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                });

                Stream.of(aCaseFolder.listFiles()).filter(aCaseInstanceFolder -> !aCaseInstanceFolder.getName().endsWith("GRAAM")).
                        collect(Collectors.toList()).forEach(recomRelatedFolder -> {
                    StringBuilder recomDot = new StringBuilder();
                    StringBuilder recomCode = new StringBuilder();
                    AtomicReference<Double> recomDistance = new AtomicReference<>(100D);

                    Stream.of(recomRelatedFolder.listFiles()).collect(Collectors.toList()).forEach(recomRelatedFile -> {

                        if (recomRelatedFile.getAbsolutePath().endsWith("dot")) {
                            try {
                                recomDot.append(readContentFromFile(recomRelatedFile.getAbsolutePath()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (recomRelatedFile.getAbsolutePath().endsWith("code")) {
                            try {
                                recomCode.append(readContentFromFile(recomRelatedFile.getAbsolutePath()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (recomRelatedFile.getAbsolutePath().endsWith("score")) {
                            try {
                                recomDistance.set(Double.parseDouble(readContentFromFile(recomRelatedFile.getAbsolutePath()).toString()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                    Recommendation recommendation = new Recommendation(Double.parseDouble(decimalFormat.format(recomDistance.get())), recomDot, recomCode);
                    recommendationList.add(recommendation);

                });
            });

            recommendationList.sort((rec1, rec2) -> rec1.getScore() > rec2.getScore() ? 1 : (rec1.getScore() == rec2.getScore() ? 0 : -1));
//            Path recommendationsFolder = Paths.get(recommendationsFolderPath);
//            Files.walk(recommendationsFolder, walkLevel).forEach(aCase -> {
//                try {
///*                    Path graamFilesPath = Files.walk(aCase, walkLevel).filter( path -> path.endsWith( "GRAAM" ) ).collect( Collectors.toList() ).get(0);
//                    Files.walk(graamFilesPath, walkLevel).forEach(graamRelatedFile -> {
//                        if( graamRelatedFile.endsWith("dot") ) {
//                            try {
//                                projectGRAAMDot = readContentFromFile( graamRelatedFile.toAbsolutePath().toString() );
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if( graamRelatedFile.endsWith("code") ) {
//                            try {
//                                projectCodeSummery = readContentFromFile( graamRelatedFile.toAbsolutePath().toString() );
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                        }
//                    );*/
//
//                    Path recomsFilesPath = Files.walk(aCase, walkLevel).filter( path -> path.startsWith( "RECOM" ) ).collect( Collectors.toList() ).get(0);
//                    Files.walk(recomsFilesPath, walkLevel).forEach(recomFilesPath -> {
//                        StringBuilder recomDot = new StringBuilder("");
//                        StringBuilder recomCode = new StringBuilder("");
//                        Double recomDistance = 100D;
//                        if( recomFilesPath.endsWith("dot") ) {
//                            try {
//                                recomDot = readContentFromFile( recomFilesPath.toAbsolutePath().toString() );
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if( recomFilesPath.endsWith("code") ) {
//                            try {
//                                recomCode = readContentFromFile( recomFilesPath.toAbsolutePath().toString() );
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if( recomFilesPath.endsWith("distance") ) {
//                            try {
//                                recomDistance = Double.parseDouble( readContentFromFile( recomFilesPath.toAbsolutePath().toString() ).toString() );
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        Recommendation recommendation = new Recommendation( Double.parseDouble( decimalFormat.format( recomDistance  ) ), recomDot, recomCode );
//                        recommendationList.add( recommendation );
//
//
//                            }
//                    );
//
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });

/*            SpecMiner trainProjsSpecMiner = new SpecMiner(framework, frameworkJarPath, frameworkPackage, trainProjectsPath, minerType, exclusionFilePath);
            trainProjsSpecMiner.mineFrameworkSpecificationFromSerializedGRAAMs();

            SpecMiner testProjsSpecMiner = new SpecMiner(framework, frameworkJarPath, frameworkPackage, testProjectsPath, minerType, exclusionFilePath);
            testProjsSpecMiner.mineFrameworkSpecificationFromScratch(false);
            List<GRAAM> loadedTestGRAAMs = GRAAMBuilder.loadGRAAMsFromSerializedFolder( testProjsSpecMiner.getSerializedGRAAMsFolder() );
            GRAAM projectGRAAM = loadedTestGRAAMs.get(0);
            projectGRAAMDot = new GRAAMVisualizer( projectGRAAM ).dotOutput();

            ClassHierarchyUtil classHierarchyUtil = new ClassHierarchyUtil(frameworkJarPath, exclusionFilePath);
            projectCodeSummery = new StringBuilder(CodeGeneratorUtil.GRAAM2Code(projectGRAAM, "Automatically generated code", "currentImplementation", classHierarchyUtil) );

            recommendations = FSpec2Recom.fspec2Recom( trainProjsSpecMiner.getMinedFSpec(), projectGRAAM, ST_RECOMMENDATION_CUTOFF );


            int maxDistance = recommendations.stream().max( (o1, o2) -> o1.getDistance() - o2.getDistance() ).get().getDistance();

            if( maxDistance == 0 )
                maxDistance++;



            int finalMaxDistance = maxDistance;
            recommendations.stream().forEach(graphEditDistanceInfo -> {
                StringBuilder recomDot = new SubFSpecVisualizer( graphEditDistanceInfo.getDistSubFSpec() ).dotOutput();
                StringBuilder recomCode = null;
                try {
                    recomCode = new StringBuilder(CodeGeneratorUtil.SubFSpec2Code(graphEditDistanceInfo.getDistSubFSpec(), "Automatically generated code", "recommendedCode", classHierarchyUtil) );
                    Double score =  (finalMaxDistance + 1 - new Double( graphEditDistanceInfo.getDistance() ) ) / (finalMaxDistance + 1) ;

                    System.out.println( graphEditDistanceInfo.getDistance() );

                    Recommendation recommendation = new Recommendation( Double.parseDouble( decimalFormat.format( score ) ), recomDot, recomCode );
                    recommendationList.add( recommendation );
//                scoreRecommendationMap.put( Double.parseDouble( decimalFormat.format( score ) ), new AbstractMap.SimpleEntry<>( recomDot, recomCode ) );

                }
                catch (Exception e){


                } } );*/

        } catch (Exception e) {
            e.printStackTrace();
        }


        ArCodeRecomPanel arCodeRecomPanel = new ArCodeRecomPanel(projectCodeSummery, projectGRAAMDot, recommendationList, ST_WIDTH - 20, ST_HEIGHT - 20);


        arCodeRecomPanel.setEnabled(true);
        return arCodeRecomPanel;

    }

    static ArCodeRecomPanel createArCodeRecomPanel2(String framework, String frameworkJarPath, String frameworkPackage,
                                                    String trainProjectsPath, String minerType, String exclusionFilePath, String testProjectsPath, String fspecPath) throws ClassHierarchyException, IOException {
        List<Recommendation> recommendationList = new ArrayList<>();

        try {


            Process process = Runtime.getRuntime().exec("java -version");

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            SpecMiner trainProjsSpecMiner = new SpecMiner(framework, frameworkJarPath, frameworkPackage, trainProjectsPath, minerType, exclusionFilePath);
            trainProjsSpecMiner.mineFrameworkSpecificationFromSerializedGRAAMs();

            SpecMiner testProjsSpecMiner = new SpecMiner(framework, frameworkJarPath, frameworkPackage, testProjectsPath, minerType, exclusionFilePath);
            testProjsSpecMiner.mineFrameworkSpecificationFromScratch(false);
            List<GRAAM> loadedTestGRAAMs = GRAAMBuilder.loadGRAAMsFromSerializedFolder(testProjsSpecMiner.getSerializedGRAAMsFolder());
            GRAAM projectGRAAM = loadedTestGRAAMs.get(0);
            projectGRAAMDot = new GRAAMVisualizer(projectGRAAM).dotOutput();

            ClassHierarchyUtil classHierarchyUtil = new ClassHierarchyUtil(frameworkJarPath, exclusionFilePath);
            projectCodeSummery = new StringBuilder(CodeGeneratorUtil.GRAAM2Code(projectGRAAM, "Automatically generated code", "currentImplementation", classHierarchyUtil));

            recommendations = FSpec2Recom.fspec2Recom(trainProjsSpecMiner.getMinedFSpec(), projectGRAAM, ST_RECOMMENDATION_CUTOFF);

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            int maxDistance = recommendations.stream().max((o1, o2) -> o1.getDistance() - o2.getDistance()).get().getDistance();

            if (maxDistance == 0)
                maxDistance++;


            int finalMaxDistance = maxDistance;
            recommendations.stream().forEach(graphEditDistanceInfo -> {
                StringBuilder recomDot = new SubFSpecVisualizer(graphEditDistanceInfo.getDistSubFSpec()).dotOutput();
                StringBuilder recomCode = null;
                try {
                    recomCode = new StringBuilder(CodeGeneratorUtil.SubFSpec2Code(graphEditDistanceInfo.getDistSubFSpec(), "Automatically generated code", "recommendedCode", classHierarchyUtil));
                    Double score = (finalMaxDistance + 1 - new Double(graphEditDistanceInfo.getDistance())) / (finalMaxDistance + 1);

                    System.out.println(graphEditDistanceInfo.getDistance());

                    Recommendation recommendation = new Recommendation(Double.parseDouble(decimalFormat.format(score)), recomDot, recomCode);
                    recommendationList.add(recommendation);
//                scoreRecommendationMap.put( Double.parseDouble( decimalFormat.format( score ) ), new AbstractMap.SimpleEntry<>( recomDot, recomCode ) );

                } catch (Exception e) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        ArCodeRecomPanel arCodeRecomPanel = new ArCodeRecomPanel(projectCodeSummery, projectGRAAMDot, recommendationList, ST_WIDTH - 20, ST_HEIGHT - 20);


        arCodeRecomPanel.setEnabled(true);
        return arCodeRecomPanel;

    }

/*    static ArCodeRecomPanel createArCodeRecomPanel(String framework, String frameworkJarPath, String frameworkPackage,
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
            recommendations = FSpec2Recom.fspec2Recom( trainProjsSpecMiner.getMinedFSpec(), projectGRAAM, ST_RECOMMENDATION_CUTOFF );
        }
        catch ( Exception e ){
            e.printStackTrace();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        int maxDistance = recommendations.stream().max( (o1, o2) -> o1.getDistance() - o2.getDistance() ).get().getDistance();

        if( maxDistance == 0 )
            maxDistance++;

        ClassHierarchyUtil classHierarchyUtil = new ClassHierarchyUtil(frameworkJarPath, exclusionFilePath);

        List<Recommendation> recommendationList = new ArrayList<>();

        int finalMaxDistance = maxDistance;
        recommendations.stream().forEach(graphEditDistanceInfo -> {
            StringBuilder recomDot = new SubFSpecVisualizer( graphEditDistanceInfo.getDistSubFSpec() ).dotOutput();
            StringBuilder recomCode = null;
            try {
                recomCode = new StringBuilder(CodeGeneratorUtil.SubFSpec2Code(graphEditDistanceInfo.getDistSubFSpec(), "Automatically generated code", "recommendedCode", classHierarchyUtil) );
                Double score =  (finalMaxDistance + 1 - new Double( graphEditDistanceInfo.getDistance() ) ) / (finalMaxDistance + 1) ;
                System.out.println( graphEditDistanceInfo.getDistance() );
                Recommendation recommendation = new Recommendation( Double.parseDouble( decimalFormat.format( score ) ), recomDot, recomCode );
                recommendationList.add( recommendation );
            }
            catch (Exception e){

            }
        } );

        ArCodeRecomPanel arCodeRecomPanel = new ArCodeRecomPanel(projectCodeSummery, projectGRAAMDot, recommendationList, ST_WIDTH - 20, ST_HEIGHT - 20 );
        arCodeRecomPanel.setEnabled(true);
        return arCodeRecomPanel ;
    }*/

    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }

    private static void runProcess(String[] command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }

}
