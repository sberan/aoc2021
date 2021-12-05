package eth.sberan.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;

import org.jooq.lambda.Seq;

public class Utils {
  public static List<String> readInput(Class<?> origin, String fileName) throws URISyntaxException, IOException {
    Path inputFile = Path.of(origin.getResource(fileName).toURI());
    return Files.readAllLines(inputFile);
  }

  public static <T> Stream<List<T>> slidingWindow(Stream<T> input, int size) {
    return Seq.seq(input).sliding(size).map(w -> w.toList()).stream();
  }

  /**
   * Returns a list of characters at the given index in the string,
   * sorted by frequency (greatest to least)
   */
  public static  List<String> charsAtIndex(List<String> values, int index) {
    Multiset<String> chars = HashMultiset.create();
    for (String entry : values) {
      chars.add(entry.substring(index, index + 1));
    }
    return chars.entrySet()
      .stream()
      .sorted(Ordering.natural().reverse().onResultOf(Multiset.Entry::getCount))
      .map(x -> x.getElement())
      .collect(Collectors.toList());
  }
}
