import java.util.*;

/** Container class to different classes, that makes the whole
 * set of classes one class formally.
 */
public class GraphTask {

   /** Main method. */
   public static void main (String[] args) {
      GraphTask a = new GraphTask();
      a.run();
   }

   /** Actual main method to run examples and everything. */
   public void run() {
      Graph g = new Graph ("nimi");
      //g.createRandomSimpleGraph(6,9);
      Graph nG = g.clone();
      System.out.println(g);
      System.out.println(nG);
   }

   class Vertex {

      private String id;
      private Vertex next;
      private Arc first;
      private int info = 0;
      // You can add more fields, if needed

      Vertex (String s, Vertex v, Arc e) {
         id = s;
         next = v;
         first = e;
      }

      Vertex (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

   }


   /** Arc represents one arrow in the graph. Two-directional edges are
    * represented by two Arc objects (for both directions).
    */
   class Arc {

      private String id;
      private Vertex target;
      private Arc next;
      private int info = 0;
      // You can add more fields, if needed

      Arc (String s, Vertex v, Arc a) {
         id = s;
         target = v;
         next = a;
      }

      Arc (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }
   }


   class Graph {

      private String id;
      private Vertex first;
      private int info = 0;
      // You can add more fields, if needed

      Graph (String s, Vertex v) {
         id = s;
         first = v;
      }

      Graph (String s) {
         this (s, null);
      }

      public void setId(String name){
         this.id = name;
      }

      @Override
      public String toString() {
         String nl = System.getProperty ("line.separator");
         StringBuilder sb = new StringBuilder(nl);
         sb.append (id);
         sb.append (nl);
         Vertex v = first;
         while (v != null) {
            sb.append (v.toString());
            sb.append (" -->");
            Arc a = v.first;
            while (a != null) {
               sb.append (" ");
               sb.append (a.toString());
               sb.append (" (");
               sb.append (v.toString());
               sb.append ("->");
               sb.append (a.target.toString());
               sb.append (")");
               a = a.next;
            }
            sb.append (nl);
            v = v.next;
         }
         return sb.toString();
      }

      public Vertex createVertex (String vid) {
         Vertex res = new Vertex (vid);
         res.next = first;
         first = res;
         return res;
      }

      public Arc createArc (String aid, Vertex from, Vertex to) {
         Arc res = new Arc (aid);
         res.next = from.first;
         from.first = res;
         res.target = to;
         return res;
      }

      /**
       * Create a connected undirected random tree with n vertices.
       * Each new vertex is connected to some random existing vertex.
       * @param n number of vertices added to this graph
       */
      public void createRandomTree (int n) {
         if (n <= 0)
            return;
         Vertex[] varray = new Vertex [n];
         for (int i = 0; i < n; i++) {
            varray [i] = createVertex ("v" + String.valueOf(n-i));
            if (i > 0) {
               int vnr = (int)(Math.random()*i);
               createArc ("a" + varray [vnr].toString() + "_"
                  + varray [i].toString(), varray [vnr], varray [i]);
               createArc ("a" + varray [i].toString() + "_"
                  + varray [vnr].toString(), varray [i], varray [vnr]);
            } else {}
         }
      }

      /**
       * Create an adjacency matrix of this graph.
       * Side effect: corrupts info fields in the graph
       * @return adjacency matrix
       */
      public int[][] createAdjMatrix() {
         info = 0;
         Vertex v = first;
         while (v != null) {
            v.info = info++;
            v = v.next;
         }
         int[][] res = new int [info][info];
         v = first;
         while (v != null) {
            int i = v.info;
            Arc a = v.first;
            while (a != null) {
               int j = a.target.info;
               res [i][j]++;
               a = a.next;
            }
            v = v.next;
         }
         return res;
      }

