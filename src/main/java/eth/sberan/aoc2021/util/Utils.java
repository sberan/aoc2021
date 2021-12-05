package eth.sberan.aoc2021.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.jooq.lambda.Seq;

public class Utils {

  public static List<String> readInput(Class<?> origin, String fileName) throws URISyntaxException, IOException {
    Path inputFile = Path.of(origin.getResource(fileName).toURI());
    return Files.readAllLines(inputFile);
  }

  public static <T> Stream<List<T>> slidingWindow(Stream<T> input, int size) {
    return Seq.seq(input).sliding(size).map(w -> w.toList()).stream();
  }
}
