import static org.junit.Assert.*;
import org.junit.Test;

public class BallsTest {

   /** Number of milliseconds allowed to spend for sorting 1 million elements */
   public static int threshold = 25;

   /** Test data */
   static Balls.Color[] balls = null;

   /** Number of red balls */
   static int rCount = 0;

   /** Correctness check for the result */
   static boolean check (Balls.Color[] balls, int r) {
       int len = balls.length;
       if (len == 0)
          return true;
       for (int i=0; i < r; i++)
          if (balls[i] != Balls.Color.red)
             return false;
       for (int i=r; i < len; i++)
          if (balls[i] != Balls.Color.green)
             return false;
       return true;
   } // check

    @Test (timeout=1000)
    public void testFunctionality() {
      balls = new Balls.Color [100000];
      rCount = 0;
      for (int i=0; i < balls.length; i++) {
         if (Math.random() < 0.5) {
            balls[i] = Balls.Color.red;
            rCount++;
         } else {
            balls[i] = Balls.Color.green;
         }   
      }
      Balls.reorder (balls);
      assertTrue ("Result incorrect", check (balls, rCount));
    }

    @Test (timeout=1000)
    public void testShort() {
      balls = new Balls.Color [1];
      if (Math.random() < 0.5) {
         balls[0] = Balls.Color.red;
         rCount = 1;
      } else {
         balls[0] = Balls.Color.green;
         rCount = 0;
      }
      Balls.reorder (balls);
      assertTrue ("One element array not working", check (balls, rCount));
      balls = new Balls.Color [0];
      rCount = 0;
      Balls.reorder (balls);
      assertTrue ("Zero element array not working", check (balls, rCount));
      balls = new Balls.Color [100000];
      rCount = 0;
      for (int i=0; i < balls.length; i++) {
         if (Math.random() < 0.5) {
            balls[i] = Balls.Color.red;
            rCount++;
         } else {
            balls[i] = Balls.Color.green;
         }
      }
      Balls.reorder (balls);
      assertTrue ("Result incorrect", check (balls, rCount));
    }

    @Test (timeout=1000)
    public void testAllGreen() {
      balls = new Balls.Color [100000];
      rCount = 0;
      for (int i=0; i < balls.length; i++) {
            balls[i] = Balls.Color.green;
      }
      Balls.reorder (balls);
      assertTrue ("Result incorrect for all green", check (balls, rCount));
      balls = new Balls.Color [100000];
      rCount = 0;
      for (int i=0; i < balls.length; i++) {
         if (Math.random() < 0.5) {
            balls[i] = Balls.Color.red;
            rCount++;
         } else {
            balls[i] = Balls.Color.green;
         }
      }
      Balls.reorder (balls);
      assertTrue ("Result incorrect", check (balls, rCount));
    }

    @Test (timeout=1000)
    public void testAllRed() {
      balls = new Balls.Color [100000];
      rCount = 0;
      for (int i=0; i < balls.length; i++) {
            balls[i] = Balls.Color.red;
            rCount++;
      }
      Balls.reorder (balls);
      assertTrue ("Result incorrect for all red", check (balls, rCount));
      balls = new Balls.Color [100000];
      rCount = 0;
      for (int i=0; i < balls.length; i++) {
         if (Math.random() < 0.5) {
            balls[i] = Balls.Color.red;
            rCount++;
         } else {
            balls[i] = Balls.Color.green;
         }
      }
      Balls.reorder (balls);
      assertTrue ("Result incorrect", check (balls, rCount));
    }

    @Test (timeout=1000)
    public void testSpeed() {
      balls = new Balls.Color [1000000];
      rCount = 0;
      for (int i=0; i < balls.length; i++) {
         if (Math.random() < 0.5) {
            balls[i] = Balls.Color.red;
            rCount++;
         } else {
            balls[i] = Balls.Color.green;
         }
      }
      long t0 = System.currentTimeMillis();
      Balls.reorder (balls);
      long t1 = System.currentTimeMillis();
      int delta = (int)(t1-t0);
      assertTrue ("Result incorrect", check (balls, rCount));
      assertTrue ("Too slow: "+ delta, delta < threshold);
      System.out.println ("Time spent: " + delta + " ms");
    }

   @Test (expected=RuntimeException.class)
   public void testNullArray() {
      Balls.reorder (null);
   }
}

