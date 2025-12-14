import java.util.Objects;

public class MyOwnHashMap<K, V> {
    private static final int CAPACITY = 8;
    private static final double EXPAND_NUM = 0.8;
    private Bucket<K, V>[] buckets;
    public int capacity = CAPACITY;
    private int size;

    private static class Bucket<K, V> {
        final K key;
        V value;
        Bucket<K, V> next;
        final int hash;

        Bucket(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }
    }

    public MyOwnHashMap() {
        buckets = new Bucket[CAPACITY];
    }

    public MyOwnHashMap(int size) {
        // Округляем вверх до следующей степени двойки
        while (size >= capacity) {
            capacity *= 2;
        }
        buckets = new Bucket[capacity];
    }

    private void checkKeyNotNull(K key) {
        if (key == null) {
            throw new NullPointerException("Не можем использовать null в качестве ключа... сорри(");
        }
    }

    private int hash(K key) {
        checkKeyNotNull(key);
        return key.hashCode();
    }

    private int getBucketIndex(int hash) {
        return hash & (capacity - 1);
    }

    static void main() {
        System.out.println("hell");
    }
}
