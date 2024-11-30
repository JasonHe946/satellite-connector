package unsw.blackout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unsw.response.models.FileInfoResponse;
import unsw.utils.Angle;

public abstract class Device {
    
    private List<File> deviceFiles = new ArrayList<File>();

    private String deviceId;
    private String type;
    private Angle position;
    
    public Device(String deviceId, String type, Angle position) {
        this.deviceId = deviceId;
        this.type = type;
        this.position = position;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Angle getPosition() {
        return position;
    }

    public void setPosition(Angle position) {
        this.position = position;
    }

    public void addFile (String filename, String content) {
        File f = new File(filename, content);
        deviceFiles.add(f);
    }

    // String filename, String data, int fileSize, boolean hasTransferCompleted
    public Map<String, FileInfoResponse> mapFileToInfo() {
        HashMap<String, FileInfoResponse> filesMap = new HashMap<String, FileInfoResponse>();
        for (File f : deviceFiles) {
            filesMap.put(f.getFilename(), new FileInfoResponse(f.getFilename(), f.getContent(), f.getFileSize(), true));
        }
        return filesMap;
    }

    public abstract int getRange();

}
