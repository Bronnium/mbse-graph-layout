package mbse.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestMbseGraphStyles {

    // object is being mocked initialized before each test method
    private MbseGraphStyles graphStyles;

    @Before
    public void setUp(){
        graphStyles = Mockito.mock(MbseGraphStyles.class);
    }
    
    @Test
    public void testLoadFile() {
        when(graphStyles.getContent()).thenReturn("Mockito");
        assertEquals("Mockito", graphStyles.getContent());
    }

}
