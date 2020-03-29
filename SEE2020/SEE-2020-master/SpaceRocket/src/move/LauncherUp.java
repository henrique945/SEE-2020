package move;

import constant.SpaceRocketConstant;
import model.Position;
import model.SpaceRocketLauncher;

public class LauncherUp implements LauncherAction{

	@Override
	public boolean move(SpaceRocketLauncher spaceElevatorLauncher) {
		
		Position position = spaceElevatorLauncher.getPosition();
		position.setZ(position.getZ() + SpaceRocketConstant.DISPLACEMENT);
		spaceElevatorLauncher.setPosition(position);
		System.out.println("Moving up:" + position);
		
		return position.getZ() == SpaceRocketConstant.LAUNCHER_HIGHEST_POSITION.getZ();
	}
	
}
