package com.alexnu.cubesolver;

import java.util.ArrayList;
import java.util.List;


public class ListHelper {

	public static <T> List<T> merge(List<T> list, T item) {
		List<T> newList = new ArrayList<T>(list);
		newList.add(item);
		return newList;
	}

	public static <T> List<T> mergeSolutions(List<T> listA, List<T> listB) {
		if (!listA.isEmpty()) {
			return listA;
		}

		return listB;
	}
}
