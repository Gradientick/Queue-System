package com.mycompany.queuingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class AdminDashboard extends JFrame {
    private final JLabel lblNowServing = new JLabel("Now Serving: 0", SwingConstants.CENTER);
    private final JPanel queueListPanel = new JPanel(); // Dynamic panel for columns

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(20, 20));

        // === BUTTONS PANEL ===
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAdd = createModernButton("Add");
        JButton btnRemove = createModernButton("Remove");
        JButton btnNext = createModernButton("Next");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnNext);

        // === NOW SERVING LABEL ===
        lblNowServing.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNowServing.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(lblNowServing, BorderLayout.CENTER);

        // === MULTI-COLUMN QUEUE PANEL ===
        queueListPanel.setLayout(new GridLayout(0, 3, 15, 10)); // 3 columns
        queueListPanel.setBackground(Color.WHITE);
        queueListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(queueListPanel);
        scrollPane.setPreferredSize(new Dimension(460, 250));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel queuePanel = new JPanel(new BorderLayout());
        queuePanel.setBorder(BorderFactory.createTitledBorder("Current Queue"));
        queuePanel.setBackground(Color.WHITE);
        queuePanel.add(scrollPane, BorderLayout.CENTER);

        // === MAIN LAYOUT ===
        add(buttonPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(queuePanel, BorderLayout.SOUTH);

        // === FUNCTIONALITY ===
        btnAdd.addActionListener(e -> {
            QueueManager.getInstance().addQueue();
            refresh();
        });

        btnRemove.addActionListener(e -> {
            QueueManager.getInstance().removeQueue();
            refresh();
        });

        btnNext.addActionListener(e -> {
            int number = QueueManager.getInstance().nextQueue();
            lblNowServing.setText("Now Serving: " + number);
            refresh();
        });

        refresh();
        setLocationRelativeTo(null); // Center window
        setVisible(true);
    }

    private void refresh() {
    queueListPanel.removeAll();

    Queue<Integer> queue = QueueManager.getInstance().getQueue();
    int total = queue.size();
    int columns = 3;
    int rows = (int) Math.ceil(total / (double) columns);

    // Convert to array to index directly
    Integer[] items = queue.toArray(new Integer[0]);

    // Create labels in top-down column-wise order
    for (int row = 0; row < rows; row++) {
        for (int col = 0; col < columns; col++) {
            int index = col * rows + row; // vertical column ordering
            if (index < total) {
                JLabel label = new JLabel("• Queue " + items[index]);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                queueListPanel.add(label);
            } else {
                queueListPanel.add(new JLabel()); // placeholder for alignment
            }
        }
    }

    queueListPanel.revalidate();
    queueListPanel.repaint();
}

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(100, 40));
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        return button;
    }
}