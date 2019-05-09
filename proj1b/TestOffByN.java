import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    OffByN offBy5 = new OffByN(5);
    OffByN offBy32 = new OffByN(32);

    @Test
    public void testEqualChars() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('f', 'h'));
        assertFalse(offBy5.equalChars('a', 'a'));
        assertTrue(offBy32.equalChars('a', 'A'));
        assertTrue(offBy32.equalChars('z', 'Z'));
        assertFalse(offBy32.equalChars('a', 'a'));
    }
}
