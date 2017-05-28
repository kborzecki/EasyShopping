import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database implements AutoCloseable{
    private MongoClient dbClient;
    private DBCollection dbCollection;
    private MongoDatabase db2;

    Database() throws IOException
    {
        DatabaseProperties properties = new DatabaseProperties();
        String passStr = properties.getValue("mongoPass");
        String user = properties.getValue("mongoUser");
        String serverAddressStr = properties.getValue("serverAddress");

        char[] passChar = passStr.toCharArray();

        MongoCredential credential = MongoCredential.createCredential(user, "admin", passChar);
        ServerAddress serverAddress = new ServerAddress(serverAddressStr, 27017);

        this.dbClient = new MongoClient(serverAddress, Arrays.asList(credential));
        db2 = dbClient.getDatabase("recipesDB");
        DB db = dbClient.getDB("recipesDB");
        this.dbCollection = db.getCollection("recipes");

    }

    DBObject FindOneByName(String name)
    {
        BasicDBObject query = new BasicDBObject("name", name);
        DBObject dbo = dbCollection.findOne(query);
        return dbo;
    }

    DBObject FindOneById(String id)
    {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        return dbCollection.findOne(query);
    }

    UpdateResult IncrementLikedValue(String id)
    {
        Bson filter = new Document("_id", new ObjectId(id));
        MongoCollection<org.bson.Document> coll = db2.getCollection("recipes");
        BasicDBObject dbo = (BasicDBObject) FindOneById(id);
        int lastLikes = Integer.parseInt(dbo.getString("liked"));
        return coll.updateOne(filter, new Document("$set", new Document("liked", lastLikes + 1)));

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
