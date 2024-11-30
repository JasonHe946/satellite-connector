package unsw.blackout;

import unsw.utils.Angle;

public class RelaySatellite extends Satellite{

    private boolean supDesktopDevice;
    private int range;
    private double linearVelocity;
    
    // for moving to next region
    private Angle lower = Angle.fromDegrees(140);
    private Angle upper = Angle.fromDegrees(190);
    private Angle oppositeMiddle = Angle.fromDegrees(345);
 
    public RelaySatellite(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        this.supDesktopDevice = true;
        this.range = 300000;
        this.linearVelocity = -1500;
    }
    
    public double getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(double linearVelocity) {
        this.linearVelocity = linearVelocity;
    }
    
    public boolean isSupDesktopDevice() {
        return supDesktopDevice;
    }

    public void setSupDesktopDevice(boolean supDesktopDevice) {
        this.supDesktopDevice = supDesktopDevice;
    }

    @Override
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }


    public void nextLocation() {
        // if not between 140 and 190 degrees, set direction so that it goes towards it
        // starting in top right region
        if (this.getPosition().compareTo(oppositeMiddle) == 1 || this.getPosition().compareTo(lower) == -1 || this.getPosition().compareTo(oppositeMiddle) == 0) {
            this.setLinearVelocity(Math.abs(this.getLinearVelocity()));
        } else if (this.getPosition().compareTo(oppositeMiddle) == -1 && this.getPosition().compareTo(upper) == 1) { // starting bottom left region
            this.setLinearVelocity(-Math.abs(this.getLinearVelocity()));
        }

        double angularVelocity = this.getLinearVelocity() / this.getHeight();
        double newPosition = this.getPosition().toRadians() + angularVelocity;
        if (newPosition < 0) {
            newPosition += 2*Math.PI;
        } else if (newPosition > 2*Math.PI) {
            newPosition -= 2*Math.PI;
        }
        this.setPosition(Angle.fromRadians(newPosition));
    }
}
