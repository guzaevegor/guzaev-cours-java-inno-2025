package task1;

public class LinkedList<T> {
  //thinking about at check object of T if (T == null) throw new UnsupportedOperationException();

  private Node<T> head;
  public LinkedList() {
    head = null;
  }
  public int size() {
    int currentSize = 0;
    Node<T> current = head;
    while (current != null) {
      currentSize++;
      current = current.next;
    }
    return currentSize;
  }

  public void addFirst(T element) {
    Node<T> newNode = new Node<>(element);
    newNode.next = head;
    head = newNode;
  }

  public void addLast(T element) {
    Node<T> newNode = new Node<>(element);
    if (head == null) {
      head = newNode;
      return;
    }
    Node<T> current = head;
    while (current.next != null) {
      current = current.next;
    }
    current.next = newNode;
  }

  public void add(int index, T element) {
    if (index < 0 || index > size()) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
    }
    if (index == 0) {
      addFirst(element);
      return;
    }
    Node<T> newNode = new Node<>(element);
    Node<T> current = head;
    for (int i = 0; i < index - 1; i++) {
      current = current.next;
    }
    newNode.next = current.next;
    current.next = newNode;
  }

  public T getFirst() {
    if (head == null) {
      throw new IllegalStateException("List is empty");
    }
    return head.data;
  }

  public T getLast() {
    if (head == null) {
      throw new IllegalStateException("List is empty");
    }
    Node<T> current = head;
    while (current.next != null) {
      current = current.next;
    }
    return current.data;
  }

  public T get(int index) {
    if (index < 0 || index >= size()) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
    }
    Node<T> current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current.data;
  }

  public T removeFirst() {
    if (head == null) {
      throw new IllegalStateException("List is empty");
    }
    T data = head.data;
    head = head.next;
    return data;
  }

  public T removeLast() {
    if (head == null) {
      throw new IllegalStateException("List is empty");
    }
    if (head.next == null) {
      T data = head.data;
      head = null;
      return data;
    }
    Node<T> current = head;
    while (current.next.next != null) {
      current = current.next;
    }
    T data = current.next.data;
    current.next = null;
    return data;
  }

  public T remove(int index) {
    if (index < 0 || index >= size()) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
    }
    if (index == 0) {
      return removeFirst();
    }
    Node<T> current = head;
    for (int i = 0; i < index - 1; i++) {
      current = current.next;
    }
    T data = current.next.data;
    current.next = current.next.next;
    return data;
  }

}
