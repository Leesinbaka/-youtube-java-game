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
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spritesheet{
    public String path;
    public int width;//width spritesheet
    public int height; //heigth spritesheet
    public int[] pixels;//same like Game.java中的pixels


public Spritesheet(String path){
    BufferedImage img = null;//不設會卡住
    try{
    img = ImageIO.read(Spritesheet.class.getResourceAsStream(path));
    }catch(IOException e){
        e.printStackTrace();
    }

    if(img == null){
        return;
    }

    this.path = path;
    this.width = img.getWidth();
    this.height = img.getHeight();

    pixels = img.getRGB(0, 0, width, height, null,0,width);

    for(int i =0 ; i <pixels.length;i++){
        pixels[i] = (pixels[i] & 0xff)/64;//0xffRRGGBB  0xff為default 顏色  example white 0xffffff &0xff = 0xff 0xff/64=3.98437
        //example pink 0x7f007f &0xff = 127 127/64 = 1.984375  main pink 0xff00ff 0xff/64 = 3
        }
    for(int i = 0; i<400 ;i++){//讀取是什麼顏色
        System.out.println(pixels[i]); 
    }
}

}
//255/3*0 = 0 BLACK 他是 RR = 0 GG = 0 BB = 0的感覺
//255/3*1 = 85 Grey
//255/3*2 = 170 light Grey