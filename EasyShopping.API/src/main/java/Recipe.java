import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

public class Recipe {
    private String id;
    private String name;

    public Recipe(BasicDBObject dbObject)
    {
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.name = dbObject.getString("name");
    }

    @Override
    public String toString() {
        return this.name;
    }
}
