package com.example.library.forms;

import com.example.library.records.Patient;
import com.example.library.utils.*;
import javax.swing.*;

public class PatientDetails extends JFrame {

    private Integer patientId;
    private JPanel contentPane;
    private JLabel titleLabel;
    private JButton closeButton;
    private JLabel idLabel;
    private JLabel forenameLabel;
    private JLabel surnameLabel;
    private JLabel nhsNumberLabel;

    public PatientDetails(Integer patientId) {
        this.patientId = patientId;

        setTitle("Loading...");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);
        setResizable(false);

        closeButton.addActionListener(event -> {
            setVisible(false);
        });

        setVisible(true);

        loadPatient();
    }

    private void loadPatient() {
        try {
            Patient patient = PatientService.getPatient(patientId);

            setTitle(patient.getFullName());
            titleLabel.setText(patient.getFullName());

            idLabel.setText("ID: " + patient.id);
            forenameLabel.setText("Forename: " + patient.firstName);
            surnameLabel.setText("Surname: " + patient.lastName);
            nhsNumberLabel.setText("NHS Number: " + patient.nhsNumber);
        } catch (ApiError e) {
            MessageDialog.showError("It seems that the UoP API is down, please contact the uni.", contentPane);
        } catch (StringParseError e) {
            MessageDialog.showError("Failed to parse patient string, please try again.", contentPane);
        }
    }
}
