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
public class Screen{
    public static final int MAP_WIDTH = 64;
    public static final int MAP_WIDTH_MASK = MAP_WIDTH-1;

    public int[] tiles = new int[MAP_WIDTH *MAP_WIDTH_MASK];//瓷磚
    public int[] colours = new int[MAP_WIDTH*MAP_WIDTH*4];//information references to tiles 知道sprite sheet 顏色

    public int xOffset = 0;
    public int yOffset = 0;

    public int width;
    public int height;

    public Spritesheet sheet;

    public Screen(int width,int height , Spritesheet sheet){
        this.width = width;
        this.height = height;
        this.sheet = sheet;

        for(int i = 0;i<MAP_WIDTH * MAP_WIDTH;i++){     //i {0~4095} 64*64
            //tiles set 
            colours[i*4 + 0] = 0xff00ff;//pink first colour of first pixel 左上角 colour[0*4 + 0] = pink
            colours[i*4 + 1] = 0x00ffff;//blue
            colours[i*4 + 2] = 0xffff00;//yello
            colours[i*4 + 3] = 0xffffff;//black
        }
    }
public void render(int[] pixels ,int offset ,int row){
        for(int yTile = yOffset>>3  ;  yTile <=(yOffset + height)>>3  ;  yTile++){//print ytile bit :-)
            //0100 << 3 0100000 2^3 -> 2^6  1 >>3 = 0  1100 12 >>3 = 1
            int yMin = yTile * 8 - yOffset;//Y這裡是影響那個畫面的上下scroll x當然便是左右
            int yMax = yMin + 8;
            if(yMin < 0)yMin = 0;//bounds set
            if(yMax > height)yMax = height;//bounds set

        for(int xTile = xOffset>>3  ;  xTile <=(xOffset + width)>>3  ;  xTile++){
            int xMin = xTile * 8 - xOffset;
            int xMax = xMin + 8;
            if(xMin < 0)xMin = 0;//bounds set
            if(xMax > width)xMax = width;//bounds set

            int tileindex = (xTile & (MAP_WIDTH_MASK))+(yTile & (MAP_WIDTH_MASK)) *MAP_WIDTH;
            //1&1=1 , 1&0=0 , 0&1=0 , 0&0=0 ->e.g 3&5 011&101 3&5 = 001 = 1

            for(int y = yMin; y<yMax ; y++){
                int sheetPixel = ((y+yOffset)&7)*sheet.width +((xMin+xOffset)&7);
                int tilePixel = offset + xMin + y*row;
                for(int x = xMin; x<xMax;x++){
                    int colour = tileindex *4 +sheet.pixels[sheetPixel++]; 
                    //這樣會跟據到spritesheet中的素材  
                    //這樣雖然上面有寫到colours[i*4+0] = 0xff00ff;//pink但其實還是原本sprite畫了的黑色
                    pixels[tilePixel++] = colours[colour];
                    //原本sprite畫了的黑色 加進 pixels[tilepixel++] = colours[colour] 
                }
            }

        }
    }
}
}