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
       
    public int haha = 0;
    
    public int width;
    public int height;
    
    public Spritesheet sheet;

    public Screen(int width,int height , Spritesheet sheet){
        this.width = width;
        this.height = height;
        this.sheet = sheet;

        for(int i = 0;i<MAP_WIDTH * MAP_WIDTH;i++){     //i {0~4095} 64*64
            //tiles set 
            colours[i*4 + 0] = 0xff00ff;//pink first colour of first pixel 左上角 colour[0*4 + 0] = pink 0,4,8,12
            colours[i*4 + 1] = 0x00ffff;//blue                                                           1,5,9,13
            colours[i*4 + 2] = 0xffff00;//yello                                                          2,6,10,14
            colours[i*4 + 3] = 0xffffff;//black                                                          3,7,11,15
        }
    }
public void render(int[] pixels ,int offset ,int row){
        for(int yTile = yOffset>>3  ;  yTile <=(yOffset + height)>>3  ;  yTile++){//print ytile bit :-)
            //0100 << 3 0100000 2^3 -> 2^6  1 >>3 = 0  1100 12 >>3 = 1
            int yMin = yTile * 8 - yOffset;//Y這裡是影響那個畫面的上下scroll x當然便是左右
            int yMax = yMin + 8;
            if(yMin < 0)yMin = 0;//bounds set
            if(yMax > height)yMax = height;//bounds set height 120

        for(int xTile = xOffset>>3  ;  xTile <=(xOffset + width)>>3  ;  xTile++){//1000 >>3 = 1
            int xMin = xTile * 8 - xOffset;
            int xMax = xMin + 8;
            if(xMin < 0)xMin = 0;//bounds set
            if(xMax > width)xMax = width;//bounds set

            int tileindex = (xTile & (MAP_WIDTH_MASK))+(yTile & (MAP_WIDTH_MASK)) *MAP_WIDTH;  //sec loop 111111& 1  = 1
            //first 64*0~19 loop xTile{0~20} yTile = 0 
            //sec 64*64~147 loop xTile{0~20} yTile = 1
            int cry = (xTile & (MAP_WIDTH_MASK));
            System.out.println("tileindex:"+tileindex+"cry"+cry+"xTile:"+xTile+"yTile:"+yTile);
            //tileindex = xTile 0到7時為0 8到16為1  MAP_WIDTH為63 0到7時為 0&63(111111) =0 tileindex first 8 loop is 0+0*64=0 sec 8 loop 1+1*64 =128
            //1&1=1 , 1&0=0 , 0&1=0 , 0&0=0 ->e.g 3&5 011&101 3&5 = 001 = 1

            for(int y = yMin; y<yMax ; y++){ // first for loop yMax=8 sec loop yMax = 16 because of ytile turn to 8>>3 = 1 yMin = 1*8 = 8 yMax = 8+8 = 16
                int sheetPixel = ((y+yOffset)&7)*sheet.width +((xMin+xOffset)&7);//first 8 loop y=0 x={0~7} 0+0&7 = 0 0*160+0
                int tilePixel = offset + xMin + y*row; //first time 0+xMin 0 + 0*160 = 0 sec time 0+(0*8-0)+ 1*160 nine time 0 +(0*8-0)+8*160
                //xMin at 9 times loop over 160 the width of the screen
                //tilepixel st = 0 nd = 160 rd = 320 
                //System.out.println("tilepixels"+tilePixel+"Xmin:"+xMin+"y:"+y+"row:"+row+"yMin:"+yMin+"yTile:"+yTile+"yMax"+yMax);
                for(int x = xMin; x<xMax;x++){ //first 8loop xMin = 0 xMax = 8
                    int colour = tileindex *4 +sheet.pixels[sheetPixel++]; //first 8 loop 0 *4+ pixels[0~7] = 3 //black
                    //sec 8 loop y=1 0+{161~168} +1*160
                    //0*4 +sheet.pixels{161~168} 0*4+3 = 3 0*4+2=2做出一個box
                    
                    //sec 8 loop 64*4+
                    //這樣會跟據到spritesheet中的素材  
                    //是原本sprite畫了的黑色 , 但可以是白色 只要我們上面colours[i*4+3]=0xffffff
                    pixels[tilePixel++] = colours[colour]; //colors[colour]  colors[15] = black 上面的設定
                    //System.out.println("tileindex:"+tileindex);
                    //if(tileindex == 1){
                    //   haha++;
                    //System.out.println(haha);
                    //haha = 64   所以說是 8loop x 8loop y 8*8=64 colour = 0*4+3=3 使黑色為底色 1*4+3 =7
                     
                    }
                    //原本sprite畫了的黑色 加進 pixels[tilepixel++] = colours[colour] 
                }
                //所以x 會loop 8 次 y loop 8次 
                //pixel[0~7] = 某一種set 好的colour xloop
                //pixel[0,160,320,480,640,800,960,1120] y loop 
                //所以我們會建了一個 方格出來
                //colours[i*4 + 0] = 0xff00ff;//pink first colour of first pixel 左上角 colour[0*4 + 0] = pink 0,4,8,12
                //colours[i*4 + 1] = 0x00ffff;//blue                                                           1,5,9,13
                //colours[i*4 + 2] = 0xffff00;//yello                                                          2,6,10,14
                //colours[i*4 + 3] = 0xffffff;//black                                                          3,7,11,15
                
               
            }

        }
    }
}