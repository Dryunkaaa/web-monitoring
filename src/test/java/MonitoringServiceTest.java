import com.monitoring.domain.URL;
import com.monitoring.entity.Message;
import com.monitoring.service.MonitoringService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MonitoringServiceTest {

    private List<URL> monitoringUrls;

    private Map<Long, Message> messageMap;

    private List<URL> allUrls;

    private URL url;

    private MonitoringService monitoringService;

    @Before
    public void init() {
        monitoringUrls = new ArrayList<>();
        allUrls = new ArrayList<>();
        messageMap = new ConcurrentHashMap<>();

        monitoringService = new MonitoringService(monitoringUrls, messageMap);
        url = createUrlWithParameters();
    }

    @Test
    public void testIfUrlAdd() {
        url.setMonitoringStatus(true);
        allUrls.add(url);

        monitoringService.startMonitoring(allUrls);

        Assert.assertEquals(1, monitoringUrls.size());
    }

    @Test
    public void testIfUrlNotAdd() {
        url.setMonitoringStatus(false);
        allUrls.add(url);

        monitoringService.startMonitoring(allUrls);

        Assert.assertEquals(0, monitoringUrls.size());
    }

    @Test
    public void testIfUrlContainsThreadTask(){
        url.setMonitoringStatus(true);
        allUrls.add(url);

        monitoringService.startMonitoring(allUrls);

        Assert.assertTrue(url.getThread() != null);
    }

    @Test
    public void testIfUrlNotContainsThreadTask(){
        url.setMonitoringStatus(false);
        allUrls.add(url);

        monitoringService.startMonitoring(allUrls);

        Assert.assertTrue(url.getThread() == null);
    }

    @Test
    public void testIfUrlRemovedFromMonitoring(){
        url.setMonitoringStatus(true);
        allUrls.add(url);

        monitoringService.startMonitoring(allUrls);

        url.setMonitoringStatus(false);

        try {
            url.getThread().join();

            Assert.assertTrue(monitoringUrls.size() == 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private URL createUrlWithParameters() {
        URL url = new URL();

        url.setId(1);
        url.setPath("http://google.com");
        url.setMonitoringPeriod(200);
        url.setExceptedResponseCode(200);
        url.setOkResponseTime(1000);
        url.setWarningResponseTime(5000);
        url.setCriticalResponseTime(10000);
        url.setMinResponseSize(0);
        url.setMaxResponseSize(100000000000L);

        return url;
    }
}
