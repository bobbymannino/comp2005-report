package com.example.library.forms;

import com.example.library.records.Patient;
import com.example.library.utils.*;

import javax.swing.*;

public class InadmittedPatients extends JFrame {
    private JPanel contentPane;
    private JButton closeButton;
    private JProgressBar loadingBar;
    private JLabel label;

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
            Integer[] patientIds = StringParser.parse(res, Integer[].class);

            for (Integer patientId : patientIds) {
                res = ApiService.get(ApiBase.UNI, "/patients/" + patientId);
                Patient patient = StringParser.parse(res, Patient.class);

                String curr = label.getText();
                label.setText(curr + " - #" + patient.id);
            }

            loadingBar.setVisible(false);
        } catch (ApiError | StringParseError e) {
            // TODO handle error
            System.out.println(e.getMessage());
        }
    }
}
