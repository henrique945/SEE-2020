package model;


public enum OxygenFactoryRooftopStatus {
    STOPPED_UP((short)1),
    MOVING_UP((short)2),
    STOPPED_DOWN((short)3),
    MOVING_DOWN((short)4);

    private short value;

    OxygenFactoryRooftopStatus(short value){
        this.value = value;
    }

    public short getValue(){return value;}

    public static OxygenFactoryRooftopStatus lookup(short value){
    	
        for(OxygenFactoryRooftopStatus rooftopStatus : OxygenFactoryRooftopStatus.values())
            if(value==rooftopStatus.value)
                return rooftopStatus;
            
        return null;
    }

}
