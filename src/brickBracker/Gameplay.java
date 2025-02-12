package brickBracker;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 5;
    private int playerX_position = 290;
    private int ballposX_position = 120;
    private int ballposY_position = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private Ourboxes map;


    public Gameplay() {
        map = new Ourboxes(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 590, 30);


        // borders
        g.setColor(Color.pink);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX_position, 550, 100, 8);

        // the ball
        g.setColor(Color.pink);
        g.fillOval(ballposX_position, ballposY_position, 20, 20);

        if(ballposY_position>570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Entre to Restart", 230, 350);
        }

        if(totalBricks <= 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You won, Scores: "+score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);


        }

        // drawing the bricks
        map.draw((Graphics2D) g);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            // Ball movement
            ballposX_position += ballXdir;
            ballposY_position += ballYdir;

            // Ball collision with left and right walls
            if (ballposX_position < 0 || ballposX_position > 670) {
                ballXdir = -ballXdir;
            }

            // Ball collision with top wall
            if (ballposY_position < 0) {
                ballYdir = -ballYdir;
            }

            for(int i = 0;i<map.map.length;i++ ){
                for(int j = 0;j<map.map[0].length;j++ ){
                    if(map.map[i][j] > 0){
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i*map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX_position, ballposY_position, 20, 20);

                        if(ballRect.intersects(rect)){
                            map.setBrickvalue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballposX_position + 19 <= rect.x || ballposX_position + 1 >= rect.x + rect.width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                        }
                    }
                }

            }

            // Ball collision with paddle to bounce back and not get cooked ;)

            if (new Rectangle(ballposX_position, ballposY_position, 20, 20)
                    .intersects(new Rectangle(playerX_position, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            // Ball collision with bottom (game over / you are cooked)
            if (ballposY_position > 570) {
                play = false;
                ballXdir = 0;
                ballYdir = 0;

            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX_position >= 600) {
                playerX_position = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX_position < 10) {
                playerX_position = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX_position = 120;
                ballposY_position = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX_position = 310;
                score = 0;
                totalBricks = 21;
                map = new Ourboxes(3, 7);

                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void moveLeft() {
        play = true;
        playerX_position -= 20;
    }

    private void moveRight() {
        play = true;
        playerX_position += 20;
    }
}