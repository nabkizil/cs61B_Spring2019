import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    @Test
    public void isSameNumberTest() {
        assertTrue(Flik.isSameNumber(128,128));
    }
    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("all", FlikTest.class);
    }
}
