package client;

public class BlockValue{
    Coord square;
    int value;
    BlockValue next;
    BlockValue prev;

    private BlockValue(Coord square, int value, BlockValue next){
        this.square = square;
        this.value = value;
        this.next = next;
        this.prev = prev;
    }

    public static BlockValue createChain(Coord[] squares, int value){
        return createChainR(squares, value, 0, null);
    }
    private static BlockValue createChainR(Coord[] squares, int value, int index){
        if(index>=squares.length){
            return null;
        }
        return new BlockValue(squares[index], value, createChainR(squares, value, index+1));
    }
}