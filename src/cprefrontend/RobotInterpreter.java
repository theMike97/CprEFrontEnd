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
        this.map  = map;
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
        }
    }
}
