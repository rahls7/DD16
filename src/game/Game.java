package game;

import display.Screen;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by rahls7 on 05/02/17.
 */
public class Game extends Canvas implements Runnable{

    //resolution
    public static int width = 300;
    public static int height = width / 16 * 9;
    public static int scale = 3;

    private Thread thread;
    private JFrame frame;

    private Screen screen;

    private BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); // Converting image object into array of pixels.

    private boolean running = false;

    public Game() {
        Dimension size = new Dimension(width *scale, height*scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        frame = new JFrame();
    }

    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0 ;
        double delta = 0;
        int frames =0;
        int updates = 0;
        //Game Loop
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta>=1) {
                update();
                updates++; // Times update method is called.

                delta--;
            }
            render();
            frames++;
        }

    }

    public void update() {

    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs==null) {
            createBufferStrategy(3); // Multi-buffering
            return;
        }
        screen.clear();
        screen.render();

        for(int i =0; i < pixels.length;i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image,0,0,getWidth(),getHeight(),null);
        g.dispose();

        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false); // No resizeable functionality to avoid Graphics error
        game.frame.setTitle("Dragon and Dungeons");
        game.frame.add(game);
        game.frame.pack(); // Same as window
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }


    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try{
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}