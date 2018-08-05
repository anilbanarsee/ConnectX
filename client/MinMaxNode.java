package client;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MinMaxNode {
    ArrayList<MinMaxNode> subNodes;
    MinMaxNode bestChild;
    MinMaxNode parent;
    boolean max;
    boolean pruned;
    boolean hasValue = false;
    int value;
    int alpha;

    public MinMaxNode(MinMaxNode parent) {
        subNodes = new ArrayList<>();
        this.parent = parent;
        if (parent != null) {
            max = !parent.max;
        } else {
            max = true;
        }
        if(max){
            value = Integer.MIN_VALUE;
        } else {
            value = Integer.MAX_VALUE;
        }
        bestChild = null;
        pruned = false;
    }
    public MinMaxNode(MinMaxNode parent, int value){
        this(parent);
        this.value = value;
        hasValue = true;
    }
    public MinMaxNode(boolean max){
        subNodes = new ArrayList<>();
        this.parent = null;
        this.max = max;
        bestChild = null;
        pruned = false;
    }
    public void calculateValue(){
        for(MinMaxNode node : subNodes){
            if(!node.hasValue){
                node.calculateValue();
            }
            if(max){
                if(node.value > value){
                    bestChild = node;
                    value = node.value;
                }
            } else {
                if(node.value < value){
                    bestChild = node;
                    value = node.value;
                }
            }
        }
    }
    public String toString(){
        //calculate width
        //build up from the bottom up

    }
    public String toStringR(String s){

    }
    public static MinMaxNode generateTestTree(int depth){
        return generateTestTreeR(depth, new MinMaxNode(null));
    }
    public static MinMaxNode generateTestTreeR(int depth, MinMaxNode master){
        if(depth == 0){
            Random r = new Random();
            return new MinMaxNode(master, r.nextInt(10)+1);
        }

        for(int i=0; i<2; i++){
            master.subNodes.add(generateTestTreeR(depth-1, new MinMaxNode(master)));
        }
        return master;
    }


}
