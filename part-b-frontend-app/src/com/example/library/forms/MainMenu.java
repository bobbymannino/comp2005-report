package com.example.library.forms;

import com.example.library.utils.MessageDialog;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;

public class MainMenu extends JFrame {

    private JPanel contentPane;
    private JButton patientsWhoHaveNotButton;
    private JButton closeButton;
    private JButton openGitHubRepoButton;
    private JLabel titleLabel;

    private final String GITHUB_REPO_LINK =
        "https://github.com/bobbymannino/comp2005-report";

    public MainMenu() {
        setTitle("COMP2005 Java App");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 500);
        setResizable(false);

        patientsWhoHaveNotButton.addActionListener(event -> {
            new InadmittedPatients();
        });

        closeButton.addActionListener(event -> {
            System.exit(0);
        });

        openGitHubRepoButton.addActionListener(event -> {
            try {
                Desktop.getDesktop().browse(new URI(GITHUB_REPO_LINK));
            } catch (Exception e) {
                MessageDialog.showError(
                    "Failed to open link, if you want to manually open it the link is: " +
                    GITHUB_REPO_LINK,
                    contentPane
                );
            }
        });

        setVisible(true);
    }
}
