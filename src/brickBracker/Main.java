package brickBracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Breaker SB");
        obj.setResizable(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Start button
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obj.remove(menuPanel);
                Gameplay gameplay = new Gameplay();
                obj.add(gameplay);
                obj.revalidate();
                gameplay.requestFocusInWindow();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(startButton, gbc);

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 1;
        menuPanel.add(exitButton, gbc);

        obj.add(menuPanel);
        obj.setVisible(true);
    }
}




