package co2103.hw2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class GenerateTasks {

    public static void main(String[] args) throws IOException {
        String username = getUser();

        if (username != null) {
            Map<String, String> concepts = getConcepts();
            String ex = getExercises(username, concepts);
            Files.writeString(Paths.get("tasks.html"), ex);
        }
    }

    public static String getUser() throws IOException, FileNotFoundException {
        Properties p = new Properties();
        p.load(new FileReader("src/main/resources/application.properties"));
        String username = p.getProperty("username");
        return username;
    }

    private static String getExercises(String username, Map<String, String> concepts) throws IOException {
        String content = Files.readString(Paths.get("src/main/resources/template.html"));
        content = content.replaceAll("\\$username", username);
        for (String key : concepts.keySet()) {
            content = content.replaceAll("\\$" + key, concepts.get(key));
        }
        return content;
    }

    public static Map<String, String> getConcepts() throws IOException {
        String username = getUser();

        String hash = Integer.toString(hash(username) % 37);

        Reader in = new FileReader("src/main/resources/concepts.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        CSVRecord last = null;
        for (CSVRecord r : records) {
            if (hash.equals(r.get("hash"))) {
                return r.toMap();
            }
            last = r;
        }
        return last.toMap();
    }

    /**
     * very simple hash, just to make sure it is deterministic
     *
     * @param username
     * @return
     */
    private static int hash(String username) {
        int hash = 0;
        for (Byte b : username.getBytes()) {
            hash += b;
        }
        return hash;
    }
}
