package task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
  private LinkedList<Integer> list;

  {
    list = new LinkedList<>();
  }

  @Test
  void testSizeOfEmptyList() {
    assertEquals(0, list.size());
  }

  @Test
  void testSizeAfterAddingOneElement() {
    list.addFirst(10);
    assertEquals(1, list.size());
  }

  @Test
  void testSizeAfterAddingMultipleElements() {
    list.addFirst(1);
    list.addFirst(2);
    list.addFirst(3);
    assertEquals(3, list.size());
  }

  @Test
  void testSizeAfterAddingWithAddLast() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    assertEquals(3, list.size());
  }

  @Test
  void testSizeAfterRemovingFirst() {
    list.addFirst(10);
    list.addFirst(20);
    list.addFirst(30);
    list.removeFirst();
    assertEquals(2, list.size());
  }

  @Test
  void testSizeAfterRemovingLast() {
    list.addLast(1);
    list.addLast(2);
    list.addLast(3);
    list.removeLast();
    assertEquals(2, list.size());
  }

  @Test
  void testSizeAfterRemovingByIndex() {
    list.addLast(10);
    list.addLast(20);
    list.addLast(30);
    list.remove(1);
    assertEquals(2, list.size());
  }

  @Test
  void testSizeAfterRemovingAllElements() {
    list.addLast(1);
    list.addLast(2);
    list.removeFirst();
    list.removeFirst();
    assertEquals(0, list.size());
  }

  @Test
  void testSizeAfterMixedOperations() {
    list.addFirst(5);
    list.addLast(10);
    list.add(1, 7);
    list.removeFirst();
    assertEquals(2, list.size());
  }
}
