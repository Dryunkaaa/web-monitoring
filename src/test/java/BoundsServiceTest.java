import com.monitoring.service.BoundService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoundsServiceTest {

    private BoundService boundService;

    @Before
    public void init() {
        boundService = new BoundService();
    }

    @Test
    public void testIfInBounds() {
        boolean result = boundService.numberIsInRange(100, 30, 101);
        Assert.assertTrue(result);
    }

    @Test
    public void testIfNoInBounds() {
        boolean result = boundService.numberIsInRange(10, 0, 5);
        Assert.assertFalse(result);
    }
}
