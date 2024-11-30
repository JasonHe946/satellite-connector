package unsw.blackout;

public class File {
    private String filename;
    private String content;
    private Integer fileSize;

    public File(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.fileSize = content.length();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    
  
    
}
