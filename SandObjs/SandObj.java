public class SandObj{
    public Vector vec;
    public int type;
    private int[][] grid;
    public int x;
    public int y;
    public SandObj(double x, double y){
        set(x,y);
        this.vec.set(0,-1);
    }
    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean checkCollision(int x,int y){
        if(this.grid[x][y] ==1){
            return true;
        }
        return false;
    }
    private boolean canMove(){
        //if the object its above has a speed of zero.
        return true;
    }
    public void nextPosition(){
        if(canMove()){
            int tempx = (int)Math.round(this.x);
            int tempy = (int)Math.round(this.y);
            if(!checkCollision(tempx, tempy)){
                set(tempx,tempy);
            }else{

            }
        }
    }
    public void update(int[][] grid){
        this.grid = grid;
        nextPosition();
    }
}