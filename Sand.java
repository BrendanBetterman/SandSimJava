
import java.util.Random;
public class Sand{
    private Random rand = new Random();
    public int rowsFilled = 0; 
    public int[][]sand;
    public Sand(int[][] grid){
        this.sand = grid;
    }
    public void generate(int[] seeds){
        for(int i=0; i<seeds.length; i++){
            for(int u=0; u<seeds[i]; u++){
                add(i,u);
            }
        }
    }
    public void removeRow(int y){
        for (int i =0; i<this.sand[0].length-1; i++){
            this.remove(i, y);
        }
    }
    private int highestPoint(int x){
        int tmp =0;
        for(int i=0; i< sand.length; i++){
            if(sand[x][i]==1){
                tmp=i;
            }
        }
        return tmp;
    }
    public void remove(int x,int y){
        if(y <= highestPoint(x)){
            this.sand[x][highestPoint(x)] = 0;
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
    private void checkRowFill(){
        int temp=0;
        for(int i=rowsFilled; i<sand[0].length; i++){
            for(int u =0; u<sand.length; u++){
                temp+=sand[u][i];
            }
            if(temp == sand.length && rowsFilled < i){
                rowsFilled =i;
                System.out.println("rows filled " + i);
            }
            temp =0;
        }
    }
    private boolean canMove(int x, int y){
        if (x >0 && x<this.sand.length-1){
            //wips
        }
        return true;
    }
    public void update(){
        //checkRowFill();
        for(int i=rowsFilled; i<sand[0].length; i++){//i = y u= x
            for(int u=0; u<sand.length; u++){
                //Check If new row has been filled.
                /*if(i == rowsFilled){
                    if (checkRowFill(sand[i])){
                        rowsFilled =i+1;
                        System.out.println("Filled" + i);
                    }
                }*/
                if(i>rowsFilled && i<sand[0].length && sand[u][i] ==1){
                    //Check If spot below is empty
                    //System.out.println(sand[i-1][u] == 0);
                    if(sand[u][i-1] == 0){
                        //move down{up matrix inverted}
                        swap(u, i, u, i-1);
                    }else {
                        //both side are open randomly pick side.
                        if(u!=sand.length-1 && u!=0){
                            if(sand[u-1][i-1]==0 && sand[u+1][i-1]==0 ){
                                if(rand.nextBoolean()){
                                    swap(u, i, u+1, i-1);
                                }else{
                                    swap(u, i, u-1, i-1);
                                }
                            }else if(sand[u-1][i-1]==0){
                                swap(u, i, u-1, i-1);
                            }else if(sand[u+1][i-1]==0){
                                swap(u, i, u+1, i-1);
                            }else{
                                //do nothing
                            }
                        }else{
                            if(u!=0){
                                if(sand[u-1][i-1]==0){
                                    swap(u, i, u-1, i-1);
                                }
                            }else if(sand[u+1][i-1]==0){
                                swap(u, i, u+1, i-1);
                            }
                        }  
                    }
                }
            }
        }
    }
}