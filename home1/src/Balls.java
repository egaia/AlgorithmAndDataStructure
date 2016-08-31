import java.util.Arrays;
import java.util.Collections;

public class Balls {

   enum Color {green, red}

   public static void main (String[] param) {
      // for debugging
   }
   
   static void reorder (Color[] balls) {
      //arraySort(balls); //Speed is 118 ms thus too slow
      countSort(balls); //~2ms
   }

   /** Sorting array of balls
    * @param balls Array of balls to be sorted
    */
   @SuppressWarnings("unused")
   private static void arraySort(Color[] balls){
      Arrays.sort(balls, Collections.reverseOrder());
   }

   /** Sorting array of balls by counting the number of balls
    * @param balls Array of balls to be sorted
    */
   private static void countSort(Color[] balls){
      int numberOfRedBalls = 0;

      //Count all the red balls there are. Red because it has to be the first set of balls.
      for (Color ball : balls) {
         if (ball.equals(Color.red)) {
            numberOfRedBalls++;
         }
      }
      //If the count of the red balls are same as number of balls, then they are all red
      //Else if the number of red balls are 0, then all the balls must be green
      if (numberOfRedBalls == balls.length){
         return;
      } else if(numberOfRedBalls == 0){
         return;
      }

      //From first ball to the count of red balls, these balls are red.
      for(int i = 0; i<numberOfRedBalls; i++){
         balls[i] = Color.red;
      }
      //from count of red balls to the count of given set of balls are green
      for(int i= numberOfRedBalls; i< balls.length; i++){
         balls[i] = Color.green;
      }
   }
}

