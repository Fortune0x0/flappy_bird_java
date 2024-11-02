package Interface;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

import javax.imageio.ImageIO;

public class Bird {
    BufferedImage birdImage;
    BufferedImage backgroundImage;
    BufferedImage topPipeImage;
    BufferedImage bottomPipeImage;
    boolean gameOver = false;
    int pos = 300;
    int gravity = -6;
    Font font = new Font("Arial", Font.PLAIN, 32);
    Gamepanel gp;

    public Bird(String backgroundDir, String birdDir,  String topPipeImageDir, String bottomPipeImageDir,  Gamepanel gp){
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(backgroundDir));
            birdImage = ImageIO.read(getClass().getResourceAsStream(birdDir));
            topPipeImage = ImageIO.read(getClass().getResourceAsStream(topPipeImageDir));
            bottomPipeImage = ImageIO.read(getClass().getResourceAsStream(bottomPipeImageDir));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.gp = gp;

    }
    
    public void update(){
        pos= Math.max(pos+=gravity, 0);
        if(gp.keyP.up) gravity = -9;
        else  gravity += 1;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(backgroundImage, 0, 0, gp.boardWidth, gp.boardHeight, null);

        g2.drawImage(birdImage, 0, pos, gp.tileSize, gp.tileSize, null);

        g2.setColor(Color.BLACK);
       
    }

    public class Pipe{
        int x = 360;
        int y = 0;
        int width = 64;
        int height = 512;
        int velocityX = -3;
        int spacing = height/2;
        int score = 0;
        ArrayList<Pipe> pipes = new ArrayList<>();
        BufferedImage pipeImage;
       
        Pipe(){

        }

        Pipe(BufferedImage pipeImage){
            this.pipeImage = pipeImage;
            
        }
        public void createPipes(){
            Pipe topPipe = new Pipe(topPipeImage);
            topPipe.y =(int) (-height/4 - Math.random() *(height/2));
            pipes.add(topPipe);
            

            Pipe bottomPipe = new Pipe(bottomPipeImage);
            bottomPipe.y = topPipe.y + height + spacing;
            pipes.add(bottomPipe);
        }

        public void restartGame(){
            gp.gameThread = new Thread(gp);
            pos = 300;
            gravity = -6;
            pipes.clear();

            score = 0;
            gameOver = false;
            gp.startGame();
        }
    
        public void updatePipes(){
            for(Pipe pipe: pipes){
                pipe.x+= velocityX;
            }

           
        }
        public boolean collision(Bird bird, Pipe pipe){
            boolean isTopPipe = (pipe.y < 0) ? true : false;
              //Top Pipe collision
           return pipe.x + pipe.width > 0 &&//Means are still on screen; Don't account for pipes off screen cuz it could triger collison 
                 gp.tileSize > pipe.x && //The bird right most passes the pipe left most so there is a collision possibility 
                isTopPipe && pos < pipe.y + height ||

           //Bottom Pipe collision
               pipe.x + pipe.width > 0 && 
                 gp.tileSize > pipe.x && 
                !isTopPipe && pos + gp.tileSize > pipe.y;   
           


        }
       
        public void drawPipes(Graphics2D g2){
           
           boolean hasPassed = false;
           g2.setColor(Color.white);
           g2.setFont(font);

            for(Pipe pipe : pipes){

                g2.drawImage(pipe.pipeImage, pipe.x, pipe.y, width, height, null);
                if(collision(Bird.this, pipe)) gameOver = true;
                if(!hasPassed && pipe.x + (pipe.width / -velocityX) * -velocityX == 0){
                    ++score;
                    hasPassed = true;    
                }
                
               
            }
            

            if(pos > gp.boardHeight) gameOver = true;
           if(gameOver){
                g2.drawString("Game Over: " + String.valueOf(score), 0, 35);
           }
           else{
            
            g2.drawString(String.valueOf(score), 0, 35);
           }
           

        }

    }
}
