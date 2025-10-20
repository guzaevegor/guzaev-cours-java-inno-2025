package task3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Factory implements Runnable {
  private final BlockingQueue<RobotPart> storage;
  private final Random random = new Random();
  private final int DAYS = 100;
  private final Object dayLock;

  public Factory(BlockingQueue<RobotPart> storage, Object dayLock) {
    this.storage = storage;
    this.dayLock = dayLock;
  }

  @Override
  public void run() {
    for (int day = 1; day <= DAYS; day++) {
      int partsToday = random.nextInt(10) + 1;

      for (int i = 0; i < partsToday; i++) {
        try {
          storage.put(getRandomPart());
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return;
        }
      }

      System.out.println("Day " + day + ": Factory produced " + partsToday + " parts.");

      // уведомляем фракции о наступлении ночи
      synchronized (dayLock) {
        dayLock.notifyAll();
      }

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }


    synchronized (dayLock) {
      dayLock.notifyAll();
    }

    System.out.println("Factory finished after 100 days.");
  }


  private RobotPart getRandomPart() {
    RobotPart[] parts = RobotPart.values();
    return parts[random.nextInt(parts.length)];
  }
}
