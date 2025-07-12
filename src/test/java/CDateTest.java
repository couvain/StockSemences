import org.junit.Test;
import static org.junit.Assert.*;

public class CDateTest {
    @Test
    public void defaultConstructorReturns20000101() {
        assertEquals(20000101L, new CDate().get_bigint());
    }

    @Test
    public void constructorWithArgsReturns20000101() {
        assertEquals(20000101L, new CDate(1, 1, 2000).get_bigint());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsOnInvalidMonth() {
        new CDate(1, 13, 2000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsOnInvalidDay() {
        new CDate(31, 2, 2001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsOnNegativeYear() {
        new CDate(1, 1, 0);
    }
}
