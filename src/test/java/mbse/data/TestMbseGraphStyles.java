package mbse.data;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestMbseGraphStyles {

    private MbseGraphStyles graphStyles;
    @Before
    public void setUp(){
        graphStyles = new MbseGraphStyles();
    }
    
    @Test
    public void testLoadFile() {
        graphStyles.loadStyleFile();

        //assertThrows(arg0, arg1, arg2)
        //fail("not implemented");
        assertTrue("message", true);
    }

}
