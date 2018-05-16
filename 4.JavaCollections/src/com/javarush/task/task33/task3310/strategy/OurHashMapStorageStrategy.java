package com.javarush.task.task33.task3310.strategy;

import java.lang.annotation.Target;
import java.util.Objects;

public class OurHashMapStorageStrategy implements StorageStrategy{
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size = 0;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;



    public int hash(Long k) {
        return k == null ? 0 : Objects.hash(k);
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        if (table == null || table.length == 0) return null;

        // можно объединит со следующим циклом, но так лучша readability
        if (key == null) { // working with null key
            Entry entry = table[0];
            while (entry != null && entry.getKey() != null) {
                entry = entry.next;
            }
            return entry;
        }

        int index = indexFor(hash(key), table.length);
        Entry entry = table[index];
        if (entry == null) return null;
        while (entry != null && entry.getKey() != null && !entry.getKey().equals(key)) {
            entry = entry.next;
        }
        return entry;
    }

    public void resize(int newCapacity) {
        int oldCapacity = table.length;
        Entry[] newTable = new Entry[oldCapacity * 2]; // max INT not checked
        transfer(newTable);
        table = newTable;
        threshold = (int)(table.length * loadFactor);
    }

    public void transfer(Entry[] newTable) {
        Entry[] src = table; // why?
        int newCapacity = newTable.length;

        for (int i = 0; i < src.length; i++) {
            Entry e = src[i];
            if (e != null) {
//                src[i] = null;  //why?
                do {
                    Entry next = e.next;
                    int ind = indexFor(e.hash, newCapacity);
                    e.next = newTable[ind];
                    newTable[ind] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        //        if (table == null || table.length <= 0) throw new Exception("table doesn't exist");

        // не проверяю совпадение ключей
        Entry entry = table[bucketIndex];
        if (entry == null) {
            createEntry(hash, key, value, bucketIndex); //?? no null keys
        } else {
            while (true) {
                if (entry.next == null ||
                (key != null && key.equals(entry.key)) ||
                (key == null && entry.getKey() == null)) break;
                entry = entry.next;
            }

            if (entry.next == null) {
                entry.next = new Entry(hash, key, value, null);
            } else {
                entry.value = value;
            }
        }
        if (size++ > threshold)
            resize(table.length * 2); //no max int check

    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex] = new Entry(hash, key, value, null);
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        return getKey(value) != null;
    }

    @Override
    public void put(Long key, String value) {
        addEntry(hash(key), key, value, indexFor(hash(key), table.length));
    }

    @Override
    public Long getKey(String value) {
//        if (value == null) return null; //??
        for (Entry entry : table) {
            while (entry != null) {
                if ((entry.getValue() != null && value.equals(entry.getValue())) ||
                        (entry.getValue() == null && value == null))
                    return entry.getKey();
                entry = entry.next;
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);
        return entry != null ? entry.getValue() : null; //?""
    }
}
