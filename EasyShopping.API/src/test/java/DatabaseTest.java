import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DatabaseTest {
    @Test
    public void shouldCreateANewMongoClient() throws Exception {
        // When
        MongoClient mongoClient = new MongoClient();

        // Then
        assertNotNull(mongoClient);
    }

    @Test
    public void shouldConnectToDatabaseServer() throws Exception{
        Database database = new Database();
        assertNotNull(database);
    }

    @Test
    public void shouldIncreaseLikedValue() throws  Exception {
        Database db = new Database();

        BasicDBObject findRecipe = (BasicDBObject) db.FindFirstRecipe();
        int prevLikedValue = Integer.parseInt(findRecipe.getString("liked"));

        db.IncrementLikedValue(findRecipe.getString("_id"));

        findRecipe = (BasicDBObject) db.FindFirstRecipe();
        int afterUpdateLikedValue = Integer.parseInt(findRecipe.getString("liked"));

        assertEquals(prevLikedValue+1, afterUpdateLikedValue);

    }




}