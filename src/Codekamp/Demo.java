package Codekamp;

import javafx.scene.media.AudioClip;

import javax.imageio.ImageIO;
//import java.applet.AudioClip;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Demo implements KeyListener, MouseListener {

    public static int vel = 2, acc = 0;
    public static int a = 0, x = 15, y = 310;
    public static boolean gameOver = false,paused=false;
    public static Graphics g;
    public static Image ground, log, bg_combined, grass, branch, fork,pause,start,apple,berries;
    public static Image pappu[] = new Image[8];
    private static java.applet.AudioClip hitAudio;

    public static void main(String[] args) {

        JFrame f = new JFrame();
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1000, 500));
        f.add(p);
        f.pack();
        f.setVisible(true);

        p.setFocusable(true);
        p.requestFocus();

        g = p.getGraphics();

        Demo.hitAudio = Applet.newAudioClip(Demo.class.getClassLoader().getResource("Codekamp/audios/hit.wav"));

        try {
            apple = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/apple.png"));
            berries = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/berries.png"));
            pause = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pause.png"));
            start = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/start.png"));
            fork = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/fork_handle.png"));
            branch = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/branch.png"));
            grass = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/grass.png"));
            bg_combined = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/bg_combined.png"));
            log = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/log.png"));
            ground = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/ground.png"));
            pappu[0] = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pappu1.png"));
            pappu[1] = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pappu2.png"));
            pappu[2] = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pappu3.png"));
            pappu[3] = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pappu4.png"));
            pappu[4] = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pappu5.png"));
            pappu[5] = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pappu6.png"));
            pappu[6] = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pappu7.png"));
            pappu[7] = ImageIO.read(Demo.class.getClassLoader().getResource("Codekamp/img/pappu8.png"));

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        g.drawImage(bg_combined, 0, 0, null);
        g.drawImage(ground, 0, 0, null);
        g.drawImage(log, 10, 350, null);
        g.drawImage(grass, -23, 0, null);
        g.drawImage(pappu[0], x, y, null);

        Demo d1 = new Demo();
        p.addKeyListener(d1);
        p.addMouseListener(d1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        start();
    }

    public static void GameOver() {
        g.clearRect(0, 0, 1000, 500);
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Game Over. Press any key to continue", 200, 100);
        gameOver = true;
    }

    public static boolean areColliding(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        boolean horizontalOverlap = (x1 > x2 && x1 < x2 + w2) || (x2 > x1 && x2 < x1 + w1);
        boolean verticalOverlap = (y1 > y2 && y1 < y2 + h2) || (y2 > y1 && y2 < y1 + h1);

        return horizontalOverlap && verticalOverlap;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        a = 1;
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            x = 35;
            vel = -30;
            acc = 4;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            Demo.paused = !Demo.paused;
            if(paused)
            {

                g.drawImage(pause,900,50,null);
            }
            else
            {
                g.drawImage(start,900,50,null);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void start() {
        int i = 0;
        int branch1X = 1100, branch2X = 1700, branch3X = 2300, fork1X = 1500, fork2X = 2100;
        gameOver = false;
        while (true) {
            //System.out.println("Hello");
            //if (a == 1)
            {
                //System.out.println("Hi");

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (Demo.paused) {
                    continue;
                }

                g.clearRect(0, 0, 800, 500);
                if (i == 8) {
                    i = 0;
                }

                branch1X -= 20;
                branch2X -= 20;
                branch3X -= 20;
                fork1X -= 20;
                fork2X -= 20;

                if (branch1X < -20) {
                    branch1X = 1080;
                }
                if (branch2X < -20) {
                    branch2X = 1080;
                }
                if (branch3X < -20) {
                    branch3X = 1080;
                }
                if (fork1X < -20) {
                    fork1X = 1280;
                }
                if (fork2X < -20) {
                    fork2X = 1280;
                }
                if (x <= 0 || y >= 500) {
                    GameOver();
                    break;
                }

                int w = branch.getWidth(null);
                int h = branch.getHeight(null);


                y += vel;
                vel += acc;


                if (areColliding(x, y, 59, 59, branch1X, 0, w, h / 3)) {
                    hitAudio.play();
                    System.out.println("h1");
                    GameOver();
                    break;
                }
                if (areColliding(x, y, 59, 59, branch2X, 0, w, h / 2)) {
                    hitAudio.play();
                    System.out.println("h2");
                    GameOver();
                    break;
                }
                if (areColliding(x, y, 59, 59, branch3X, 0, w, h / 3)) {
                    hitAudio.play();
                    System.out.println("h3");
                    GameOver();
                    break;
                }
                if (areColliding(x, y, 59, 59, fork1X, 330, 18, 170)) {
                    hitAudio.play();
                    System.out.println("h4");
                    GameOver();
                    break;
                }
                if (areColliding(x, y, 59, 59, fork2X, 330, 18, 170 )) {
                    hitAudio.play();
                    System.out.println("h5");
                    GameOver();
                    break;
                }
                g.drawImage(bg_combined, 0, 0, null);
                g.drawImage(ground, 0, 0, null);
                g.drawImage(log, 10, 350, null);
                g.drawImage(grass, -23, 0, null);
                g.drawImage(pappu[i++], x, y, null);
                g.drawImage(branch, branch1X, 0, w, h / 3, null);
                g.drawImage(branch, branch2X, 0, w, h / 2, null);
                g.drawImage(branch, branch3X, 0, w, h / 3, null);
                g.drawImage(branch, fork1X, 330, w, h, null);
                g.drawImage(branch, fork2X, 330, w, h, null);
                g.drawImage(start,900,50,null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > 900 && e.getX() < 950 && e.getY() > 50 && e.getY() < 100) {
            Demo.paused = !Demo.paused;
            if(paused)
            {
                g.drawImage(pause,900,50,null);
            }
            else
            {
                g.drawImage(start,900,50,null);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
