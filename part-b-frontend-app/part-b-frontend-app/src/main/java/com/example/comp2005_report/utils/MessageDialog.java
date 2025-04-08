package com.example.comp2005_report.utils;

import javax.swing.*;

public class MessageDialog {
    public static void showError(String message, JPanel contentPane) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(contentPane, message, "Error!", JOptionPane.ERROR_MESSAGE);
        });
    }

    public static void showWarning(String message, JPanel contentPane) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(contentPane, message, "Warning!", JOptionPane.WARNING_MESSAGE);
        });
    }

    public static void showInfo(String message, JPanel contentPane) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(contentPane, message, "Info", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
