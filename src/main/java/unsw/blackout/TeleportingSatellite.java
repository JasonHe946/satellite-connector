package unsw.blackout;

import unsw.utils.Angle;

public class TeleportingSatellite extends Satellite{

    private boolean supDesktopDevice;
    private int range;
    private double linearVelocity;
    private int maxFiles;
    private int maxBytes;
    private int downloadSpeed;
    private int uploadSpeed;


    public TeleportingSatellite(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        this.supDesktopDevice = true;
        this.range = 200000;
        this.linearVelocity = 1000;
        this.maxFiles = Integer.MAX_VALUE;
        this.maxBytes = 200;
        this.downloadSpeed = 15;
        this.uploadSpeed = 10;
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

    public int getMaxFiles() {
        return maxFiles;
    }

    public void setMaxFiles(int maxFiles) {
        this.maxFiles = maxFiles;
    }

    public int getMaxBytes() {
        return maxBytes;
    }

    public void setMaxBytes(int maxBytes) {
        this.maxBytes = maxBytes;
    }

    public int getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(int downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public int getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(int uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }


    public void nextLocation() {

        // if you hit 180 deg and your veloctiy is positive, teleport to 0 and make velocity negative

        // if you hit 180 deg and your velocity is negative, teleport to 0 and make velocity positive
        

        double angularVelocity = this.getLinearVelocity() / this.getHeight();
        double oldPosition = this.getPosition().toRadians();
        double newPosition = this.getPosition().toRadians() + angularVelocity;

        if ((oldPosition < Math.PI && newPosition >= Math.PI) || (oldPosition > Math.PI && newPosition <= Math.PI)) {
            if (this.getLinearVelocity() > 0) {
                this.setLinearVelocity(-Math.abs(this.getLinearVelocity()));
            } else if (this.getLinearVelocity() < 0) {
                this.setLinearVelocity(Math.abs(this.getLinearVelocity()));
            }
            this.setPosition(Angle.fromRadians(0));
        } else {
            if (newPosition > 2*Math.PI) {
                newPosition -= 2*Math.PI;
            } else if (newPosition < 0) {
                newPosition += 2*Math.PI;
            }
            this.setPosition(Angle.fromRadians(newPosition));
        }
        
    }
    
}
