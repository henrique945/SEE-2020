package move;

import helper.PositionOfLuHa;
import model.MarsHabitatRooftop;
import model.Position;

public class RooftopUp implements RooftopAction {


	@Override
	public boolean move(MarsHabitatRooftop lunarHabitatRooftop) {
		Position position = lunarHabitatRooftop.getPosition();
		position.setZ(position.getZ() + 10);
		lunarHabitatRooftop.setPosition(position);
		System.out.println("Moving up:"+position);
		if(position.equals(PositionOfLuHa.reference))
			return true;
		return false;
	}

}
