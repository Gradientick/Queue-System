package com.mycompany.queuingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class DisplayView extends JFrame {
    private final JLabel lblNowServing = new JLabel("NOW SERVING: 000", SwingConstants.CENTER);
    private final JTextArea txtQueue = new JTextArea();

    public DisplayView() {
        setTitle("Queue Display");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20)); // Add spacing

        // Top panel for "NOW SERVING"
        lblNowServing.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblNowServing.setOpaque(true);
        lblNowServing.setBackground(Color.BLACK);
        lblNowServing.setForeground(Color.RED);
        lblNowServing.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Center panel for queue list
        txtQueue.setEditable(false);
        txtQueue.setFont(new Font("Monospaced", Font.PLAIN, 20));
        txtQueue.setBackground(new Color(245, 245, 245));
        txtQueue.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(txtQueue);

        add(lblNowServing, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Auto-refresh queue every 1 second
        Timer timer = new Timer(1000, e -> refresh());
        timer.start();

        setLocationRelativeTo(null); // Center window
        setVisible(true);
    }

    private void refresh() {
        int nowServing = QueueManager.getInstance().getNowServing();
        lblNowServing.setText("NOW SERVING: " + String.format("%03d", nowServing));

        Queue<Integer> queue = QueueManager.getInstance().getQueue();
        StringBuilder sb = new StringBuilder("NEXT IN LINE:\n\n");
        for (int num : queue) {
            sb.append("Queue ").append(num).append("\n");
        }

        txtQueue.setText(sb.toString());
    }
}