import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class CubeSolver {

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

	private List<Step> solve(List<Movement> moves, List<Step> steps) {

		if (!steps.isEmpty() && steps.get(steps.size() - 1).isInvalid()) {
			return new ArrayList<>();
		}

		if (moves.isEmpty()) {
			return steps;
		}

		Movement nextMove = moves.get(0);
		List<Movement> remainingMoves = moves.subList(1, moves.size());
		List<Step> nextSteps = getNextSteps(steps, nextMove);

		return nextSteps
				.stream()
				.map(nextStep -> solve(remainingMoves, ListHelper.merge(steps, nextStep)))
				.reduce(new ArrayList<Step>(), ListHelper::merge);
	}

	private List<Step> getNextSteps(List<Step> currentSteps, Movement nextMove) {

		Step lastStep = currentSteps.get(currentSteps.size() - 1);
		List<Point> visitedPoints = currentSteps.stream()
				.map(step -> step.point)
				.collect(Collectors.toList());

		Point nextPoint = getNextPoint(lastStep);
		if (visitedPoints.contains(nextPoint)) {
			return new ArrayList<>();
		}

		List<Direction> nextDirections = getNextDirections(lastStep, nextMove);

		return nextDirections.stream()
				.map(nextDirection -> new Step(nextPoint, nextDirection))
				.collect(Collectors.toList());
	}

	private List<Direction> getNextDirections(Step lastStep, Movement nextMove) {

		List<Direction> nextDirections = new ArrayList<>();

		if (nextMove.equals(Movement.STRAIGHT)) {
			nextDirections.add(lastStep.direction);
		}

		if (nextMove.equals(Movement.TURN)) {

			switch (lastStep.direction) {
				case UP:
				case DOWN:
					nextDirections.add(Direction.RIGHT);
					nextDirections.add(Direction.LEFT);
					nextDirections.add(Direction.FRONT);
					nextDirections.add(Direction.BACK);
					break;
				case RIGHT:
				case LEFT:
					nextDirections.add(Direction.UP);
					nextDirections.add(Direction.DOWN);
					nextDirections.add(Direction.FRONT);
					nextDirections.add(Direction.BACK);
					break;
				case FRONT:
				case BACK:
					nextDirections.add(Direction.RIGHT);
					nextDirections.add(Direction.LEFT);
					nextDirections.add(Direction.UP);
					nextDirections.add(Direction.DOWN);
					break;
			}
		}
		return nextDirections;
	}

	private Point getNextPoint(Step lastStep) {
		Integer nextX = lastStep.point.x;
		Integer nextY = lastStep.point.y;
		Integer nextZ = lastStep.point.z;

		switch (lastStep.direction) {
			case UP:
				nextY++;
				break;
			case DOWN:
				nextY--;
				break;
			case RIGHT:
				nextX++;
				break;
			case LEFT:
				nextX--;
				break;
			case FRONT:
				nextZ--;
				break;
			case BACK:
				nextZ++;
				break;
		}

		return new Point(nextX, nextY, nextZ);
	}

	public static void main(String[] args) {

		CubeSolver cubeSolver = new CubeSolver();

		for (Integer x = 0; x <= 2; x++) {
			for (Integer y = 0; y <= 2; y++) {
				for (Integer z = 0; z <= 2; z++) {
					for (Direction orientation : Direction.values()) {

						List<Step> initialSteps = Collections.singletonList(new Step(new Point(x, y, z), orientation));

						List<Step> solution = cubeSolver.solve(initialMoves, initialSteps);
						if (!solution.isEmpty()) {
							System.out.println("Found a solution:\n" + solution);
							return;
						}
					}
				}
			}
		}

		System.out.println("Could not find solution");
	}
}