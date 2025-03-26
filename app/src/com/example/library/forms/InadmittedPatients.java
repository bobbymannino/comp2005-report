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

    private void getInadmittedPatients() {
        Integer[] patientIds;

        try {
            patientIds = AdmissionService.getInadmittedPatients();
        } catch (ApiError e) {
            MessageDialog.showError("Something went wrong with the local API service, are you sure it's running?", contentPane);
            dispose();
            return;
        } catch (StringParseError e) {
            MessageDialog.showError("The local API returned malformed data, contact support at soso@yahoo.co.uk", contentPane);
            dispose();
            return;
        }

        DefaultListModel listModel = new DefaultListModel();

        Integer patientCount = 0;
        Integer badPatientCount = 0;

        for (Integer patientId : patientIds) {
            Patient patient;

            try {
                patient = PatientService.getPatient(patientId);
            } catch (ApiError | StringParseError e) {
                // if the patient id exists but cannot get it from the UoP api,
                // skip it as if it doesnt exist
                badPatientCount++;
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

        // If patient IDs exist but something went wrong fetching a patients details,
        // show the user how many users we couldn't get details for
        if (badPatientCount > 0) MessageDialog.showWarning("There are " + badPatientCount + " patient(s) that we could not fetch.", contentPane);

        loadingBar.setVisible(false);
    }

    private void openPatientDetailsWindow(Integer patientId) {
        new PatientDetails(patientId);
    }
}
