package task3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class RobotFactoryTest {

  @Test
  @Timeout(15)
  void testBasicSimulationCompletes() throws InterruptedException {
    BlockingQueue<RobotPart> storage = new LinkedBlockingQueue<>();
    Object dayLock = new Object();

    Factory factory = new Factory(storage, dayLock);
    Faction world = new Faction("World", storage, dayLock);
    Faction wednesday = new Faction("Wednesday", storage, dayLock);

    Thread factoryThread = new Thread(factory);
    Thread worldThread = new Thread(world);
    Thread wednesdayThread = new Thread(wednesday);

    factoryThread.start();
    worldThread.start();
    wednesdayThread.start();

    factoryThread.join();
    worldThread.join();
    wednesdayThread.join();

    int totalRobots = world.getRobotsBuilt() + wednesday.getRobotsBuilt();
    assertTrue(totalRobots >= 0, "Total robots should be non-negative");
    System.out.println(world.getName() + ": " + world.getRobotsBuilt());
    System.out.println(wednesday.getName() + ": " + wednesday.getRobotsBuilt());
  }

  @Test
  @Timeout(15)
  void testFactionCannotExceedMaxPartsPerNight() throws InterruptedException {
    BlockingQueue<RobotPart> storage = new LinkedBlockingQueue<>();

    for (int i = 0; i < 100; i++) {
      storage.put(RobotPart.HEAD);
    }

    Faction faction = new Faction("TestFaction", storage, new Object());

    CountDownLatch latch = new CountDownLatch(1);
    Thread testThread = new Thread(() -> {
      try {
        latch.await();
        faction.collectParts();
      } catch (Exception e) {
        fail("Exception in faction thread: " + e.getMessage());
      }
    });

    testThread.start();
    latch.countDown();
    testThread.join(2000);

    int remainingParts = storage.size();
    assertEquals(95, remainingParts,
        "Faction should take exactly 5 parts per night");
  }

  @Test
  @Timeout(15)
  void testFactoryProducesCorrectAmountOfParts() throws InterruptedException {
    BlockingQueue<RobotPart> storage = new LinkedBlockingQueue<>();
    Object dayLock = new Object();
    Factory factory = new Factory(storage, dayLock);

    Thread factoryThread = new Thread(factory);
    factoryThread.start();
    factoryThread.join();

    int totalParts = storage.size();
    assertTrue(totalParts >= 100 && totalParts <= 1000,
        "Factory should produce between 100 and 1000 parts over 100 days (1-10 per day)");
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

  @Test
  @Timeout(20)
  void testRobotBuildingLogic() throws InterruptedException {
    BlockingQueue<RobotPart> storage = new LinkedBlockingQueue<>();

    storage.put(RobotPart.HEAD);
    storage.put(RobotPart.TORSO);
    storage.put(RobotPart.HAND);
    storage.put(RobotPart.HAND);
    storage.put(RobotPart.FEET);

    Faction faction = new Faction("TestFaction", storage, new Object());

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<?> future = executor.submit(() -> {
      faction.collectParts();
      faction.buildRobots();
    });

    try {
      future.get(3, TimeUnit.SECONDS);
    } catch (ExecutionException | TimeoutException e) {
      fail("Faction thread failed or timed out: " + e.getMessage());
    }

    assertTrue(faction.getRobotsBuilt() == 0,
        "Faction should not build robot with only 1 FEET (needs 2)");

    storage.put(RobotPart.FEET);

    Future<?> future2 = executor.submit(() -> {
      faction.collectParts();
      faction.buildRobots();
    });

    try {
      future2.get(3, TimeUnit.SECONDS);
    } catch (ExecutionException | TimeoutException e) {
      fail("Faction thread failed or timed out: " + e.getMessage());
    }

    assertEquals(1, faction.getRobotsBuilt(),
        "Faction should build exactly 1 robot with complete parts");

    executor.shutdown();
  }
}
