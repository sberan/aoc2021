package eth.sberan.aoc2021.day10;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import eth.sberan.aoc2021.util.Utils;

enum Token {
  OPEN_PAREN('('),
  CLOSE_PAREN(')', 3),
  OPEN_CURLY_BRACE('{'),
  CLOSE_CURLY_BRACE('}', 1197),
  OPEN_ANGLE_BRACKET('<'),
  CLOSE_ANGLE_BRACKET('>', 25137),
  OPEN_SQUARE_BRACKET('['),
  CLOSE_SQUARE_BRACKET(']', 57);

  char value;
  int points = 0;

  Token(char value) { this.value = value;}
  Token(char value, int points) { this.value = value; this.points = points; }

  static Token from(char value) {
    var token = Arrays.stream(Token.values()).filter(x -> x.value == value).findFirst();
    if (!token.isPresent()) {
      throw new RuntimeException("unknown token: " + value);
    }
    return token.get();
  }

  boolean isOpen () {
    return this.name().startsWith("OPEN");
  }

  Token getClose () {
    Preconditions.checkState(this.isOpen(), "%s: not an opening token", this.name());
    return Token.valueOf(this.name().replace("OPEN", "CLOSE"));
  }

  boolean isClose () {
    return this.name().startsWith("CLOSE");
  }

  Token getOpen () {
    Preconditions.checkState(this.isClose(), "%s: not an closing token", this.name());
    return Token.valueOf(this.name().replace("CLOSE", "OPEN"));
  }
}


record Error(int points, String message) {}

public class Day10 {
  static Optional<Error> parse(String line) {
    var tokens = line.chars().mapToObj(x -> Token.from((char)x)).toList();
    var stack = new LinkedList<Token>();
    for (var token : tokens) {
      if (token.isOpen()) {
        stack.push(token);
      } else {
        var expectedClose = stack.pop().getClose();
        if (!expectedClose.equals(token)) {
          return Optional.of(new Error(token.points, String.format("%s: expected '%s' but found '%s' instead.", line, expectedClose.value, token.value)));
        }
      }
    }
    return Optional.empty();
  }

  public static void main(String[] args) throws Exception {
    var input = Utils.readInput(Day10.class, "input.txt");
    var sum = input.map(line -> parse(line))
      .filter(Optional::isPresent).mapToInt(x -> x.get().points()).sum();

    System.out.println(sum);
  }
}
