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

    public RobotInterpreter(Map map) {
        this.map = map;
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
            case "rotate":
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

            if (diameter < 1) {
                return; // dont do anything, this was a fluke
            } else if (diameter < 9) {
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
        switch (arr[1]) {
            case "left":
                break;
            case "right":
                break;
            case "line":
                break;
            case "cliff":
                break;
            default:
                try {
                    int distance = Integer.parseInt(arr[1]);
//                    System.out.println("[" + arr[1] + "]");
                    switch (Robot.getDirection()) {
                        case Robot.NORTH:
                            map.moveRobot(map.getCurrentRobotCoords()[0], map.getCurrentRobotCoords()[1] - distance);
                            break;
                        case Robot.EAST:
                            map.moveRobot(map.getCurrentRobotCoords()[0] + distance, map.getCurrentRobotCoords()[1]);
                            break;
                        case Robot.SOUTH:
                            map.moveRobot(map.getCurrentRobotCoords()[0], map.getCurrentRobotCoords()[1] + distance);
                            break;
                        case Robot.WEST:
                            map.moveRobot(map.getCurrentRobotCoords()[0] - distance, map.getCurrentRobotCoords()[1]);
                            break;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
        }
    }

    private void parseRotateResponse(String[] arr) {
        switch (arr[1]) {
            case "0":
                break;
            case "90":
                break;
            case "180":
                break;
            case "270":
                break;
            default:
                break;
        }
    }
}
