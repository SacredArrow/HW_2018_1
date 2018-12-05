import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyConcurrentQueue<T> {
    private class Node<T> {
        Node<T> next;
        T value;

        public Node(T value) {
            this.value = value;
        }
    }
    Node<T> head;
    Node<T> tail;
    Lock lock = new ReentrantLock();

    public void offer(T elem) {
        lock.lock();
        Node<T> node = new Node<>(elem);
        if (head == null) {
            head = node;
            tail=node;
        } else {
            tail.next = node;
            tail = node;
        }
        lock.unlock();
    }

    public T poll() {
        try {
            lock.lock();
            if (head != null) {
                Node<T> tmp = head;
                head = head.next;
                if (tmp == tail) {
                    tail = null;
                }
                return tmp.value;
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean contains(T obj) {
        Node<T> tmp = head;
        while (tmp!=null) {
            if (obj.equals(tmp.value)) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;

    }
    public boolean isEmpty() {
        return head == null;
    }
}
