package com.alexnu;

import com.alexnu.cubesolver.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/solve")
public class SnakeCubeController {

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public String getSolution(@RequestParam String input) {

		List<Movement> moveList = Arrays.stream(input.split(","))
				.map(this::parseMove)
				.collect(Collectors.toList());

		if (moveList.size() != 26 || moveList.contains(null)) {
			return "Input must contain 27 valid moves (S or T).";
		}

		CubeSolver cubeSolver = new CubeSolver();

		for (Integer x = 0; x <= 2; x++) {
			for (Integer y = 0; y <= 2; y++) {
				for (Integer z = 0; z <= 2; z++) {
					for (Direction orientation : Direction.values()) {

						List<Step> initialSteps = Collections.singletonList(new Step(new Point(x, y, z), orientation));

						List<Step> solution = cubeSolver.solve(moveList, initialSteps);
						if (!solution.isEmpty()) {
							return printResults(moveList, solution);
						}
					}
				}
			}
		}

		return printResults(moveList, null);
	}

	private Movement parseMove(String move) {
		if (move.equals("S"))
			return Movement.STRAIGHT;

		if (move.equals("T"))
			return Movement.TURN;

		return null;
	}

	private String printResults(List<Movement> moves, List<Step> steps) {
		StringBuilder output = new StringBuilder("Attempting to solve 3x3 cube with the following movelist:\n\n");

		for (Movement move : moves) {
			output.append(move.toString()).append("\n");
		}

		output.append("\n............................................................\n\n");

		if (steps == null) {
			output.append("Could not find solution");
			return output.toString();
		}

		output.append("Found solution:\n\n");

		int index = 1;
		for (Step step : steps) {
			output.append(index++).append(". ").append(step.toString()).append("\n");
		}

		return output.toString();
	}
}