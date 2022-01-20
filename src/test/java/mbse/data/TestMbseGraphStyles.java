package mbse.data;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestMbseGraphStyles {

    // object is being mocked initialized before each test method
    private MbseGraphStyles graphStyles;

    @Before
    public void setUp() {
        // graphStyles = Mockito.mock(MbseGraphStyles.class);
        graphStyles = new MbseGraphStyles();
    }

    /** Test if file is found */
    @Test
    public void testLoadFileExists() {
        // arrange
        String path = TestMbseGraphStyles.class.getResource("file.xml").getPath();

        // act
        boolean found = graphStyles.loadAndReadFile(path);

        // assert
        assertTrue("File is found", found);
    }
}
