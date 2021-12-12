package eth.sberan.aoc2021.day8;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;

import eth.sberan.aoc2021.util.Utils;

public class Day8 {
  static final Map<String, Integer> uniqueSegments = Map.of(
    "cf", 1,
    "bcdf", 4,
    "acf", 7,
    "abcdefg", 8
  );

  public static void main(String[] args) throws Exception {
    var uniqueKeyLengths = uniqueSegments.keySet().stream()
      .mapToInt(x -> x.length())
      .boxed()
      .collect(Collectors.toSet());

    var allOutputSegments = Utils.readInput(Day8.class, "input.txt")
      .map(line -> Splitter.on("|").trimResults().splitToList(line).get(1))
      .flatMap(output -> Splitter.on(" ").splitToStream(output))
      .filter(x -> uniqueKeyLengths.contains(x.length()))
      .count();
    
    System.out.println(allOutputSegments);
  }

}
