package task3;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Faction implements Runnable {
  private final String name;
  private final BlockingQueue<RobotPart> factoryStorage;
  private final Map<RobotPart, Integer> inventory = new ConcurrentHashMap<>();
  private int robotsBuilt = 0;
  private final int MAX_PARTS_PER_NIGHT = 5;

  public Faction(String name, BlockingQueue<RobotPart> storage) {
    this.name = name;
    this.factoryStorage = storage;

    for (RobotPart part : RobotPart.values()) {
      inventory.put(part, 0);
    }
  }

  @Override
  public void run() {
    for (int day = 1; day <= 100; day++) {
      collectParts();

      buildRobots();

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  void collectParts() {
    int collected = 0;
    while (collected < MAX_PARTS_PER_NIGHT && !factoryStorage.isEmpty()) {
      RobotPart part = factoryStorage.poll();
      if (part != null) {
        inventory.merge(part, 1, Integer::sum);
        collected++;
      }
    }
  }

  void buildRobots() {
    while (canBuildRobot()) {
      inventory.merge(RobotPart.HEAD, -1, Integer::sum);
      inventory.merge(RobotPart.TORSO, -1, Integer::sum);
      inventory.merge(RobotPart.HAND, -2, Integer::sum);
      inventory.merge(RobotPart.FEET, -2, Integer::sum);

      robotsBuilt++;
    }
  }

  private boolean canBuildRobot() {
    return inventory.get(RobotPart.HEAD) >= 1 &&
        inventory.get(RobotPart.TORSO) >= 1 &&
        inventory.get(RobotPart.HAND) >= 2 &&
        inventory.get(RobotPart.FEET) >= 2;
  }

  public int getRobotsBuilt() {
    return robotsBuilt;
  }

  public String getName() {
    return name;
  }
}
