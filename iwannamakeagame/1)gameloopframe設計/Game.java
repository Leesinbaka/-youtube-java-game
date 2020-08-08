package iwannamakeagame;//先建立folder 包起來

import java.awt.Canvas;//畫布元素是HTML5的一部分，允許手稿語言動態彩現點陣圖像。
import java.awt.Color;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    private static final double lock=1D/60D;
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
        long timer = System.currentTimeMillis();
        while(running){
            firsttime = System.nanoTime()/1000000000D;//nanosec --> sec這樣:O
            passedtime = firsttime - lasttime;
            lasttime = firsttime;
            unprocessedtime +=passedtime;
            boolean rendernow = true;//開始時false


            while(unprocessedtime >= lock){
                tick();
                ticks++;
                unprocessedtime -= lock;
                //update game 的確保 已update cap 多一點點為上限 又不會超過updatecap *2
                rendernow = true;
                System.out.println(pixels[0]);
            }
            if(System.currentTimeMillis() - timer >= 1000){//計算每一秒的更新量
                timer+=1000;
                System.out.println("ticks"+ticks+",frames"+frames);
                ticks=0;
                frames=0;
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
        //here just for testing loop 不會用這個做事情
        for (int i = 0;i<pixels.length;i++){//first line 0到319
            pixels[i] = i+tickCount;
            //但是他不會變成全白 因為當他們加到0 的時候會重來
            //pixels會是一個負數
            //從黑色加加加加到綠色
            //pixels 其實是指他在那一個位置 因為我們設定了160*3 x 120*3 的size
            //所以 pixel[0]為左上角的一個位置 , pixel[320]為第二行的位置
            //因為 pixel跟據gameloop 改變顏色
            //因為 pixel
            //pixels[i] = 0;會是黑色 
            //pixels[i] = -1 -> 0xffffff 白色
            //pixels[i:0]=12
            //pixels[i:1]=13
            //pixels[i:2]=14
            //System.out.println(pixels.length);
            //
        }

    }
    public void render(){//動態用
        BufferStrategy bus = getBufferStrategy();//顯示image 組織好他們放進canvas
        if(bus == null){
            createBufferStrategy(3);//reducing tearing 就是更新不想要 一更全部一起更新 閃閃閃閃的那種
           return;
        }
        
        Graphics g = bus.getDrawGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g.dispose();//組建刪除 顏色加到尾 再把他重組一次
        bus.show();//
    }
public static void main(String[] args){

    new Game().start();
}

}
//tick()我loop 是比render loop 慢的 所以running tick()中的pixel[i] 再去跑 render 中的graphics g
