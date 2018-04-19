/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

/**
 *
 * @author Mike
 */
public class Map extends JPanel {
    
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Line> lines;
    private final double scale = 1.0;
    Robot r;
    
    public Map() {
        super();
        obstacles = new ArrayList<>();
        lines = new ArrayList<>();
        r = new Robot();
    }
    
    private static class Obstacle {
        
        final int x;
        final int y;
        final int diameter;
        final int dist;
        
        /**
         * Instantiates new obstacle relative to Robot where distance and diameter
         * are measured in cm and angleThroughCenter is measured in radians.
         * 
         * @param distance
         * @param angleThroughCenter
         * @param diameter 
         */
        public Obstacle(double distance, double angleThroughCenter, double diameter) {
            this.diameter = (int) (diameter);
            x = (int) (distance * Math.cos(angleThroughCenter));
            y = (int) (distance * Math.sin(angleThroughCenter));
            dist = (int) distance;
        }
        
    }
    
    private static class Line {
        
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        
        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        
    }
    
    public void addObstacle(double distance, double angleThroughCenter, double diameter) {
        obstacles.add(new Obstacle(distance, angleThroughCenter, diameter));
        repaint();
    }
    
    public void addLine(int x1, int y1, int x2, int y2) {
        lines.add(new Line(x1, y1, x2, y2));
        repaint();
    }
    
    public void moveRobot(int x, int y) { // moves origin since robot is always at origin
        r.setPosition(x, y);
        repaint();
    }
    
    private int[] getAdjustedObstacleCoords(Obstacle o, int robotx, int roboty) {
        int[] adjustedCoords = new int[2];
        adjustedCoords[0] = o.x + robotx;
        adjustedCoords[1] = o.y + roboty;
        return adjustedCoords;
    }
    
    private int[] getOvalCoords(int x, int y, double diameter) {
        int[] ovalCoords = new int[2];
        ovalCoords[0] = (int)(x - (diameter / 2));
        ovalCoords[1] = (int)(y - (diameter / 2));
        return ovalCoords;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // loop through lines and obstacles and paint
        for (Line line : lines) {
//            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
        
        for (Obstacle obstacle : obstacles) {
            int[] adjusted = getAdjustedObstacleCoords(obstacle, r.getPosition()[0], r.getPosition()[1]);
            g.drawOval(getOvalCoords(adjusted[0], adjusted[1], obstacle.diameter)[0], getOvalCoords(adjusted[0], adjusted[1], obstacle.diameter)[1], obstacle.diameter, obstacle.diameter);
        }
        
        // draw robot
        g.drawOval(
                getOvalCoords(r.getPosition()[0], r.getPosition()[1], r.getDiameter())[0], 
                getOvalCoords(r.getPosition()[0], r.getPosition()[1], r.getDiameter())[1], 
                r.getDiameter(), 
                r.getDiameter()
        );
    }
    
}
