package unsw.blackout;

import unsw.utils.Angle;

public class StandardSatellite extends Satellite{

    private boolean supDesktopDevice;
    private int range;
    private double linearVelocity;
    private int maxFiles;
    private int maxBytes;
    private int downloadSpeed;
    private int uploadSpeed;

    private Angle revolution = Angle.fromDegrees(360);

    public StandardSatellite(String satelliteId, String type, double height, Angle position) {
        super(satelliteId, type, height, position);
        this.supDesktopDevice = false;
        this.range = 150000;
        this.linearVelocity = -2500;
        this.maxFiles = 3;
        this.maxBytes = 80;
        this.downloadSpeed = 1;
        this.uploadSpeed = 1;
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

    public Angle getRevolution() {
        return revolution;
    }

    public void setRevolution(Angle revolution) {
        this.revolution = revolution;
    }


    public void nextLocation() {
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
