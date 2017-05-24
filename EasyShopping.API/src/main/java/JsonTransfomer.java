
import com.google.gson.*;
import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;

public class JsonTransfomer implements ResponseTransformer {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String render(Object model) throws Exception {
        if(model instanceof Response)
        {
            return gson.toJson(new HashMap<>());
        }
        return gson.toJson(model);
    }
}
