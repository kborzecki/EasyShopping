import static org.junit.Assert.*;

import com.mongodb.MongoClient;
import org.junit.*;

public class DatabaseTest {
    @Test
    public void shouldCreateANewMongoClient() throws Exception {
        // When
        MongoClient mongoClient = new MongoClient();

        // Then
        assertNotNull(mongoClient);
    }

}