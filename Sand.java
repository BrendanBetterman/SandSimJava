
import java.util.Random;
public class Sand{
    private Random rand = new Random();
    public int rowsFilled = 0; 
    public int[][]sand;
    public Sand(int[][] grid){
        this.sand = grid;
    }
    public void remove(int x,int y){
        this.sand[x][y] = 0;
        if(rowsFilled >= x ){
            rowsFilled =x -1;
        }
    }
    
    public void add(int x,int y){
        this.sand[x][y] = 1;
    }
    public void add(){
        if(sand.length-rowsFilled >0){
            this.add(rand.nextInt(sand.length-rowsFilled)+rowsFilled,rand.nextInt(sand[0].length));
        }
    }
    
    private void swap(int x1,int y1,int x2,int y2){
        this.sand[x1][y1] = 0;
        this.sand[x2][y2] = 1;
    }
    private boolean checkRowFill(int[] row){
        int temp=0;
        for(int i=0; i<row.length; i++){
            temp+=row[i];
        }
        return temp == row.length;
    }
    private boolean canMove(int x, int y){
        if (x >0 && x<this.sand.length-1){
            //wips
        }
        
        return true;
    }
    public void update(){
        for(int i=rowsFilled; i<sand.length; i++){
            for(int u=0; u<sand[0].length; u++){
                //Check If new row has been filled.
                if(i == rowsFilled +1){
                    if (checkRowFill(sand[i])){
                        rowsFilled =i+1;
                        System.out.println("Filled" + i);
                    }
                }
                if(i>rowsFilled && i<sand.length && sand[i][u] ==1){
                    //Check If spot below is empty
                    //System.out.println(sand[i-1][u] == 0);
                    if(sand[i-1][u] == 0){
                        //move down{up matrix inverted}
                        swap(i, u, i-1, u);
                    }else {
                        //both side are open randomly pick side.
                        if(u!=sand[0].length-1 && u!=0){
                            if(sand[i-1][u-1]==0 && sand[i-1][u+1]==0 ){
                                if(rand.nextBoolean()){
                                    swap(i, u, i-1, u+1);
                                }else{
                                    swap(i, u, i-1, u-1);
                                }
                            }else if(sand[i-1][u-1]==0){
                                swap(i, u, i-1, u-1);
                            }else if(sand[i-1][u+1]==0){
                                swap(i, u, i-1, u+1);
                            }else{
                                //do nothing
                            }
                        }else{
                            if(u!=0){
                                if(sand[i-1][u-1]==0){
                                    swap(i, u, i-1, u-1);
                                }
                            }else if(sand[i-1][u+1]==0){
                                swap(i, u, i-1, u+1);
                            }
                        }  
                    }
                }
            }
        }
    }
}