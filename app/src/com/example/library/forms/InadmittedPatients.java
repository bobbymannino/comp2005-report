package com.example.library.forms;

import com.example.library.records.Patient;
import com.example.library.utils.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class InadmittedPatients extends JFrame {
    private JPanel contentPane;
    private JLabel titleLabel;
    private JButton closeButton;
    private JProgressBar loadingBar;
    private JList patientJList;

    private static final boolean SHOW_PATIENT_ID_IN_LIST = true;

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
    /// return a list of patient IDs. If there are 0 IDs it will also show a
    /// message and dispose the window. If something does go wrong, an error
    /// message will appear and the window will be disposed off.
    ///
    /// @returns Integer[] where length > 0
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

    /// This function will return null if something goes wrong, otherwise it will
    /// return a list of patients. If it fails to get 1 or more patients details
    /// it will show an error message then dispose. If there are 0 patients it will also show a
    /// message and dispose the window. If something does go wrong, an error
    /// message will appear and the window will be disposed off.
    ///
    /// @return `List<Patient>` where length > 0 or `null`
    private List<Patient> getPatientsOrNull(Integer[] patientIds) {
        if (patientIds.length == 0) {
            MessageDialog.showInfo("There are no patients who have not been admitted.", contentPane);

            dispose();

            return null;
        }

        List<Patient> patients = new ArrayList<>();

        for (Integer patientId : patientIds) {
            Patient patient = PatientService.getPatientOrNull(patientId);
            if (patient != null) patients.add(patient);
        }

        // if failed to get every patients details, alert the user and dispose the window
        if (patients.isEmpty()) {
            MessageDialog.showError("There are patient IDs but we cannot get any details about them, please contact support bob@uop.ac.uk", contentPane);

            dispose();

            return null;
        }

        // get number of patients we failed to get details for
        int failedPatientCount = patientIds.length - patients.size();

        // If patient IDs exist but something went wrong fetching a patients details,
        // show the user how many users we couldn't get details for, this means we have
        // at least 1 patients details
        if (failedPatientCount > 0) MessageDialog.showWarning("There are " + failedPatientCount + " patient(s) that we could not fetch.", contentPane);

        return patients;
    }

    /// Turns patients into a list of their full name, and depending on
    /// the `SHOW_PATIENT_ID_IN_LIST` flag, possibly their ID as well
    ///
    /// @return `List<String>` A list of patient names (and maybe IDs)
    private List<String> patientsToRowTexts(List<Patient> patients) {
        List<String> patientRowTexts = new ArrayList<>();

        for (Patient patient : patients) {
            String patientRowTextsStr = patient.getFullName();

            if (SHOW_PATIENT_ID_IN_LIST) patientRowTextsStr += " (ID: " + patient.id + ")";

            patientRowTexts.add(patientRowTextsStr);
        }

        return patientRowTexts;
    }

    private void loadNeverAdmittedPatients() {
        Integer[] patientIds = getPatientIds();
        if (patientIds == null) return;

        List<Patient> patients = getPatientsOrNull(patientIds);
        if (patients == null) return;

        List<String> patientRowTexts = patientsToRowTexts(patients);

        // create list model for list, i hate that i have to do this for just text list
        DefaultListModel listModel = new DefaultListModel();

        // add all patient rows to list
        listModel.addAll(patientRowTexts);

        // set patient list element's model to the one we made
        patientJList.setModel(listModel);

        // on double click, open patient details
        patientJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                // only on double click
                if (event.getClickCount() == 2) {
                    // get index of list item that was double-clicked
                    int index = patientJList.locationToIndex(event.getPoint());

                    // if click out of bounds do nothing
                    if (index == -1) return;

                    // open a window with their details
                    new PatientDetails(patients.get(index).id);
                }
            }
        });

        titleLabel.setText("Never Admitted Patients (" + patientRowTexts.size() + ")");

        loadingBar.setVisible(false);
    }
}
