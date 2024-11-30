package unsw.blackout;

import unsw.utils.Angle;

public class DesktopDevice extends Device {
    
    private final int range = 200000;

    public DesktopDevice(String deviceId, String type, Angle position) {
        super(deviceId, type, position);
    }

    public int getRange() {
        return range;
    }
    
}
