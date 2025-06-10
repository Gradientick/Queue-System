package com.mycompany.queuingsystem;

import java.util.LinkedList;
import java.util.Queue;

public class QueueManager {
    private static QueueManager instance;
    private final Queue<Integer> queue = new LinkedList<>();
    private int counter = 1;
    private int nowServing = 0;

    private QueueManager() {}

    public static QueueManager getInstance() {
        if (instance == null) instance = new QueueManager();
        return instance;
    }

    public void addQueue() {
        queue.offer(counter++);
    }

    public void removeQueue() {
        if (!queue.isEmpty()) queue.poll();
    }

    public int nextQueue() {
        if (!queue.isEmpty()) {
            nowServing = queue.poll();
        }
        return nowServing;
    }

    public Queue<Integer> getQueue() {
        return new LinkedList<>(queue); // to avoid external modification
    }

    public int getNowServing() {
        return nowServing;
    }
}