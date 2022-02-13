package mbse.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mxgraph.layout.mxCompactTreeLayout;

import org.junit.Test;

public class TestMbseGraphController {

    // class to be tested
    private MbseGraphController controller;

    // dependencies (will be mocked)
    private MbseGraphModel model;

    @Test
    public void changeStyle() {
        // arrange
        model = new MbseGraphModel();
        controller = new MbseGraphController(model);

        // act
        controller.changeStyle(true);

        // assert
        assertEquals("style is not correctly set", "saeml", model.getAppliedStyle());
    }

    @Test
    public void keepStyle() {
        // arrange
        model = new MbseGraphModel();
        controller = new MbseGraphController(model);

        // act
        controller.changeStyle(false);

        // assert
        assertEquals("style is not correctly set", "tom_sawyer", model.getAppliedStyle());
    }

    @Test
    public void changeLayoutForEdges() {
        // arrange
        model = new MbseGraphModel();
        controller = new MbseGraphController(model);

        // act
        controller.sameOriginControl(true);

        // assert
        assertTrue("Wrong layout applied", model.getAppliedLayout() instanceof StructureLayout);
    }

    @Test
    public void changeLayoutForDifferentExitEdges() {
        // arrange
        model = new MbseGraphModel();
        controller = new MbseGraphController(model);

        // act
        controller.sameOriginControl(false);

        // assert
        assertTrue("Wrong layout applied", model.getAppliedLayout() instanceof mxCompactTreeLayout);
    }
}
