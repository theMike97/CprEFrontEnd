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

    public RobotInterpreter(Map map, ActivityLog log) {
        this.map = map;
        linePoints = new int[2][2];
        pointsIndex = 0;
        this.log = log;
    }

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
                return;
        }
    }

    private void parseObstacleResponse(String[] arr) {
        try {
            int distToCenter = (int) (Double.parseDouble(arr[1]));
            int angleToCenter = (int) Double.parseDouble(arr[2]);
            int diameter = (int) Double.parseDouble(arr[3]);

            if (diameter < 1 || distToCenter < 4) {
                log.logErrOverwriteln("There were some issues\nConsider rescanning");
                return; // dont do anything, this was a fluke
            } else if (diameter < 8) {
                diameter = 5;
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
                    distance /= 3.32;
                    map.moveRobotInCurrentDirection((int)distance);
                    map.addObstacle(7, 155, 13);
                    log.logPrintln("DETECTED LEFT BUMP");
                    break;
                case "right":
                    distance = Double.parseDouble(arr[2]);
                    distance /= 3.32;
                    map.moveRobotInCurrentDirection((int)distance);
                    map.addObstacle(12, 25, 13);
                    log.logPrintln("DETECTED RIGHT BUMP");
                    break;
                case "line":
                    distance = Double.parseDouble(arr[2]);
                    distance /= 3.32;
                    linePoints[pointsIndex++] = map.getRobot().getPosition();
                    if (pointsIndex == 2) {
                        map.addLine(linePoints[0][0], linePoints[0][1], linePoints[1][0], linePoints[1][1]);
                        map.repaint();
                        pointsIndex = 0;
                    }
                    log.logPrintln("DETECTED LINE");
                    break;
                case "cliff":
                    distance = Double.parseDouble(arr[2]);
                    distance /= 3.32;
                    map.moveRobotInCurrentDirection((int)distance - 6);
                    log.logPrintln("DETECTED CLIFF");
                    break;
                default:
                    distance = Double.parseDouble(arr[1]);
                    distance /= 3.32;
                    map.moveRobotInCurrentDirection((int)distance);
//                    System.out.println("[" + arr[1] + "]");
//                    switch (Robot.getDirection()) {
//                        case Robot.NORTH:
//                            map.moveRobot(map.getCurrentRobotCoords()[0], map.getCurrentRobotCoords()[1] - distance);
//                            break;
//                        case Robot.EAST:
//                            map.moveRobot(map.getCurrentRobotCoords()[0] + distance, map.getCurrentRobotCoords()[1]);
//                            break;
//                        case Robot.SOUTH:
//                            map.moveRobot(map.getCurrentRobotCoords()[0], map.getCurrentRobotCoords()[1] + distance);
//                            break;
//                        case Robot.WEST:
//                            map.moveRobot(map.getCurrentRobotCoords()[0] - distance, map.getCurrentRobotCoords()[1]);
//                            break;
//                    }
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
