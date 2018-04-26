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
    public final static int NORTH_EAST = 1;
    public final static int EAST = 2;
    public final static int SOUTH_EAST = 3;
    public final static int SOUTH = 4;
    public final static int SOUTH_WEST = 5;
    public final static int WEST = 6;
    public final static int NORTH_WEST = 7;
    
    private int[] pos;
    public static int DIAMETER = 34;
    private static int DIRECTION = NORTH;
    
    /**
     * Default constructor sets direction to north and position to (0,0)
     */
    public Robot() {
        pos = new int[2];
    }
    
    public void setPosition(int x, int y) {
        pos[0] = x;
        pos[1] = y;
    }
    
    public void setPosition(int[] pos) {
        this.pos[0] = pos[0];
        this.pos[1] = pos[1];
    }
    
    public int[] getPosition() {
//        System.out.println("new pos: " + Arrays.toString(pos));
        return pos;
    }
    
    public static void setDirection(int direction) {
        DIRECTION = direction;
    }
    
    public static int getDirection() {
        return DIRECTION;
    }
    
}
