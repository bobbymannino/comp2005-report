package com.example.library.forms;

import com.example.library.records.Patient;
import com.example.library.utils.*;

import javax.swing.*;
import java.awt.*;

public class InadmittedPatients extends JFrame {
    private JPanel contentPane;
    private JButton closeButton;
    private JProgressBar loadingBar;
    private JLabel titleLabel;
    private JList list1;

    public InadmittedPatients() {
        setTitle("Never Admitted Patients");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);

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

            titleLabel.setText("Never Admitted Patients (" + patientIds.length + ")");

            loadingBar.setVisible(false);

            DefaultListModel listModel = new DefaultListModel();

            for (Integer patientId : patientIds) {
                res = ApiService.get(ApiBase.UNI, "/patients/" + patientId);
                Patient patient = StringParser.parse(res, Patient.class);

                listModel.addElement(patient.getFullName());
            }

            list1.setModel(listModel);
        } catch (ApiError | StringParseError e) {
            // TODO handle error
        }
    }
}
