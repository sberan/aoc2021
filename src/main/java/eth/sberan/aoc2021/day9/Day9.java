package eth.sberan.aoc2021.day9;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import eth.sberan.aoc2021.util.Utils;

record Point(int row, int col) {}

public class Day9 {
  static IntStream safeGet(int[][] grid, int row, int col) {
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[row].length) {
      return IntStream.empty();
    }
    return IntStream.of(grid[row][col]);
  }

  public static void main(String[] args) throws Exception {
    var grid = Utils.readInput(Day9.class, "input.txt")
      .map(line -> Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray())
      .toArray(int[][]::new);

    Stream<Point> lowPoints = IntStream.range(0, grid.length)
      .mapToObj(rowNum -> {
        int[] row = grid[rowNum];
        return IntStream.range(0, row.length).mapToObj(colNum -> {
          var cell = grid[rowNum][colNum];
          var neighbors = Stream.of(
            safeGet(grid, rowNum, colNum + 1),
            safeGet(grid, rowNum, colNum - 1),
            safeGet(grid, rowNum - 1, colNum),
            safeGet(grid, rowNum + 1, colNum)
          ).flatMapToInt(x -> x);
          var min = neighbors.min().getAsInt();
          if (cell < min) {
            return Stream.of(new Point(rowNum, colNum));
          }
          return Stream.<Point>empty();
        }).flatMap(x -> x);
      }).flatMap(x -> x);

    var total = lowPoints.mapToInt(p -> grid[p.row()][p.col()] + 1).sum();
    System.out.println(total);
    
    
  }
}
