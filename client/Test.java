package client;

import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        MinMaxNode master = MinMaxNode.generateTestTree(3);
        master.paint(3);
    }
}
