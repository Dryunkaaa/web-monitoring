import com.monitoring.domain.URL;
import com.monitoring.service.UrlService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlServiceTest {

    private Map<String, String> urlsMap;
    private UrlService urlService;

    @Before
    public void init() {
        urlsMap = new HashMap<>();
        urlService = new UrlService();

        urlsMap.put("http://google.com", "http://google.com");
        urlsMap.put("http://google.com?", "http://google.com");
        urlsMap.put("http://google.com?id=0", "http://google.com");
        urlsMap.put("http://google.com?id=0????", "http://google.com");
    }

    @Test
    public void testIfParametersRemovedCase1() {
        for (Map.Entry<String, String> entry : urlsMap.entrySet()) {
            String inputUrl = urlService.removeParameters(entry.getKey());
            String outputUrl = entry.getValue();

            Assert.assertEquals(inputUrl, outputUrl);
        }
    }

    @Test
    public void testIfUrlFoundInList() {
        List<URL> urls = new ArrayList<>();

        URL url = new URL();
        url.setId(1);
        url.setPath("http://google.com");

        URL url2 = new URL();
        url2.setId(2);
        url2.setPath("http://google.com");

        urls.add(url);
        urls.add(url2);

        URL receivedUrl = urlService.findUrlById(urls, 1);

        Assert.assertEquals(receivedUrl, url);
    }
}
