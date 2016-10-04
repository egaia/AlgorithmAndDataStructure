import java.util.*;

/** This class represents fractions of form n/d where n and d are long integer 
 * numbers. Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction> {

   private Long numerator;
   private Long denominator;

   /** Constructor.
    * @param numerator numerator
    * @param denominator denominator > 0
    */
   public Lfraction (long numerator, long denominator) {
      if(denominator == 0){
         throw new RuntimeException("Denominator is zero");
      }
      if(denominator <0){
         numerator *= -1;
         denominator *= -1;
      }
      this.numerator = numerator;
      this.denominator = denominator;
   }

   /** Public method to access the numerator field. 
    * @return numerator
    */
   public long getNumerator() {
      return numerator;
   }

   /** Public method to access the denominator field. 
    * @return denominator
    */
   public long getDenominator() { 
      return denominator;
   }

   /** Conversion to string.
    * @return string representation of the fraction
    */
   @Override
   public String toString() {
      return this.numerator + "/" + this.denominator;
   }

   /**
    * The greatest common denominator
    * @param numerator numerator part of fraction
    * @param denominator denominator part of fraction
    * @return the greatest common denominator
    */
   public static long gcm(long numerator, long denominator) {
      return denominator == 0 ? numerator : gcm(denominator, numerator % denominator); // Not bad for one line of code :)
   }

   public Pair<Long, Long> asFraction(long a, long b) {
      long gcm = gcm(a, b);
      Pair<Long,Long> ret = new Pair<>(a / gcm, b / gcm);
      return ret;
   }

   /** Equality test.
    * @param m second fraction
    * @return true if fractions this and m are equal
    */
   @Override
   public boolean equals (Object m) {
      Pair<Long, Long> eq1 = asFraction(this.numerator, this.denominator);
      Pair<Long, Long> eq2 = asFraction(((Lfraction) m).numerator, ((Lfraction) m).denominator);
      if(eq1.getFirst() == eq2.getFirst() && eq1.getSecond() == eq2.getSecond()){
         return true;
      }else{
         return false;
      }
   }

   /** Hashcode has to be equal for equal fractions.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      int hash = (int) (this.numerator+this.denominator);
      return hash;
   }

   /** Sum of fractions.
    * @param m second addend
    * @return this+m
    */
   public Lfraction plus (Lfraction m) {
       // TODO!!!
      return m;
   }

   /** Multiplication of fractions.
    * @param m second factor
    * @return this*m
    */
   public Lfraction times (Lfraction m) {
      return null; // TODO!!!
   }

   /** Inverse of the fraction. n/d becomes d/n.
    * @return inverse of this fraction: 1/this
    */
   public Lfraction inverse() {
      Long n = this.numerator;
      Long d = this.denominator;
      this.numerator = d;
      this.denominator = n;
      if(this.denominator == 0L){
         throw new RuntimeException("Zero Inverse will not go!");
      }else if(this.denominator < 0L){
         this.denominator = this.denominator * -1;
         this.numerator = this. numerator * -1;
         return this;
      }
      else{
         return this;
      }
   }

   /** Opposite of the fraction. n/d becomes -n/d.
    * @return opposite of this fraction: -this
    */
   public Lfraction opposite() {
      this.numerator = this.numerator*-1;
      return this;
   }

   /** Difference of fractions.
    * @param m subtrahend
    * @return this-m
    */
   public Lfraction minus (Lfraction m) {
      return null; // TODO!!!
   }

   /** Quotient of fractions.
    * @param m divisor
    * @return this/m
    */
   public Lfraction divideBy (Lfraction m) {
      if(m.numerator == 0L){
         throw new RuntimeException("Division by zero");
      }
      return null;
   }

   /** Comparision of fractions.
    * @param m second fraction
    * @return -1 if this < m; 0 if this==m; 1 if this > m
    */
   @Override
   public int compareTo (Lfraction m) {
      if(this.numerator.compareTo(m.numerator) == 0 && this.denominator.compareTo(m.denominator) == 0) {
         return 0;
      }
      return 3;
   }

   /** Clone of the fraction.
    * @return new fraction equal to this
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      return new Lfraction(this.numerator, this.denominator);
   }

   /** Integer part of the (improper) fraction. 
    * @return integer part of this fraction
    */
   public long integerPart() {
      boolean isNull = false;
      if(this.numerator < 0){
         isNull = true;
         this.numerator = this.numerator * (-1);
      }
      Long a = this.numerator;
      Long b = this.denominator;
      int i = 0;
      while(this.numerator>=this.denominator){
         a = a - b;
         this.numerator = a;
         i = i + 1;
      }
      if(isNull){
         return i*-1;
      }else{
         return i;
      }
   }

   /** Extract fraction part of the (improper) fraction
    * (a proper fraction without the integer part).
    * @return fraction part of this fraction
    */
   public Lfraction fractionPart() {
      return null; // TODO!!!
   }

   /** Approximate value of the fraction.
    * @return numeric value of this fraction
    */
   public double toDouble() {
      double a = this.numerator;
      double b = this.denominator;
      return a/b;
   }

   /** Double value f presented as a fraction with denominator d > 0.
    * @param f real number
    * @param d positive denominator for the result
    * @return f as an approximate fraction of form nd
    */
   public static Lfraction toLfraction (double f, long d) {
      return null; // TODO!!!
   }

   /** Conversion from string to the fraction. Accepts strings of form
    * that is defined by the toString method.
    * @param s string form (as produced by toString) of the fraction
    * @return fraction represented by s
    */
   public static Lfraction valueOf (String s) {
      String[] splits = s.split("/");
      Long numerator = Long.parseLong(splits[0]);
      Long denominator = Long.parseLong(splits[1]);
      return new Lfraction(numerator, denominator);
   }
}

class Pair<T, U> {
   public final T t;
   public final U u;

   Pair(T t, U u) {
      this.t= t;
      this.u= u;
   }

   public T getFirst(){
      return t;
   }

   public U getSecond(){
      return u;
   }
}

