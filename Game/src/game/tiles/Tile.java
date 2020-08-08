/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.tiles;

import game.level.Level;
import game.pfx.Colours;
import game.pfx.Screen;

/**
 *
 * @author Him
 */
public abstract class Tile{
    
    public static final Tile[] tiles = new Tile[256];
    public static final Tile VOID = new BasicTile(0,0,0,Colours.get(000,-1,-1,-1));
    public static final Tile STONE = new BasicTile(1,1,0,Colours.get(-1,333,-1,-1));
    public static final Tile GRASS= new BasicTile(2,2,0,Colours.get(-1, 131, 141, -1));
    
    protected byte id;
    protected boolean soild;
    protected boolean emlitter;
    public Tile(int id,boolean isSoild,boolean isEmlitter){
    this.id = (byte)id;
        if(tiles[id]!=null)throw new RuntimeException("Duplicate tile id on "+id);
        this.soild = isSoild;
        this.emlitter = isEmlitter;
        tiles[id] = this;
    }
    public byte getid(){
    return id;
    }
    public boolean isSoild(){
    return soild;
    }
    public boolean isEmlitter(){
    return emlitter;
    }
    
    public abstract void render(Screen screen,Level level,int x,int y);
    
    
}
