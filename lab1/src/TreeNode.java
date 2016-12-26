import java.util.*;

public class TreeNode  {
   private String name;
   private TreeNode firstChild;
   private TreeNode nextSibling;

   TreeNode (String n, TreeNode d, TreeNode r) {
      setNodeName(n);
      setFirstChild(d);
      setNextSibling(r);
   }

   public void setNodeName(String n) {
      this.name = n;
   }

   public void setFirstChild(TreeNode d) {
      this.firstChild = d;
   }

   public void setNextSibling(TreeNode r) {
      this.nextSibling = r;
   }

   public boolean hasChild() {
      return this.firstChild != null;
   }

   public String getName() {
      return this.name;
   }

   public TreeNode getChild() {
      return this.firstChild;
   }

   public TreeNode getSibling() {
      return this.nextSibling;
   }

   public TreeNode nextSibling() {
      return getSibling();
   }

   public boolean hasNextSibling() {
      return (getSibling() != null);
   }

   public static TreeNode parsePrefix (String s) {
      validateInputString(s);

      if (!s.contains("(") && !s.contains(",") && !s.contains(")")) {
         return new TreeNode(s, null, null);
      } else {
         return parseFromString(s);
      }
   }

   public String rightParentheticRepresentation() {
      StringBuffer b = new StringBuffer();

      TreeNode child = this.getChild();

      if (child != null) {
         b.append("(");
      }

      while (child != null) {
         b.append(child.rightParentheticRepresentation());

         if(child.hasNextSibling()) {
            b.append(",");
         } else {
            b.append(")");
         }

         child = child.nextSibling();
      }

      b.append(this.name);

      return b.toString();
   }

   /*
    * Meetod, mis parsib stringi põhjal puu. 
    * Omakorda eraldi meetod parsimiseks on selletõttu, et mul 
    * olid alguses teised lihtsamad meetodid lihtsamate stringide 
    * parsimiseks ja lihtsamate puude loomiseks.
    */
   private static TreeNode parseFromString(String input) {
      String name = null;
      TreeNode firstChild = null;
      TreeNode nextSibling = null;
      int nameEndIndex = 0, bracketsOpened = 0;
      boolean decreasedBefore = false;

      char[] array = input.toCharArray();

      for (int i = 0; i < array.length; i++) {
         switch(array[i]) {
            case '(' :
               if (bracketsOpened == 0) {
                  if (name == null) {
                     name = input.substring(0, i);
                  }
                  if (nextSibling == null) {
                     firstChild = TreeNode.parseFromString(input.substring(i+1));
                  }
               }
               bracketsOpened++;
               break;
            case ')' :
               if (name == null) {
                  name = input.substring(0, i);
               }

               if (bracketsOpened == 0) {
                  decreasedBefore = true;
               }

               bracketsOpened--;
               break;
            case ',' :
               if (name == null) {
                  name = input.substring(0, i);
                  nextSibling = TreeNode.parseFromString(input.substring(i+1));
                  break;
               }

               if (bracketsOpened == 0 && nextSibling == null) {
                  if (!decreasedBefore) {
                     nextSibling = TreeNode.parseFromString(input.substring(i+1));
                  }

               }
               break;
         }
      }

      return new TreeNode(name, firstChild, nextSibling);
   }

   /*
    * Meetod, mis kontrollib parsitavat stringi. 
    */
   public static void validateInputString(String input) {
      if (input.trim().isEmpty()) {
         throw new RuntimeException(input + " string is empty.");
      }

      if (input.contains("()")) {
         throw new RuntimeException(input + " contains empty subtree.");
      }

      if (input.contains(",,")) {
         throw new RuntimeException(input + " contains double commas.");
      }

      if (input.contains(" ")) {
         throw new RuntimeException(input + " contains empty spaces.");
      }

      if (input.contains("((")) {
         throw new RuntimeException(input + " contains double brackets.");
      }

      if (input.contains(",") && (!input.contains("(") || !input.contains(")"))) {
         throw new RuntimeException(input + " contains no brackets.");
      }

      int leftParanthesis = 0;
      int rightParanthesis = 0;

      for (int i = 0;  i < input.length() ; i++) {
         if (input.charAt(i) == '(') {
            leftParanthesis++;
         } else if (input.charAt(i) == ')') {
            rightParanthesis++;
         }
      }

      if (leftParanthesis != rightParanthesis) {
         throw new RuntimeException(input + " is unbalanced.");
      }
   }

   public static void main (String[] param) {
//	   String s = "A(B1,C)";
//	   TreeNode t = TreeNode.parsePrefix (s);
//	   String v = t.rightParentheticRepresentation();
//	   System.out.println (s + " ==> " + v); // A(B1,C) ==> (B1,C)A

//	   String s = "A(B(D(G,H),E,F(I)),C(J))";
//	   String s = "A(B,C,D,E,D,F,G)";
      String s = "A(B(D,E(R)),F)";
//	   String s = "A(B(C(D)))";
//	   String s = "AA";
//	   String s = "+(*(-(2,1),4),/(6,3))";


//	   TreeNode node = new TreeNode("A", new TreeNode("B", null, new TreeNode("C", new TreeNode("D", null, null),
//			   new TreeNode("X", new TreeNode("Z", null, new TreeNode("O", null, null)), null))
//			   ), null);

//	   TreeNode node = TreeNode.parseFromString(s);
      TreeNode node = TreeNode.parsePrefix(s);
      String sRpr = node.rightParentheticRepresentation();


      System.out.println("String to parse: \t" + s);
      System.out.println("Tree's RPR: \t\t" + sRpr);

//	   String sLpr = node.lpr();
//	   System.out.println("Tree's LRP: \t\t" + sLpr);
   }

   /*
    * Meetod puu vasakpoolse esituskuju jaoks. Hetkel kontrollmeetod, 
    * et puu ikka korrektselt tehtud sai.
    */
   public String lpr() {
      String result = this.name;
      result += firstChild == null ? "" : "(" + firstChild.lpr() + ")";
      result += nextSibling == null ? "" : "," + nextSibling.lpr();
      return result;
   }
}