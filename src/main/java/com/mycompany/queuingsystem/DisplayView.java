package com.mycompany.queuingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;

public class DisplayView extends JFrame {
    private final JLabel lblNowServing = new JLabel("NOW SERVING: 000", SwingConstants.CENTER);
    private final JPanel queueListPanel = new JPanel();

    public DisplayView() {
        setTitle("Queue Display");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load the background image
        ImageIcon backgroundIcon = new ImageIcon("src/main/java/com/mycompany/queuingsystem/background.png");

        // Custom panel with background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout(20, 20));
        setContentPane(backgroundPanel);

        Color headerBg = new Color(33, 33, 33);
        Color headerText = new Color(255, 82, 82);

        // === TOP: Now Serving ===
        lblNowServing.setFont(new Font("SansSerif", Font.BOLD, 42));
        lblNowServing.setOpaque(true);
        lblNowServing.setBackground(headerBg);
        lblNowServing.setForeground(headerText);
        lblNowServing.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        backgroundPanel.add(lblNowServing, BorderLayout.NORTH);

        // === CENTER: Queue List in Multi-Column Grid ===
        queueListPanel.setLayout(new GridLayout(0, 3, 30, 15));
        queueListPanel.setOpaque(false); // Transparent to see background

        JScrollPane scrollPane = new JScrollPane(queueListPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Timer to refresh queue
        Timer timer = new Timer(1000, e -> refresh());
        timer.start();

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
                int index = col * rows + row;
                if (index < total) {
                    JLabel label = new JLabel("Queue " + items[index]);
                    label.setFont(new Font("Segoe UI", Font.PLAIN, 24));
                    label.setForeground(new Color(40, 40, 40));
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    queueListPanel.add(label);
                } else {
                    queueListPanel.add(new JLabel());
                }
            }
        }

        queueListPanel.revalidate();
        queueListPanel.repaint();
    }
}