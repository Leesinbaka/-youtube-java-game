/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pfx;

/**
 *
 * @author Him
 */
public class Font {
    private static String chars ="ABCDEFGHIJKLMNOPQRSTUVWXYZ      "+"1234567890:                      ";
    
    public static void render(String msg , Screen screen,int x,int y,int colour){
    msg = msg.toUpperCase();
    
    for(int i = 0;i<msg.length();i++){
    int charIndex = chars.indexOf(msg.charAt(i));
    if(charIndex>=0)screen.render(x+(i*8),y,charIndex+30*32,colour);
    }
    //30*32是他們的最後2行
    //charindex 去找到是那一個英文字
    }
    
}
