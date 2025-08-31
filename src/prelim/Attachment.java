package prelim;

public class Attachment {
    private String fileName;
    private int fileSize;
    private String fileType;

    public Attachment(String var1, int var2, String var3) {
        this.fileName = var1;
        this.fileSize = var2;
        this.fileType = var3;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String var1) {
        this.fileName = var1;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int var1) {
        this.fileSize = var1;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String var1) {
        this.fileType = var1;
    }

    public String toString() {
        return "Attachment{fileName='" + this.fileName + "', fileSize=" + this.fileSize + ", fileType='" + this.fileType + "'}";
    }
}
