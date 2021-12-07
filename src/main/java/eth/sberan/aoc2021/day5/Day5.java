package eth.sberan.aoc2021.day5;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkState;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMultiset;

import eth.sberan.aoc2021.util.Utils;

record Point(int x, int y) {

  static Point fromString (String input) {
    int[] items = Utils.splitInts(input, ",").toArray();
    checkState(items.length == 2, "invalid input: %s", input);
    return new Point(items[0], items[1]);
  }

  @Override public String toString() {
    return String.format("%s,%s", x, y);
  }
}

record LineSegment(Point start, Point end) {
  
  static LineSegment fromString(String input) {
    List<String> items = Splitter.on("->").trimResults().splitToList(input);
    checkState(items.size() == 2, "invalid input: %s", input);
    return new LineSegment(Point.fromString(items.get(0)), Point.fromString(items.get(1)));
  }

  @Override public String toString() {
    return String.format("%s -> %s", start, end);
  }

  public Stream<Point> getPointsOnLine() {
    if (start.x() == end.x()) {
      return IntStream.rangeClosed(Math.min(start.y(), end.y()), Math.max(start.y(), end.y()))
        .mapToObj(y -> new Point(start.x(), y));
    }
    if (start.y() == end.y()) {
      return IntStream.rangeClosed(Math.min(start.x(), end.x()), Math.max(start.x(), end.x()))
        .mapToObj(x -> new Point(x, start.y()));
    }
    return Stream.empty();
  }
}

public class Day5 {

  public static void main(String[] args) throws Exception {
    long count = Utils.readInput(Day5.class, "input.txt")
      .map(LineSegment::fromString)
      .flatMap(line -> line.getPointsOnLine())
      .collect(ImmutableMultiset.toImmutableMultiset())
      .entrySet()
      .stream()
      .filter(x -> x.getCount() >= 2)
      .count();

    System.out.println(count);
  }
}
