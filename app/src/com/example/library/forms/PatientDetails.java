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
        System.out.println("Loading patient " + patientId);
        try {
            System.out.println("1");
            String res = ApiService.get(ApiBase.UNI, "/patients/" + patientId);
            System.out.println("2");
            Patient patient = StringParser.parse(res, Patient.class);
            System.out.println("3");

            setTitle(patient.getFullName());
            titleLabel.setText(patient.getFullName());

            idLabel.setText("ID: " + patient.id);
            forenameLabel.setText("Forename: " + patient.firstName);
            surnameLabel.setText("Surname: " + patient.lastName);
            nhsNumberLabel.setText("NHS Number: " + patient.nhsNumber);
        } catch (ApiError | StringParseError e) {
            // TODO handle error properly
        }
    }
}
