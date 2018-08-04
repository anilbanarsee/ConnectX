package client;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockMap{

    HashMap<Integer, HashMap<Integer, List<BlockValue>>> map;
    HashMap<BlockValue, BlockValue> links;
    int[] valueSet;

    public BlockMap(int size, int max_score){
        valueSet = new int[max_score];
        map = new HashMap<>();
        for(int i=0; i<size; i++){
            map.put(i, new HashMap<>());
        }
    }

    public void addBlock(BlockValue block){


        int x = block.square.x;
        int y = block.square.y;
        ArrayList<BlockValue> delBlocks = new ArrayList<>();
        insertBlock(block);
        checkLinked(block);
    }
    private void insertBlock(BlockValue block){
        if(block == null)
            return;

        if(map.get(block.square.x).get(block.square.y) == null){
            map.get(block.square.x).put(block.square.y, new ArrayList<BlockValue>());
        }
        map.get(block.square.x).get(block.square.y).add(block);

        insertBlock(block.next);

    }
    public int removeBlock(BlockValue block){
        if(block == null){
            return 0;
        }
        map.get(block.square.x).get(block.square.y).remove(block);
        return removeBlock(block.next) + removeBlock(block.prev) + 1;
    }
    void checkLinked(BlockValue block){
        if(block == null)
            return;

        for(BlockValue b: map.get(block.square.x).get(block.square.y)) {
            if (links.get(b.getMaster()) == null) {
                links.put(block.getMaster(), b.getMaster());
            } else if (links.get(b.getMaster()).value > block.value) {
                links.put(block.getMaster(), b.getMaster());
            } else {
                links.put(b, b.getMaster());
            }
        }
        checkLinked(block.next);
    }
    boolean checkSubset(BlockValue a, BlockValue b){
        if(b==null)
            return false;
        if(a==null)
            return true;

        return checkSubset(a.next, b.next);
    }
}