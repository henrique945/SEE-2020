package move;


public enum LauncherStates {
	
	IDLE(new LauncherIdle()),
	UP(new LauncherUp()),
	DOWN(new LauncherDown());
	
	private LauncherAction value;
	
	private LauncherStates(LauncherAction value){
		this.value = value;
	}
	
	public LauncherAction getValue(){
		return value;
	}
}
