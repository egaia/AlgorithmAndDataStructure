import java.util.LinkedList;

public class LongStack {
   protected LinkedList<Long> stack;
   LongStack() {
      stack = new LinkedList<>();
   }

   public static void main(String[] args) {
      Long x = interpret("1 -10 4 8 3 - + * +");
      System.out.println(x);
   }
   /**
    * Overiding method
    * @return cloned object
    * @throws CloneNotSupportedException if cloning is not possible
    * {@inheritDoc}
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      LongStack ret = new LongStack();
      ret.stack = (LinkedList<Long>) stack.clone();
      return ret;
   }

   /**
    * Returns true if this stack contains no elements.
    * @return true if this stack is empty.
    */
   public boolean stEmpty() {
      return stack.isEmpty() ? true : false;
   }

   /**
    * Pushes an element onto the stack.
    * @param a the element to push.
    */
   public void push (long a) {
      stack.push(a);
   }

   /**
    * Pops an element from the stack.
    * @return the element at the front of this list.
    */
   public long pop() {
      return stack.pop();
   }

   /**
    * Perform an arithmetic operation between two topmost elements of the stack (result is left on top).
    * @param s arithmetic operation (valid values are +, -, *, /).
    */
   public void op (String s) {
      char op = s.charAt(0);
      long el1 = stack.remove(1);
      long el2 = stack.remove(0);
      switch (op){
         case '+':
            push(el1+el2);
            break;
         case '-':
            push(el1-el2);
            break;
         case '*':
            push(el1*el2);
            break;
         case '/':
            push(el1/el2);
            break;
         default:
            throw new RuntimeException("Illegal operation " + op + " valid operations are + - * /");
      }

   }

   /**
    * Retrieves, but does not remove, the head (first element) of this list.
    * @return the head of this list, or null if this list is empty.
    */
   public long tos() {
      return stack.peek();
   }

   /**
    * Overriding method
    * @param o LongStack
    * @return true if it equals, false if it doesnt
    * {@inheritDoc}
    */
   @Override
   public boolean equals (Object o) {
      if(o instanceof LongStack){
         LinkedList<Long> tmp = ((LongStack) o).stack;
         if( stack.size() == tmp.size()){
            for(int i = 0; i < stack.size(); i++){
               if(stack.get(i) != tmp.get(i)){
                  return false;
               }
            }
            return true;
         }
      }
      return false;
   }

   /**
    * Overriding method
    * @return String( upside down)
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      StringBuffer ret = new StringBuffer();
      for(int i = stack.size()-1; i>=0; i--){
         ret.append(stack.get(i));
         if(i > 0){
            ret.append(' ');
         }
      }
      return ret.toString();
   }

   /**
    * Calculate the value of RPN (Reverse Polish Notation) using this stack type.
    * @param pol an arithmetic expression.
    * @return the value of RPN.
    */
   public static long interpret (String pol) {
      if(pol == null | pol.length() == 0 | pol == ""){
         throw new RuntimeException("Expression can't be empty");
      }
      LongStack longStack = new LongStack();
      String[] elements = pol.trim().split("\\s+");
      int i = 0, o = 0;

      for(String element : elements){
         try{
            longStack.push(Integer.valueOf(element));
            i++;
         }catch(NumberFormatException e){
            if(longStack.stEmpty()){
               throw new RuntimeException("Operation can't be first element of expression");
            }
            else if(longStack.stack.size() < 2){
               throw new RuntimeException("Too few elements in stack");
            }
            else if(!element.equals("+") && !element.equals("*") && !element.equals("/") && !element.equals("-")){
               throw new RuntimeException("Expression contains illegal symbol " + element +"." );
            }
            longStack.op(element);
            o++;
         }
      }

      if(i-1 != o){
         throw new RuntimeException("Stack is out of balance (integers: " + i + " , operations: " + o+").");
      }
      return longStack.pop();
   }
}

