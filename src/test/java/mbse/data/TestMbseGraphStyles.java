package mbse.data;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class TestMbseGraphStyles {

    private MbseGraphStyles graphStyles;

    /**
     * This test makes sure that all styles defined in the XML file are loaded.
     * 
     * @expected 2 - correspond to number of styles defined in the default XML
     */
    @Test
    public void loadingStyles() {
        // arrange
        graphStyles = new MbseGraphStyles();
        // act
        Map<String, Map<String, String>> output = graphStyles.getAvailableStyles();

        // assert
        assertTrue("The default styles has not been founded.", output.size() == 2);
    }
}
