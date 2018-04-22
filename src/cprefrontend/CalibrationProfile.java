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
    private int offset;
    private int multiplier;

    public CalibrationProfile() {
        name = null;
        offset = 0;
        multiplier = 1;
    }

    public CalibrationProfile(String name) {
        this();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public String getName() {
        return name;
    }

    public int getOffset() {
        return offset;
    }
    
    public int getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        return name + ", " + offset + ", " + multiplier;
    }

}
