import com.mongodb.client.result.UpdateResult;

import static spark.Spark.get;
import static spark.Spark.put;



class RecipeResource {

    RecipeResource()
    {
        SetUpEndPoints();
    }
    private void SetUpEndPoints()
    {
        get("/recipes/:id", "application/json", (request, response) -> {
            final Database db = new Database();
            return db.FindOneById(request.params(":id"));
        });

        get("/recipes", "application/json", (request, response) -> {
            try (final Database db = new Database()){
                return db.FindAll(false);
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
