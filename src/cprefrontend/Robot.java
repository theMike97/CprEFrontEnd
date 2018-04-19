/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

/**
 *
 * @author Mike
 */
public class Robot {
    
    public final static int NORTH = 0;
    public final static int EAST = 1;
    public final static int SOUTH = 2;
    public final static int WEST = 3;
    
    private int direction;
    private int[] pos;
    public static int DIAMETER = 34;
    
    /**
     * Default constructor sets direction to north and position to (0,0)
     */
    public Robot() {
        direction = 0; // north
        pos = new int[2];
    }
    
    public void setPosition(int x, int y) {
        pos[0] = x;
        pos[1] = y;
    }
    
    public void setPosition(int[] pos) {
        this.pos = pos;
    }
    
    public int[] getPosition() {
        return pos;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public int getDirection() {
        return direction;
    }
    
}
