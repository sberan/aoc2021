package eth.sberan.aoc2021.day6;

import java.math.BigInteger;
import java.util.Map;

import com.google.common.collect.Maps;

import eth.sberan.aoc2021.util.Utils;

class Simulation {
  static Map<Integer, BigInteger> memo = Maps.newHashMap();

  static BigInteger childrenAtDays(int days) {
    if (memo.containsKey(days)) {
      return memo.get(days);
    }
    var children = BigInteger.ZERO;
    for (var d = days - 1; d >= 0; d -= 7) {
      children = children.add(BigInteger.ONE).add(childrenAtDays(8, d));
    }
    memo.put(days, children);
    return children;
  }

  static BigInteger childrenAtDays(int startTimer, int days) {
    if (days <= startTimer) {
      return BigInteger.ZERO;
    }
    return childrenAtDays(days - startTimer);
  }

  public static BigInteger simulate(int[] initialState, int days) {
    BigInteger children = BigInteger.ZERO;
    for(var init : initialState) {
      children = children.add(BigInteger.ONE).add(childrenAtDays(init, days));
    }
    return children;
  }
}

public class Day6 {

  public static void main(String[] args) throws Exception {
    var initialState = Utils.readInput(Day6.class, "input.txt")
      .flatMapToInt(line -> Utils.splitInts(line, ","))
      .toArray();

    System.out.println(Simulation.simulate(initialState, 256));
  }
}
