package com.mycompany.queuingsystem;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private final JLabel lblNowServing = new JLabel("Now Serving: 0");

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");
        JButton btnNext = new JButton("Next");

        btnAdd.addActionListener(e -> QueueManager.getInstance().addQueue());
        btnRemove.addActionListener(e -> QueueManager.getInstance().removeQueue());
        btnNext.addActionListener(e -> {
            int num = QueueManager.getInstance().nextQueue();
            lblNowServing.setText("Now Serving: " + num);
        });

        add(btnAdd);
        add(btnRemove);
        add(btnNext);
        add(lblNowServing);

        setVisible(true);
    }
}