package unsw.blackout;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.EntityInfoResponse;
import unsw.utils.Angle;
import unsw.utils.MathsHelper;

public class BlackoutController {


    private static final double RADIUS_OF_JUPITER = 69911.0;

    private List<Satellite> satelliteList = new ArrayList<Satellite>();
    private List<Device> deviceList = new ArrayList<Device>();

    public void createDevice(String deviceId, String type, Angle position) {
        switch (type) {
            case "HandheldDevice":
                deviceList.add(new HandheldDevice(deviceId, type, position)); break;
            case "LaptopDevice":
                deviceList.add(new LaptopDevice(deviceId, type, position)); break;
            case "DesktopDevice":
                deviceList.add(new DesktopDevice(deviceId, type, position)); break;
        }
    }

    public void removeDevice(String deviceId) {
        List<Device> indexRemove = new ArrayList<Device>(); 
        for (Device d: deviceList) {
            if (deviceId.equals(d.getDeviceId())) {
                indexRemove.add(d);
            }
        }
        deviceList.removeAll(indexRemove);
    }

    public void createSatellite(String satelliteId, String type, double height, Angle position) {
        switch (type) {
            case "StandardSatellite":
                satelliteList.add(new StandardSatellite(satelliteId, type, height, position)); break;
            case "TeleportingSatellite":
                satelliteList.add(new TeleportingSatellite(satelliteId, type, height, position)); break;
            case "RelaySatellite":
                satelliteList.add(new RelaySatellite(satelliteId, type, height, position)); break;
        }
    }

    public void removeSatellite(String satelliteId) {
        List<Satellite> indexRemove = new ArrayList<Satellite>(); 
        for (Satellite s : satelliteList) {
            if (satelliteId.equals(s.getSatelliteId())) {
                indexRemove.add(s);
            }
        }
        satelliteList.removeAll(indexRemove);
    }

    public List<String> listDeviceIds() {
        List<String> allDevices = new ArrayList<String>();
        for (Device d : deviceList) {
            allDevices.add(d.getDeviceId());
        }
        return allDevices;
    }

    public List<String> listSatelliteIds() {
        List <String> allSatellites = new ArrayList<String>();
        for (Satellite s : satelliteList) {
            allSatellites.add(s.getSatelliteId());
        }
        return allSatellites;
    }

    public void addFileToDevice(String deviceId, String filename, String content) {
        for (Device d : deviceList) {
            if (deviceId.equals(d.getDeviceId())) {
                d.addFile(filename, content);
            }
        }
    }

    public EntityInfoResponse getInfo(String id) {
        // find if it's a satellite or device by going through both lists
        for (Satellite s : satelliteList) {
            if (id.equals(s.getSatelliteId())) {
                return new EntityInfoResponse(id, s.getPosition(), s.getHeight(), s.getType());
            }
        }
        for (Device d : deviceList) {
            if (id.equals(d.getDeviceId())) {
                return new EntityInfoResponse(id, d.getPosition(), RADIUS_OF_JUPITER, d.getType(), d.mapFileToInfo());
            }
        }
        return null;
    }

    public void simulate() {
        for (Satellite s : satelliteList) {
            s.nextLocation();
        }
    }

    /**
     * Simulate for the specified number of minutes.
     * You shouldn't need to modify this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();
        }
    }

    public List<String> communicableEntitiesInRange(String id) {
        
        Integer type = 0;
        Satellite thisSat = null;
        Device thisDev = null;
        for (Satellite s : satelliteList) {
            if (id.equals(s.getSatelliteId())) {
                type = 1;
                thisSat = s;
            }
        }
        for (Device d : deviceList) {
            if (id.equals(d.getDeviceId())) {
                type = 2;
                thisDev = d;
            }
        }

        if (type == 1) {
            return findFromSat(id, thisSat);
        } else if (type == 2) {
            return findFromDevice(id, thisDev);
        } 
        return null;
        
    }

    public void sendFile(String fileName, String fromId, String toId) throws FileTransferException {
        // TODO: Task 2 c)
    }

    public void createDevice(String deviceId, String type, Angle position, boolean isMoving) {
        createDevice(deviceId, type, position);
        // TODO: Task 3
    }

    public void createSlope(int startAngle, int endAngle, int gradient) {
        // TODO: Task 3
        // If you are not completing Task 3 you can leave this method blank :)
    }



    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////         HELPER FUNCTIONS           //////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    public List<String> findFromSat(String id, Satellite thisSat) {
        List <String> objectsInRange = new ArrayList<String>();
        for (Satellite s: satelliteList) {
            if (id.equals(s.getSatelliteId()) == false) {
                // add other satellites
                if (satInRangeOfSat(thisSat, s)) {
                    objectsInRange.add(s.getSatelliteId());
                }
            }
        }
        // add devices
        for (Device d : deviceList) {
            if (devInRangeOfSat(thisSat, d)) {
                objectsInRange.add(d.getDeviceId());
            }
        }
        return objectsInRange;
    }

    public boolean satInRangeOfSat(Satellite thisSat, Satellite s) {
        boolean inRange;
        inRange = MathsHelper.isVisible(thisSat.getHeight(), thisSat.getPosition(), s.getHeight(), s.getPosition())
        && (MathsHelper.getDistance(thisSat.getHeight(), thisSat.getPosition(), s.getHeight(), s.getPosition()) <= thisSat.getRange());
        return inRange;
    }

    public boolean devInRangeOfSat(Satellite thisSat, Device d) {
        boolean inRange;
        inRange = MathsHelper.isVisible(thisSat.getHeight(), thisSat.getPosition(), d.getPosition())
        && (MathsHelper.getDistance(thisSat.getHeight(), thisSat.getPosition(), d.getPosition()) <= thisSat.getRange())
        && ((d.getType().equals("HandheldDevice") && thisSat.isSupHandheldDevice())
        || (d.getType().equals("LaptopDevice") && thisSat.isSupLaptopDevice())
        || (d.getType().equals("DesktopDevice") && thisSat.isSupDesktopDevice()));

        return inRange;
    }

    public List<String> findFromDevice(String id, Device thisDev) {
        List <String> objectsInRange = new ArrayList<String>();
        // add satellites
        for (Satellite s: satelliteList) {
            if (satInRangeOfDev(thisDev, s)) {
                objectsInRange.add(s.getSatelliteId());
            }
        }
        return objectsInRange;
    }

    public boolean satInRangeOfDev(Device thisDev, Satellite s) {
        boolean inRange;
        inRange = MathsHelper.isVisible(s.getHeight(), s.getPosition(), thisDev.getPosition())
        && (MathsHelper.getDistance(s.getHeight(), s.getPosition(), thisDev.getPosition()) <= thisDev.getRange())
        && ((thisDev.getType().equals("HandheldDevice") && s.isSupHandheldDevice())
        || (thisDev.getType().equals("LaptopDevice") && s.isSupLaptopDevice())
        || (thisDev.getType().equals("DesktopDevice") && s.isSupDesktopDevice()));

        return inRange;
    }
}
