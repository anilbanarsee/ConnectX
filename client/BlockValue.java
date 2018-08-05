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
    }

    public static BlockValue createChain(Coord[] squares, int value){
        BlockValue b = createChainR(squares, value, 0);
        b.setPrevBlocksR(null);
        return b;
    }
    public BlockValue getMaster(){
        BlockValue point = this;
        while(point.prev!=null){
            point = point.prev;
        }
        return point;
    }
    private static BlockValue createChainR(Coord[] squares, int value, int index){
        if(index>=squares.length){
            return null;
        }
        return new BlockValue(squares[index], value, createChainR(squares, value, index+1));
    }
    private void setPrevBlocksR(BlockValue point){
        this.prev = point;
        if(this.next == null){
            return;
        }
        this.next.setPrevBlocksR(this);
    }
}