import com.mongodb.DBObject;
import com.mongodb.client.result.UpdateResult;

import javax.xml.crypto.Data;

import static spark.Spark.*;


class RecipeResource {

    RecipeResource()
    {
        SetUpEndPoints();
    }
    private void SetUpEndPoints()
    {
        get("/recipes/:name", "application/json", (request, response) -> {
            final Database db = new Database();
            DBObject dbo = db.FindOneByName(request.params(":name"));
            db.Dispose();
            return dbo;
        });

        get("/recipes", "application/json", (request, response) -> {
            try (final Database db = new Database()){
                return db.FindAll();
            }

        }, new JsonTransfomer());

        put("/recipes/update/:id", "application/json", (request, response) ->{

            try(final Database db = new Database()) {
                String id = request.params(":id");
                UpdateResult updateResult = db.IncrementLikedValue(id);
                return updateResult.getModifiedCount();
            }
        });

        get("/test", (req, res) -> "TEST");
    }

}
