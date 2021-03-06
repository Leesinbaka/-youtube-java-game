package iwannamakeagame;//先建立folder 包起來

import java.awt.Canvas;//畫布元素是HTML5的一部分，允許手稿語言動態彩現點陣圖像。
import java.awt.Color;

import javax.swing.JFrame;

import game.pfx.Spritesheet;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable{ //canvas 就好像一個空間可以工作 override jframe to run
    public static final long serialVersionUID = -2284879212465893870L;
    public boolean running  =  false;
    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH/12*9;
    public static final int SCALE = 3;
    public static final String NAME = "iwanna";
    private static final double UPDATE_CAP=1D/60D;
    public int tickCount = 0;

    private JFrame frame;

    private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);//加東西進 Canvas 因為Canvas 是空的
    private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();//紀錄多少pixels在那個img裡面
    //getRaster() 取得像素值


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
        double lasttime=System.nanoTime()/1000000000D;//nanosec --> sec這樣:O
        double passedtime;
        double unprocessedtime=0;
        double x = 0;

        while(running){
            firsttime = System.nanoTime()/1000000000D;//nanosec --> sec這樣:O
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


        for (int i = 0;i<pixels.length;i++){
            pixels[i] = i+tickCount;//從黑色加加加加到綠色
            //pixels 其實是指他在那一個位置 因為我們設定了160 x 120 的size
            //所以 pixel[0]為左上角的一個位置 , pixel[170]為第二行的位置
            //因為 pixel跟據gameloop 改變顏色
            //因為 pixel
            //pixels[i] = 255*255*255; 會是一種紫色 
            //pixels[i] = 0;會是黑色 
            //pixels[i] = 255;會是黑色 
            //pixels[i] = 16777215 -> 0xffffff
            //-8454017 purple
            //-16777216 black
            //System.out.println("pixels["+"i:"+i+"]"+pixels[i]);
            //pixels[i:0]=12
            //pixels[i:1]=13
            //pixels[i:2]=14
            //System.out.println(pixels.length);
            //18720 = 19200 = 160*120 480 20 160*3 120*3 = 360 11
            //74880 = 76800 = 320*240 1920
        }

    }
    public void render(){//動態用
        BufferStrategy bus = getBufferStrategy();
        if(bus == null){
            createBufferStrategy(3);
           return;
        }
        Graphics g = bus.getDrawGraphics();
        //g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        System.out.println("W:"+getWidth()+"H:"+getHeight());
        //g.dispose();//
        bus.show();//
    }
public static void main(String[] args){

    new Game().start();
}

}
//tick()我loop 是比render loop 慢的 所以running tick()中的pixel[i] 再去跑 render 中的graphics g
