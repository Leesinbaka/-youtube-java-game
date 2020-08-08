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

import game.pfx.Colours;
import game.pfx.Screen;
import game.pfx.Spritesheet;
import java.awt.Canvas;//畫布元素是HTML5的一部分，允許手稿語言動態彩現點陣圖像。

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable{ //canvas 就好像一個空間可以工作 override jframe to run
    public static final long serialVersionUID = 1L;
    public boolean running  =  false;
    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH/12*9;
    public static final int SCALE = 3;
    public static final String NAME = "iwanna";
    private static final double UPDATE_CAP=1D/60D;
    public int tickCount = 0;

    private JFrame frame;

    private BufferedImage img = new BufferedImage(WIDTH , HEIGHT , BufferedImage.TYPE_INT_RGB);//加東西進 Canvas 因為Canvas 是空的
    private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();//紀錄多少pixels在那個img裡面
    private int[] colours = new int[6*6*6];
    //getRaster() 取得像素值
    //private Spritesheet spritesheet = new Spritesheet("/spritesheet200x200.png");//因為想用multiple sprite所以不用static
    //after day 1 test we can close it right now
    
    

    private Screen screen;
    public InputHandler input;
    public Colours clo;

    public Game(){//set up Jframe
        setMinimumSize(new Dimension(WIDTH*SCALE , HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE , HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE , HEIGHT*SCALE));//這裡是固定size 用的

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this,BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);//固定在中間
        frame.setVisible(true);

    }
    public void init(){
    
    int index = 0;
    for(int r = 0;r<6;r++){
        for(int g = 0;g<6;g++){
            for(int b = 0;b<6;b++){
                int red = (r*255/5);//6種紅
                int green = (g*255/5);//6種綠
                int blue = (b*255/5);//6種藍
                
                colours[index++]=red<<16|green<<8|blue; //2to8 data for blue ,red, green
            }
        }
    }
    screen = new Screen(WIDTH ,HEIGHT,new Spritesheet("/spritesheet256x256.png"));
    input = new InputHandler(this);
    }

    public synchronized void start(){
    running = true;//開始run()中的loop
    new Thread(this).start(); //去跑run()中的東西 當前這一個function.start();
    }
    public synchronized void stop(){
    running = false;//關掉run()中的loop
    
    }


    public void run(){
        int ticks = 0;
        int frames= 0;
        double firsttime=0;
        double lasttime=System.nanoTime()/1000000000D;//nanosec --> sec我想是這樣:O
        double passedtime;
        double unprocessedtime=0;
        
        init(); //add this *************************************************************************************
    while(running){
            firsttime = System.nanoTime()/1000000000D;//nanosec --> sec我想是這樣:O
            passedtime = firsttime - lasttime;
            lasttime = firsttime;
            unprocessedtime +=passedtime;
            boolean rendernow = true;//開始時false


            while(unprocessedtime >= UPDATE_CAP){
                tick();
                ticks++;
                unprocessedtime -= UPDATE_CAP;
                //update game 的確保 已update cap 多一點點為上限 又不會超過updatecap *2
                rendernow = true;
                System.out.println("ticks"+ticks+",frames"+frames);
            }
            try{
            Thread.sleep(2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            if (rendernow){
            render();
            frames++;
            }
        }
 }

    public void tick(){//update the game
        tickCount++;
        if(input.up.isPressed()){
        screen.yOffset--;
        };
        if(input.down.isPressed()){
        screen.yOffset++;
        };
        if(input.left.isPressed()){
        screen.xOffset--;
        };
        if(input.right.isPressed()){
        screen.xOffset++;
        };
    }
    public void render(){//動態用
        BufferStrategy bus = getBufferStrategy();
        if(bus == null){
            createBufferStrategy(3);
            return;
        }
        for(int y =0;y<32;y++){
            for(int x = 0 ;x<32 ; x++){
            screen.render(x<<3,y<<3,0,clo.get(555,500,050,005));//make them to W R G B //0 first box
            }
        }
        for(int y = 0;y<screen.height;y++){
            for(int x = 0;x<screen.width;x++){
            int colourCode = screen.pixels[x+y *screen.width];
            if(colourCode < 255)pixels[x+y*WIDTH] = colours[colourCode];
            }
        }
        Graphics g = bus.getDrawGraphics();
        g.drawRect(0, 0, getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bus.show();
    }
public static void main(String[] args){

    new Game().start();
}

}
//tick()我loop 是比render loop 慢的 所以running tick()中的pixel[i] 再去跑 render 中的graphics g


