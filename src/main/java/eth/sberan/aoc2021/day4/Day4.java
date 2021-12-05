package eth.sberan.aoc2021.day4;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import eth.sberan.aoc2021.util.Utils;
import static java.util.stream.Collectors.toList;

import java.util.AbstractMap;

class Board {
  Entry<Integer, Integer> lastMark = null;

  class Space {
    int value;
    boolean marked;

    Space (int value) {
      this.value = value;
    }

    @Override public String toString() {
      String marker = this.marked ? "*" : " ";
      return Strings.padStart(""+this.value+marker, 4, ' ');
    }
  }

  final List<List<Space>> spaces = Lists.newArrayList();

  void addRow(String input) {
    spaces.add(Utils.splitInts(input, " ").stream()
      .map(i -> new Space(i))
      .collect(toList()));
  }

  void mark(int number) {
    int rowNum = 0;
    for (List<Space> row : this.spaces) {
      int colNum = 0;
      for (Space space : row) {
        if (space.value == number) {
          space.marked = true;
          lastMark = new AbstractMap.SimpleEntry<>(rowNum, colNum);
        }
        colNum++;
      }
      rowNum++;
    }
  }

  boolean isWinner () {
    if (lastMark == null) {
      return false;
    }
    return spaces.get(lastMark.getKey()).stream().allMatch(m -> m.marked)
      || spaces.stream().allMatch(row -> row.get(lastMark.getValue()).marked);
  }

  int score () {
    if (!isWinner()) {
      return 0;
    }

    int lastValue = spaces.get(lastMark.getKey()).get(lastMark.getValue()).value;
    return lastValue * spaces.stream()
      .flatMap(row -> row.stream())
      .filter(space -> !space.marked)
      .mapToInt(space -> space.value)
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
    List<String> inputLines = Utils.readInput(Day4.class, "input.txt");
    List<Integer> numbers = Utils.splitInts(inputLines.get(0), ",");
    
    List<Board> boards = Lists.newArrayList();
    for (String line : inputLines.subList(1, inputLines.size() - 1)) {
      if (line.equals("")) {
        boards.add(new Board());
      } else {
        Board board = boards.get(boards.size() - 1);
        board.addRow(line);
      }
    }

    for (Integer number: numbers) {
      for (Board board : boards) {
        board.mark(number);
        if (board.isWinner()){
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