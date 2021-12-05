package eth.sberan.aoc2021.day2;

import java.util.List;
import eth.sberan.aoc2021.util.Utils;

public class Day2 {
  public static void main(String[] args) throws Exception {
    List<String> lines = Utils.readInput(Day2.class, "input.txt");
    
    int aim = 0;
    int horizontal = 0;
    int depth = 0;

    for (String line : lines) {
      String[] split = line.split(" ");
      String command = split[0];
      int arg = Integer.parseInt(split[1]);
      if (command.equals("forward")) {
          horizontal += arg;
          depth = depth + aim * arg;
      } else if (command.equals("down")) {
          aim += arg;
      } else if (command.equals("up")) {
          aim -= arg;
      }
    }

    System.out.println(horizontal * depth);

  }
}
