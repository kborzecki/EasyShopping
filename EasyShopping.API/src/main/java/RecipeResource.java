import com.mongodb.DBObject;

import static spark.Spark.get;


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

        get("/test", (req, res) -> "TEST");
    }

}
