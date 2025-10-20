package task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class SomeInterestingTestCases {
  private final LinkedList<Integer> list;

  {
    list = new LinkedList<>();
  }

  @Test
  void testSizeConsistencyAfterMultipleOperations() {
    int expectedSize = 0;
    int actualSize = list.size();
    assertEquals(expectedSize, actualSize);

    list.addFirst(10);
    expectedSize = 1;
    actualSize = list.size();
    assertEquals(expectedSize, actualSize);

    list.addLast(20);
    list.addFirst(5);
    expectedSize = 3;
    actualSize = list.size();
    assertEquals(expectedSize, actualSize);

    list.removeFirst();
    expectedSize = 2;
    actualSize = list.size();
    assertEquals(expectedSize, actualSize);

    list.remove(0);
    list.removeLast();
    expectedSize = 0;
    actualSize = list.size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void testSizeAfterAddingAtDifferentPositions() {
    list.add(0, 10);
    assertEquals(1, list.size());

    list.add(1, 20);
    assertEquals(2, list.size());

    list.add(1, 15);
    assertEquals(3, list.size());

    list.add(0, 5);
    assertEquals(4, list.size());
  }

  @Test
  void testSizeNeverNegative() {
    list.addFirst(10);
    list.removeFirst();

    int expected = 0;
    int actual = list.size();
    assertEquals(expected, actual);
    assertTrue(actual >= 0);
  }


  @Test
  void testIndexBoundaryAtZero() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);

    int expected = 10;
    int actual = list.get(0);
    assertEquals(expected, actual);

    list.add(0, 5);
    expected = 5;
    actual = list.get(0);
    assertEquals(expected, actual);

    int removedExpected = 5;
    int removedActual = list.remove(0);
    assertEquals(removedExpected, removedActual);
  }

  @Test
  void testIndexBoundaryAtSize() {
    list.addLast(10);
    list.addLast(20);

    list.add(2, 30);
    int expected = 30;
    int actual = list.getLast();
    assertEquals(expected, actual);

    list.add(3, 40);
    expected = 40;
    actual = list.get(3);
    assertEquals(expected, actual);
  }

  @Test
  void testIndexBoundaryAtSizeMinusOne() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);

    int expected = 30;
    int actual = list.get(2);
    assertEquals(expected, actual);

    int removedExpected = 30;
    int removedActual = list.remove(2);
    assertEquals(removedExpected, removedActual);
    assertEquals(2, list.size());
  }

  @Test
  void testInvalidNegativeIndex() {
    list.addLast(10);

    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 5));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
  }

  @Test
  void testInvalidIndexGreaterThanSize() {
    list.addLast(10);
    list.addLast(20);

    assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(10, 5));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
  }

  @Test
  void testIndexAtExactSize() {
    list.addLast(10);
    list.addLast(20);

    assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));

    list.add(2, 30);
    assertEquals(30, list.getLast());
  }


  @Test
  void testOperationsOnEmptyList() {
    assertThrows(IllegalStateException.class, () -> list.getFirst());
    assertThrows(IllegalStateException.class, () -> list.getLast());
    assertThrows(IllegalStateException.class, () -> list.removeFirst());
    assertThrows(IllegalStateException.class, () -> list.removeLast());
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
  }

  @Test
  void testGetFirstAfterRemovingAllElements() {
    list.addFirst(10);
    list.removeFirst();

    assertThrows(IllegalStateException.class, () -> list.getFirst());
  }

  @Test
  void testGetLastAfterRemovingAllElements() {
    list.addLast(10);
    list.addLast(20);
    list.removeLast();
    list.removeLast();

    assertThrows(IllegalStateException.class, () -> list.getLast());
  }

  @Test
  void testSingleElementListOperations() {
    list.addFirst(42);

    int expectedFirst = 42;
    int actualFirst = list.getFirst();
    assertEquals(expectedFirst, actualFirst);

    int expectedLast = 42;
    int actualLast = list.getLast();
    assertEquals(expectedLast, actualLast);

    int expected = 42;
    int actual = list.get(0);
    assertEquals(expected, actual);

    int removedExpected = 42;
    int removedActual = list.removeFirst();
    assertEquals(removedExpected, removedActual);

    assertThrows(IllegalStateException.class, () -> list.getFirst());
  }

  @Test
  void testHeadReferenceAfterAddFirst() {
    list.addFirst(10);
    int expectedFirst = 10;
    int actualFirst = list.getFirst();
    assertEquals(expectedFirst, actualFirst);

    list.addFirst(20);
    expectedFirst = 20;
    actualFirst = list.getFirst();
    assertEquals(expectedFirst, actualFirst);

    int expectedSecond = 10;
    int actualSecond = list.get(1);
    assertEquals(expectedSecond, actualSecond);
  }

  @Test
  void testHeadReferenceAfterRemoveFirst() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);

    list.removeFirst();
    int expectedFirst = 20;
    int actualFirst = list.getFirst();
    assertEquals(expectedFirst, actualFirst);

    list.removeFirst();
    expectedFirst = 30;
    actualFirst = list.getFirst();
    assertEquals(expectedFirst, actualFirst);
  }

  @Test
  void testAllElementsAccessibleAfterOperations() {
    list.addLast(1);
    list.addLast(2);
    list.addLast(3);
    list.addFirst(0);
    list.add(2, 99);

    assertEquals(0, list.get(0));
    assertEquals(1, list.get(1));
    assertEquals(99, list.get(2));
    assertEquals(2, list.get(3));
    assertEquals(3, list.get(4));
    assertEquals(5, list.size());
  }

  @Test
  void testNoOrphanedNodesAfterRemove() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    list.addLast(40);

    list.remove(1);

    assertEquals(10, list.get(0));
    assertEquals(30, list.get(1));
    assertEquals(40, list.get(2));
    assertEquals(3, list.size());
  }

  @Test
  void testChainIntegrityAfterMultipleInsertions() {
    list.addLast(10);
    list.addLast(30);
    list.add(1, 20);
    list.add(3, 40);
    list.add(0, 5);

    assertEquals(5, list.get(0));
    assertEquals(10, list.get(1));
    assertEquals(20, list.get(2));
    assertEquals(30, list.get(3));
    assertEquals(40, list.get(4));
  }

  @Test
  void testSizeComputationDoesNotHang() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);

    assertTimeout(java.time.Duration.ofSeconds(1), () -> {
      int size = list.size();
      assertEquals(3, size);
    });
  }

  @Test
  void testGetLastDoesNotHang() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);

    assertTimeout(java.time.Duration.ofSeconds(1), () -> {
      int expected = 30;
      int actual = list.getLast();
      assertEquals(expected, actual);
    });
  }

  @Test
  void testRemoveLastDoesNotHang() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);

    assertTimeout(java.time.Duration.ofSeconds(1), () -> {
      int removed = list.removeLast();
      assertEquals(30, removed);
    });
  }

  @Test
  void testIteratingThroughListDoesNotHang() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);

    assertTimeout(java.time.Duration.ofSeconds(1), () -> {
      for (int i = 0; i < list.size(); i++) {
        list.get(i);
      }
    });
  }

  @Test
  void testMultipleOperationsDoNotCreateCycle() {
    list.addFirst(10);
    list.addLast(20);
    list.addFirst(5);
    list.add(2, 15);
    list.removeFirst();
    list.removeLast();

    assertTimeout(java.time.Duration.ofSeconds(1), () -> {
      int size = list.size();
      assertEquals(2, size);

      for (int i = 0; i < size; i++) {
        list.get(i);
      }
    });
  }
}
