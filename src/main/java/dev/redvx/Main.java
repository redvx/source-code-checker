package dev.redvx;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {
    private static final String[] stringsToCheck = new String[]{"LaunchClassLoader", "field.setAccessible(true);", "Launch", "InputStreamReader", "new File", "FileOutputStream", "ProcessBuilder", "BufferedReader", "Base64", "URL", "Runtime", "ProcessBuilder", "HttpsURLConnection", "HttpURLConnection", "URLConnection", "Process", "isProcessRunning", "isProcessRunningTitle", "System.getProperty", "System.getenv", "Runtime.getRuntime().exec", ".exec"};

    public static void main(String[] stringArray) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        long l = System.currentTimeMillis();
        List<CheckedLine> list = Main.listFiles();
        Main.writeCheckedLines(list);
        System.out.format("Successfully created & written to lines.txt. The file is in the same directory as the jar\n");
        long l2 = System.currentTimeMillis();
        System.out.format("Finished in %s seconds!", decimalFormat.format((double) (l2 - l) / 1000.0));
        System.exit(0);
    }

    public static Path getFileLocation() {
        try {
            return Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<CheckedLine> listFiles() {
        ArrayList<CheckedLine> arrayList = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Main.getFileLocation())) {
            stream.filter(Files::isReadable).filter(path -> path.toString().endsWith(".java")).forEach(path -> {
                try {
                    List<String> list = Files.readAllLines(path, Charset.defaultCharset());
                    block2:
                    for (int i = 0; i < list.size(); ++i) {
                        for (String string : stringsToCheck) {
                            String string2 = list.get(i);
                            if (!string2.contains(string)) continue;
                            CheckedLine checkedLine = new CheckedLine(path.toAbsolutePath().resolve(path.toAbsolutePath()).toString(), path.getFileName().toString(), i, string2);
                            arrayList.add(checkedLine);
                            continue block2;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void writeCheckedLines(List<CheckedLine> list) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Objects.requireNonNull(Main.getFileLocation()).resolve("lines.txt"))) {
            for (CheckedLine checkedLine : list) {
                String string = checkedLine.getFilePath().replace('\\', '/');
                bufferedWriter.write(string + ":" + checkedLine.getLineNumber() + " | " + checkedLine.getLineContent().trim() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
