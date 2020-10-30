package today.exusiai.utils.animation;

public class AnimationUtil {


	private static float defaultSpeed = 0.125f;

	public static double animate(double target, double current, double speed) {
		      boolean larger = target > current;
		      if (speed < 0.0D) {
		         speed = 0.0D;
		      } else if (speed > 1.0D) {
		         speed = 1.0D;
		      }

		      double dif = Math.max(target, current) - Math.min(target, current);
		      double factor = dif * speed;
		      if (factor < 0.1D) {
		         factor = 0.1D;
		      }

		      if (larger) {
		         current += factor;
		      } else {
		         current -= factor;
		      }

		      return current;
	}
	   
	public static float moveTowards(float current, float end, float minSpeed) {
		return moveTowards(current, end, defaultSpeed, minSpeed);
	}

	public static float moveTowards(float current, float end, float smoothSpeed, float minSpeed) {
		float movement = (end - current) * smoothSpeed;

		if (movement > 0) {
			movement = Math.max(minSpeed, movement);
			movement = Math.min(end - current, movement);
		} else if (movement < 0) {
			movement = Math.min(-minSpeed, movement);
			movement = Math.max(end - current, movement);
		}

		return current + movement;
	}
	
    // IDK where this is from but I cant remember making it so it's probably skidded

    public static float calculateCompensation(float target, float current, long delta, int speed) {
        float diff = current - target;
        if (delta < 1L) {
            delta = 1L;
        }

        double xD;
        if (diff > (float)speed) {
            xD = (double)((long)speed * delta / 16L) < 0.25D ? 0.5D : (double)((long)speed * delta / 16L);
            current = (float)((double)current - xD);
            if (current < target) {
                current = target;
            }
        } else if (diff < (float)(-speed)) {
            xD = (double)((long)speed * delta / 16L) < 0.25D ? 0.5D : (double)((long)speed * delta / 16L);
            current = (float)((double)current + xD);
            if (current > target) {
                current = target;
            }
        } else {
            current = target;
        }

        return current;
    }
}
