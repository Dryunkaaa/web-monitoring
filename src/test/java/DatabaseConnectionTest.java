import com.monitoring.storage.DatabaseStorage;
import org.junit.Assert;
import org.junit.Test;

public class DatabaseConnectionTest {

    @Test
    public void testIfConnectToDb(){
        DatabaseStorage databaseStorage = new DatabaseStorage();
        Assert.assertNotNull(databaseStorage.getConnection());
    }
}
