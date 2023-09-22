import org.junit.Test;
import static org.junit.Assert.*;

// Ceci est un commentaire en ligne

/*
Ceci est un commentaire
sur plusieurs
lignes
*/

public class testFile {

    @Test
    public void testAddition() {
        int result = 5 + 5;
        assertEquals(10, result);
        assertFalse(result > 10);
    }

    @Test
    public void testSubtraction() {
        int result = 10 - 5;
        assertNotEquals(3, result);
    }

    @Test
    public void testStrings() {
        String str1 = "hello";
        String str2 = "world";
        assertNotSame(str1, str2);
        assertNull(null);
    }
}