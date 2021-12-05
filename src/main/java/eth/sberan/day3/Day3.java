package eth.sberan.day3;

import java.util.List;
import eth.sberan.util.Utils;

public class Day3 {

  public static void main(String[] args) throws Exception {
    List<String> diagnosticReport = Utils.readInput(Day3.class, "example.txt");
    
    String gamma = "";
    String epsilon = "";

    for (int i = 0; i < diagnosticReport.get(0).length(); i++) {
      List<String> chars = Utils.charsAtIndex(diagnosticReport, i);
      gamma += chars.get(0);
      epsilon += chars.get(chars.size() - 1);
    }
    
    System.out.println("gamma: " + gamma);
    System.out.println("epsilon: " + epsilon);
    System.out.println(Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2));
  }
}
