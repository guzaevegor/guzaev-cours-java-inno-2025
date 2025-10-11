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

  public void add(int index, T el){
    throw new UnsupportedOperationException();
  }
  public void getFirst(){
    throw new UnsupportedOperationException();
  }
  public void getLast(T el){
    throw new UnsupportedOperationException();
  }
  public void get(int index){
    throw new UnsupportedOperationException();
  }
  public void removeFirst(){
    throw new UnsupportedOperationException();
  }
  public void removeLast(){
    throw new UnsupportedOperationException();
  }
  public void remove(int index){
    throw new UnsupportedOperationException();
  }
}
