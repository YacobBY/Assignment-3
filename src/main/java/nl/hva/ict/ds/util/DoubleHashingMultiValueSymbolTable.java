package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class DoubleHashingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    List<List<Player>> doubleHashingList;

    public DoubleHashingMultiValueSymbolTable(int arraySize) {
        for (int i = 0; i < arraySize; i++) {
            doubleHashingList.add(new ArrayList<Player>());
        }
    }

    @Override
    public void put(String key, Player value) {
        int counter = 0;
        int index = doubleCounterKeyHash(key, counter);
        System.out.println(key);

        while ((!doubleHashingList.get(index).isEmpty()) && (getFullName(doubleHashingList.get(index).get(0))) != key) {
            counter++;
            index = doubleCounterKeyHash(key, counter);
        }
        doubleHashingList.get(index).add(value);

    }


    @Override
    public List<Player> get(String key) {
        System.out.println(key);
        int counter = 0;
        int index = doubleCounterKeyHash(key, counter);
        while ((!doubleHashingList.get(index).isEmpty()) && !(getFullName(doubleHashingList.get(index).get(0))).equals(key)) {
            System.out.println(getFullName(doubleHashingList.get(index).get(0)));
            counter++;
            index = doubleCounterKeyHash(key, counter);
        }
        System.out.println(getFullName(doubleHashingList.get(index).get(0)));
        return doubleHashingList.get(index);
    }


    public int doubleCounterKeyHash(String key, int collisionCounter) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i);
        }
        //Nadat collision is opgetreden is wordt dit uitgevoerd
        if (collisionCounter != 0) {
            hash += collisionCounter*31;
        }
        hash = hash % doubleHashingList.size();
        return hash;
    }


    public String getFullName(Player player) {
        return (player.getFirstName() + player.getLastName());
    }
}