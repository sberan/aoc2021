package eth.sberan.day1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jooq.lambda.Seq;

import eth.sberan.util.Utils;

public class Day1 {

  static <T> Stream<List<T>> slidingWindow(Stream<T> input, int size) {
    return Seq.seq(input).sliding(size).map(w -> w.toList()).stream();
  }

  public static void main(String[] args) throws Exception {
    Stream<Integer> measurements = Utils.readInput(Day1.class)
      .map((line) -> Integer.parseInt(line));

    Stream<Integer> sums = slidingWindow(measurements, 3)
      .map(w -> w.stream().collect(Collectors.summingInt(Integer::intValue)));

    long countDeeper = slidingWindow(sums, 2)
      .filter(pair -> pair.get(0) < pair.get(1))
      .count();

    System.out.println(countDeeper);
  }
}
