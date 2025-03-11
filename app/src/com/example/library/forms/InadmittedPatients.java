package com.example.library.forms;

import javax.swing.*;

public class InadmittedPatients extends JFrame {
    private JPanel contentPane;
    private JButton closeButton;

    public InadmittedPatients() {
        setTitle("Never Admitted Patients");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        closeButton.addActionListener((event) -> {
            setVisible(false);
        });

        setVisible(true);
    }
}
