package move;

import helper.PositionOfLuHa;
import model.MarsHabitatRooftop;
import model.Position;

public class RooftopDown implements RooftopAction{


	@Override
	public boolean move(MarsHabitatRooftop lunarHabitatRooftop) {
		Position position = lunarHabitatRooftop.getPosition();
		position.setZ(position.getZ() - 10);
		lunarHabitatRooftop.setPosition(position);
		System.out.println("Moving down:"+position);
		if(position.equals(PositionOfLuHa.heightLimit))
			return true;
		return false;
	}

}
