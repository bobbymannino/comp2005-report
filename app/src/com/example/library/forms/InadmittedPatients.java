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
    private JLabel label;

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

                listModel.addElement("#" + patient.id + " - " + patient.getFullName());
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

            label.setText("Double click a patient to see their details");
        } catch (ApiError e) {
            // TODO handle error

            label.setForeground(Color.RED);
            label.setText("The API has failed, please try again by closing and reopending the application. If that does not work the API may be down, in that case contact support.");
        } catch (StringParseError e ) {
            label.setForeground(Color.RED);
            label.setText("The API returned malformed data");
        }
    }

    private void openPatientDetailsWindow(Integer patientId) {
        new PatientDetails(patientId);
    }
}
