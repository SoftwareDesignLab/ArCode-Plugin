package edu.rit.se.design.ArCodePlugin.recommendation;

import javax.swing.*;
import java.awt.*;

public class SourceCodeViewerPanel extends JPanel {
    int ST_WIDTH;
    int ST_HEIGHT;

    final JLabel noPreviewIsAvailable;
    private StringBuilder sourceCode;
    JTextPane textPane;

    public SourceCodeViewerPanel(StringBuilder sourceCode, int width, int height ) {
        super();
        ST_HEIGHT = height;
        ST_WIDTH = width;
        noPreviewIsAvailable = new JLabel("No preview is available");
        add(noPreviewIsAvailable, BorderLayout.PAGE_START);
        noPreviewIsAvailable.setVisible(false);
        textPane = new JTextPane() {

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }

        };
        setPreferredSize(new Dimension(ST_WIDTH, ST_HEIGHT));
        setBackground( Color.WHITE );
//        setEnabled(true);
        add(textPane, BorderLayout.CENTER);
//        setBorder( BorderFactory.createStrokeBorder(new BasicStroke(1.0f)) );
//        setBorder( BorderFactory.createStrokeBorder(new BasicStroke(1.0f)) );
//        add(new JScrollPane(textPane));

        textPane.setContentType("text/html");
        textPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        textPane.setBackground( new Color( 255, 255, 240 ) );
        textPane.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createMatteBorder(
                1, 1, 1, 1, new Color( 255, 204, 153 )), BorderFactory.createEmptyBorder(5, 10, 5, 10) ));
        textPane.setPreferredSize( new Dimension(ST_WIDTH - 20, ST_HEIGHT - 10) );

        JScrollPane scrollPane = new JScrollPane( textPane );
        scrollPane.setViewportBorder(null);
        scrollPane.setBorder(null);

        add( scrollPane );

        setSourceCode(sourceCode);
//        setBorder( BorderFactory.createEtchedBorder() );

    }

    public synchronized void setSourceCode(StringBuilder sourceCode) {
        this.sourceCode = sourceCode;
        try {
            String html = "<html><pre>" + sourceCode.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>")/*.replaceAll(" ", "&ensp")*/ + "</pre></html>";
            textPane.setText(html);
            textPane.setSize(textPane.getPreferredSize().width, textPane.getPreferredSize().height);
            noPreviewIsAvailable.setVisible(false);
        }
        catch (Exception e){
            noPreviewIsAvailable.setVisible(true);
        }

/*
        textPane.setStyledDocument( new HTMLDocument() );
        HTMLDocument htmlDocument = ((HTMLDocument)textPane.getStyledDocument());
        try {
            htmlDocument.insertAfterEnd( htmlDocument.getCharacterElement(htmlDocument.getLength()), html);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
//        textPane.setText( html );


    }


    public void update( StringBuilder code ){
        setSourceCode(code);
//        repaint();
    }

    /*    @Override
    protected void paintComponent(Graphics g) {
        try {
            g.
        } catch (GraphvizException ignored) {

        }
    }*/
}
