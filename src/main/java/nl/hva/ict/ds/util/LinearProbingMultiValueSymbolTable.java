package nl.hva.ict.ds.util;

import nl.hva.ict.ds.Player;

import java.util.ArrayList;
import java.util.List;

public class LinearProbingMultiValueSymbolTable implements MultiValueSymbolTable<String, Player> {
    Player[] linearProbeList;

    public int linearCollisionCount =0;

    public int getLinearCollisionCount() {
        return this.linearCollisionCount;
    }

    public LinearProbingMultiValueSymbolTable(int arraySize) {
        linearProbeList = new Player[arraySize];
    }

    @Override
    //First name is key, gehele player object is value.
    public void put(String key, Player value) {
        int index = keyHash(key);
        while (!(linearProbeList[index] == null)) {
            index++;
            linearCollisionCount++;
            if (index >= linearProbeList.length) {
                index = 0;
            }
        }

        linearProbeList[index] = (value);
        System.out.println("linear probe collision count: " + getLinearCollisionCount());
    }

    @Override
    public List<Player> get(String key) {
        List<Player> returnList = new ArrayList<>();
        int index = keyHash(key);
        while (!linearProbeList[index].getFirstName().equals(key)) {
            index++;
            linearCollisionCount++;
            if (index >= linearProbeList.length) {
                index = 0;
            }
        }
        while (linearProbeList[index].getFirstName().equals(key)) {
            returnList.add(linearProbeList[index]);
            index++;
            linearCollisionCount++;
            if (index >= linearProbeList.length) {
                index = 0;
            }
        }

        return returnList;

    }

    public int keyHash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = hash + key.charAt(i)*461;
        }
        hash = hash % linearProbeList.length;
        return hash;
    }
}
