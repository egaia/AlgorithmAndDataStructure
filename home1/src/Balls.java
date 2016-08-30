import java.util.Arrays;
import java.util.Collections;

public class Balls {

   enum Color {green, red}

   public static void main (String[] param) {
      // for debugging
   }
   
   static void reorder (Color[] balls) {
      arraySort(balls); //Speed is 118 ms thus too slow
   }

   /** Sorting array of balls
    * @param balls Array of balls to be sorted
    */
   private static void arraySort(Color[] balls){
      Arrays.sort(balls, Collections.reverseOrder());
   }


}

