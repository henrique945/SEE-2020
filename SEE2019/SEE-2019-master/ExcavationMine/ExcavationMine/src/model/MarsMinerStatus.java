package model;


public enum MarsMinerStatus {
	FULL_LOAD((short)1),
	EMPTY_LOAD((short)2),
	LOADING((short)3),
	UNLOADING((short)4);

    private short value;

    MarsMinerStatus(short value){
        this.value = value;
    }

    public short getValue(){return value;}

    public static MarsMinerStatus lookup(short value){
        for(MarsMinerStatus marsMinerStatus:MarsMinerStatus.values()){
            if(value==marsMinerStatus.value){
                return marsMinerStatus;
            }
        }
        return null;
    }

}
