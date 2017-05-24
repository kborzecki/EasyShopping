import com.mongodb.*;

import java.util.List;

import static spark.Spark.*;


public class Main {
    public static void main(String[] args)
    {
        final Database db = new Database();
        List<Recipe> recipes = db.FindAll();
        for (Recipe x : recipes)
        {
            System.out.println(x.toString());
        }

        get("/recipes", "application/json", (request, response) -> "not now");


        db.Dispose();
    }
}
