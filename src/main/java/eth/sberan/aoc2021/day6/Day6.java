package eth.sberan.aoc2021.day6;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

import eth.sberan.aoc2021.util.Utils;

record LanternFish(int days) {
  Stream<LanternFish> next () {
    if (days == 0) {
      return Stream.of(new LanternFish(6), new LanternFish(8));
    }
    return Stream.of(new LanternFish(days - 1));
  }
}
record Simulation(List<LanternFish> state, int day) {

  
  static Stream<Simulation> simulate (List<LanternFish> initialState) {
    var state = new AtomicReference<>(new Simulation(initialState, 0));
    return Stream.generate(() -> {
      return state.updateAndGet(curr -> curr.next());
    });
  }

  Simulation next () {
    var nextState = state.stream().flatMap(f -> f.next()).toList();
    return new Simulation(nextState, day + 1);
  }

  @Override public String toString () {
    return String.format("After %s days: %s", day, state.size());
  }

  int total () {
    return this.state.size();
  }
}

public class Day6 {

  public static void main(String[] args) throws Exception {
    var initialState = Utils.readInput(Day6.class, "input.txt")
      .flatMapToInt(line -> Utils.splitInts(line, ","))
      .mapToObj(timer -> new LanternFish(timer))
      .toList();


    Simulation.simulate(initialState).limit(80).forEach(s -> {
      System.out.println(s);
    });
    
  }
}
