package task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
  private final LinkedList<Integer> list;

  {
    list = new LinkedList<>();
  }

  @Test
  void testSizeOperation() {
    int expected = 0;
    int actual = list.size();
    assertEquals(expected, actual);

    list.addFirst(10);
    expected = 1;
    actual = list.size();
    assertEquals(expected, actual);

    list.addLast(20);
    list.addLast(30);
    expected = 3;
    actual = list.size();
    assertEquals(expected, actual);

    list.removeFirst();
    expected = 2;
    actual = list.size();
    assertEquals(expected, actual);

    list.removeLast();
    list.removeLast();
    expected = 0;
    actual = list.size();
    assertEquals(expected, actual);
  }

  @Test
  void testAddFirstOperation() {
    list.addFirst(30);
    int expected = 30;
    int actual = list.getFirst();
    assertEquals(expected, actual);
    assertEquals(30, list.getLast());

    list.addFirst(20);
    expected = 20;
    actual = list.getFirst();
    assertEquals(expected, actual);
    assertEquals(30, list.getLast());

    list.addFirst(10);
    expected = 10;
    actual = list.getFirst();
    assertEquals(expected, actual);

    int expectedSize = 3;
    int actualSize = list.size();
    assertEquals(expectedSize, actualSize);

    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(30, list.get(2));
  }

  @Test
  void testAddLastOperation() {
    list.addLast(10);
    int expected = 10;
    int actual = list.getLast();
    assertEquals(expected, actual);
    assertEquals(10, list.getFirst());

    list.addLast(20);
    expected = 20;
    actual = list.getLast();
    assertEquals(expected, actual);
    assertEquals(10, list.getFirst());

    list.addLast(30);
    expected = 30;
    actual = list.getLast();
    assertEquals(expected, actual);

    int expectedSize = 3;
    int actualSize = list.size();
    assertEquals(expectedSize, actualSize);

    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(30, list.get(2));
  }

  @Test
  void testAddByIndexOperation() {
    list.addLast(10);
    list.addLast(40);

    list.add(1, 20);
    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(40, list.get(2));

    list.add(2, 30);
    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(30, list.get(2));
    assertEquals(40, list.get(3));

    list.add(0, 5);
    assertEquals(5, list.getFirst());

    list.add(5, 50);
    assertEquals(50, list.getLast());

    int expectedSize = 6;
    int actualSize = list.size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void testGetFirstOperation() {
    list.addLast(10);
    int expected = 10;
    int actual = list.getFirst();
    assertEquals(expected, actual);

    list.addFirst(5);
    expected = 5;
    actual = list.getFirst();
    assertEquals(expected, actual);

    list.add(0, 1);
    expected = 1;
    actual = list.getFirst();
    assertEquals(expected, actual);

    list.removeFirst();
    expected = 5;
    actual = list.getFirst();
    assertEquals(expected, actual);
  }

  @Test
  void testGetLastOperation() {
    list.addFirst(10);
    int expected = 10;
    int actual = list.getLast();
    assertEquals(expected, actual);

    list.addLast(20);
    expected = 20;
    actual = list.getLast();
    assertEquals(expected, actual);

    list.add(2, 30);
    expected = 30;
    actual = list.getLast();
    assertEquals(expected, actual);

    list.removeLast();
    expected = 20;
    actual = list.getLast();
    assertEquals(expected, actual);
  }

  @Test
  void testGetByIndexOperation() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    list.addLast(40);
    list.addLast(50);

    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(30, list.get(2));
    assertEquals(40, list.get(3));
    assertEquals(50, list.get(4));

    list.add(2, 25);
    assertEquals(25, list.get(2));
    assertEquals(30, list.get(3));

    list.remove(2);
    assertEquals(30, list.get(2));
  }

  @Test
  void testRemoveFirstOperation() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);

    int expectedRemoved = 10;
    int actualRemoved = list.removeFirst();
    assertEquals(expectedRemoved, actualRemoved);

    int expectedFirst = 20;
    int actualFirst = list.getFirst();
    assertEquals(expectedFirst, actualFirst);

    expectedRemoved = 20;
    actualRemoved = list.removeFirst();
    assertEquals(expectedRemoved, actualRemoved);

    int expectedSize = 1;
    int actualSize = list.size();
    assertEquals(expectedSize, actualSize);

    expectedRemoved = 30;
    actualRemoved = list.removeFirst();
    assertEquals(expectedRemoved, actualRemoved);

    expectedSize = 0;
    actualSize = list.size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void testRemoveLastOperation() {
    list.addFirst(30);
    list.addFirst(20);
    list.addFirst(10);

    int expectedRemoved = 30;
    int actualRemoved = list.removeLast();
    assertEquals(expectedRemoved, actualRemoved);

    int expectedLast = 20;
    int actualLast = list.getLast();
    assertEquals(expectedLast, actualLast);

    expectedRemoved = 20;
    actualRemoved = list.removeLast();
    assertEquals(expectedRemoved, actualRemoved);

    int expectedSize = 1;
    int actualSize = list.size();
    assertEquals(expectedSize, actualSize);

    expectedRemoved = 10;
    actualRemoved = list.removeLast();
    assertEquals(expectedRemoved, actualRemoved);

    expectedSize = 0;
    actualSize = list.size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void testRemoveByIndexOperation() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    list.addLast(40);
    list.addLast(50);

    int expectedRemoved = 30;
    int actualRemoved = list.remove(2);
    assertEquals(expectedRemoved, actualRemoved);
    assertEquals(40, list.get(2));

    expectedRemoved = 10;
    actualRemoved = list.remove(0);
    assertEquals(expectedRemoved, actualRemoved);
    assertEquals(20, list.getFirst());

    expectedRemoved = 50;
    actualRemoved = list.remove(2);
    assertEquals(expectedRemoved, actualRemoved);
    assertEquals(40, list.getLast());

    int expectedSize = 2;
    int actualSize = list.size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void testMixedOperations() {
    list.addFirst(30);
    list.addLast(40);
    list.addFirst(20);
    list.add(0, 10);

    assertEquals(4, list.size());
    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(30, list.get(2));
    assertEquals(40, list.get(3));

    list.removeFirst();
    assertEquals(20, list.getFirst());

    list.removeLast();
    assertEquals(30, list.getLast());

    list.add(1, 25);
    assertEquals(25, list.get(1));
    assertEquals(3, list.size());
  }

  @Test
  void testExceptionHandling() {
    assertThrows(IllegalStateException.class, () -> list.getFirst());
    assertThrows(IllegalStateException.class, () -> list.getLast());
    assertThrows(IllegalStateException.class, () -> list.removeFirst());
    assertThrows(IllegalStateException.class, () -> list.removeLast());

    list.addFirst(10);

    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(10));
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 99));
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(10, 99));
  }

  @Test
  void testComplexScenario() {
    list.addLast(5);
    list.addLast(10);
    list.addLast(15);
    list.addLast(20);
    list.addLast(25);

    assertEquals(5, list.size());
    assertEquals(5, list.getFirst());
    assertEquals(25, list.getLast());

    list.remove(2);
    assertEquals(4, list.size());
    assertEquals(20, list.get(2));

    list.addFirst(0);
    list.add(3, 12);

    assertEquals(0, list.get(0));
    assertEquals(5, list.get(1));
    assertEquals(10, list.get(2));
    assertEquals(12, list.get(3));
    assertEquals(20, list.get(4));
    assertEquals(25, list.get(5));

    int removed = list.removeFirst();
    assertEquals(0, removed);

    removed = list.removeLast();
    assertEquals(25, removed);

    assertEquals(4, list.size());
  }
}
