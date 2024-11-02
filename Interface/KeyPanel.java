package Interface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Interface.Bird.Pipe;

public class KeyPanel implements KeyListener {
    public boolean up = false;
    Gamepanel gp;
    Pipe pipe;
    public KeyPanel(Gamepanel gp, Pipe pipe){
        this.gp = gp;
        this.pipe = pipe;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_SPACE) up = true;
        if(!gp.gameThread.isAlive() && up) {
            pipe.restartGame();
        }
       
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_SPACE) up = false;
        
    }

    
    
}
