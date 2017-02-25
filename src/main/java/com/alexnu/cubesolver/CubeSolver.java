package com.alexnu.cubesolver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CubeSolver {

	public List<Step> solve(List<Movement> moves, List<Step> steps) {

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
				.map(nextStep -> solve(remainingMoves, addToList(steps, nextStep)))
				.reduce(new ArrayList<>(), this::mergeSolutions);
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

	private <T> List<T> addToList(List<T> list, T item) {
		List<T> newList = new ArrayList<>(list);
		newList.add(item);
		return newList;
	}

	private <T> List<T> mergeSolutions(List<T> listA, List<T> listB) {
		if (!listA.isEmpty()) {
			return listA;
		}

		return listB;
	}
}