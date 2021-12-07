package eth.sberan.aoc2021.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.base.Splitter;

import org.jooq.lambda.Seq;

public class Utils {

  public static Stream<String> readInput(Class<?> origin, String fileName) throws URISyntaxException, IOException {
    Path inputFile = Path.of(origin.getResource(fileName).toURI());
    return Files.readAllLines(inputFile).stream();
  }

  public static <T> Stream<List<T>> slidingWindow(Stream<T> input, int size) {
    return Seq.seq(input).sliding(size).map(w -> w.toList()).stream();
  }

  public static Stream<Integer> splitInts(String input, String separator) {
    return Splitter.on(separator)
      .trimResults()
      .omitEmptyStrings()
      .splitToStream(input)
      .map(Integer::parseInt);
  }

  public static interface ConsumerWithIndex<T> {
    public void apply(T value, int index);
  }

  public static <T> void forEachWithIndex (Iterable<T> subject, ConsumerWithIndex<? super T> consumer) {
    int[] index = {0};
    subject.forEach(item -> consumer.apply(item, index[0]++));
  }
}
