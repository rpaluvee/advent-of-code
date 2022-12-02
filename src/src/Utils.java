import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public final class Utils {

    private Utils() {

    }

    public static List<String> readFileToList(String path) throws IOException {
        return Files.readAllLines(new File(path).toPath(), Charset.defaultCharset() );
    }

}
