on screen.java 
we type 
//public int[] colours = new int[MAP_WIDTH*MAP_WIDTH*4];
is make us only can print few colour in one box

so we have to fix it
to

Game.java
private int[] colours = new int[6 * 6 * 6];


public void init(){
    int index = 0;
    for(int r = 0;r<6;r++){
        for(int g = 0;g<6;g++){
            for(int b = 0;b<6;b++){
                int red = (r*255/5);//5種紅
                int green = (g*255/5);//5種綠
                int blue = (b*255/5);//5種藍
                
                colours[index++]=red<<16 | green <<8 | blue; //2to8 data for blue ,red, green
            }
        }
    }



build up Colours class

public static int get(int col1,int col2,int col3,int col4){
        //col1 to save black
        //col2 to save asian
        //col3 to save japan
        //col4 to save white
        return (get(col4) << 24) + (get(col3) << 16) + (get(col2) << 8)+ get(col1);
        //return long number
        }


這可以使我們在Game.java 中
Colour.get(500,505,000,555)
那便會是 紅,紫,黑,白
R255G0B0 RED

    private static int get(int col){
        if(col <0)return 255;//prevent Game.java Colour.get(-1)<0 的
        int r = col/100%10; //r.g.b 
        int g = col/10%10;
        int b = col%10;
        return r*36+g*6+b;
    }
}

