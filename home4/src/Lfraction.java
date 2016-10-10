import java.util.*;

/** This class represents fractions of form n/d where n and d are long integer 
 * numbers. Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction> {

   private long numerator;
   private long denominator;

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
      return denominator == 0 ? numerator : gcm(denominator, numerator % denominator);
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
      if(eq1.t.equals(eq2.t) && eq1.u.equals(eq2.u)){
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
      return Objects.hash(this.numerator, this.denominator);
   }

   /** Sum of fractions.
    * @param m second addend
    * @return this+m
    */
   public Lfraction plus (Lfraction m) {
      long oneDenominator = this.denominator * m.denominator;
      long firstExp = oneDenominator / this.denominator;
      long secondExp = oneDenominator / m.denominator;
      long oneNumerator = firstExp*this.numerator + secondExp*m.numerator;
      Pair<Long, Long> gcm = asFraction(oneNumerator, oneDenominator);
      Lfraction frac = new Lfraction(gcm.t,gcm.u);
      return frac;
   }

   /** Multiplication of fractions.
    * @param m second factor
    * @return this*m
    */
   public Lfraction times (Lfraction m) {
      if((this.denominator == 0) || (m.denominator == 0)){
         throw new IllegalArgumentException("Denominator can't be zero.");
      }
      long oneDenominator = this.denominator*m.denominator;
      long oneNumerator = this.numerator*m.numerator;
      Pair<Long, Long> gcm = asFraction(oneNumerator, oneDenominator);
      Lfraction frac = new Lfraction(gcm.t,gcm.u);
      return frac;
   }

   /** Inverse of the fraction. n/d becomes d/n.
    * @return inverse of this fraction: 1/this
    */
   public Lfraction inverse() {
      if(this.denominator == 0L){
         throw new RuntimeException("Zero denominator will not pass!");
      }else{
         return new Lfraction(this.denominator, this.numerator);
      }
   }

   /** Opposite of the fraction. n/d becomes -n/d.
    * @return opposite of this fraction: -this
    */
   public Lfraction opposite() {
      if(this.denominator == 0L){
         throw new RuntimeException("Denominator can't be 0");
      }
      return new Lfraction(-this.numerator, this.denominator);
   }

   /** Difference of fractions.
    * @param m subtrahend
    * @return this-m
    */
   public Lfraction minus (Lfraction m) {
      long oneDenominator = this.denominator * m.denominator;
      long firstExp = oneDenominator / this.denominator;
      long secondExp = oneDenominator / m.denominator;
      long oneNumerator = firstExp*this.numerator - secondExp*m.numerator;
      Pair<Long, Long> gcm = asFraction(oneNumerator, oneDenominator);
      Lfraction frac = new Lfraction(gcm.t,gcm.u);
      return frac;
   }

   /** Quotient of fractions.
    * @param m divisor
    * @return this/m
    */
   public Lfraction divideBy (Lfraction m) {
      if(m.numerator == 0L){
         throw new RuntimeException("Division by zero");
      }
      return this.times(new Lfraction(m.denominator, m.numerator));
   }

   /** Comparision of fractions.
    * @param m second fraction
    * @return -1 if this < m; 0 if this==m; 1 if this > m
    */
   @Override
   public int compareTo (Lfraction m) {
      long thi = this.numerator * m.denominator;
      long fra = m.numerator * this.denominator;
      int result = 0;
      if(thi > fra){
         result = 1;
      }else if(fra > thi){
         result = -1;
      }
      return result;
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
      boolean isNeg = false;
      if(this.numerator < 0){
         isNeg = true;
         this.numerator = this.numerator * (-1);
      }
      Long a = this.numerator;
      Long b = this.denominator;
      while(this.numerator > this.denominator){
         a = a - b;
         this.numerator =a;
      }
      if(this.numerator ==this.denominator){
         return new Lfraction(0, 1);
      }
      if(isNeg){
         this.numerator = this.numerator*-1;
      }
      return new Lfraction(this.numerator, this.denominator);
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
      if (d > 0) {
         return new Lfraction((Math.round(f * d)), d);
      }else{
         throw  new RuntimeException("Illegal denominator");
      }
   }

   /** Conversion from string to the fraction. Accepts strings of form
    * that is defined by the toString method.
    * @param s string form (as produced by toString) of the fraction
    * @return fraction represented by s
    */
   public static Lfraction valueOf (String s) {

      String[] splits;
      long numerator;
      long denominator;
      try{
         splits = s.split("/");
      }catch(Exception e){
         throw new IllegalArgumentException(s + " is not a fraction");
      }
      try{
         numerator = Long.parseLong(splits[0]);
      }catch(Exception e){
         throw new IllegalArgumentException(splits[0] + " is not correct value for numerator");
      }
      try{
         denominator = Long.parseLong(splits[1]);
      }catch(Exception e){
         throw new IllegalArgumentException(splits[1] + " is not correct value for denumerator");
      }
      return new Lfraction(numerator, denominator);
   }
}

/**
 * Custom class Pair which takes in two objects f.e. Pair<Long, Long>
 * @param <T> First object
 * @param <U> Second object
 */
class Pair<T, U> {
   public final T t;
   public final U u;

   Pair(T t, U u) {
      this.t= t;
      this.u= u;
   }
}

