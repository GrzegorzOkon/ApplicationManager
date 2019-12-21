package okon.ApplicationManager;

public class Program {
    private final String alias;
    private final String filename;
    private final String path;

    public Program(String alias, String filename, String path) {
        this.alias = alias;
        this.filename = filename;
        this.path = path;
    }

    public String getAlias() {
        return alias;
    }

    public String getFilename() {
        return filename;
    }

    public String getPath() {
        return path;
    }
}
