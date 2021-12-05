package eth.sberan.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Utils {
  public static Stream<String> readInput(Class<?> origin) throws URISyntaxException, IOException {
    Path inputFile = Path.of(origin.getResource("input.txt").toURI());
    return Files.readAllLines(inputFile).stream();
  }
}
