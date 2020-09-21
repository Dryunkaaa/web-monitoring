import com.monitoring.service.ConnectionService;
import com.monitoring.service.UrlStatusService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

public class UrlStatusServiceTest {

    private final String URL_PATH = "http://google.com";
    private UrlStatusService urlStatusService;
    private ConnectionService connectionService;
    private HttpURLConnection connection;

    @Before
    public void init(){
        urlStatusService = new UrlStatusService();
        connectionService = new ConnectionService();
        connection = connectionService.openConnection(URL_PATH);
    }

    @Test
    public void testIfContainsHeaderSubstring(){
        String headerSubstring = "Content-Type";

        boolean result = urlStatusService.containsHeaderSubstring(connection, headerSubstring);

        Assert.assertTrue(result);
    }

    @Test
    public void testIfNotContainsHeaderSubstring(){
        String headerSubstring = "Unknown-Header";

        boolean result = urlStatusService.containsHeaderSubstring(connection, headerSubstring);

        Assert.assertFalse(result);
    }

    @Test
    public void testIfEqualsResponseCode(){
        int expectedCode = 200;
        boolean result = urlStatusService.equalsResponseCode(connection, expectedCode);

        Assert.assertTrue(result);
    }

    @Test
    public void testIfNotEqualsResponseCode(){
        int expectedCode = 404;
        boolean result = urlStatusService.equalsResponseCode(connection, expectedCode);

        Assert.assertFalse(result);
    }

}
