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

package edu.rit.se.design.ArCodePlugin.settings;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.uiDesigner.core.AbstractLayout;
import com.intellij.util.ui.GridBag;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ArCodeSettingsDialogWrapper extends DialogWrapper {

    JPanel panel = new JPanel(new GridBagLayout());
    JTextField fSpecPath = new JTextField();
    JTextField trainingProjectsPath = new JTextField();
    JTextField frameworkJarPath = new JTextField();
    JTextField frameworkPackage = new JTextField();
    JTextField framework = new JTextField();
    JTextField arCodeJarPath = new JTextField();
    JTextField projectJarPath = new JTextField();
    JTextField exclusionFilePath = new JTextField();
    JRadioButton trainFromScratch = new JRadioButton("Yes");
    JRadioButton notTrainFromScratch = new JRadioButton( "No");
    JRadioButton testFromScratch = new JRadioButton("Yes");
    JRadioButton notTestFromScratch = new JRadioButton("No");


    public ArCodeSettingsDialogWrapper(Project project, boolean canBeParent ) {
        super(project, canBeParent);
        init();
        setTitle( "ArCode Settings" );
        ArCodePersistentStateComponent arCodePersistentStateComponent = ArCodePersistentStateComponent.getInstance();
        if ( arCodePersistentStateComponent != null ){
            fSpecPath.setText( arCodePersistentStateComponent.getState().getfSpecPath() );
            trainingProjectsPath.setText( arCodePersistentStateComponent.getState().getTrainingProjectsPath() );
            frameworkJarPath.setText( arCodePersistentStateComponent.getState().getFrameworkJarPath() );
            frameworkPackage.setText( arCodePersistentStateComponent.getState().getFrameworkPackage() );
            framework.setText( arCodePersistentStateComponent.getState().getFramework() );
            arCodeJarPath.setText( arCodePersistentStateComponent.getState().getArCodeJarPath() );
            projectJarPath.setText( arCodePersistentStateComponent.getState().getProjectJarPath() );
            exclusionFilePath.setText( arCodePersistentStateComponent.getState().getExclusionFilePath() );
            trainFromScratch.setSelected( arCodePersistentStateComponent.getState().getTrainFromScratch() );
            notTrainFromScratch.setSelected( !arCodePersistentStateComponent.getState().getTrainFromScratch() );
            testFromScratch.setSelected( arCodePersistentStateComponent.getState().getTestFromScratch() );
            notTestFromScratch.setSelected( !arCodePersistentStateComponent.getState().getTestFromScratch() );
        }
    }


    @Override
    protected @Nullable JComponent createCenterPanel() {
        GridBag gb = new GridBag().setDefaultInsets(new Insets(0, 0, AbstractLayout.DEFAULT_VGAP, AbstractLayout.DEFAULT_HGAP))
        .setDefaultWeightX(1.0).
        setDefaultFill(GridBagConstraints.HORIZONTAL);

        panel.add( createLabel( "ArCode Jar Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( arCodeJarPath, gb.next().weightx(0.8) );

        panel.add( createLabel( "Framework Name" ), gb.nextLine().next().weightx(0.2) );
        panel.add( framework, gb.next().weightx(0.8) );

        panel.add( createLabel( "Framework Jar Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( frameworkJarPath, gb.next().weightx(0.8) );

        panel.add( createLabel( "Framework Package" ), gb.nextLine().next().weightx(0.2) );
        panel.add( frameworkPackage, gb.next().weightx(0.8) );

        ButtonGroup buttonGroupTrain = new ButtonGroup();
        buttonGroupTrain.add( trainFromScratch );
        buttonGroupTrain.add( notTrainFromScratch );
        panel.add( createLabel( "Mine FSpec From Scratch" ), gb.nextLine().next().weightx(0.2) );
        panel.add( trainFromScratch, gb.next().weightx(0.4) );
        panel.add( notTrainFromScratch, gb.next().weightx(0.4) );

        panel.add( createLabel( "FSpec Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( fSpecPath, gb.next().weightx(0.8) );

        panel.add( createLabel( "Training Projects Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( trainingProjectsPath, gb.next().weightx(0.8) );

        ButtonGroup buttonGroupTest = new ButtonGroup();
        buttonGroupTest.add( testFromScratch );
        buttonGroupTest.add( notTestFromScratch );
        panel.add( createLabel( "Create a Fresh GRAAM" ), gb.nextLine().next().weightx(0.2) );
        panel.add( testFromScratch, gb.next().weightx(0.4) );
        panel.add( notTestFromScratch, gb.next().weightx(0.4) );

        panel.add( createLabel( "Project Jar Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( projectJarPath, gb.next().weightx(0.8) );

        panel.add( createLabel( "Exclusion File Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( exclusionFilePath, gb.next().weightx(0.8) );

        panel.setPreferredSize( new Dimension( 400, 200 ) );
        panel.setEnabled(true);
        return panel;
    }

    JLabel createLabel( String text ){
        JLabel label = new JLabel();
        label.setText( text );
//        label.component(UIUtil.ComponentStyle.SMALL);
//        label.setBorder(JBUI.Borders.empty( 0,5,2,0 )  );
        return label;
    }

    @Override
    protected void doOKAction() {
        ArCodePersistentStateComponent arCodePersistentStateComponent = ArCodePersistentStateComponent.getInstance();
        arCodePersistentStateComponent.getState().setArCodeJarPath( arCodeJarPath.getText() );
        arCodePersistentStateComponent.getState().setfSpecPath( fSpecPath.getText() );
        arCodePersistentStateComponent.getState().setFramework( framework.getText() );
        arCodePersistentStateComponent.getState().setExclusionFilePath( exclusionFilePath.getText() );
        arCodePersistentStateComponent.getState().setFrameworkPackage( frameworkPackage.getText() );
        arCodePersistentStateComponent.getState().setFrameworkJarPath( frameworkJarPath.getText() );
        arCodePersistentStateComponent.getState().setProjectJarPath( projectJarPath.getText() );
        arCodePersistentStateComponent.getState().setTrainingProjectsPath( trainingProjectsPath.getText() );
        arCodePersistentStateComponent.getState().setTestFromScratch( testFromScratch.isSelected() );
        arCodePersistentStateComponent.getState().setTrainFromScratch( trainFromScratch.isSelected() );


        super.doOKAction();
    }



    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return fSpecPath;
    }
}
