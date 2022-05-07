package implementations;

import interfaces.List;
import org.apache.velocity.util.ArrayIterator;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    private E[] array;
    private int size;
    private int capacity;
    private final int INITIAL_CAPACITY = 8;

    public ArrayList() {
        capacity = INITIAL_CAPACITY;
        size = 0;
    }

    @Override
    public boolean add(E element) {
        assertArrayNotNull(element);
        checkAndFixCapacity();
        array[size++] = element;
        return true;

    }

    private void checkAndFixCapacity() {
        E[] newArr;
        if (size == capacity) {
            capacity = capacity * 2;
            newArr = (E[]) Array.newInstance(array[0].getClass(), capacity);
        } else if (size > 8 && size == capacity / 4) {
            capacity = capacity / 2;
            newArr = (E[]) Array.newInstance(array[0].getClass(), capacity);
        } else {
            return;
        }
        for (int i = 0; i < size; i++) {
            newArr[i] = array[i];
        }
        array = newArr;
    }

    @Override
    public boolean add(int index, E element) {
        assertArrayNotNull(element);
        assertIndexIsValid(index);
        size++;
        checkAndFixCapacity();
        for (int i = size - 1; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        return true;
    }


    private void assertArrayNotNull(E element) {
        if (array == null) {
            array = (E[]) Array.newInstance(element.getClass(), INITIAL_CAPACITY);
        }
    }

    private void assertIndexIsValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
    }

    @Override
    public E get(int index) {
        assertIndexIsValid(index);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        assertIndexIsValid(index);
        E previous = array[index];
        array[index] = element;
        return previous;
    }

    @Override
    public E remove(int index) {
        assertIndexIsValid(index);
        E removed = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        checkAndFixCapacity();
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };


    }
}
