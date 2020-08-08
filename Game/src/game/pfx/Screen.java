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
    
    public int xOffset = 0;
    public int yOffset = 0;
    
    public int[] pixels;
    
    public int width;
    public int height;
    
    public Spritesheet sheet;

    public Screen(int width,int height , Spritesheet sheet){
        this.width = width;
        this.height = height;
        this.sheet = sheet;
        
        pixels = new int[width * height];//正常pixels 打法  
    }
    public void render(int xPos,int yPos,int tile,int colour){
    render(xPos,yPos,tile,colour,false,false);
    }
public void render(int xPos,int yPos,int tile,int colour,boolean mirrorX,boolean mirrorY){//tile is reference to 256.png 8*8為一格 0~32 width 0~32 height int tile is to set every tile to indiv
//那x 軸便是 0~32格
//            0 1 2 3 4 5
//            0 1 2 3 4 5
//            0 1 2 3 4 5

//Y 軸便是/32 0 0 0 0 0 0
//            1 1 1 1 1 1
//            2 2 2 2 2 2
 xPos -= xOffset;
 yPos -= yOffset;
 
 int xTile = tile %32; // 32=0 64=0
 int yTile = tile /32;//  32=1 64=2  (x,y) (0,1)(0,2)這樣
 int tilesOffset = (xTile << 3) + (yTile << 3) * sheet.width;  //11111 <<3 111 =7+1*160
         //<<3 是因為我們每一格是 8*8 0000 -> 1000 8
 for(int y = 0;y<8;y++){
     if(y+yPos <0 || y+yPos >=height)continue;//step 2 如果y+yPos <0 or y+yPos >=height 就無限增大的意思 set bounds
     int ySheet = y;//step 1
     if(mirrorY)ySheet = 7-y;//filp it XD
     for(int x = 0;x<8;x++){
         if(x+xPos <0 || x+xPos >=width)continue;//step 2 
         int xSheet = x;//step 1
         if(mirrorX)xSheet = 7-x;//filp it XD
         int col = (colour >> (sheet.pixels[xSheet + ySheet * sheet.width + tilesOffset]*8))&255; //will repos 012345 & 255 return to game.java init()
         if(col <255){//prevent invisible colour
             pixels[(x+xPos)+(y+yPos)*width] = col;
         }//then go change Game.java render method
     
     }
 }
}

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
//To change body of generated methods, choose Tools | Templates.
    }
}