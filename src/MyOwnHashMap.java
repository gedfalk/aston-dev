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

    public V put(K key, V value) {
        // TODO: рехешировать при переполнении словаря

        int hash = hash(key);
        int index = getBucketIndex(hash);
        Bucket<K, V> cur = buckets[index];

        // Если ключ уже есть
        while (cur != null) {
            if (cur.hash == hash && Objects.equals(cur.key, key)) {
                V oldValue = cur.value;
                cur.value = value;
                return oldValue;
            }
            cur = cur.next;
        }

        Bucket<K, V> newBucket = new Bucket<>(key, value, hash);
        newBucket.next = buckets[index];
        buckets[index] = newBucket;
        size++;
        return null;
    }

    public V get(K key) {
        int hash = hash(key);
        int index = getBucketIndex(hash);
        Bucket<K, V> cur = buckets[index];

        while (cur != null) {
            if (cur.hash == hash && Objects.equals(cur.key, key)) {
                return cur.value;
            }
            cur = cur.next;
        }

        return null;
    }

    public V remove(K key) {
        int hash = hash(key);
        int index = getBucketIndex(hash);

        Bucket<K, V> cur = buckets[index];
        Bucket<K, V> prev = null;

        while (cur != null) {
            if (cur.hash == hash && Objects.equals(cur.key, key)) {
                V oldValue = cur.value;

                if (prev == null) {
                    buckets[index] = cur.next;
                } else {
                    prev.next = cur.next;
                }

                size--;
                return oldValue;
            }

            prev = cur;
            cur = cur.next;
        }

        return null;
    }

    public void print() {
        for (int i = 0; i < capacity; i++) {
            Bucket<K, V> cur = buckets[i];
            System.out.print(i + ": ");
            while (cur != null) {
                System.out.print("{" + cur.key + " : " + cur.value + "} -> ");
                cur = cur.next;
            }
            System.out.println("null");
        }
    }


    static void main() {
        MyOwnHashMap<String, Integer> map = new MyOwnHashMap<>();

        map.put("jan", 1);
        map.put("feb", 200);
        map.put("feb", 2);
        map.put("dec", 30);
        map.remove("dec");
        map.put("apr", 4);

        map.print();

        System.out.println(map.get("apr"));
    }
}
