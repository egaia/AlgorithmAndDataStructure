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

   Node (){

   }

   
   public static Node parsePostfix (String s) {
      checkForBasicErrors(s);
      Stack<Node> stack = new Stack<>();
      Node newNode = new Node();
      StringTokenizer st = new StringTokenizer(s, "(),", true);
      while(st.hasMoreTokens()){
         String token = st.nextToken().trim();
         if(token.equals("(")){
            stack.push(newNode);
            newNode.firstChild = new Node();
            newNode = newNode.firstChild;
         }else if( token.equals(")")){
            Node node = stack.pop();
            newNode = node;
         }else if(token.equals(",")){
            if(stack.empty())
               throw new RuntimeException("Comma exception" + s);
            newNode.nextSibling = new Node();
            newNode = newNode.nextSibling;
         }else{
            newNode.name = token;
         }
      }
      return newNode;
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
      if(s.contains(",") && !s.contains("(") && !s.contains(")"))
         throw new RuntimeException("String contains double root nodes " + s);
      for(int i = 0; i < s.length(); i++){
         if(s.charAt(i) == '(' && s.charAt(i+1) == ',')
            throw new RuntimeException("String containts comma error, parenthesis can't be followed by comma " +s);
         if(s.charAt(i) == ')' && (s.charAt(i+1) == ',' || s.charAt(i+1) == ')'))
            throw new RuntimeException("Double rightbracket error " +s);
      }
   }
   public static void main (String[] param) {
      String s = "(B1,C)A";
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)
   }
}

