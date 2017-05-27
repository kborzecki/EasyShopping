import com.mongodb.BasicDBObject;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Recipe {

    private String id;
    private String name;
    private String category;
    private String subCategory;
    private String difficulty;
    private String prepTime;
    private String imageURL;
    private List<Ingredient> ingredients;
    private List<Document> steps;

    public Recipe() {}

    public Recipe(BasicDBObject dbObject) {
        this.id = dbObject.get("_id").toString();
        this.name = dbObject.getString("name");
        this.category = dbObject.getString("category");
        this.subCategory = dbObject.getString("sub_category");
        this.difficulty = dbObject.getString("difficulty");
        this.prepTime = dbObject.getString("prep_time");
        this.imageURL = dbObject.getString("imageURL");
        this.steps = (List<Document>) dbObject.get("steps");
        this.ingredients = (List<Ingredient>) dbObject.get("ingredients");
    }


    @Override
    public String toString() {
        return this.name;
    }
}
