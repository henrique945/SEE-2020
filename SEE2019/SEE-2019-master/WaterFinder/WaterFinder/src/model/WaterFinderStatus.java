package model;


public enum WaterFinderStatus {
	FULL_LOAD((short)1),
	EMPTY_LOAD((short)2),
	LOADING((short)3),
	UNLOADING((short)4);

    private short value;

    WaterFinderStatus(short value){
        this.value = value;
    }

    public short getValue(){return value;}

    public static WaterFinderStatus lookup(short value){
        for(WaterFinderStatus waterFinderStatus:WaterFinderStatus.values()){
            if(value==waterFinderStatus.value){
                return waterFinderStatus;
            }
        }
        return null;
    }
}