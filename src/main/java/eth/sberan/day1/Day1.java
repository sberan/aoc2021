package eth.sberan.day1;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import eth.sberan.util.Utils;

public class Day1 {

  public static void main(String[] args) throws Exception {
    Stream<Integer> measurements = Utils.readInput(Day1.class, "input.txt").stream()
      .map((line) -> Integer.parseInt(line));

    Stream<Integer> sums = Utils.slidingWindow(measurements, 3)
      .map(w -> w.stream().collect(Collectors.summingInt(Integer::intValue)));

    long countDeeper = Utils.slidingWindow(sums, 2)
      .filter(pair -> pair.get(0) < pair.get(1))
      .count();

    System.out.println(countDeeper);
  }
}
