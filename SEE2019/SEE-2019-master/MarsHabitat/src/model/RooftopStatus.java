package model;

/**
 * Created by Lucas on 31/03/2017.
 */
public enum RooftopStatus {
    STOPPED_UP((short)1),
    MOVING_UP((short)2),
    STOPPED_DOWN((short)3),
    MOVING_DOWN((short)4);

    private short value;

    RooftopStatus(short value){
        this.value = value;
    }

    public short getValue(){return value;}

    public static RooftopStatus lookup(short value){
        for(RooftopStatus rooftopStatus:RooftopStatus.values()){
            if(value==rooftopStatus.value){
                return rooftopStatus;
            }
        }
        return null;
    }

}
