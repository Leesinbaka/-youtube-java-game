package iwannamakeagame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.*;
import java.io.File;

public class Spritesheet{
    public String path;
    public int width;//width spritesheet
    public int height; //heigth spritesheet
    public int[] pixels;//same like Game.java中的pixels


public Spritesheet(String path){
    File f = new file(path);
    BufferedImage img = null;//不設會卡住
    try{
    img = ImageIO.read(f);
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
        pixels[i] = (pixels[i] & 0xff)/64;//0xffRRGGBB  0xff為default 顏色
        }
    for(int i = 0; i<8 ;i++){
        System.out.println(pixels[i]);
    }
}

}
//255/3*0 = 0 BLACK
//255/3*1 = 85 Grey
//255/3*2 = 170 light Grey