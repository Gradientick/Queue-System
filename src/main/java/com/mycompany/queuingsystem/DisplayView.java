package com.mycompany.queuingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class DisplayView extends JFrame {
    private final JLabel lblNowServing = new JLabel("NOW SERVING: 000", SwingConstants.CENTER);
    private final JPanel queueListPanel = new JPanel();

    public DisplayView() {
        setTitle("Queue Display");
        setSize(900, 600); // wider for multi-column layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        Color backgroundColor = new Color(248, 248, 248); // soft white
        Color headerBg = new Color(33, 33, 33);           // near black
        Color headerText = new Color(255, 82, 82);        // soft red (not harsh)

        getContentPane().setBackground(backgroundColor);

        // === TOP: Now Serving ===
        lblNowServing.setFont(new Font("SansSerif", Font.BOLD, 42));
        lblNowServing.setOpaque(true);
        lblNowServing.setBackground(headerBg);
        lblNowServing.setForeground(headerText);
        lblNowServing.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(lblNowServing, BorderLayout.NORTH);

        // === CENTER: Queue List in Multi-Column Grid ===
        queueListPanel.setLayout(new GridLayout(0, 3, 30, 15)); // 3 columns
        queueListPanel.setBackground(backgroundColor);
        queueListPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JScrollPane scrollPane = new JScrollPane(queueListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        add(scrollPane, BorderLayout.CENTER);

        // Auto-refresh queue every 1 second
        Timer timer = new Timer(1000, e -> refresh());
        timer.start();

        setLocationRelativeTo(null); // Center window
        setVisible(true);
    }

    private void refresh() {
        queueListPanel.removeAll();

        int nowServing = QueueManager.getInstance().getNowServing();
        lblNowServing.setText("NOW SERVING: " + String.format("%03d", nowServing));

        Queue<Integer> queue = QueueManager.getInstance().getQueue();
        int total = queue.size();
        int columns = 3;
        int rows = (int) Math.ceil((double) total / columns);

        Integer[] items = queue.toArray(new Integer[0]);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int index = col * rows + row; // Top-down, per column
                if (index < total) {
                    JLabel label = new JLabel("Queue " + items[index]);
                    label.setFont(new Font("Segoe UI", Font.PLAIN, 24));
                    label.setForeground(new Color(40, 40, 40)); // dark gray
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    queueListPanel.add(label);
                } else {
                    queueListPanel.add(new JLabel()); // blank for alignment
                }
            }
        }

        queueListPanel.revalidate();
        queueListPanel.repaint();
    }
}