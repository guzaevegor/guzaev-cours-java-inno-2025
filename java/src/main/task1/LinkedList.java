package task1;


public class LinkedList<T> {

  private Node<T> head;
  private int size;

  public LinkedList() {
    head = null;
    size = 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(T element) {
    if (element == null) throw new UnsupportedOperationException("Null elements are not supported");
    Node<T> newNode = new Node<>(element);
    newNode.next = head;
    head = newNode;
    size++;
  }

  public void addLast(T element) {
    if (element == null) throw new UnsupportedOperationException("Null elements are not supported");
    Node<T> newNode = new Node<>(element);
    if (head == null) {
      head = newNode;
    } else {
      Node<T> current = head;
      while (current.next != null) {
        current = current.next;
      }
      current.next = newNode;
    }
    size++;
  }

  public void add(int index, T element) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    if (element == null) throw new UnsupportedOperationException("Null elements are not supported");
    if (index == 0) {
      addFirst(element);
      return;
    }
    Node<T> current = head;
    for (int i = 0; i < index - 1; i++) {
      current = current.next;
    }
    Node<T> newNode = new Node<>(element);
    newNode.next = current.next;
    current.next = newNode;
    size++;
  }

  public T getFirst() {
    if (head == null) throw new IllegalStateException("List is empty");
    return head.data;
  }

  public T getLast() {
    if (head == null) throw new IllegalStateException("List is empty");
    Node<T> current = head;
    while (current.next != null) {
      current = current.next;
    }
    return current.data;
  }

  public T get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    Node<T> current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current.data;
  }

  public T removeFirst() {
    if (head == null) throw new IllegalStateException("List is empty");
    T data = head.data;
    head = head.next;
    size--;
    return data;
  }

  public T removeLast() {
    if (head == null) throw new IllegalStateException("List is empty");
    if (head.next == null) {
      T data = head.data;
      head = null;
      size--;
      return data;
    }
    Node<T> current = head;
    while (current.next.next != null) {
      current = current.next;
    }
    T data = current.next.data;
    current.next = null;
    size--;
    return data;
  }

  public T remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    if (index == 0) return removeFirst();

    Node<T> current = head;
    for (int i = 0; i < index - 1; i++) {
      current = current.next;
    }
    T data = current.next.data;
    current.next = current.next.next;
    size--;
    return data;
  }
}
