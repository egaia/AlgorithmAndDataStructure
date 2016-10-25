import java.util.*;

public class Node {


   private String name;
   private Node firstChild;
   private Node nextSibling;

   Node (String n, Node d, Node r) {
      this.name = n;
      this.firstChild = d;
      this.nextSibling = r;
   }
   
   public static Node parsePostfix (String s) {
      checkForBasicErrors(s);
      StringTokenizer st = new StringTokenizer(s, "(),", true);
      while(st.hasMoreTokens()){
         String token = st.nextToken().trim();
         if(token.equals('(')){
            //samm alla
         }else if( token.equals(')')){
            // samm üles

         }else if(token.equals(',')){
            //samal tasemel samm paremale
         }
      }
      return null;
   }

   public String leftParentheticRepresentation() {
      StringBuilder str = new StringBuilder();
      str.append(this.name);
      if(this.firstChild != null){
         str.append("(");
         str.append(this.firstChild.leftParentheticRepresentation());
         str.append(")");
      }
      if(this.nextSibling != null){
         str.append(",");
         str.append(this.nextSibling.leftParentheticRepresentation());
      }

      return str.toString();
   }

   public static void checkForBasicErrors(String s){
      if(s.length() == 0)
         throw new RuntimeException("The tree is empty " + s);
      if(!s.matches("[\\w(),+--/ *]+"))
         throw new RuntimeException("String contains illegal symbols: " + s );
      if(s.contains(" "))
         throw new RuntimeException("There are empty whitespaces in string " + s);
      if(s.contains(",,"))
         throw new RuntimeException("String contains double commas " + s);
      if(s.contains("()"))
         throw new RuntimeException("String contains empty subtree " + s);
      if(s.contains("(("))
         throw new RuntimeException("String contains double brackets " + s);
      if(s.contains(",") && !s.contains("(") || !s.contains(")"))
         throw new RuntimeException("String does not contain brackets " + s);
   }
   public static void main (String[] param) {
      String s = "(B1,C)A";
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)
   }
}

