/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.tiles;

import game.level.Level;
import game.pfx.Screen;

/**
 *
 * @author Him
 */
public class BasicTile extends Tile{
    protected int tileid;
    protected int tileColour;
    public BasicTile(int id ,int x ,int y,int tileColour){
    super(id,false,false);
    this.tileid = x+y;//那一格
    this.tileColour = tileColour;
    }
    public void render(Screen screen,Level level,int x ,int y){
    screen.render(x,y,tileid,tileColour);
    }
    
}
