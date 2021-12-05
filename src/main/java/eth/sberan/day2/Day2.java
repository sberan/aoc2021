package eth.sberan.day2;

import java.util.List;
import java.util.stream.Collectors;

import eth.sberan.util.Utils;


public class Day2 {
  public static void main(String[] args) throws Exception {
    List<String> lines = Utils.readInput(Day2.class).collect(Collectors.toList());
    
    int horizontal = 0;
    int depth = 0;

    for (String line : lines) {
      String[] split = line.split(" ");
      String command = split[0];
      int arg = Integer.parseInt(split[1]);
      if (command.equals("forward")) {
          horizontal += arg;
      }
      if (command.equals("down")) {
          depth += arg;
      }
      if (command.equals("up")) {
          depth -= arg;
      }
      System.out.println("horizontal: " + horizontal + ", depth: " + depth);
    }

    System.out.println(horizontal * depth);

  }
}
