package client;

public class Main {
    public static void main(String[] args){
       CLClient client = new CLClient(1,5, 5);
       Thread t = new Thread(client);
       t.start();
    }
}
