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
    img = ImageIO.read(Spritesheet.class.getResourceAsStream(path));//read default package中的resource 這個是需要用到 java的libary
    }catch(IOException e){
        e.printStackTrace();
    }

    if(img == null){
        return;
    }

    this.path = path;
    this.width = img.getWidth();//定義
    this.height = img.getHeight();//定義

    pixels = img.getRGB(0, 0, width, height, null,0,height);
    //  public int[] getRGB(int arg0, int arg1, int arg2, int arg3, int[] arg4, int arg5, int arg6) {
    //  return null;
    //}

    for(int i =0 ; i <pixels.length;i++){
        pixels[i] = (pixels[i] & 0xff)/64;//0xffRRGGBB  0xff為default 顏色
        }
    for(int i = 0; i<170 ;i++){//這個是測試用的 170便是 0到170(第一行的pixels colour 讀出來)
        System.out.println(pixels[i]);
    }
}

}
//255/3*0 = 0 BLACK 他是 RR = 0 GG = 0 BB = 0的感覺
//255/3*1 = 85 Grey
//255/3*2 = 170 light Grey


//這個範例不會跑到因為imageio 是好像需要java lib 中的RunTimeEnv 才能讀到Spritesheet.png
//應該說才跑到這一Spritesheet.class.getResourceAsStream(path) 句