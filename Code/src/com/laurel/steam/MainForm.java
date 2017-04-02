package com.laurel.steam;

import org.omg.CORBA.Environment;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by alexg on 4/1/2017.
 */
public class MainForm {
    private JPanel panel1;
    private JTextField studentFileDataField;
    private JTextField workshopFileDataField;
    private JTextField outputFolderDataField;
    private JCheckBox assignStudentsRandomlyCheckBox;
    private JButton chooseStudentsButton;
    private JButton chooseWorkshopsButton;
    private JButton selectFolderButton;
    private JButton performAssignmentButton;
    private JButton exitButton;
    private JTextArea statusTextArea;

    public String getStudentFile () {
        return studentFileDataField.getText();
    }

    public String getWorkshopFile () {
        return workshopFileDataField.getText();
    }

    public String getOutputFolder () {
        return outputFolderDataField.getText();
    }

    public Boolean getAssignmentChoice () {
        return assignStudentsRandomlyCheckBox.isSelected();
    }

    public void Log(String text) {
        statusTextArea.append(text + System.lineSeparator());
    }


    public MainForm() {

        workshopFileDataField.setText("./data/workshops.tsv");
        studentFileDataField.setText("./data/students.tsv");
        outputFolderDataField.setText("./data");
        performAssignmentButton.setEnabled(UnblockGoButton());
        chooseStudentsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                final JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Choose the file with student data:");

                if (fc.showOpenDialog(chooseStudentsButton) == JFileChooser.APPROVE_OPTION) {
                    studentFileDataField.setText(fc.getSelectedFile().getAbsolutePath());
                }
                performAssignmentButton.setEnabled(UnblockGoButton());
            }
        });
        chooseWorkshopsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                final JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Choose the file with workshop data:");

                if (fc.showOpenDialog(chooseStudentsButton) == JFileChooser.APPROVE_OPTION) {
                    workshopFileDataField.setText(fc.getSelectedFile().getAbsolutePath());
                }
                performAssignmentButton.setEnabled(UnblockGoButton());

            }
        });
        selectFolderButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                final JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Choose the folder to place output data:");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                if (fc.showOpenDialog(chooseStudentsButton) == JFileChooser.APPROVE_OPTION) {
                    outputFolderDataField.setText(fc.getSelectedFile().getAbsolutePath());
                }

                performAssignmentButton.setEnabled(UnblockGoButton());

            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
        performAssignmentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!performAssignmentButton.isEnabled()) return;
                SteamChoice.DoAssignment();
            }
        });
    }

    public Boolean UnblockGoButton() {
        return
                (getStudentFile() != null && !getStudentFile().trim().isEmpty()) &&
                        (getWorkshopFile() != null && !getWorkshopFile().trim().isEmpty()) &&
                        (getOutputFolder() != null && !getOutputFolder().trim().isEmpty());
    }

    public static MainForm main(String[] args) {
        JFrame frame = new JFrame("Laurel STEAM Fair Student Assignment Tool");
        MainForm mf =new MainForm();
        frame.setContentPane(mf.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        return mf;
    }
}
