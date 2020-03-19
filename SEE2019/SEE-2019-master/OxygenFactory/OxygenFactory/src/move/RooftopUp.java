package move;

import constant.OxygenConstant;
import helper.PositionOfOxygenFactory;
import model.OxygenFactoryRooftop;
import model.Position;

public class RooftopUp implements RooftopAction{

	@Override
	public boolean move(OxygenFactoryRooftop oxygenFactoryRooftop) {
		
		Position position = oxygenFactoryRooftop.getPosition();
		position.setZ(position.getZ() + OxygenConstant.DISPLACEMENT);
		oxygenFactoryRooftop.setPosition(position);
		System.out.println("Moving up:" + position);
		
		return position.getZ() == PositionOfOxygenFactory.reference.getZ();
	}
	
}
