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
public class RobotInterpreter {

    Map map;
    int[][] linePoints;
    int pointsIndex;
    ActivityLog log;

    /**
     * Constructor selects the map and activity log for interfacing with the user
     * @param map
     * @param log
     */
    public RobotInterpreter(Map map, ActivityLog log) {
        this.map = map;
        linePoints = new int[2][2];
        pointsIndex = 0;
        this.log = log;
    }

    /**
     * Parses response received by robot
     * @param data
     */
    public void parseResponse(String data) {
        String[] responseArray = data.split(",");
        switch (responseArray[0]) {
            case "obstacle":
                parseObstacleResponse(responseArray);
                break;
            case "move":
                parseMoveResponse(responseArray);
                break;
            default:
                break;
        }
    }

    private void parseObstacleResponse(String[] arr) {
        try {
            int distToCenter = (int) (Double.parseDouble(arr[1]));
            int angleToCenter = (int) Double.parseDouble(arr[2]);
            int diameter = (int) Double.parseDouble(arr[3]);

            if (diameter < 3 || distToCenter < 4 || distToCenter > 50) {
                log.logErrOverwriteln("There were some issues\nConsider rescanning");
                return; // dont do anything, this was a fluke
            } else if (diameter < 5) {
                diameter = 5;
            } else if (diameter < 8) {
                diameter = 10;
            } else {
                diameter = 12;
            }

            map.addObstacle(distToCenter, angleToCenter, diameter);

        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            ex.printStackTrace(); // do more stuff later
        }
    }

    private void parseMoveResponse(String[] arr) {
        double distance;
        try {
            switch (arr[1]) {
                case "left":
                    distance = Double.parseDouble(arr[2]);
                    distance /= 3;
                    map.moveRobotInCurrentDirection((int)distance);
                    map.addObstacle(7, 155, 13);
                    log.logPrintln("DETECTED LEFT BUMP");
                    map.moveRobotInCurrentDirection(-5);
                    break;
                case "right":
                    distance = Double.parseDouble(arr[2]);
                    distance /= 3;
                    map.moveRobotInCurrentDirection((int)distance);
                    map.addObstacle(12, 25, 13);
                    log.logPrintln("DETECTED RIGHT BUMP");
                    map.moveRobotInCurrentDirection(-5);
                    break;
                case "line":
                    distance = Double.parseDouble(arr[2]);
                    distance /= 3;
                    linePoints[pointsIndex++] = map.getRobot().getPosition();
//                    if (pointsIndex == 2) {
//                        map.addLine(linePoints[0][0], linePoints[0][1], linePoints[1][0], linePoints[1][1]);
////                        map.repaint();
//                        pointsIndex = 0;
//                    }
                    log.logPrintln("DETECTED LINE");
                    map.moveRobotInCurrentDirection(-5);
                    break;
                case "cliff":
                    distance = Double.parseDouble(arr[2]);
                    distance /= 3;
                    map.moveRobotInCurrentDirection((int)distance);
                    map.addCliff();
                    log.logPrintln("DETECTED CLIFF");
                    map.moveRobotInCurrentDirection(-5);
                    break;
                default:
                    distance = Double.parseDouble(arr[1]);
//                    System.out.println(distance);
                    distance /= 3;
                    map.moveRobotInCurrentDirection((int)distance);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
