package com.javarush.task.task33.task3310.strategy;

import java.util.Objects;

public class FileStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    int size = 0;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;

    public FileStorageStrategy() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new FileBucket();
        }
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public int hash(Long k) {
        return k == null ? 0 : Objects.hash(k);
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) { //как выдать все записи
        if (table == null || table.length == 0) return null;

        // можно объединит со следующим циклом, но так лучше readability
        if (key == null) { // working with null key
            Entry entry = table[0].getEntry();

            while (entry != null && entry.getKey() != null) {
                entry = entry.next;
            }
            return entry;
        }

        int index = indexFor(hash(key), table.length);
        Entry entry = table[index].getEntry();

        if (entry == null) return null;
        while (true) {
                if (entry == null ||
                        entry.getKey() != null && entry.getKey().equals(key)) // correct with null keys
                    break;
            entry = entry.next;
        }
        return entry;
    }

    public void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        for (int i = 0; i < newTable.length; i++) {
            newTable[i] = new FileBucket();
        }
        transfer(newTable);
        table = newTable;
    }

    public void transfer(FileBucket[] newTable) {
        FileBucket[] src = table;
        int newCapacity = newTable.length;

        for (int i = 0; i < src.length; i++) {
            Entry e = src[i].getEntry();
            if (e != null) {
//                src[i] = null;  //why?
                do {
                    Entry next = e.next;
                    int ind = indexFor(e.hash, newCapacity);
                    e.next = newTable[ind].getEntry();
                    newTable[ind].putEntry(e);
                    e = next;
                } while (e != null);
            }
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex].getEntry();
        Entry initial = table[bucketIndex].getEntry();

        if (entry == null) {
            createEntry(hash, key, value, bucketIndex);
        }
//        else {
//            while (true) {
//                if (entry.next == null ||
//                        (key != null && entry.getKey() != null && key.equals(entry.getKey())) ||
//                        (key == null && entry.getKey() == null)) break;
//                entry = entry.next;
//            }
//
//            if (entry.next == null) {
//                entry.next = new Entry(hash, key, value, null);
//            } else {
//                entry.value = value;
//            }
//        }
//        System.out.println(entry + " " + initial);
//        table[bucketIndex].putEntry(initial);


        table[bucketIndex].putEntry(new Entry(hash, key, value, entry));

        size++;
        if (table[bucketIndex].getFileSize() > bucketSizeLimit)
            resize(table.length * 2); //no max int check


    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex].putEntry(new Entry(hash, key, value, null));
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
        for (FileBucket fileBucket : table) {
            Entry entry = fileBucket.getEntry();
            while (entry != null) {
                if ((entry.getValue() != null && value != null && value.equals(entry.getValue())) ||
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
        return entry != null ? entry.getValue() : null;
    }



}


