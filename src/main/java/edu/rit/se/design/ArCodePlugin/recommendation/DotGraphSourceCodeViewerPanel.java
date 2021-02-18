package edu.rit.se.design.ArCodePlugin.recommendation;


import javax.swing.*;
import java.awt.*;

public class DotGraphSourceCodeViewerPanel  extends JPanel {
    StringBuilder code;
    StringBuilder dotGraph;
     int ST_WIDTH;
     int ST_HEIGHT;

    DotGraphViewerPanel dotGraphViewerPanel;
    SourceCodeViewerPanel sourceCodeViewerPanel;
/*
    BufferedImage bufferedImage;
    final JLabel noPreviewIsAvailable;*/

    public DotGraphSourceCodeViewerPanel(String title, StringBuilder code, StringBuilder dotGraph, int width, int height ) {
        super();

        ST_WIDTH = width;
        ST_HEIGHT = height;
/*
        noPreviewIsAvailable = new JLabel("No preview is available");
*/


//        setLayout( new GridBagLayout() );
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        setBackground( Color.WHITE );

        setDotGraph( dotGraph );
        setCode( code );
/*        add(noPreviewIsAvailable);
        noPreviewIsAvailable.setVisible(false);
        setPreferredSize( new Dimension( 300, 400 ) );*/
        setPreferredSize( new Dimension( ST_WIDTH, ST_HEIGHT ) );
        dotGraphViewerPanel = new DotGraphViewerPanel( getDotGraph(), ST_WIDTH - 10, (2 * ( ST_HEIGHT ) / 3 - 20) );
        sourceCodeViewerPanel = new SourceCodeViewerPanel( getCode(), ST_WIDTH - 10, (1 * ( ST_HEIGHT ) / 3) - 20  );

/*        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
//        gridBagConstraints.ipady = 180;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;*/
/*
        JLabel titleLabel = new JLabel(title + ":");
        titleLabel.setFont( titleLabel.getFont().deriveFont(  titleLabel.getFont().getStyle() | Font.BOLD  ) );
        add( titleLabel, gridBagConstraints );
*/


        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
//        gridBagConstraints.ipady = 400;
        gridBagConstraints.gridheight = 5;
        add( dotGraphViewerPanel, /*gridBagConstraints*/ BorderLayout.PAGE_START  );

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
//        gridBagConstraints.ipady = (1 * ( ST_HEIGHT - 5 ) / 3);
        gridBagConstraints.gridheight = 3;
        add( sourceCodeViewerPanel, /*gridBagConstraints*/ BorderLayout.CENTER  );
//        setEnabled(true);

//        setBorder( BorderFactory.createStrokeBorder(new BasicStroke(1.0f)) );
        setBorder( BorderFactory.createTitledBorder( title ) );

    }

    public void update( StringBuilder code, StringBuilder dotGraph ){
        this.code = code;
        this.dotGraph = dotGraph;
        dotGraphViewerPanel.update( dotGraph );
        sourceCodeViewerPanel.update( code );
    }

    public StringBuilder getCode() {
        return code;
    }

    public void setCode(StringBuilder code) {
        this.code = code;
    }

    public StringBuilder getDotGraph() {
        return dotGraph;
    }

    public void setDotGraph(StringBuilder dotGraph) {
        this.dotGraph = dotGraph;
    }

    /*
    @Override
    protected void paintComponent(Graphics g) {
        try {
            if (bufferedImage == null) {
                try {
                    Graphviz graphviz = Graphviz.fromGraph(new Parser().read(getDotGraph().toString()));
                    bufferedImage = graphviz.width(300).height(400).render(Format.SVG).toImage();
                    noPreviewIsAvailable.setVisible(false);
                } catch (IOException | ParserException | GraphvizException | NullPointerException e) {
                    noPreviewIsAvailable.setVisible(true);
                }
            }
            if (bufferedImage != null) {
                g.drawImage(bufferedImage, 50, 50, this.getWidth() - 100, this.getHeight() - 100, this);
                g.drawString(code.toString(), 50, 400);
            }
        } catch (GraphvizException ignored) {

        }
    }



    public StringBuilder getCode() {
        return code;
    }

    public void setCode(StringBuilder code) {
        bufferedImage = null;
        this.code = code != null ? code : new StringBuilder();
    }

    public StringBuilder getDotGraph() {
        return dotGraph;
    }

    public void setDotGraph(StringBuilder dotGraph) {
        bufferedImage = null;
        this.dotGraph = dotGraph != null ? dotGraph : new StringBuilder();
    }*/


}
