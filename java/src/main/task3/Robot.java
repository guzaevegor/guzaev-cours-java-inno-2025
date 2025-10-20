package task3;

import java.util.Map;

public class Robot {
  private static final int HEAD_NEEDED = 1;
  private static final int TORSO_NEEDED = 1;
  private static final int HAND_NEEDED = 2;
  private static final int FEET_NEEDED = 2;

  public static boolean canBuildFrom(Map<RobotPart, Integer> inv) {
    return inv.getOrDefault(RobotPart.HEAD, 0) >= HEAD_NEEDED &&
        inv.getOrDefault(RobotPart.TORSO, 0) >= TORSO_NEEDED &&
        inv.getOrDefault(RobotPart.HAND, 0) >= HAND_NEEDED &&
        inv.getOrDefault(RobotPart.FEET, 0) >= FEET_NEEDED;
  }

  public static void buildFrom(Map<RobotPart, Integer> inv) {
    inv.merge(RobotPart.HEAD, -HEAD_NEEDED, Integer::sum);
    inv.merge(RobotPart.TORSO, -TORSO_NEEDED, Integer::sum);
    inv.merge(RobotPart.HAND, -HAND_NEEDED, Integer::sum);
    inv.merge(RobotPart.FEET, -FEET_NEEDED, Integer::sum);
  }
}
