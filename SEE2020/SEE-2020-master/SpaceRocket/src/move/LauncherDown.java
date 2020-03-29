package move;


import constant.SpaceElevatorConstant;
import model.Position;
import model.SpaceRocketLauncher;

public class LauncherDown implements LauncherAction{

		@Override
		public boolean move(SpaceRocketLauncher spaceElevatorLauncher) {
			
			Position position = spaceElevatorLauncher.getPosition();
			position.setZ(position.getZ() - SpaceElevatorConstant.DISPLACEMENT);
			spaceElevatorLauncher.setPosition(position);
			System.out.println("Moving down:" + position);
			return position.getZ() == SpaceElevatorConstant.LAUNCHER_LOWEST_POSITION.getZ();
		}
		
}
