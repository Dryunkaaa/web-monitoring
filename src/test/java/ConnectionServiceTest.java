import com.monitoring.service.ConnectionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

public class ConnectionServiceTest {

    private ConnectionService connectionService;

    @Before
    public void init(){
        connectionService = new ConnectionService();
    }

    @Test
    public void testIfConnectionOpen(){
        String url = "http://google.com";

        HttpURLConnection connection = connectionService.openConnection(url);

        Assert.assertNotNull(connection);
    }

    @Test
    public void testIfConnectionNotOpen(){
        String url = "hello";

        HttpURLConnection connection = connectionService.openConnection(url);

        Assert.assertNull(connection);
    }
}
