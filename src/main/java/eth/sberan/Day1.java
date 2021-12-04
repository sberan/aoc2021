package eth.sberan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Streams;

import org.jooq.lambda.Seq;
import org.jooq.lambda.Window;

public class Day1 {
  public static void main(String[] args) throws Exception {
    Path inputFile = Path.of(Day1.class.getResource("input.txt").toURI());
    Stream<Integer> measurements = Files.readAllLines(inputFile).stream()
      .map((line) -> Integer.parseInt(line));

    
    Seq<List<Integer>> pairs = Seq.seq(measurements)
      .sliding(2)
      .map(w -> w.collect(Collectors.toList()));

    long countDeeper = pairs
      .filter(pair -> pair.get(0) < pair.get(1))
      .count();

    System.out.println(countDeeper);
  }
}
