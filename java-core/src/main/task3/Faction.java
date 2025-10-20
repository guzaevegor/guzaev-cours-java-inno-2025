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
  private final Object dayLock;

  public Faction(String name, BlockingQueue<RobotPart> storage, Object dayLock) {
    this.name = name;
    this.factoryStorage = storage;
    this.dayLock = dayLock;

    for (RobotPart part : RobotPart.values()) {
      inventory.put(part, 0);
    }
  }

  @Override
  public void run() {
    for (int day = 1; day <= 100; day++) {
      synchronized (dayLock) {
        try {
          dayLock.wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return;
        }
      }

      collectParts();
      buildRobots();
    }

    System.out.println(name + " built total: " + robotsBuilt);
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
    while (Robot.canBuildFrom(inventory)) {
      Robot.buildFrom(inventory);
      robotsBuilt++;
    }
  }

  public int getRobotsBuilt() {
    return robotsBuilt;
  }

  public String getName() {
    return name;
  }
}
