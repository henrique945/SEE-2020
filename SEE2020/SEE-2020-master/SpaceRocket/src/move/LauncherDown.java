package move;


import constant.SpaceRocketConstant;
import model.Position;
import model.SpaceRocketLauncher;

public class LauncherDown implements LauncherAction{

		@Override
		public boolean move(SpaceRocketLauncher spaceElevatorLauncher) {
			
			Position position = spaceElevatorLauncher.getPosition();
			position.setZ(position.getZ() - SpaceRocketConstant.DISPLACEMENT);
			spaceElevatorLauncher.setPosition(position);
			System.out.println("Moving down:" + position);
			return position.getZ() == SpaceRocketConstant.LAUNCHER_LOWEST_POSITION.getZ();
		}
		
}
