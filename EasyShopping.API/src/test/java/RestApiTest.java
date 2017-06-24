import com.mongodb.BasicDBObject;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class RestApiTest {
    @Test
    public void shouldGetARecipesFromApi() throws Exception
    {
        DatabaseProperties prop = new DatabaseProperties();
        String url = "http://" + prop.getValue("serverAddress") + ":4567/recipes";
        given().when().get(url).then().statusCode(200);
    }

    @Test
    public void shouldUpdateLikedValueByPutRequest() throws Exception
    {
        DatabaseProperties prop = new DatabaseProperties();
        Database db = new Database();
        BasicDBObject dbo = (BasicDBObject) db.FindFirstRecipe();
        double prevLikedValue = dbo.getDouble("liked");

        String url = "http://" + prop.getValue("serverAddress") + ":4567/recipes_update?id=" + dbo.getString("_id");
        given().when().put(url).then().statusCode(200);

        dbo = (BasicDBObject) db.FindFirstRecipe();

        assertEquals(prevLikedValue + 1., dbo.getDouble("liked"), 0);

    }
}