import com.mongodb.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database implements AutoCloseable{
    private MongoClient dbClient;
    private DBCollection dbCollection;

    Database()
    {
        String passStr = "f37c5662@#";
        char[] passChar = passStr.toCharArray();
        MongoCredential credential = MongoCredential.createCredential("myAdmin", "admin", passChar);
        ServerAddress serverAddress = new ServerAddress("bornt2.myqnapcloud.com", 27017);

        this.dbClient = new MongoClient(serverAddress, Arrays.asList(credential));
        DB db = dbClient.getDB("recipesDB");
        this.dbCollection = db.getCollection("recipes");

    }

    DBObject FindOneByName(String name)
    {
        BasicDBObject query = new BasicDBObject("name", name);
        DBObject dbo = dbCollection.findOne(query);
        return dbo;
    }

    List<Recipe> FindAll()
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
