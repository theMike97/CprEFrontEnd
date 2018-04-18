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
public class CalibrationProfile {

    private String name;
    private int robot;

    public CalibrationProfile() {
        name = null;
        robot = -1;
    }

    public CalibrationProfile(String name) {
        this.name = name;
        robot = -1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBot(int bot) {
        robot = bot;
    }

    public String getName() {
        return name;
    }

    public int getBot() {
        return robot;
    }

    @Override
    public String toString() {
        return name + "\n" + robot;
    }

}
