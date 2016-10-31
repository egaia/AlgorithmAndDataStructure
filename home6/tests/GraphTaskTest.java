import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class GraphTaskTest {

   @Test (timeout=1000)
   public void checkModifiedVertexCount() {
      GraphTask task = new GraphTask();
      GraphTask.Graph g = task.new Graph("Test1");
      g.createRandomSimpleGraph(6,9);
      GraphTask.Graph ng = g.clone();
      g.createVertex("new Vertex");

      int gVertixes = g.getAllVertexes().size();
      int ngVertixes = ng.getAllVertexes().size();

      assertEquals("Cloned vertex should be 6 instead of 7", ngVertixes, 6);
      assertEquals("First graph should have 7 vertexes", gVertixes, 7);
   }

   @Test (expected = AssertionError.class)
   public void checkModifiedArc(){
      GraphTask task = new GraphTask();
      GraphTask.Graph g = task.new Graph("Test2");
      g.createRandomSimpleGraph(6,9);
      GraphTask.Graph ng = g.clone();
      //set name to be same as first graph
      ng.setId("Test2");
      g.createArc("newArc", g.getVertexByName("v1"), g.getVertexByName("v2"));
      assertEquals(g, ng);
   }

   @Test (timeout=1000)
   public void checkDeletedVertexCount(){
      GraphTask task = new GraphTask();
      GraphTask.Graph g = task.new Graph("Test1");
      g.createRandomSimpleGraph(6,9);
      GraphTask.Graph ng = g.clone();
      g.createRandomSimpleGraph(1,0);

      int gVertixes = g.getAllVertexes().size();
      int ngVertixes = ng.getAllVertexes().size();

      assertEquals("Vertex count should be 1", gVertixes, 1);
      assertEquals("Cloned graph should still have 6 vertexes", ngVertixes, 6);

   }

   @Test (expected = RuntimeException.class)
   public void testEmptyGraphClone(){
      GraphTask task = new GraphTask();
      GraphTask.Graph g = task.new Graph("TestGraph");
      GraphTask.Graph ng = g.clone();
   }

}

