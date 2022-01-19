package mbse.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestMbseGraphStyles {

    // object is being mocked initialized before each test method
    private MbseGraphStyles graphStyles;

    @Before
    public void setUp() {
        // graphStyles = Mockito.mock(MbseGraphStyles.class);
        graphStyles = new MbseGraphStyles();
    }

    @Test
    public void testLoadFile2() {
        when(graphStyles.getContent()).thenReturn("Mockito");
        assertEquals("Mockito", graphStyles.getContent());
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

    @Test
    public void testLoadFileNotExists() {
        // exce
    }
}
