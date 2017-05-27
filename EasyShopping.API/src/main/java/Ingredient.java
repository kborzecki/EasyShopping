import com.mongodb.BasicDBObject;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Ingredient {
    private String quantity;
    private String quantityType;
    private String quantityDisplay;
    private String name;
    private String type;

    public Ingredient() {}

    public Ingredient(BasicDBObject dbobject)
    {
        this.quantity = dbobject.getString("quantity");
        this.quantityType = dbobject.getString("quantity_type");
        this.quantityDisplay = dbobject.getString("quantity_display");
        this.name = dbobject.getString("name");
        this.type = dbobject.getString("type");
    }

    public Ingredient(String quantity, String quantityType, String quantityDisplay, String name, String type) {
        this.quantity = quantity;
        this.quantityType = quantityType;
        this.quantityDisplay = quantityDisplay;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}