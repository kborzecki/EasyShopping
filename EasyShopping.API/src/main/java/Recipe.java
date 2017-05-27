import com.mongodb.BasicDBObject;

import javax.swing.text.Document;
import java.util.List;

public class Recipe {

    private String id;
    private String name;
    private String category;
    private String subCategory;
    private String difficulty;
    private String prepTime;
    private String imageURL;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private int liked;

    public Recipe() {}

    public Recipe(BasicDBObject dbObject) {
        this.id = dbObject.get("_id").toString();
        this.name = dbObject.getString("name");
        this.category = dbObject.getString("category");
        this.subCategory = dbObject.getString("sub_category");
        this.difficulty = dbObject.getString("difficulty");
        this.prepTime = dbObject.getString("prep_time");
        this.imageURL = dbObject.getString("imageURL");
        this.steps = (List<String>) dbObject.get("steps");
        this.ingredients = (List<Ingredient>) dbObject.get("ingredients");
        this.liked = Integer.parseInt(dbObject.getString("liked"));
    }


    @Override
    public String toString() {
        return this.name;
    }
}
