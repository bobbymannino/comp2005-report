package com.example.library.forms;

import javax.swing.*;

public class MainMenu extends JFrame {
    private JPanel contentPane;
    private JButton patientsWhoHaveNotButton;
    private JFrame inadmittedPatientsFrame;

    public MainMenu() {
        setTitle("COMP2005 Java App");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        patientsWhoHaveNotButton.addActionListener((event) -> {
            openInadmittedPatients();
        });

        setVisible(true);
    }

    public void openInadmittedPatients() {
        inadmittedPatientsFrame = new InadmittedPatients();
    }
}
