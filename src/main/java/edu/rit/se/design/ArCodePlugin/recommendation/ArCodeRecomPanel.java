package edu.rit.se.design.ArCodePlugin.recommendation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ArCodeRecomPanel extends JPanel implements ActionListener {
    int ST_WIDTH ;
    int ST_HEIGHT ;
    int ST_COMBO_WIDTH = 200;
    int ST_COMBO_HEIGHT = 60;

    StringBuilder programCodeSummery;
    //    List<StringBuilder> recommendedCodeSummeries;
    StringBuilder programGraamDot;
//    List<StringBuilder> recommendedGraamsDot;

    DotGraphSourceCodeViewerPanel sourceDotGraphSourceCodeViewerPanel;
    DotGraphSourceCodeViewerPanel recomDotGraphSourceCodeViewerPanel;

//    Map<Double, Map.Entry<StringBuilder, StringBuilder>> scoreRecommendationMap;

    List<Recommendation> recommendationList;

    public ArCodeRecomPanel(StringBuilder programCodeSummery, StringBuilder programGraamDot, List<Recommendation> recommendationList, int width, int height){
        super(/*new GridBagLayout()*/);

        ST_WIDTH = width;
        ST_HEIGHT = height;

        this.programCodeSummery =  programCodeSummery;
//        setRecommendedCodeSummeries( recommendedCodeSummeries );
        this.programGraamDot = programGraamDot ;
//        setRecommendedGraamsDot( recommendedGraamsDot );

        this.recommendationList = recommendationList;
        init();
        setBackground( Color.WHITE );
    }

    void init(){


        sourceDotGraphSourceCodeViewerPanel = new DotGraphSourceCodeViewerPanel( "Current Implementation", programCodeSummery, programGraamDot, (ST_WIDTH - 10 - ST_COMBO_WIDTH) / 2, ST_HEIGHT - 20 );

        JPanel scoreDeviationPanel = new JPanel();
        scoreDeviationPanel.setPreferredSize( new Dimension( ST_COMBO_WIDTH, ST_COMBO_HEIGHT + 50 ) );
        scoreDeviationPanel.setBackground( Color.WHITE );
        JPanel scorePanel = new JPanel();
//        scorePanel.setLayout( new BoxLayout( scorePanel , BoxLayout.Y_AXIS ) );
        JLabel scorePanelLabel = new JLabel("Recommendation Score:");
//        JLabel scorePanelLabel = new JLabel("Rec:");

        scorePanelLabel.setAlignmentX( LEFT_ALIGNMENT );
        Comparator<Recommendation> recommendationComparator = new Comparator<Recommendation>() {
            @Override
            public int compare(Recommendation o1, Recommendation o2) {
                return o1.getScore() == o2.getScore() ? 0 : (o1.getScore() < o2.getScore() ? 1 : -1);
            }
        };

        Recommendation bestRecom = recommendationList.stream().max( recommendationComparator ).get();
        JLabel deviationLabel = null;
        if( bestRecom.getScore() < 1 ) {
            deviationLabel = new JLabel( "Deviation Detected!" );
            deviationLabel.setForeground(Color.RED);
        }
        else {
            deviationLabel = new JLabel( "Deviation Not Detected!" );
            deviationLabel.setForeground(Color.GREEN);
        }

        deviationLabel.setFont( new Font( deviationLabel.getFont().getName(), Font.BOLD, deviationLabel.getFont().getSize() ));

        scoreDeviationPanel.add( new JLabel(" " ), BorderLayout.NORTH) ;
        scoreDeviationPanel.add(deviationLabel, BorderLayout.NORTH);

        scorePanel.add( scorePanelLabel, BorderLayout.LINE_START );
        scorePanel.setPreferredSize( new Dimension( ST_COMBO_WIDTH, ST_COMBO_HEIGHT ) );
        Double highestScore = null;


        StringBuilder recomDot = bestRecom.getDotGraph();
        StringBuilder recomCode = bestRecom.getCodeSnippet();

        recomDotGraphSourceCodeViewerPanel = new DotGraphSourceCodeViewerPanel( "Recommended Implementation", recomCode, recomDot, (ST_WIDTH - 10 - ST_COMBO_WIDTH) / 2, ST_HEIGHT - 20 );

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
//        gridBagConstraints.weightx = 0.4;
        add( sourceDotGraphSourceCodeViewerPanel, /*gridBagConstraints*/ BorderLayout.LINE_START  );

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
//        gridBagConstraints.ipadx = ST_COMBO_WIDTH;
        List<Recommendation> comboItems = new ArrayList<>();
//        List<Color> colors = new ArrayList<>();




        recommendationList.stream().sorted(recommendationComparator).forEach( recommendation -> {
                    comboItems.add(  recommendation );
//                colors.add( getScoreColor( aDouble ) );
                }
        );

        JComboBox<Recommendation> comboBox = new JComboBox<>( new Vector<>( comboItems ));

        ComboBoxRenderer renderer = new ComboBoxRenderer( comboBox );

        comboBox.setRenderer(renderer);

        comboBox.addActionListener( this );

        scorePanel.add( comboBox, BorderLayout.SOUTH );

        scorePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        scoreDeviationPanel.add( scorePanel, BorderLayout.SOUTH );

        add( /*new JLabel( "To Be Filled" )*/ scoreDeviationPanel , /*gridBagConstraints*/ BorderLayout.CENTER );

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
//        gridBagConstraints.weightx = 0.4;
        add( recomDotGraphSourceCodeViewerPanel , /*gridBagConstraints*/ BorderLayout.LINE_END );
        setPreferredSize( new Dimension( ST_WIDTH, ST_HEIGHT ) );
//        setBorder( BorderFactory.createStrokeBorder(new BasicStroke(1.0f)) );

//        setEnabled(true);
    }

    void updateRecommendation( Recommendation recommendation ){
        recomDotGraphSourceCodeViewerPanel.update( recommendation.getCodeSnippet(), recommendation.getDotGraph() );

    }


    /** Listens to the combo box. */
    public void actionPerformed(ActionEvent e) {
        JComboBox comboBox = (JComboBox)e.getSource();
        Recommendation selectedItem = (Recommendation) comboBox.getSelectedItem();
        updateRecommendation(selectedItem);
    }

}
