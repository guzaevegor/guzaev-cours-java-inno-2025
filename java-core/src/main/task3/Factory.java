package task3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Factory implements Runnable {
  private final BlockingQueue<RobotPart> storage;
  private final Random random = new Random();
  private final int DAYS = 100;

  public Factory(BlockingQueue<RobotPart> storage) {
    this.storage = storage;
  }

  @Override
  public void run() {
    for (int day = 1; day <= DAYS; day++) {
      int partsToday = random.nextInt(10) + 1;

      for (int i = 0; i < partsToday; i++) {
        RobotPart part = getRandomPart();
        try {
          storage.put(part);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private RobotPart getRandomPart() {
    RobotPart[] parts = RobotPart.values();
    return parts[random.nextInt(parts.length)];
  }
}
