package edu.rit.se.design.ArCodePlugin.recommendation;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizException;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import guru.nidi.graphviz.parse.ParserException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DotGraphViewerPanel extends JPanel{
    int ST_WIDTH;
    int ST_HEIGHT;

    BufferedImage bufferedImage;
    private StringBuilder dot;
    final JLabel noPreviewIsAvailable;
    JLabel dotView;

    public DotGraphViewerPanel(StringBuilder dot, int width, int height ) {
        super();
        ST_HEIGHT = height;
        ST_WIDTH = width;
        noPreviewIsAvailable = new JLabel("No preview is available");
//        noPreviewIsAvailable.setPreferredSize( new Dimension( ST_WIDTH - 5, ST_HEIGHT - 5 ) );
        add(noPreviewIsAvailable, BorderLayout.PAGE_START);
        noPreviewIsAvailable.setVisible(false);
        dotView = new JLabel();
//        dotView.setSize( new Dimension( ST_WIDTH - 5, ST_HEIGHT - 5 ) );
        add( dotView, BorderLayout.CENTER );
        setDot( dot );
        setPreferredSize( new Dimension( ST_WIDTH, ST_HEIGHT ) );
        setBackground( Color.WHITE );
//        setBorder( BorderFactory.createStrokeBorder(new BasicStroke(1.0f)) );
//        setBorder( BorderFactory.createEtchedBorder() );

//        setEnabled(true);
    }

/*    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if( bufferedImage != null )
            g.drawImage( bufferedImage, 0,0, 1920, 1200, Color.white, null );
        else
            noPreviewIsAvailable.setVisible(true);

    }*/

    public synchronized void setDot(StringBuilder dot) {
        this.dot = dot ;
        try {
            MutableGraph mutableGraph = new Parser().read(dot.toString());
            guru.nidi.graphviz.attribute.Label label = (guru.nidi.graphviz.attribute.Label) mutableGraph.graphAttrs().get( "label" );
            mutableGraph.graphAttrs().add("label", "");
            Graphviz graphviz = Graphviz.fromGraph( mutableGraph );
            bufferedImage = graphviz./*width(500).height(1000).*/render(Format.SVG).toImage();

            File imgeOutput = new File( label + ".png" );
            ImageIO.write( bufferedImage, "png", imgeOutput );


            int biWidth = bufferedImage.getWidth();
            int biHeight = bufferedImage.getHeight();

            double wRation = (ST_WIDTH-20) * 1.0 / biWidth ;
            double hRation = (ST_HEIGHT-20) * 1.0 / biHeight;

            double ration = Math.min( wRation, hRation );

            double justifiedW = ration * biWidth;
            double justifiedH = ration * biHeight;


            ImageIcon tmpImageIcon = new ImageIcon( bufferedImage );
            Image image = tmpImageIcon.getImage(); // transform it
            Image newimg = image.getScaledInstance((int)justifiedW, (int)justifiedH,  Image.SCALE_SMOOTH); // scale it the smooth way
            ImageIcon imageIcon = /*new ImageIcon();*/ new ImageIcon(newimg);  // transform it back


            dotView.setIcon( imageIcon );
//            dotView.setBorder( BorderFactory.createEtchedBorder() );
//            dotView.setSize( new Dimension( ST_WIDTH - 5, ST_HEIGHT - 5 ) );
//            dotView.repaint();
//            noPreviewIsAvailable.setVisible(false);
//            add( new JLabel( new ImageIcon( bufferedImage ) )  );
        } catch (IOException | ParserException | GraphvizException e) {
            noPreviewIsAvailable.setVisible(true);
        }
    }

/*    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int biWidth = bufferedImage.getWidth();
        int biHeight = bufferedImage.getHeight();

        double wRation = 500 * 1.0 / biWidth ;
        double hRation = 400 * 1.0 / biHeight;

        double ration = Math.min( wRation, hRation );

        double justifiedW = ration * biWidth;
        double justifiedH = ration * biHeight;


        if( bufferedImage != null )

            g.drawImage( bufferedImage, 500 / 2 - (int)(justifiedW / 2),400 / 2 - (int)(justifiedH / 2), (int)justifiedW, (int)justifiedH, Color.white, null );

        else
            noPreviewIsAvailable.setVisible(true);
    }*/

    public void update( StringBuilder dotGraph ){
        setDot( dotGraph );
        repaint();
    }





}