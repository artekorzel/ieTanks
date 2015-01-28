package pl.edu.agh.ietanks.engine.api;

public enum Direction {
    Right(1,0),
    Left(-1,0),
    Up(0,1),
    Down(0,-1),
    Up_Right(1,1),
    Up_Left(-1,1),
    Down_Right(1,-1),
    Down_Left(-1,-1),
    None(0,0),
    ;

    private int x;
    private int y;
    private Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
