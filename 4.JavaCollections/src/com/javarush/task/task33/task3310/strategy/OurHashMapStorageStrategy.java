package com.javarush.task.task33.task3310.strategy;

public class OurHashMapStorageStrategy implements StorageStrategy{
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;

    public int hash(Long k) {
        //some added comment to commit

    }

    public int indexFor(int hash, int length)
    public Entry getEntry(Long key)
    public void resize(int newCapacity)
    public void transfer(Entry[] newTable)
    public void addEntry(int hash, Long key, String value, int bucketIndex)
    public void createEntry(int hash, Long key, String value, int bucketIndex)
}
