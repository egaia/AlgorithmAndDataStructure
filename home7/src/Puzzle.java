import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * References:
 * http://www.dreamincode.net/forums/topic/105427-cryptarithmetic-solver-using-java/
 * http://forum.codecall.net/topic/55991-sendmoremoney-solution/
 * https://coderanch.com/t/595306/java/Optimizing-send-money-code
 * http://www.codeproject.com/Articles/176768/Cryptarithmetic
 */

public class Puzzle {

   /**
    * HashMap for storing character and integer relations
    */
   private static HashMap<Character, Integer> hashMap = new HashMap<>();

   /**
    * Unique characters
    */
   private static List<Character> uniqueCharacters = new ArrayList<>();

   /**
    * Array of chars that can't be 0
    */
   private static char[] noNulls = new char[3];

   /**
    * Container for three words
    */
   private static String[] words = new String[3];

   /**
    * Boolean if task is solved or not
    */
   private static boolean solved = false;

   /**
    * Count of solutions
    */
   private static int countSolutions = 0;


   /** Solve the word puzzle.
    * @param args three words (addend1, addend2 and sum)
    */
   public static void main (String[] args) {
      if(args.length != 3)
         throw new RuntimeException("There has to be 3 parameters");

      for (String s : args){
         if(s.length() > 18)
            throw new RuntimeException("There are more than 18 letters in this word: " +s);
         for (char c : s.toCharArray()){
            if(!Character.isLetter(c))
               throw new RuntimeException("There are other symbols besides letters in this word: " +s);
         }
      }

      String inp1 = args[0].toUpperCase();
      String inp2 = args[1].toUpperCase();
      String inp3 = args[2].toUpperCase();

      //Mark the characters that can't be 0
      noNulls[0] = inp1.charAt(0);
      noNulls[1] = inp2.charAt(0);
      noNulls[2] = inp3.charAt(0);

      //Mark the words
      words[0] = inp1;
      words[1] = inp2;
      words[2] = inp3;

      solvePuzzle(inp1,inp2,inp3);
   }

   private static void solvePuzzle(String first, String second, String sum){
      hashMap = new HashMap<>();
      uniqueCharacters = new ArrayList<>();
      solved = false;
      countSolutions =0;

      findUniqueLetters(first,second,sum);

      //Basic hashmap creation
      for(Character c : uniqueCharacters){
         hashMap.put(c, 0);
      }

      System.out.printf("%s %s %s%n", words[0], words[1], words[2]);
      System.out.println(uniqueCharacters);

      findNumbers(0);

      System.out.println("The words are processed");
      if (solved) {
         System.out.println("Number of correct solutions: " + countSolutions);
      } else {
         System.out.println("This is not solvable exercise");
      }
      System.out.println();

   }

   /**
    * Finds unique characters from the inputs
    * @param first first word to search letters from
    * @param second second word to search letters from
    * @param sum the sum of words to search letters from
    */
   private static void findUniqueLetters(String first, String second, String sum){
      for(char c : first.toCharArray()){
         if (!uniqueCharacters.contains(c))
            uniqueCharacters.add(c);
      }

      for(char c : second.toCharArray()){
         if(!uniqueCharacters.contains(c))
            uniqueCharacters.add(c);
      }

      for(char c : sum.toCharArray()){
         if(!uniqueCharacters.contains(c))
            uniqueCharacters.add(c);
      }
   }

   /**
    * Generates combinations of numbers and if each character has a number, tries to solve the puzzle with these
    */
   private static void findNumbers(int count) {

      if (count >= uniqueCharacters.size()) {
         checkIfFits();
         return;
      }

      OUTER:
      for (int nr = 0; nr <= 9; nr++) {

         hashMap.put(uniqueCharacters.get(count), nr);
         for (int i = 0; i < count; i++) {
            //check if it is unique
            if (hashMap.get(uniqueCharacters.get(i)) == nr)
               continue OUTER;
         }
         findNumbers(count + 1);
      }
   }

   /**
    * Checks if letters that can't be 0, are not 0.
    * If combination is correct, it is stated as correct and it is printed out.
    */
   private static void checkIfFits() {
      long[] values = new long[]{0, 0, 0};

      if (hashMap.get(noNulls[0]) == 0) {
         return;
      } else if (hashMap.get(noNulls[1]) == 0) {
         return;
      } else if (hashMap.get(noNulls[2]) == 0) {
         return;
      }

      convToValue(values);

      long answer = values[0] + values[1];
      if (answer == values[2]) {

         solved = true;
         countSolutions++;
         System.out.println(hashMap);
         System.out.printf("%d + %d = %d%n", values[0], values[1], values[2]);
      }
   }

   /**
    * Creates numeric values for words, using character values
    *
    * @param values Array that holds word values
    */
   private static void convToValue(long[] values) {

      long nr = 10;
      int whatString = 0;

      for (String s : words) {
         nr = (long) Math.pow(nr, s.length() - 1);
         for (char c : s.toCharArray()) {
            values[whatString] += hashMap.get(c) * nr;
            nr = nr / 10;
         }
         nr = 10;
         whatString++;
      }
   }

}

