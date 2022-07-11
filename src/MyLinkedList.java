import java.util.*;
import java.util.function.Consumer;

public class MyLinkedList<E>//Дополняет AbstractSequentialList и применяет методы List, Deque, Cloneable, java.io.Serializable
        extends AbstractSequentialList<E>
        implements List<E>, Deque<E>, Cloneable, java.io.Serializable {
    transient int size = 0; //Устанавливаем размер,  не для передачи

    transient Node<E> first;//Создаем одну точку


    transient Node<E> last;//Создаем вторую точку


    public MyLinkedList() {
    }


    public MyLinkedList(Collection<? extends E> c) { //Добавляем интерфейс Collection
        this();
        addAll(c);
    }


    private void linkFirst(E e) { //е - первая точка
        final Node<E> f = first; //Сохраняем первую точку
        final Node<E> newNode = new Node<>(null, e, f); //Новая первая точка
        first = newNode; // Присваеваем новую первую точку
        if (f == null) //Если первой точки не было
            last = newNode; // last -тоже что и first
        else
            f.prev = newNode;//
        size++;//Увеличение размера на 1
        modCount++; //Увеличение количества изменений
    }


    void linkLast(E e) {//Последняя точка
        final Node<E> l = last;// бывшая последняя точка, сохраним ее
        final Node<E> newNode = new Node<>(l, e, null); //Новая последняяя точка
        last = newNode;//Присваеваем новую последнюю точку
        if (l == null) //Если последней точки не было
            first = newNode; //Первая = последняя
        else
            l.next = newNode;//
        size++;//Увеличение размера на 1
        modCount++;//Увеличение количества изменений
    }


    void linkBefore(E e, Node<E> succ) { //Вставляем элемент перед
        // assert succ != null;
        final Node<E> pred = succ.prev; // Сохраняем
        final Node<E> newNode = new Node<>(pred, e, succ); //Создание нового
        succ.prev = newNode;//Присвоение старому
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++; //Размер
        modCount++;//Изменения
    }


    private E unlinkFirst(Node<E> f) { //Удаление первого элемента и его возвращение
        // assert f == first && f != null;
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null;
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
        modCount++;
        return element;
    }


    private E unlinkLast(Node<E> l) { //Удаление последнего элемента и его возвращение
        // assert l == last && l != null;
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null;
        last = prev;
        if (prev == null)
            first = null;
        else
            prev.next = null;
        size--;
        modCount++;
        return element;
    }


    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }


    public E getFirst() {//Получение первого элемента
        final Node<E> f = first;
        if (f == null) //Если его нет то исключение
            throw new NoSuchElementException();
        return f.item; //Если есть то возвращаем
    }


    public E getLast() {//Получение последнего элемента
        final Node<E> l = last;
        if (l == null) //Если его нет то исключение
            throw new NoSuchElementException();
        return l.item; //Если есть то возвращаем
    }


    public E removeFirst() { // удаление певого элемента
        final Node<E> f = first;
        if (f == null) // если его нет - исключение
            throw new NoSuchElementException();
        return unlinkFirst(f); // если есть - удалим
    }


    public E removeLast() { // удаление последнего эдемента
        final Node<E> l = last;
        if (l == null) // если его нет - исключение
            throw new NoSuchElementException();
        return unlinkLast(l); // если есть - удалим
    }

    public void addFirst(E e) {
        linkFirst(e);
    }//Добавление в начало

    public void addLast(E e) {
        linkLast(e);
    } //Добавление в конец


    public boolean contains(Object o) {
        return indexOf(o) >= 0; // если найден объект вернем true
    }


    public int size() {
        return size;
    } // вернем размер


    public boolean add(E array) {// добавляем в конец
        linkLast(array);
        return true;
    }


    public boolean remove(Object o) { // поиск первого вхождения объекта и его удаление
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) { // отдельно для null
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {// отдельно для не null
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    } // добавление других объектов в конец


    public boolean addAll(int index, Collection<? extends E> c) {// добавление других объектов в определенную позицию
        checkPositionIndex(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;

        Node<E> pred, succ;
        if (index == size) {
            succ = null;
            pred = last;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for (Object o : a) {
            @SuppressWarnings("unchecked") E e = (E) o;
            Node<E> newNode = new Node<>(pred, e, null);
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        modCount++;
        return true;
    }


    public void clear() { // обнуление листа

        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
        modCount++;
    }





    public E get(int index) { // возвращаем элемент в указанной позиции
        checkElementIndex(index);
        return node(index).item;
    }


    public E set(int index, E element) { // заменим элемент в указанной позиции и вернем старый
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }


    public void add(int index, E element) {// вставим элемент в нужныю позицию
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }


    public E remove(int index) { // удаление элемента в указанной позиции
        checkElementIndex(index);
        return unlink(node(index));
    }


    private boolean isElementIndex(int index) { // проверим что индекс лежит в границах листа
        return index >= 0 && index < size;
    }


    private boolean isPositionIndex(int index) {// проверим что индекс лежит в границах листа
        return index >= 0 && index <= size;
    }


    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    } // конструктор описания исключения

    private void checkElementIndex(int index) { // проверим что индекс лежит в границах листа или выбросим исключение
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void checkPositionIndex(int index) { // проверим что индекс лежит в границах листа или выбросим исключение
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }


    Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }




    public int indexOf(Object o) { // вернем первый индес искомого элемента или -1 если его нет
        int index = 0;
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) // отдельная проверка для null
                    return index;
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) { // отдельная проверка для объектов
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }


    public int lastIndexOf(Object o) { // вернем последний индес искомого элемента или -1 если его нет
        int index = size;
        if (o == null) {
            for (Node<E> x = last; x != null; x = x.prev) {
                index--;
                if (x.item == null)  // отдельная проверка для null
                    return index;
            }
        } else {
            for (Node<E> x = last; x != null; x = x.prev) {
                index--;
                if (o.equals(x.item)) // отдельная проверка для объектов
                    return index;
            }
        }
        return -1;
    }



    public E peek() { // вернем первый элемент или null
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }


    public E element() {
        return getFirst();
    } // вернем первый элемент или исключение

    public E poll() { // удалим и вернем первый элемент, или вернем null
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }


    public E remove() {
        return removeFirst();
    } // удалим и вернем первый элемент, или исключение


    public boolean offer(E e) {
        return add(e);
    } // добавим как последний элемент

    // Deque operations


    public boolean offerFirst(E e) { // добавим и установим первым
        addFirst(e);
        return true;
    }


    public boolean offerLast(E e) { // добавим и установим последним
        addLast(e);
        return true;
    }


    public E peekFirst() { // получим первый элемент
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }


    public E peekLast() {// получим последний элемент
        final Node<E> l = last;
        return (l == null) ? null : l.item;
    }


    public E pollFirst() { // получим и удалим первый элемент
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }


    public E pollLast() { // получим и удалим последний элемент
        final Node<E> l = last;
        return (l == null) ? null : unlinkLast(l);
    }


    public void push(E e) {
        addFirst(e);
    }// добавим в качестве первого элемента


    public E pop() {
        return removeFirst();
    }// удалим и вернем первый элемент


    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }// удалим элемент с первым вхождением


    public boolean removeLastOccurrence(Object o) { // удалим элемент с последним вхождением
        if (o == null) {
            for (Node<E> x = last; x != null; x = x.prev) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = last; x != null; x = x.prev) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }


    public ListIterator<E> listIterator(int index) {
        checkPositionIndex(index);
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        ListItr(int index) {
            // assert isPositionIndex(index);
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public E next() {
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        public E previous() {
            checkForComodification();
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = next = (next == null) ? last : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        public void remove() {
            checkForComodification();
            if (lastReturned == null)
                throw new IllegalStateException();

            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
            expectedModCount++;
        }

        public void set(E e) {
            if (lastReturned == null)
                throw new IllegalStateException();
            checkForComodification();
            lastReturned.item = e;
        }

        public void add(E e) {
            checkForComodification();
            lastReturned = null;
            if (next == null)
                linkLast(e);
            else
                linkBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            while (modCount == expectedModCount && nextIndex < size) {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private static class Node<E> {// класс узел, содержит значение, предыдущий, последующий
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public Iterator<E> descendingIterator() {
        return new DescendingIterator();

    private class DescendingIterator implements Iterator<E> {
        private final ListItr itr = new ListItr(size());

        public boolean hasNext() {
            return itr.hasPrevious();
        }

        public E next() {
            return itr.previous();
        }

        public void remove() {
            itr.remove();
        }
    }

    @SuppressWarnings("unchecked")
    private MyLinkedList<E> superClone() {
        try {
            return (MyLinkedList<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }


    public Object clone() {
        MyLinkedList<E> clone = superClone();

        // Put clone into "virgin" state
        clone.first = clone.last = null;
        clone.size = 0;
        clone.modCount = 0;

        // Initialize clone with our elements
        for (Node<E> x = first; x != null; x = x.next)
            clone.add(x.item);

        return clone;
    }


    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;
        return result;
    }


    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        int i = 0;
        Object[] result = a;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @java.io.Serial
    private static final long serialVersionUID = 876323262645176354L;


    @java.io.Serial
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out size
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (Node<E> x = first; x != null; x = x.next)
            s.writeObject(x.item);
    }


    @SuppressWarnings("unchecked")
    @java.io.Serial
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read in size
        int size = s.readInt();

        // Read in all elements in the proper order.
        for (int i = 0; i < size; i++)
            linkLast((E) s.readObject());
    }


    @Override
    public Spliterator<E> spliterator() {
        return new LLSpliterator<>(this, -1, 0);
    }


    static final class LLSpliterator<E> implements Spliterator<E> {
        static final int BATCH_UNIT = 1 << 10;  // batch array size increment
        static final int MAX_BATCH = 1 << 25;  // max batch array size;
        final MyLinkedList<E> list; // null OK unless traversed
        Node<E> current;      // current node; null until initialized
        int est;              // size estimate; -1 until first needed
        int expectedModCount; // initialized when est set
        int batch;            // batch size for splits

        LLSpliterator(MyLinkedList<E> list, int est, int expectedModCount) {
            this.list = list;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }

        final int getEst() {
            int s; // force initialization
            final MyLinkedList<E> lst;
            if ((s = est) < 0) {
                if ((lst = list) == null)
                    s = est = 0;
                else {
                    expectedModCount = lst.modCount;
                    current = lst.first;
                    s = est = lst.size;
                }
            }
            return s;
        }

        public long estimateSize() {
            return (long) getEst();
        }

        public Spliterator<E> trySplit() {
            Node<E> p;
            int s = getEst();
            if (s > 1 && (p = current) != null) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                Object[] a = new Object[n];
                int j = 0;
                do {
                    a[j++] = p.item;
                } while ((p = p.next) != null && j < n);
                current = p;
                batch = j;
                est = s - j;
                return Spliterators.spliterator(a, 0, j, Spliterator.ORDERED);
            }
            return null;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Node<E> p;
            int n;
            if (action == null) throw new NullPointerException();
            if ((n = getEst()) > 0 && (p = current) != null) {
                current = null;
                est = 0;
                do {
                    E e = p.item;
                    p = p.next;
                    action.accept(e);
                } while (p != null && --n > 0);
            }
            if (list.modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }

        public boolean tryAdvance(Consumer<? super E> action) {
            Node<E> p;
            if (action == null) throw new NullPointerException();
            if (getEst() > 0 && (p = current) != null) {
                --est;
                E e = p.item;
                current = p.next;
                action.accept(e);
                if (list.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }