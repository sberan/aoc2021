package eth.sberan.aoc2021.day9;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import eth.sberan.aoc2021.util.Utils;

record Point(int row, int col) {}


public class Day9 {
  static Stream<Point> neighborsOf (int[][] grid, Point point) {
    return Stream.of(
      new Point(point.row() - 1, point.col()),
      new Point(point.row() + 1, point.col()),
      new Point(point.row(), point.col() - 1),
      new Point(point.row(), point.col() + 1)
    )
    .filter(p -> p.row() >= 0 
      && p.row() < grid.length
      && p.col() >= 0
      && p.col() < grid[p.row()].length);
  }

  static int valueAt(int[][] grid, Point point) {
    return grid[point.row()][point.col()];
  }

  static Set<Point> expandBasin (int[][] grid, Set<Point> basin) {
    var newNeighbors = basin.stream().flatMap(point -> {
      return neighborsOf(grid, point)
        .filter(n -> !basin.contains(n))
        .filter(n -> valueAt(grid, n) != 9);
    }).collect(Collectors.toSet());

    if (newNeighbors.isEmpty()) {
      return basin;
    }
    Set<Point> newBasin = Sets.union(basin, newNeighbors);
    return expandBasin(grid, newBasin);
  }

  public static void main(String[] args) throws Exception {
    var grid = Utils.readInput(Day9.class, "input.txt")
      .map(line -> Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray())
      .toArray(int[][]::new);

    var lowPoints = IntStream.range(0, grid.length)
      .mapToObj(rowNum -> {
        int[] row = grid[rowNum];
        return IntStream.range(0, row.length).mapToObj(colNum -> {
          var point = new Point(rowNum, colNum);
          var cell = valueAt(grid, point);
          var neighbors = neighborsOf(grid, point);
          var min = neighbors.mapToInt(p -> valueAt(grid, p)).min().getAsInt();
          if (cell < min) {
            return Stream.of(point);
          }
          return Stream.<Point>empty();
        }).flatMap(x -> x);
      }).flatMap(x -> x);

    var basins = lowPoints.map(p -> expandBasin(grid, Set.of(p)))
      .sorted(Ordering.natural().reverse().onResultOf(Set::size));
    
    var total = basins.limit(3)
      .mapToInt(b -> b.size())
      .reduce(1, (a, b) -> a * b);

    System.out.println(total);
    
  }
}
