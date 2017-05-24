import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class Database implements AutoCloseable{
    private DB db;
    private MongoClient dbClient;
    private DBCollection dbCollection;

    Database()
    {
        String URI = "mongodb://127.0.0.1:27017";
        this.dbClient = new MongoClient();
        this.db = dbClient.getDB("recipesDB");
        this.dbCollection = db.getCollection("recipes");

    }

    public DBObject FindOneByName(String name)
    {
        BasicDBObject query = new BasicDBObject("name", name);
        DBObject dbo = dbCollection.findOne(query);
        System.out.println(dbo);
        return dbo;
    }

    public List<Recipe> FindAll()
    {
        List<Recipe> recipes = new ArrayList<>();
        DBCursor dbObjects = dbCollection.find();
        while(dbObjects.hasNext())
        {
            DBObject dbObject = dbObjects.next();
            recipes.add(new Recipe((BasicDBObject) dbObject));
        }
        return recipes;
    }

    void Dispose()
    {
        //clean up resources
        this.dbClient.close();
    }

    @Override
    public void close() throws Exception {
        this.Dispose();
    }
}
