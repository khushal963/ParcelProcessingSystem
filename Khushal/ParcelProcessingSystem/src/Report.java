import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class Report {
    private static Report instance;
    private StringJoiner logEntries = new StringJoiner("\n");

    private Report() {
    }

    public static Report getInstance() {
        if (instance == null) {
            instance = new Report();
        }

        return instance;
    }

    public void addEvent(String event) {
        this.logEntries.add(event);
    }

    public String getLog() {
        return this.logEntries.toString();
    }

    public void writeLogToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(this.logEntries.toString());
        }
    }
}