package com.example.comp2005_report.forms;

import com.example.comp2005_report.records.Patient;
import com.example.comp2005_report.utils.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

public class InadmittedPatients extends JFrame {

    private JPanel contentPane;
    private JLabel titleLabel;
    private JButton closeButton;
    private JList patientJList;

    private static final boolean SHOW_PATIENT_ID_IN_LIST = true;

    public InadmittedPatients() {
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);
        setResizable(false);

        closeButton.addActionListener(event -> {
            dispose();
        });

        setLoading();

        setVisible(true);

        SwingUtilities.invokeLater(this::loadNeverAdmittedPatients);
    }

    private void setLoading() {
        setTitle("Loading...");
        titleLabel.setText("Loading...");
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
            MessageDialog.showError(
                    "Something went wrong with the local API service, are you sure it's running?",
                    contentPane
            );

            dispose();

            return null;
        } catch (StringParseError e) {
            MessageDialog.showError(
                    "The local API returned malformed data, contact support at soso@yahoo.co.uk",
                    contentPane
            );

            dispose();

            return null;
        }

        // if there are no patient IDs then tell the user and close
        if (patientIds.length == 0) {
            MessageDialog.showInfo(
                    "There are no patients who have not been admitted.",
                    contentPane
            );

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
            MessageDialog.showInfo(
                    "There are no patients who have not been admitted.",
                    contentPane
            );

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
            MessageDialog.showError(
                    "There are patient IDs but we cannot get any details about them, please contact support bob@uop.ac.uk",
                    contentPane
            );

            dispose();

            return null;
        }

        // get number of patients we failed to get details for
        int failedPatientCount = patientIds.length - patients.size();

        // If patient IDs exist but something went wrong fetching a patients details,
        // show the user how many users we couldn't get details for, this means we have
        // at least 1 patients details
        if (failedPatientCount > 0) MessageDialog.showWarning(
                "There are " +
                        failedPatientCount +
                        " patient(s) that we could not fetch.",
                contentPane
        );

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

            if (SHOW_PATIENT_ID_IN_LIST) patientRowTextsStr +=
                    " (ID: " + patient.id + ")";

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
        patientJList.addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        // only on double click
                        if (event.getClickCount() == 2) {
                            // get index of list item that was double-clicked
                            int index = patientJList.locationToIndex(
                                    event.getPoint()
                            );

                            // if click out of bounds do nothing
                            if (index == -1) return;

                            // open a window with their details
                            new PatientDetails(patients.get(index).id);
                        }
                    }
                }
        );

        titleLabel.setText(
                "Never Admitted Patients (" + patientRowTexts.size() + ")"
        );
        titleLabel.setIcon(null);

        setTitle("Never Admitted Patients (" + patientRowTexts.size() + ")");
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(4, 2, new Insets(16, 16, 16, 16), 8, 8));
        titleLabel = new JLabel();
        Font titleLabelFont = this.$$$getFont$$$("Fira Mono", Font.BOLD, 24, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setText("Never Admitted Patients");
        contentPane.add(titleLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        closeButton = new JButton();
        closeButton.setText("Close");
        contentPane.add(closeButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        patientJList = new JList();
        contentPane.add(patientJList, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Double click an employee to see their details");
        contentPane.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
