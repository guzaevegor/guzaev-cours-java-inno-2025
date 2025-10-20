package task3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

public class Factory implements Runnable {
  private final BlockingQueue<RobotPart> storage;
  private final Random random = new Random();
  private final int DAYS = 100;
  private final CyclicBarrier barrier;

  public Factory(BlockingQueue<RobotPart> storage, CyclicBarrier barrier) {
    this.storage = storage;
    this.barrier = barrier;
  }

  @Override
  public void run() {
    try {
      for (int day = 1; day <= DAYS; day++) {
        int partsToday = random.nextInt(10) + 1;

        for (int i = 0; i < partsToday; i++) {
          storage.put(getRandomPart());
        }

        System.out.println("Day " + day + ": Factory produced " + partsToday + " parts.");

        barrier.await();

        barrier.await();
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
      return;
    }

    System.out.println("Factory finished after 100 days.");
  }

  private RobotPart getRandomPart() {
    RobotPart[] parts = RobotPart.values();
    return parts[random.nextInt(parts.length)];
  }
}
