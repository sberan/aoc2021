package eth.sberan.aoc2021.day5;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkState;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Streams;

import eth.sberan.aoc2021.util.Utils;

record Point(int x, int y) {

  static Point fromString (String input) {
    var items = Utils.splitInts(input, ",").toArray();
    checkState(items.length == 2, "invalid input: %s", input);
    return new Point(items[0], items[1]);
  }

  @Override public String toString() {
    return String.format("%s,%s", x, y);
  }
}

record LineSegment(Point start, Point end) {
  
  static LineSegment fromString(String input) {
    var items = Splitter.on("->").trimResults().splitToList(input);
    checkState(items.size() == 2, "invalid input: %s", input);
    return new LineSegment(Point.fromString(items.get(0)), Point.fromString(items.get(1)));
  }

  @Override public String toString() {
    return String.format("%s -> %s", start, end);
  }

  IntStream lineRange(Function<Point, Integer> fn) {
    var from = fn.apply(start);
    var to = fn.apply(end);
    if (from <= to) {
      return IntStream.rangeClosed(from, to);
    } else {
      return IntStream.rangeClosed(to, from).boxed()
        .sorted(Collections.reverseOrder())
        .mapToInt(x -> x.intValue());
    }
  }

  public Stream<Point> getPointsOnLine() {
    if (start.x() == end.x()) {
      return lineRange(l -> l.y()).mapToObj(y -> new Point(start.x(), y));
    }
    if (start.y() == end.y()) {
      return lineRange(l -> l.x()).mapToObj(x -> new Point(x, start.y()));
    }

    if (Math.abs(end.y() - start.y()) == Math.abs(end.x() - start.x())) {
      var xs = lineRange(l -> l.x());
      var ys = lineRange(l -> l.y());
      return Streams.zip(xs.boxed(), ys.boxed(), (x, y) -> new Point(x, y));
    }
    return Stream.empty();
  }
}

public class Day5 {
  public static void main(String[] args) throws Exception {
    var points = Utils.readInput(Day5.class, "input.txt")
      .map(LineSegment::fromString)
      .flatMap(line -> line.getPointsOnLine())
      .collect(ImmutableMultiset.toImmutableMultiset());
    
    var count = points.entrySet()
      .stream()
      .filter(x -> x.getCount() >= 2)
      .count();

    System.out.println(count);
  }
}
