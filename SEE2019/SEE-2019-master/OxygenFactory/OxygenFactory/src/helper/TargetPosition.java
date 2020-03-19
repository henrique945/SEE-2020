package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

import constant.MessageConstant;

public class TargetPosition {

	private ArrayList<String> list = new ArrayList<>();
	private ArrayList<String> listPositions = new ArrayList<>();

	public TargetPosition() {
		listPositions.add(MessageConstant.REQUEST_NORTH);
		listPositions.add(MessageConstant.REQUEST_SOUTH);
		listPositions.add(MessageConstant.REQUEST_EAST);
		listPositions.add(MessageConstant.REQUEST_WEST);
	}

	private ArrayList<String> generateList() {
		@SuppressWarnings("unchecked")
		ArrayList<String> clone = (ArrayList<String>) listPositions.clone();
		ArrayList<String> list = clone;
		long seed = System.nanoTime();
		Collections.shuffle(list, new Random(seed));
		return list;
	}

	public ArrayList<String> getList() {
		if (list.isEmpty())
			list.add(MessageConstant.REQUEST_O2FAC);
		if (list.size() < 2) {
			ArrayList<String> new_list = new ArrayList<>();
			do {
				new_list = generateList();
				System.out.println(new_list);
			} while (new_list.get(0).equals(list.get(0)));
			@SuppressWarnings("unchecked")
			ArrayList<String> clone = (ArrayList<String>) new_list.clone();
			list = clone;
		}
		return list;
	}

}
