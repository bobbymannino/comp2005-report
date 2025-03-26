package com.example.library.forms;

import com.example.library.records.Patient;
import com.example.library.utils.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InadmittedPatients extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JButton closeButton;
    private JProgressBar loadingBar;
    private JList patientJList;

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

        loadNeverAdmittedPatients();
    }

    /// This function will return null if something goes wrong, otherwise it will
    /// return patient IDs. If there are 0 IDs it will also show a message and
    /// dispose the window. If something does go wrong, an error message will
    /// appear and the window will be disposed off.
    private Integer[] getPatientIds() {
        Integer[] patientIds;

        try {
            patientIds = AdmissionService.getInadmittedPatients();
        } catch (ApiError e) {
            MessageDialog.showError("Something went wrong with the local API service, are you sure it's running?", contentPane);

            dispose();

            return null;
        } catch (StringParseError e) {
            MessageDialog.showError("The local API returned malformed data, contact support at soso@yahoo.co.uk", contentPane);

            dispose();

            return null;
        }

        // if there are no patient IDs then tell the user and close
        if (patientIds.length == 0) {
            MessageDialog.showInfo("There are no patients who have not been admitted.", contentPane);

            dispose();

            return null;
        }

        return patientIds;
    }

    private void loadNeverAdmittedPatients() {
        Integer[] patientIds = getPatientIds();
        if (patientIds != null) return;

        DefaultListModel listModel = new DefaultListModel();

        int patientCount = 0;
        int badPatientCount = 0;

        for (Integer patientId : patientIds) {
            Patient patient;

            try {
                patient = PatientService.getPatient(patientId);
            } catch (ApiError | StringParseError e) {
                // if the patient id exists but cannot get it from the UoP api,
                // skip it as if it doesn't exist, but make a note of the failure
                badPatientCount++;

                continue;
            }

            listModel.addElement("#" + patient.id + " - " + patient.getFullName());

            patientCount++;
        }

        patientJList.setModel(listModel);

        patientJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    int index = patientJList.locationToIndex(event.getPoint());
                    openPatientDetailsWindow(patientIds[index]);
                }
            }
        });

        titleLabel.setText("Never Admitted Patients (" + patientCount + ")");

        // if patient count is 0, this means
        if (patientCount == 0) {
            MessageDialog.showError("There are patient IDs but we cannot get any details about them, please contact support bob@blahblah.com", contentPane);

            dispose();

            return;
        }

        // If patient IDs exist but something went wrong fetching a patients details,
        // show the user how many users we couldn't get details for
        if (badPatientCount > 0) MessageDialog.showWarning("There are " + badPatientCount + " patient(s) that we could not fetch.", contentPane);

        loadingBar.setVisible(false);
    }

    private void openPatientDetailsWindow(Integer patientId) {
        new PatientDetails(patientId);
    }
}
