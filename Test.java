package client;

import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        MinMaxNode master = MinMaxNode.generateTestTree(3);
        Scanner sc = new Scanner(System.in);
        String input = "start";

        while(!input.equals("q")){
            if(input.startsWith("f")){
                try {
                    int i = Integer.parseInt(input.substring(1));

                } catch(NumberFormatException e) {
                    System.out.println("Invalid number input after 'f': "+input.substring(1));
                    continue;
                }

            }
            if(input.equals(""))
            input = sc.nextLine();
        }
    }
}
