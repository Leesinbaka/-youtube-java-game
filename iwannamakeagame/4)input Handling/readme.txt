add inputHandler.java in package game

Game.java 
public InputHandler input;
input = new InputHandler(this);

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