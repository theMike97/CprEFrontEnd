/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.awt.Color;
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
//    private final double scale = 1.0;
    private Robot r;
    
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
         * @param distance distance to the center of the obstacle from the robot sensor
         * @param angleThroughCenter angle through the center of the obstacle to the robot sensor
         * @param diameter diameter of the obstacle
         */
        public Obstacle(double distance, double angleThroughCenter, double diameter) {
            if (diameter < 9) {
                this.diameter = 6;
            } else {
                this.diameter = 12;
            }
            x = 0 + (int) (distance * Math.cos(Math.toRadians(angleThroughCenter)));
            y = 0 - (Robot.DIAMETER / 2) - (int)(diameter / 2) - (int) (distance * Math.sin(Math.toRadians(angleThroughCenter)));
            dist = (int) distance;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Obstacle)) {
                return false;
            }
            Obstacle obs = (Obstacle) o;
            return obs.diameter == this.diameter && obs.dist == this.dist && obs.x == this.x && obs.y == this.y;
        }
        
    }
    
    private static class Line {
        
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        
        /**
         * Instantiates a line from two points (x1,y1),(x2,y2)
         * 
         * @param x1 x-coordinate of first point
         * @param y1 y-coordinate of first point
         * @param x2 x-coordinate of second point
         * @param y2 y-coordinate of second point
         */
        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Line)) {
                return false;
            }
            Line line = (Line) o;
            return line.x1 == this.x1 && line.x2 == this.x2 && line.y1 == this.y1 && line.y2 == this.y2;
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
        g.setColor(Color.black);
        
        // loop through lines and obstacles and paint
        for (Line line : lines) {
//            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
        
        for (Obstacle obstacle : obstacles) {
            int[] adjusted = getAdjustedObstacleCoords(obstacle, r.getPosition()[0], r.getPosition()[1]);
            g.drawOval(getOvalCoords(adjusted[0], adjusted[1], obstacle.diameter)[0], getOvalCoords(adjusted[0], adjusted[1], obstacle.diameter)[1], obstacle.diameter, obstacle.diameter);
        }
        
        g.setColor(Color.green);
        // draw robot
        g.drawOval(
                getOvalCoords(r.getPosition()[0], r.getPosition()[1], Robot.DIAMETER)[0], 
                getOvalCoords(r.getPosition()[0], r.getPosition()[1], Robot.DIAMETER)[1], 
                Robot.DIAMETER, 
                Robot.DIAMETER
        );
    }
    
}
