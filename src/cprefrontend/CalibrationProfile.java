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

    /**
     * Constructor which initialized profile name, offset, and multiplier
     */
    public CalibrationProfile() {
        name = null;
        offset = 0;
        multiplier = 1;
    }

    /**
     * Constructor which sets profile name
     * @param name
     */
    public CalibrationProfile(String name) {
        this();
        this.name = name;
    }

    /**
     * Sets profile name
     * @param name name of the profile
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets profile offset (center to 90 degrees)
     * @param offset offset required to line servo to 90 degrees
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    /**
     * Sets profile multiplier
     * @param multiplier multiplier required to make servo sweep a full 180 degrees
     */
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Returns profile name
     * @return profile name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns servo offset
     * @return offset
     */
    public int getOffset() {
        return offset;
    }
    
    /**
     * Returns servo multiplier
     * @return multiplier
     */
    public int getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        return name + ", " + offset + ", " + multiplier;
    }

}
