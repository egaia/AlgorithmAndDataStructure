import java.util.*;

public class Answer {

   public static void main (String[] param) {

      // conversion double -> String
      String s1 = String.valueOf(2016.);
      System.out.println(s1);
      // conversion String -> int
      try{
         int n1 = Integer.parseInt("2016");
         System.out.println(n1);
      } catch (NumberFormatException ex){
         ex.printStackTrace();
      }

      // "hh:mm:ss"
      Calendar calendar = Calendar.getInstance();
      String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
      System.out.println(time);
      // cos 45 deg
      System.out.println(Math.cos(Math.toRadians(45)));

      // table of square roots
      for (int i = 0; i<=100;i= i+5){
         System.out.println("Square root of " + i + " is:" + Math.sqrt(i));
      }

      String firstString = "ABcd12";
      String result = reverseCase (firstString);
      System.out.println ("\"" + firstString + "\" -> \"" + result + "\"");

      // reverse string

      String s = "How  many	 words   here";
      int nw = countWords (s);
      System.out.println (s + "\t" + String.valueOf (nw));

      // pause. COMMENT IT OUT BEFORE JUNIT-TESTING!
      /*long startTime = System.nanoTime();
      try{
         Thread.sleep(3000);
      }catch(InterruptedException ex){
         ex.printStackTrace();
      }
      long endTime = System.nanoTime();
      System.out.println(endTime-startTime);*/

      final int LSIZE = 100;
      ArrayList<Integer> randList = new ArrayList<>(LSIZE);
      Random generaator = new Random();
      for (int i=0; i<LSIZE; i++) {
         randList.add (generaator.nextInt(1000));
      }

      // minimal element
      System.out.println("Minimal element of list is " + minimum(randList));

      // HashMap tasks:
      //    create
      HashMap<String, String> hashMap = new HashMap<>();
      hashMap.put("I001", "Esimene õppekava");
      hashMap.put("I002", "Teine õppeaine");
      hashMap.put("I003", "Kolmas õppeaine");
      hashMap.put("I004", "Neljas õppeaine");
      hashMap.put("I005", "Viies õppeaine");
      //    print all keys
      System.out.println("All the keys for hashmap are");
      hashMap.keySet().forEach(System.out::println);
      System.out.println();
      //    remove a key
      hashMap.remove("I001");
      //    print all pairs
      for(Map.Entry<String, String> entry : hashMap.entrySet()){
         System.out.println("K- " + entry.getKey() + "  V- " + entry.getValue());
      }

      System.out.println ("Before reverse:  " + randList);
      reverseList (randList);
      System.out.println ("After reverse: " + randList);

      System.out.println ("Maximum: " + maximum (randList));
   }

   /** Finding the minimum element.
    * @param a Collection of Comparable elements
    * @return minimum element
    * @throws NoSuchElementException if <code> a </code> is empty.
    */
   private static <T extends Object & Comparable<? super T>> T minimum(Collection<? extends T> a) throws NoSuchElementException{
      if(!a.isEmpty()){
         T minimum = null;
         for(T t : a){
            minimum = (minimum == null || t.compareTo(minimum) < 0) ? t : minimum;
         }
         return minimum;
      }else{
         throw new NoSuchElementException();
      }
   }

   /** Finding the maximal element.
    * @param a Collection of Comparable elements
    * @return maximal element.
    * @throws NoSuchElementException if <code> a </code> is empty.
    */
   static  <T extends Object & Comparable<? super T>> T maximum(Collection<? extends T> a) throws NoSuchElementException {
      if (!a.isEmpty()) {
         T maximum = null;
         for (T t : a){
            maximum = (maximum == null || t.compareTo(maximum) > 0) ? t : maximum;
         }
         return maximum;
      }else{
         throw new NoSuchElementException();
      }
   }

   /** Counting the number of words. Any number of any kind of
    * whitespace symbols between words is allowed.
    * @param text text
    * @return number of words in the text
    */
    static int countWords (String text) {

      return new StringTokenizer(text).countTokens();
   }

   /** Case-reverse. Upper -> lower AND lower -> upper.
    * @param s string
    * @return processed string
    */
    static String reverseCase (String s) {
      StringBuilder stringBuffer = new StringBuilder();
      for (char letter : s.toCharArray()) {
         stringBuffer.append(Character.isUpperCase(letter) ? Character.toLowerCase(letter) : Character.toUpperCase(letter));
      }
      return stringBuffer.toString();
   }

   /** List reverse. Do not create a new list.
    * @param list list to reverse
    */
    static <T> void reverseList (List<T> list)
      throws UnsupportedOperationException {
         if(!list.isEmpty()){
            Collections.reverse(list);
         }else{
            throw new UnsupportedOperationException();
         }
   }
}
