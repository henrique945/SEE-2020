package objectClass;

/**
 * Created by Lucas on 21/03/2017.
 */
public enum ResourceType {
    FOOD((short)1),
    OXYGEN((short)2),
    CARBON_DIOXIDE((short)3);

    private short value;

    private ResourceType(short value){
        this.value = value;
    }

    public static ResourceType lookup(short value){
        for(ResourceType resourceType:ResourceType.values()){
            if(resourceType.value == value)return resourceType;
        }
        return null;
    }

    public short getValue(){
        return value;
    }
}
