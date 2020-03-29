package model;


public enum LauncherStatus {
    STOPPED_UP((short)1),
    MOVING_UP((short)2),
    STOPPED_DOWN((short)3),
    MOVING_DOWN((short)4);

    private short value;

    LauncherStatus(short value){
        this.value = value;
    }

    public short getValue(){return value;}

    public static LauncherStatus lookup(short value){
    	
        for(LauncherStatus rooftopStatus : LauncherStatus.values())
            if(value==rooftopStatus.value)
                return rooftopStatus;
            
        return null;
    }

}
