package move;


import constant.OxygenConstant;
import helper.PositionOfOxygenFactory;
import model.OxygenFactoryRooftop;
import model.Position;

public class RooftopDown implements RooftopAction{

		@Override
		public boolean move(OxygenFactoryRooftop oxygenFactoryRooftop) {
			
			Position position = oxygenFactoryRooftop.getPosition();
			position.setZ(position.getZ() - OxygenConstant.DISPLACEMENT);
			oxygenFactoryRooftop.setPosition(position);
			System.out.println("Moving down:" + position);
			return position.getZ() == PositionOfOxygenFactory.lowestHeightLimit.getZ();
			//return position.getZ() == PositionOfOxygenFactory.heightLimit.getZ();
		}
		
}
