package com.example.library.forms;

import javax.swing.*;

public class MainMenu extends JFrame {
    private JPanel contentPane;
    private JButton patientsWhoHaveNotButton;

    public MainMenu() {
        setTitle("COMP2005 Java App");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);
        setResizable(false);

        patientsWhoHaveNotButton.addActionListener((event) -> {
            new InadmittedPatients();
        });

        setVisible(true);
    }
}
