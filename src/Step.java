public class Step {

	Point point;
	Direction direction;

	Step(Point point, Direction direction) {
		this.point = point;
		this.direction = direction;
	}

	Boolean isInvalid() {
		if (point.x < 0 || point.x > 2) {
			return true;
		}

		if (point.y < 0 || point.y > 2) {
			return true;
		}

		if (point.z < 0 || point.z > 2) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "\nPoint: " + point.toString() + ", Direction: " + direction.toString();
	}
}