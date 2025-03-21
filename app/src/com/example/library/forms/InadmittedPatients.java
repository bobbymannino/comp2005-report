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

    public InadmittedPatients() {
        setTitle("Never Admitted Patients");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);
        setResizable(false);

        closeButton.addActionListener((event) -> {
            dispose();
        });

        setVisible(true);

        getInadmittedPatients();
    }

    private void showError(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(contentPane, message, "Error", JOptionPane.ERROR_MESSAGE);

            dispose();
        });
    }

    private void getInadmittedPatients() {
        String res;

        try {
            res = ApiService.get(ApiBase.LOCAL, "/admissions/never");
        } catch (ApiError e) {
            showError("Something went wrong with the local API service, are you sure it's running?");

            return;
        }

        Integer[] patientIds;

        try {
            // res = { admissions: int[] }
            // FIXME
            //  fix me please, i havent changed it to work with the new local api format
            patientIds = StringParser.parse(res, Integer[].class);
        } catch (StringParseError e) {
            showError("The local API returned malformed data, contact support at soso@yahoo.co.uk");

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

        titleLabel.setText("Never Admitted Patients (" + patientCount + ")");

        loadingBar.setVisible(false);
    }

    private void openPatientDetailsWindow(Integer patientId) {
        new PatientDetails(patientId);
    }
}
