package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Interface.Bird.Pipe;

public class Gamepanel extends JPanel implements Runnable{
    final int boardWidth = 360;
    final int boardHeight = 640;
    final int FPS = 60;
    final int secondsPerFrames = 1000000000/FPS;
    
   
    final int tileSize = 28;

   
    Thread gameThread =  new Thread(this);
    Bird bird = new Bird( "/Images/flappybirdbg.png", "/Images/flappybird.png", "/Images/toppipe.png", "/Images/bottompipe.png", this);
    Bird.Pipe pipe = bird.new Pipe();
    KeyPanel keyP = new KeyPanel(this, pipe);
    

    public Gamepanel(){
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));
        this.setDoubleBuffered(true);
        this.setBackground(Color.black);
        this.addKeyListener(keyP);
        this.setFocusable(true);
        
    }
    @Override 
    public void run(){
        double passedTime = 0;
        double pipeDrawTime = 0;
        long startTime = System.nanoTime();
        double timer = 0;
        int frames = 0;
        while(gameThread != null){
            long currentTime = System.nanoTime();
            passedTime += (currentTime - startTime);
            timer += (currentTime - startTime);
            pipeDrawTime +=(currentTime - startTime);

            startTime = currentTime;
           
            if(passedTime >= secondsPerFrames){
                update();
                repaint();
                if(bird.gameOver) break;
                ++frames;
                passedTime-= secondsPerFrames;

            }

            if(pipeDrawTime >= (1000000000 * 1.5)){
                pipe.createPipes();
                pipeDrawTime-= (1000000000 * 1.5);
                
               

            }  
            if(timer >= 1000000000){
                System.out.println("FPS: " + frames);
                frames = 0;
                timer = 0;
            }

        }


    }
   
    public void startGame(){
        gameThread.start();
    }

    public void update(){
        bird.update();
        pipe.updatePipes();
        
    }
    public void paintComponent(Graphics g){ 
        super.paintComponent(g);    
        Graphics2D g2 = (Graphics2D)g;

        bird.draw(g2);
        pipe.drawPipes(g2);
        
        g2.dispose();


    }
}