      /**
       * Create a connected simple (undirected, no loops, no multiple
       * arcs) random graph with n vertices and m edges.
       * @param n number of vertices
       * @param m number of edges
       */
      public void createRandomSimpleGraph (int n, int m) {
         if (n <= 0)
            return;
         if (n > 2500)
            throw new IllegalArgumentException ("Too many vertices: " + n);
         if (m < n-1 || m > n*(n-1)/2)
            throw new IllegalArgumentException
               ("Impossible number of edges: " + m);
         first = null;
         createRandomTree (n);       // n-1 edges created here
         Vertex[] vert = new Vertex [n];
         Vertex v = first;
         int c = 0;
         while (v != null) {
            vert[c++] = v;
            v = v.next;
         }
         int[][] connected = createAdjMatrix();
         int edgeCount = m - n + 1;  // remaining edges
         while (edgeCount > 0) {
            int i = (int)(Math.random()*n);  // random source
            int j = (int)(Math.random()*n);  // random target
            if (i==j)
               continue;  // no loops
            if (connected [i][j] != 0 || connected [j][i] != 0)
               continue;  // no multiple edges
            Vertex vi = vert [i];
            Vertex vj = vert [j];
            createArc ("a" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected [i][j] = 1;
            createArc ("a" + vj.toString() + "_" + vi.toString(), vj, vi);
            connected [j][i] = 1;
            edgeCount--;  // a new edge happily created
         }
      }

      /**
       * Get all the vertixes for current graph.
       * @return List of graph's Vertixes
       */
      List<Vertex> getAllVertexes(){
         List<Vertex> allVertexes = new ArrayList<>();
         Vertex currVertex = this.first;
         allVertexes.add(currVertex);
         try {
            while (currVertex.next != null) {
               allVertexes.add(currVertex.next);
               currVertex = currVertex.next;
            }
            return allVertexes;
         }catch(Exception e){
            throw new RuntimeException("You can't get vertexes from an empty graph, therefore you can't clone it as well.\n" +
                  "Your graph was: " + this.toString());
         }
      }

      /**
       * Returns Vertex by its name
       * @param name the id of Vertex
       * @return Vertex if one is found else null.
       */
      Vertex getVertexByName(String name){
         List<Vertex> allVertexes = getAllVertexes();
         for(Vertex v : allVertexes){
            if(v.id.equals(name)){
               return v;
            }
         }
         return null;
      }

      /**
       * Overriding method for cloning a graph
       * @return A cloned graph
       * {@inheritDoc}
       */
      @Override
      public Graph clone(){
         Graph newGraph = new Graph(this.id + "_clone");
         //Clone first Vertex
         List<Vertex> oVertexes = getAllVertexes();
         Vertex first = oVertexes.get(0);
         oVertexes.remove(first);
         newGraph.first = new Vertex(first.id);
         //Add pointer to the first Vertex of graph
         Vertex pointer = newGraph.first;
         //Add other Vertexes for graph
         for (Vertex v : oVertexes){
            pointer.next = new Vertex(v.id);
            pointer = pointer.next;
         }
         //Take first vertex from graph to be cloned
         List<Vertex> allRealVertexes = getAllVertexes();
         Vertex firstRealVertex = getAllVertexes().get(0);
         //Take first vertex from cloned graph
         Vertex firstCloneVertex = newGraph.first;
         //Create first arc with its target to the cloned graph
         firstCloneVertex.first = new Arc(firstRealVertex.first.id);
         firstCloneVertex.first.target = newGraph.getVertexByName(firstRealVertex.first.target.id);
         //Move pointers
         Arc firstCloneArc = firstCloneVertex.first;
         Arc firstRealArc = firstRealVertex.first;
         //Add other arcs to the first vertex
         while(firstRealArc.next != null){
            firstCloneArc.next = new Arc(firstRealArc.next.id);
            firstCloneArc.next.target = newGraph.getVertexByName(firstRealArc.next.target.id);
            firstCloneArc = firstCloneArc.next;
            firstRealArc = firstRealArc.next;
         }
         //Rewrite cloned graph first vertex
         newGraph.first = firstCloneVertex;
         //Move pointer to the next Vertex
         pointer = newGraph.first;
         //deal with adding arcs to other vertexes
         for(Vertex v : allRealVertexes){
            pointer.first = new Arc(v.first.id);
            pointer.first.target = newGraph.getVertexByName(v.first.target.id);
            //ArcPointers
            Arc fCloneArc = pointer.first;
            Arc rCloneArc = v.first;
            //Follow the arc tree and add new ones
            while(rCloneArc.next != null){
               fCloneArc.next = new Arc(rCloneArc.next.id);
               fCloneArc.next.target = newGraph.getVertexByName(rCloneArc.next.target.id);
               //Move Arc pointers
               fCloneArc = fCloneArc.next;
               rCloneArc = rCloneArc.next;
            }
            //move pointer of Vertexes to the next one in the tree
            pointer = pointer.next;
         }
         return newGraph;
      }
   }
}