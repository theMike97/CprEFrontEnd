/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cprefrontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

/**
 *
 * @author Mike
 */
public class Map extends JPanel {

    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Cliff> cliffs;
    private final ArrayList<Line> lines;
    private final ArrayList<int[]> rOPositions;
    private final ArrayList<int[]> rCPositions;
    private final ArrayList<int[]> rLPositions;
    private final Robot r;
    private Point mousePt;
    private Point origin;

    /**
     * Map constructor initializes location ArrayLists for obstacles, cliffs, and lines
     * Sets up starting origin location for grid
     */
    public Map() {
        super();
        origin = new Point(0, 0);
        obstacles = new ArrayList<>();
        cliffs = new ArrayList<>();
        lines = new ArrayList<>();
        rOPositions = new ArrayList<>(); // for obstacles
        rCPositions = new ArrayList<>(); // for cliffs
        rLPositions = new ArrayList<>(); // for lines
        r = new Robot();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mousePt = evt.getPoint();
//                System.out.println(mousePt);
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                if (evt.getButton() == 0) {
                    int dx = evt.getX() - mousePt.x;
                    int dy = evt.getY() - mousePt.y;
                    origin.setLocation(origin.x + dx, origin.y + dy);
                    mousePt = evt.getPoint();
                    repaint();
                }
            }
        });
    }
    
    private class Cliff {
        
        private final int x;
        private final int y;
        private final int width;
        
        /**
         * Instantiates new cliff object to provide reference for cliff detection
         * 
         * @param x1 x coordinate of cliff with respect to robot
         * @param y1 y coordinate of cliff with respect to robot
         */
        public Cliff() {
            width = 10;
            
            int robotDirection = Robot.getDirection();
            double direction = ((Math.PI / 2) - robotDirection*(Math.PI / 4));
            
            this.x = (int) (((Robot.DIAMETER / 2) + (width / 2)) * Math.cos(direction));
            this.y = (int) (((Robot.DIAMETER / 2) + (width / 2)) * -Math.sin(direction));
//            System.out.println(Arrays.toString(getPosition()));
//            System.out.println(Arrays.toString(r.getPosition()));
        }
        
        /**
         * Returns x and y coordinates as int array
         * @return [x1, y1]
         */
        public int[] getPosition() {
            int[] pos = {x, y};
            return pos;
        }

    }

    private class Obstacle { // not static because robots direction can change

        int x;
        int y;
        final int diameter;
        int dist;

        /**
         * Instantiates new obstacle relative to Robot where distance and
         * diameter are measured in cm and angleThroughCenter is measured in
         * radians.
         *
         * @param distance distance to the center of the obstacle from the robot
         * sensor
         * @param angleThroughCenter angle through the center of the obstacle to
         * the robot sensor
         * @param diameter diameter of the obstacle
         */
        public Obstacle(double distance, double angleThroughCenter, double diameter) {
            if (diameter < 9) {
                this.diameter = 6;
            } else {
                this.diameter = 12;
            }
            x = 0;
            y = 0;
            dist = (int) distance;
            
            int robotDirection = Robot.getDirection();
            double direction = ((Math.PI / 2) - robotDirection*(Math.PI / 4));
            
            x = (int) (((Robot.DIAMETER / 2) + (diameter / 2) - 3) * Math.cos(direction)) + (int) (distance * Math.cos(Math.toRadians(angleThroughCenter - robotDirection*(45))));
            y = (int) (((Robot.DIAMETER / 2) + (diameter / 2) - 3) * -Math.sin(direction)) + (int) (distance * -Math.sin(Math.toRadians(angleThroughCenter - robotDirection*(45))));
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Obstacle)) {
                return false;
            }
            Obstacle obs = (Obstacle) o;
            return obs.diameter == this.diameter && obs.dist == this.dist && obs.x == this.x && obs.y == this.y;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.x;
            hash = 97 * hash + this.y;
            hash = 97 * hash + this.diameter;
            hash = 97 * hash + this.dist;
            return hash;
        }

    }

    private class Line { // not static because robot direction can change

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

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 31 * hash + this.x1;
            hash = 31 * hash + this.y1;
            hash = 31 * hash + this.x2;
            hash = 31 * hash + this.y2;
            return hash;
        }

    }

    /**
     * Adds an Obstacle to ArrayList of obstacles for GUI to draw and repaints
     * @param distance
     * @param angleThroughCenter
     * @param diameter 
     */
    public void addObstacle(double distance, double angleThroughCenter, double diameter) {
        Obstacle o = new Obstacle(distance, angleThroughCenter, diameter);
        obstacles.add(o);
        int[] adjusted = getAdjustedObstacleCoords(o, r.getPosition()[0], r.getPosition()[1]);
        rOPositions.add(adjusted);
        repaint();
    }
    
    /**
     * Adds a Cliff to ArrayList of cliffs for GUI to draw and repaints 
     */
    public void addCliff() {
        Cliff cliff = new Cliff();
//        System.out.println(Arrays.toString(cliff.getPosition()));
        cliffs.add(cliff);
        int[] pos = getAdjustedCliffCoords(cliff, r.getPosition()[0], r.getPosition()[1]);
        rCPositions.add(pos);
        repaint();
    }

    /**
     * Adds a Line to ArrayList of lines for GUI to draw and repaints
     * @param x1
     * @param y1
     * @param x2
     * @param y2 
     */
    public void addLine(int x1, int y1, int x2, int y2) {
        lines.add(new Line(x1, y1, x2, y2));
        repaint();
    }

    /**
     * Moves robot to exact location described by x and y on the map
     * @param x x position on the map
     * @param y y position on the map
     */
    public void moveRobot(int x, int y) { // moves origin since robot is always at origin
        r.setPosition(x, y);
//        System.out.println(Arrays.toString(r.getPosition()));
        repaint();
    }

    /**
     * Moves robot a distance described by distance in the direction it is pointing
     * @param distance distance to move robot
     */
    public void moveRobotInCurrentDirection(int distance) {
//        System.out.println(distance);
        switch (Robot.getDirection()) {
            case Robot.NORTH:
                System.out.println(Arrays.toString(getCurrentRobotCoords()));
                moveRobot(getCurrentRobotCoords()[0], getCurrentRobotCoords()[1] - distance);
                System.out.println(Arrays.toString(getCurrentRobotCoords()));
                break;
            case Robot.NORTH_EAST:
                moveRobot(getCurrentRobotCoords()[0] + (int) (Math.sin(Math.PI / 4) * distance), getCurrentRobotCoords()[1] - (int) (Math.sin(Math.PI / 4) * distance));
                break;
            case Robot.EAST:
                moveRobot(getCurrentRobotCoords()[0] + distance, getCurrentRobotCoords()[1]);
                break;
            case Robot.SOUTH_EAST:
                moveRobot(getCurrentRobotCoords()[0] + (int) (Math.sin(Math.PI / 4) * distance), getCurrentRobotCoords()[1] + (int) (Math.sin(Math.PI / 4) * distance));
                break;
            case Robot.SOUTH:
                moveRobot(getCurrentRobotCoords()[0], getCurrentRobotCoords()[1] + distance);
                break;
            case Robot.SOUTH_WEST:
                moveRobot(getCurrentRobotCoords()[0] - (int) (Math.sin(Math.PI / 4) * distance), getCurrentRobotCoords()[1] + (int) (Math.sin(Math.PI / 4) * distance));
                break;
            case Robot.WEST:
                moveRobot(getCurrentRobotCoords()[0] - distance, getCurrentRobotCoords()[1]);
                break;
            case Robot.NORTH_WEST:
                moveRobot(getCurrentRobotCoords()[0] - (int) (Math.sin(Math.PI / 4) * distance), getCurrentRobotCoords()[1] - (int) (Math.sin(Math.PI / 4) * distance));
                break;
        }
    }

    /**
     * Returns robot coordinates
     * @return
     */
    public int[] getCurrentRobotCoords() {
        return r.getPosition();
    }

    private int[] getAdjustedObstacleCoords(Obstacle o, int robotx, int roboty) {
        int[] adjustedCoords = new int[2];
        adjustedCoords[0] = o.x + robotx;
        adjustedCoords[1] = o.y + roboty;
        return adjustedCoords;
    }
    
    private int[] getAdjustedCliffCoords(Cliff c, int robotx, int roboty) {
        int[] adjusted = new int[2];
        adjusted[0] = c.x + robotx;
        adjusted[1] = c.y + roboty;
        return adjusted;
    }

    private int[] getOvalCoords(int x, int y, double diameter) {
        int[] ovalCoords = new int[2];
        ovalCoords[0] = (int) (x - (diameter / 2)) + origin.x;
        ovalCoords[1] = (int) (y - (diameter / 2)) + origin.y;
        return ovalCoords;
    }
    
    private int[] getSquareCoords(int x, int y, int width) {
        int[] squareCoords = new int[2];
        squareCoords[0] = (x - (width / 2)) + origin.x;
        squareCoords[1] = (y - (width / 2)) + origin.y;
        return squareCoords;
    }
    
    /**
     * Resets all ArrayLists, moves the origin back to (0,0), and resets the robots location
     */
    public void clear() {
        obstacles.clear();
        cliffs.clear();
        lines.clear();
        origin.setLocation(new Point(0,0));
        r.setPosition(getWidth() / 2, getHeight() / 2);
        repaint();
    }

    /**
     * Returns instantiated Robot object
     * @return r
     */
    public Robot getRobot() {
        return r;
    }

    private void drawGrid(Graphics g, int gridSize) {
        int offsetx = origin.x;
        int offsety = origin.y;
        g.setColor(new Color(220, 220, 220));
        while (offsetx < getWidth()) {
            g.drawLine(offsetx, 0, offsetx, getHeight());
            offsetx += gridSize;
        }
        while (offsetx > 0) {
            g.drawLine(offsetx, 0, offsetx, getHeight());
            offsetx -= gridSize;
        }
        while (offsety < getHeight()) {
            g.drawLine(0, offsety, getWidth(), offsety);
            offsety += gridSize;
        }
        while (offsety > 0) {
            g.drawLine(0, offsety, getWidth(), offsety);
            offsety -= gridSize;
        }
        g.setColor(Color.black);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);

        // draw a grid - static right now
        drawGrid(g, 61); // floor tiles are 61x61 cm

        // loop through lines and paint
        for (Line line : lines) {
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }

        // loop through obstacles and paint
        for (int i = 0; i < obstacles.size(); i++) {
//            System.out.println("rOPositions: " + Arrays.toString(rOPositions.get(i)));
            g.drawOval(
                    getOvalCoords(rOPositions.get(i)[0], rOPositions.get(i)[1], obstacles.get(i).diameter)[0],
                    getOvalCoords(rOPositions.get(i)[0], rOPositions.get(i)[1], obstacles.get(i).diameter)[1],
                    obstacles.get(i).diameter,
                    obstacles.get(i).diameter
            );
        }
        
        // loop through cliffs and paint
        for (int i = 0; i < cliffs.size(); i++) {
            g.fillRect(
                    getSquareCoords(rCPositions.get(i)[0], rCPositions.get(i)[1], cliffs.get(i).width)[0],
                    getSquareCoords(rCPositions.get(i)[0], rCPositions.get(i)[1], cliffs.get(i).width)[1],
                    cliffs.get(i).width,
                    cliffs.get(i).width
            );
//            System.out.println(Arrays.toString((int[]) rCPositions.toArray()[0]));
        }
        
        g.setColor(Color.green);
        // draw robot
        g.fillOval(
                getOvalCoords(r.getPosition()[0], r.getPosition()[1], Robot.DIAMETER)[0],
                getOvalCoords(r.getPosition()[0], r.getPosition()[1], Robot.DIAMETER)[1],
                Robot.DIAMETER,
                Robot.DIAMETER
        );
    }

}
