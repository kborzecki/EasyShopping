import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;

import javax.xml.crypto.Data;

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