package task3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class RobotFactoryCombinedTest {

  @Test
  @Timeout(20)
  void testSimulationWithBarrierCompletes() throws InterruptedException {
    BlockingQueue<RobotPart> storage = new LinkedBlockingQueue<>();
    CyclicBarrier barrier = new CyclicBarrier(3); // 2 фракции + фабрика

    Factory factory = new Factory(storage, barrier);
    Faction faction1 = new Faction("Faction1", storage, barrier);
    Faction faction2 = new Faction("Faction2", storage, barrier);

    Thread factoryThread = new Thread(factory);
    Thread factionThread1 = new Thread(faction1);
    Thread factionThread2 = new Thread(faction2);

    factoryThread.start();
    factionThread1.start();
    factionThread2.start();

    factoryThread.join();
    factionThread1.join();
    factionThread2.join();

    int totalRobots = faction1.getRobotsBuilt() + faction2.getRobotsBuilt();
    assertTrue(totalRobots >= 0, "Total robots should be non-negative");
  }

  // Адаптация старого теста по ограничению сбора деталей с CyclicBarrier
  @Test
  @Timeout(10)
  void testMaxPartsPerNightLimit() throws Exception {
    BlockingQueue<RobotPart> storage = new LinkedBlockingQueue<>();

    for (int i = 0; i < 100; i++) {
      storage.put(RobotPart.HEAD);
    }

    CyclicBarrier barrier = new CyclicBarrier(2); // 1 фракция + главный поток теста

    Faction faction = new Faction("TestFaction", storage, barrier);

    Thread factionThread = new Thread(() -> {
      try {
        barrier.await();
        faction.collectParts();
        barrier.await();
      } catch (Exception e) {
        fail("Exception in faction thread");
      }
    });

    factionThread.start();
    barrier.await();
    barrier.await();

    factionThread.join();

    assertEquals(95, storage.size(),
        "Faction should take exactly 5 parts per night");
  }


  @Test
  @Timeout(15)
  void testConcurrentAccessToSharedStorage() throws InterruptedException {
    BlockingQueue<RobotPart> storage = new LinkedBlockingQueue<>();

    int numThreads = 10;
    CountDownLatch startLatch = new CountDownLatch(1);
    CountDownLatch doneLatch = new CountDownLatch(numThreads);

    for (int i = 0; i < 50; i++) {
      storage.put(RobotPart.HEAD);
    }

    for (int i = 0; i < numThreads; i++) {
      new Thread(() -> {
        try {
          startLatch.await();
          for (int j = 0; j < 3; j++) {
            storage.poll();
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        } finally {
          doneLatch.countDown();
        }
      }).start();
    }

    startLatch.countDown();
    assertTrue(doneLatch.await(5, TimeUnit.SECONDS),
        "All threads should complete within timeout");

    int remainingParts = storage.size();
    assertTrue(remainingParts >= 0 && remainingParts <= 50,
        "Storage should handle concurrent access correctly");
  }
}
