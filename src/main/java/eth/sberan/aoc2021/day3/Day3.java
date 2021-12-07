package eth.sberan.aoc2021.day3;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import eth.sberan.aoc2021.util.Utils;

public class Day3 {
  public enum CharFrequency {
    MIN(1),
    MAX(-1);

    public final int comparator;

    CharFrequency(int comparator) {
      this.comparator = comparator;
    }
  }

  static String charAtIndex(List<String> values, int index, CharFrequency sort) {
    Multiset<String> chars = HashMultiset.create();
    for (String entry : values) {
      chars.add(entry.substring(index, index + 1));
    }
    return chars.entrySet()
      .stream()
      .sorted((a, b) -> {
        if (a.getCount() == b.getCount()) {
          return a.getElement().compareTo(b.getElement()) * sort.comparator;
        }
        return Integer.compare(a.getCount(), b.getCount()) * sort.comparator;
      })
      .map(x -> x.getElement())
      .findFirst()
      .get();
  }

  static String frequencyString(List<String> dictionary, CharFrequency charFrequency, boolean filterPrevious) {
    String result = "";
    for (int i = 0; i < dictionary.get(0).length(); i++) {
      if (filterPrevious && i > 0) {
        final int ix = i;
        final String prevResult = result.substring(ix - 1, ix);
        dictionary = dictionary.stream()
          .filter(word -> word.substring(ix - 1 , ix).equals(prevResult))
          .collect(Collectors.toList());
        if (dictionary.size() == 1) {
          return dictionary.get(0);
        }
      }
      result += charAtIndex(dictionary, i, charFrequency);
    }
    return result;
  }


  public static void main(String[] args) throws Exception {
    List<String> diagnosticReport = Utils.readInput(Day3.class, "input.txt")
      .collect(Collectors.toList());
    
    String gamma = frequencyString(diagnosticReport, CharFrequency.MAX, false);
    String epsilon = frequencyString(diagnosticReport, CharFrequency.MIN, false);
    String oxygen = frequencyString(diagnosticReport, CharFrequency.MAX, true);
    String co2 = frequencyString(diagnosticReport, CharFrequency.MIN, true);

    System.out.println("gamma: " + gamma);
    System.out.println("epsilon: " + epsilon);
    System.out.println("oxygen: " + oxygen);
    System.out.println("co2: " + co2);
    System.out.println(Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2));
    System.out.println(Integer.parseInt(oxygen, 2) * Integer.parseInt(co2, 2));
  }
}
