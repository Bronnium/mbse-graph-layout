package mbse.data;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class TestMbseGraphStyles {

    private MbseGraphStyles graphStyles;

    @Test
    public void getAvailableStylesTest() {
        // arrange
        graphStyles = new MbseGraphStyles();
        // act
        Map<String, Map<String, String>> output = graphStyles.getAvailableStyles();

        // assert
        assertTrue("The default styles has not been founded.", output.size() > 0);

    }
}
