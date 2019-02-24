package objectClass;

/**
 * Created by Lucas on 21/03/2017.
 */
public class Resource {

    private Double quantity;
    private ResourceType resourceType;

    public Resource(){

    }

    public Resource(Double quantity,ResourceType resourceType){
        this.setResourceType(resourceType);
        this.setQuantity(quantity);
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public String toString(){
        String lineSeparator = System.getProperty("line.separator");
        return "Resource:"+lineSeparator+
                "Quantity: "+quantity.toString()+lineSeparator+
                "Resource Type: "+resourceType.toString();
    }
}
