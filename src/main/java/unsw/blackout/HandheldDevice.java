package unsw.blackout;

import unsw.utils.Angle;

public class HandheldDevice extends Device {

    private final int range = 50000;
    
    public HandheldDevice(String deviceId, String type, Angle position) {
        super(deviceId, type, position);
    }

    public int getRange() {
        return range;
    }
    
    
}
