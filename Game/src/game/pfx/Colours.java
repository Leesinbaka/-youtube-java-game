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
public class Colours{
    
    public static int get(int col1,int col2,int col3,int col4){
        //col1 to save black
        //col2 to save asian
        //col3 to save japan
        //col4 to save white
        return (get(col4)<<24) + (get(col3)<<16) + (get(col2) << 8)+ get(col1);
        //255.255.255 8進位
        //return long number
    
    }
    private static int get(int colour){
        if(colour <0){
            return 255;
        }//防止 Game.java Colour.get(-1)<0 的
        int r = colour/100 % 10; //r.g.b 
        int g = colour/10 % 10;
        int b = colour % 10;
        return r*36 + g*6 + b;
    }
}
