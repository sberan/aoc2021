package eth.sberan.aoc2021.day7;

import java.util.Arrays;
import java.util.stream.IntStream;

import eth.sberan.aoc2021.util.Utils;

public class Day7 {
  static int part1(int[] positions, int toPosition) {
    return Arrays.stream(positions)
      .map(fromPosition -> Math.abs(fromPosition - toPosition))
      .sum();
  }

  static int part2(int[] positions, int toPosition) {
    return Arrays.stream(positions)
      .map(fromPosition -> {
        var moveSize = Math.abs(fromPosition - toPosition);
        return IntStream.rangeClosed(1, moveSize).sum();
      })
      .sum();
  }

  public static void main(String[] args) throws Exception {
    var initialState = Utils.readInput(Day7.class, "input.txt")
      .flatMapToInt(line -> Utils.splitInts(line, ","))
      .toArray();

    var maxPosition = Arrays.stream(initialState).max().getAsInt();

    var minFuel = IntStream.rangeClosed(0, maxPosition)
      .map(pos -> part2(initialState, pos))
      .min();

    System.out.println(minFuel.getAsInt());
  }
}
