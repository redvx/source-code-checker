package dev.redvx;

public class CheckedLine {
    private final String filePath;
    private final String fileName;
    private final int lineNumber;
    private final String lineContent;

    public CheckedLine(String filePath, String fileName, int lineNumber, String lineContent) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.lineContent = lineContent;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String getLineContent() {
        return this.lineContent;
    }

    public String toString() {
        return "CheckedLine(filePath=" + this.getFilePath() + ", fileName=" + this.getFileName() + ", lineNumber=" + this.getLineNumber() + ", lineContent=" + this.getLineContent() + ")";
    }
}
