package com.example.library.forms;

import com.example.library.records.Patient;
import com.example.library.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InadmittedPatients extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JButton closeButton;
    private JProgressBar loadingBar;
    private JList list1;
    private JTextArea hintTextArea;

    public InadmittedPatients() {
        setTitle("Never Admitted Patients");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);
        setResizable(false);

        closeButton.addActionListener((event) -> {
            setVisible(false);
        });

        setVisible(true);

        getInadmittedPatients();
    }

    private void showError(String message) {
        hintTextArea.setForeground(Color.RED);
        hintTextArea.setText(message);
    }

    private void getInadmittedPatients() {
        String res;

        try {
            res = ApiService.get(ApiBase.LOCAL, "/admissions/never");
        } catch (ApiError e) {
            showError("The API has failed, please try again by closing and reopending the application. If that does not work the API may be down, in that case contact support.");

            loadingBar.setVisible(false);
            list1.setVisible(false);

            return;
        }

        Integer[] patientIds;

        try {
            patientIds = StringParser.parse(res, Integer[].class);
        } catch (StringParseError e) {
            showError("The API returned malformed data");

            loadingBar.setVisible(false);
            list1.setVisible(false);

            return;
        }

        DefaultListModel listModel = new DefaultListModel();

        Integer patientCount = 0;

        for (Integer patientId : patientIds) {
            try {
                res = ApiService.get(ApiBase.UNI, "/patients/" + patientId);
            } catch (ApiError e) {
                continue;
            }

            Patient patient;

            try {
                patient = StringParser.parse(res, Patient.class);
            } catch (StringParseError e) {
                continue;
            }

            listModel.addElement("#" + patient.id + " - " + patient.getFullName());

            patientCount++;
        }

        list1.setModel(listModel);

        list1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Integer index = list1.locationToIndex(event.getPoint());
                    openPatientDetailsWindow(patientIds[index]);
                }
            }
        });

        hintTextArea.setText("Double click a patient to see their details");

        titleLabel.setText("Never Admitted Patients (" + patientCount + ")");

        loadingBar.setVisible(false);
    }

    private void openPatientDetailsWindow(Integer patientId) {
        new PatientDetails(patientId);
    }
}
