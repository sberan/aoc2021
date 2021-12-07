package eth.sberan.aoc2021.day4;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import eth.sberan.aoc2021.util.Utils;
import static java.util.stream.Collectors.toList;

import static eth.sberan.aoc2021.util.Utils.forEachWithIndex;


record Space (int value, boolean marked) {
    
  public Space (int value) { this(value, false); }

  @Override public String toString() {
    var marker = this.marked ? "*" : " ";
    return Strings.padStart(""+this.value+marker, 4, ' ');
  }

  public Space mark() {
    return new Space(this.value, true);
  }
}

record Mark(int rowNum, int colNum) {}

class Board {
  private Optional<Mark> lastMark = Optional.empty();
  private final List<List<Space>> spaces = Lists.newArrayList();

  void addRow(String input) {
    spaces.add(Utils.splitInts(input, " ")
      .mapToObj(i -> new Space(i))
      .collect(toList()));
  }

  void mark(int number) {
    if (isWinner()) {
      return;
    }

    forEachWithIndex(spaces, (row, rowNum) -> {
      forEachWithIndex(row, (space, colNum) -> {
        if (space.value() == number) {
          row.set(colNum, space.mark());
          lastMark = Optional.of(new Mark(rowNum, colNum));
        }
      });
    });
  }

  boolean isWinner () {
    if (this.lastMark.isEmpty()) {
      return false;
    }
    var lastMark = this.lastMark.get();
    return spaces.get(lastMark.rowNum()).stream().allMatch(m -> m.marked())
      || spaces.stream().allMatch(row -> row.get(lastMark.colNum()).marked());
  }

  int score () {
    if (!isWinner() || this.lastMark.isEmpty()) {
      return 0;
    }
    var lastMark = this.lastMark.get();
    var lastValue = spaces.get(lastMark.rowNum()).get(lastMark.colNum()).value();
    return lastValue * spaces.stream()
      .flatMap(row -> row.stream())
      .filter(space -> !space.marked())
      .mapToInt(space -> space.value())
      .sum();
  }

  @Override public String toString() {
    return spaces.stream()
      .map(row -> row.stream().map(Space::toString).collect(Collectors.joining()))
      .collect(Collectors.joining("\n"));
  }
}

public class Day4 {
  public static void main(String[] args) throws Exception {
    var inputLines = Utils.readInput(Day4.class, "input.txt").collect(toList());
    var numbers = Utils.splitInts(inputLines.get(0), ",").toArray();
    var boards = Lists.<Board>newArrayList();

    for (String line : inputLines.subList(1, inputLines.size() - 1)) {
      if (line.equals("")) {
        boards.add(new Board());
      } else {
        var board = boards.get(boards.size() - 1);
        board.addRow(line);
      }
    }

    for (int number: numbers) {
      for (Board board : boards) {
        board.mark(number);
        if (boards.stream().allMatch(b -> b.isWinner())){
          System.out.println(board + "\n");
          System.out.println(board.score());
          return;
        }
      }
    }

    boards.forEach(board -> {
      System.out.println(board + "\n");
    });
  }
}