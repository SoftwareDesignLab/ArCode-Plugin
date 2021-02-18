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

        panel.add( createLabel( "FSpec Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( fSpecPath, gb.next().weightx(0.8) );

        panel.add( createLabel( "Project Jar Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( projectJarPath, gb.next().weightx(0.8) );

        panel.add( createLabel( "Exclusion File Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( exclusionFilePath, gb.next().weightx(0.8) );

        panel.add( createLabel( "Training Projects Path" ), gb.nextLine().next().weightx(0.2) );
        panel.add( trainingProjectsPath, gb.next().weightx(0.8) );
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

        super.doOKAction();
    }



    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return fSpecPath;
    }
}
