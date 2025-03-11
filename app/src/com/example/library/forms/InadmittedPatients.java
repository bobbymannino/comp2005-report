package com.example.library.forms;

import com.example.library.utils.ApiBase;
import com.example.library.utils.ApiError;
import com.example.library.utils.ApiService;

import javax.swing.*;

public class InadmittedPatients extends JFrame {
    private JPanel contentPane;
    private JButton closeButton;
    private JProgressBar loadingBar;

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

        getInadmittedPatients();
    }

    private void getInadmittedPatients() {
        try {
            String res = ApiService.get(ApiBase.LOCAL, "/admissions/never");
            // Turn res into Integer[]

            loadingBar.setVisible(false);
        } catch (ApiError e) {
            throw new RuntimeException(e);
        }
    }
}
