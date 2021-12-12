package eth.sberan.aoc2021.day8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eth.sberan.aoc2021.util.Utils;

record Segment(char value) { 
  @Override public String toString () { return Character.toString(value); }
}

record Signal(char value) {
  @Override public String toString () { return Character.toString(value); }
}

record Measurement(ImmutableSet<Signal> signals) {

  public static Measurement from(String joinedSignals) {
    var signals = joinedSignals.chars()
      .mapToObj(c -> new Signal((char)c))
      .collect(ImmutableSet.toImmutableSet());

    return new Measurement(signals);
  }

  @Override public String toString () {
    return this.signals.stream().map(x -> x.toString()).collect(Collectors.joining());
  }
}

record Digit(int value, ImmutableSet<Segment> segments) {
  public static Digit from(int value, String joinedSegments) {
    var segments = joinedSegments.chars()
      .mapToObj(c -> new Segment((char)c))
      .collect(ImmutableSet.toImmutableSet());
    return new Digit(value, segments);
  }

  @Override public String toString() {
    return this.value + " " + this.segments.stream()
      .map(Segment::toString)
      .collect(Collectors.joining());
  }
}

record Mapping(Measurement measurement, List<Digit> digits) {

  public Mapping (Measurement measurement, List<Digit> digits) {
    this.digits = new ArrayList<>(digits);
    this.measurement = measurement;
  }

  @Override public String toString() {
    return this.measurement + ": " + this.digits.stream().map(d -> d.value()).toList();
  }

  public void intersect(Mapping other) {
    var commonSignals = Sets.intersection(measurement.signals(), other.measurement.signals());
    for (var iter = digits.iterator(); iter.hasNext();) {
      var digit = iter.next();
      var found = false;
      for (var otherDigit : other.digits) {
        var commonSegments = Sets.intersection(digit.segments(), otherDigit.segments());
        if (!found && commonSignals.size() == commonSegments.size()) {
          found = true;
        }
      }
      if (!found) {
        iter.remove();
      }
    }
  }
}

record LogEntry (List<Measurement> measurements, List<Measurement> outputs) {
  static final List<Digit> digits = ImmutableList.of(
    Digit.from(0, "abcefg"),
    Digit.from(1, "cf"),
    Digit.from(2, "acdeg"),
    Digit.from(3, "acdfg"),
    Digit.from(4, "bcdf"),
    Digit.from(5, "abdfg"),
    Digit.from(6, "abdefg"),
    Digit.from(7, "acf"),
    Digit.from(8, "abcdefg"),
    Digit.from(9, "abcdfg")
  );

  static List<Measurement> splitMeasurements (String input) {
    return Splitter.on(" ").trimResults().splitToStream(input)
      .map(Measurement::from).toList();
  }

  public static LogEntry from(String joinedEntry) {
    var entry = Splitter.on("|").trimResults().split(joinedEntry).iterator();
    var measurements = splitMeasurements(entry.next());
    var outputs = splitMeasurements(entry.next());;
    return new LogEntry(measurements, outputs);
  }

  public int decodeOutput () {
    List<Mapping> mappings = Lists.newArrayList();
    for (var measurement : measurements) {
      var possibleDigits = digits.stream().filter(d -> d.segments().size() == measurement.signals().size()).toList();
      var mapping = new Mapping(measurement, new ArrayList<>(possibleDigits));
      for (var existing : mappings) {
        existing.intersect(mapping);
        mapping.intersect(existing);
      }
      mappings.add(mapping);
    }
    var digits = outputs.stream()
      .flatMap(output -> mappings.stream().filter(m -> m.measurement().equals(output)).limit(1))
      .map(m -> Integer.toString(m.digits().get(0).value()))
      .collect(Collectors.joining());
    
    return Integer.parseInt(digits);
  }
}

public class Day8 {

  public static void main(String[] args) throws Exception {
    var sum = Utils.readInput(Day8.class, "input.txt")
      .map(line -> LogEntry.from(line))
      .mapToInt(entry -> entry.decodeOutput())
      .sum();
    
    System.out.println(sum);
    
  }
}
