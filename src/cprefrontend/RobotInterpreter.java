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
    
    private Map parentMap;
    
    public RobotInterpreter(Map map) {
        parentMap = map;
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
        }
    }
    
    private void parseObstacleResponse(String[] arr) {
        
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
    
    public void drawResult() {
        
    }
    
}
