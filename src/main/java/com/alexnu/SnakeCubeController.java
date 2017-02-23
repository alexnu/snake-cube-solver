package com.alexnu;

import com.alexnu.cubesolver.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping
public class SnakeCubeController {

	private static final List<Movement> initialMoves = Arrays.asList(
			Movement.STRAIGHT,
			Movement.TURN,
			Movement.STRAIGHT,
			Movement.TURN,
			Movement.STRAIGHT,
			Movement.TURN,
			Movement.STRAIGHT,
			Movement.TURN,
			Movement.TURN,
			Movement.TURN,
			Movement.TURN,
			Movement.STRAIGHT,
			Movement.TURN,
			Movement.STRAIGHT,
			Movement.TURN,
			Movement.TURN,
			Movement.TURN,
			Movement.STRAIGHT,
			Movement.TURN,
			Movement.TURN,
			Movement.STRAIGHT,
			Movement.TURN,
			Movement.TURN,
			Movement.TURN,
			Movement.STRAIGHT,
			Movement.STRAIGHT
	);

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public String getSolution() {

		CubeSolver cubeSolver = new CubeSolver();

		for (Integer x = 0; x <= 2; x++) {
			for (Integer y = 0; y <= 2; y++) {
				for (Integer z = 0; z <= 2; z++) {
					for (Direction orientation : Direction.values()) {

						List<Step> initialSteps = Collections.singletonList(new Step(new Point(x, y, z), orientation));

						List<Step> solution = cubeSolver.solve(initialMoves, initialSteps);
						if (!solution.isEmpty()) {
							return printSolution(solution);
						}
					}
				}
			}
		}

		return "Could not find solution";
	}

	private String printSolution(List<Step> steps) {
		StringBuffer output = new StringBuffer("Found solution:\n\n");

		int index = 1;
		for (Step step : steps) {
			output.append(index++ + ". " + step.toString() + "\n");
		}

		return output.toString();
	}
}
