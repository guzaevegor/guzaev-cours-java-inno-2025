package task1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class LinkedListTest {
  private LinkedList<Integer> list;
  {
    list = new LinkedList<>();
  }
  @Test
  void testSize() {
    assertEquals(0, list.size());
  }
}