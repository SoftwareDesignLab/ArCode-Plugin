/*
 * Copyright (c) 2021 - Present. Rochester Institute of Technology
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.rit.se.design.ArCodePlugin.recommendation;

import javax.swing.*;
import java.awt.*;

public class ComboBoxRenderer extends JPanel implements ListCellRenderer{
    private static final long serialVersionUID = -1L;

    JPanel textPanel;
    JLabel text;

    public ComboBoxRenderer(JComboBox combo) {

        textPanel = new JPanel();
        textPanel.add(this);
        text = new JLabel();
        text.setOpaque(true);
        text.setFont(combo.getFont());
        textPanel.add(text);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected)
        {
            setBackground(new Color(224, 225, 255));
        }
        else
        {
            setBackground(Color.WHITE);
        }
        setForeground( getScoreColor( (Recommendation)value ) );

 /*       if (colors.length != values.length)
        {
            System.out.println("colors.length does not equal strings.length");
            return this;
        }
        else if (colors == null)
        {
            System.out.println("use setColors first.");
            return this;
        }
        else if (values == null)
        {
            System.out.println("use setStrings first.");
            return this;
        }*/

        text.setBackground(getBackground());

        text.setText(value.toString());

        text.setForeground( getScoreColor((Recommendation) value) );
/*        if (index>-1) {
            text.setForeground(colors[index]);
        }*/
        return text;
    }

    Color getScoreColor( Recommendation recommendation ){
        if( recommendation.getScore() >= .8 )
            return new Color(0,153,0); // GREEN
        if( recommendation.getScore() >= .5 )
            return new Color(51,153,255); // BLUE
        if( recommendation.getScore() >= .2 )
            return new Color(255,128,0); // BLUE
        return new Color(255,51,51); // BLUE
    }

}
