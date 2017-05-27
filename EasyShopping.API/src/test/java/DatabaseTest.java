import com.mongodb.MongoClient;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DatabaseTest {
    @Test
    public void shouldCreateANewMongoClient() throws Exception {
        // When
        MongoClient mongoClient = new MongoClient();

        // Then
        assertNotNull(mongoClient);
    }

}