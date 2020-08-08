/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Him
 */
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputHandler implements KeyListener{
    public InputHandler(Game game){
        game.addKeyListener(this);//JFrame event 
    
    }
    
    
    public class Key{
    private int numTimesPressed = 0; 
    private boolean pressed =false;
    public boolean isPressed(){
    return pressed;//true
    }
    public int getNumTimesPressed(){
    return numTimesPressed;
    }
    public void toggle(boolean isPressed){
        pressed = isPressed;
        if(isPressed)numTimesPressed++;
    }
}
    public List<Key> keys = new ArrayList<Key>();
    
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
       
    public void keyPressed(KeyEvent e) {
       toggleKey(e.getKeyCode(),true);
    }
    public void keyReleased(KeyEvent e) {
       toggleKey(e.getKeyCode(),false);
    }
    public void keyTyped(KeyEvent e) {
        
    }
    public void toggleKey(int keyCode,boolean isPressed){
    if (keyCode == KeyEvent.VK_W) {
	up.toggle(isPressed);
    }
    if (keyCode == KeyEvent.VK_S) {
	down.toggle(isPressed);
    }
    if (keyCode == KeyEvent.VK_A) {
	left.toggle(isPressed);
    }
    if (keyCode == KeyEvent.VK_D) {
	right.toggle(isPressed);
    }
   }
    
    
}
