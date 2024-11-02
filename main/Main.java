package main;
import java.util.Scanner;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.print.attribute.standard.PrinterLocation;
import javax.swing.JFrame;

import Interface.Gamepanel;
import javax.swing.JPanel;


public class Main { 
     public static void main(String args[])  {
      JFrame window = new JFrame();
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setTitle("Flappy Bird");

      window.setResizable(false);
     
      Gamepanel game = new Gamepanel();
      window.add(game);
      window.pack();
      window.setLocationRelativeTo(null);
      window.setVisible(true);
      game.startGame();
    
     
     
} 


}             