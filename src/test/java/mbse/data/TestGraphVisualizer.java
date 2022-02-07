package mbse.data;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

public class TestGraphVisualizer {

    private GraphVisualizer graphVisualizer;

    @Test
    public void getAvailableStylesTest() {
        // arrange
        HashSet<GraphNode> nodes = new HashSet<>();
        HashSet<GraphEdge> edges = new HashSet<>();

        // act
        graphVisualizer = new GraphVisualizer(nodes, edges);

        // assert
        assertTrue("Application could not be created.", graphVisualizer != null);

    }
}
