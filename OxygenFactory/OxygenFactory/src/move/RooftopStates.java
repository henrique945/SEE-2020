package move;


public enum RooftopStates {
	
	IDLE(new RooftopIdle()),
	UP(new RooftopUp()),
	DOWN(new RooftopDown());
	
	private RooftopAction value;
	
	private RooftopStates(RooftopAction value){
		this.value = value;
	}
	
	public RooftopAction getValue(){
		return value;
	}
}
