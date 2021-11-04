
import java.util.Random;
public class Sand{
    private Random rand = new Random();
    public int rowsFilled = 0; 
    public int[][]sand;
    public int[] selected= new int[]{0,0};
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
            if(sand[x][i]!=0){
                tmp=i;
            }
        }
        return tmp;
    }
    public void remove(int x,int y){
        this.sand[x][y] = 0;
    }
    public void removeHighest(int x,int y){
        if(y <= highestPoint(x)){
            this.sand[x][highestPoint(x)] = 0;
        }
    }
    public void addtype(int x,int y ,int type){
        this.sand[x][y] = type;
    }
    public void add(int x,int y){
        this.sand[x][y] = 1;
    }
    public void add(){
        if(sand.length-rowsFilled >0){
            this.add(rand.nextInt(sand.length-rowsFilled)+rowsFilled,rand.nextInt(sand[0].length));
        }
    }
    
    private void swap(int x1,int y1,int x2,int y2,int type){
        this.sand[x1][y1] = 0;
        this.sand[x2][y2] = type;
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
    public void structGravity(){
        try{
            if(!(sand[this.selected[0]-1][this.selected[1]-1] != 0 || sand[this.selected[0]+1][this.selected[1]-1] != 0 )){//sides are touching
                if(sand[this.selected[0]][this.selected[1]-1] == 0 ){
                    swap(this.selected[0], this.selected[1], this.selected[0], this.selected[1]-1,sand[this.selected[0]][this.selected[1]]);
                }
            }
        }catch(Exception e){}
        
    }
    public void burnout(){
        if(rand.nextInt(16) ==1){
            remove(this.selected[0],this.selected[1]);
        }
    }
    public void burn(){
        int size = 3;
        int midpoint = size/2;
        if(rand.nextInt(4)==1){
            try{
                if(SandType.flamable(sand[this.selected[0]][this.selected[1]+1])){
                    addtype(this.selected[0], this.selected[1]+1, sand[this.selected[0]][this.selected[1]]);
                }
                if(SandType.flamable(sand[this.selected[0]-1][this.selected[1]+1])){
                    addtype(this.selected[0]-1, this.selected[1]+1, sand[this.selected[0]][this.selected[1]]);
                }
                if(SandType.flamable(sand[this.selected[0]+1][this.selected[1]+1])){
                    addtype(this.selected[0]+1, this.selected[1]+1, sand[this.selected[0]][this.selected[1]]);
                }
                if(SandType.flamable(sand[this.selected[0]][this.selected[1]-1])){
                    addtype(this.selected[0], this.selected[1]-1, sand[this.selected[0]][this.selected[1]]);
                }
                if(SandType.flamable(sand[this.selected[0]-1][this.selected[1]-1])){
                    addtype(this.selected[0]-1, this.selected[1]-1, sand[this.selected[0]][this.selected[1]]);
                }
                if(SandType.flamable(sand[this.selected[0]+1][this.selected[1]-1])){
                    addtype(this.selected[0]+1, this.selected[1]-1, sand[this.selected[0]][this.selected[1]]);
                }
            }catch(Exception e){}
        }
    }
    public void grow(){
        if(rand.nextInt(16) == 1){
            try{
                //System.out.println(rand.nextInt(3)-1);
                if(sand[this.selected[0]][this.selected[1]+1] == 0){
                    int tmp = this.selected[0]+rand.nextInt(3)-1;
                    if (sand[tmp][this.selected[1]+1] == 0){
                        //if(tmp + this.selected[0] >= sand.length || tmp + this.selected[0] <=0){
                           // if(this.selected[1]+1 >= sand[0].length || this.selected[1]+1 <= 0){
                                addtype(tmp,this.selected[1]+1,sand[this.selected[0]][this.selected[1]]);
                          //  }
                            
                       // }
                        
                    }
                    
                }
                
            }catch(Exception e){
            }
        }
        
    }
    public void sandGravity(){
        if(sand[this.selected[0]][this.selected[1]-1] == 0){
            //move down{up matrix inverted}
            swap(this.selected[0], this.selected[1], this.selected[0], this.selected[1]-1,sand[this.selected[0]][this.selected[1]]);
        }else {
            //both side are open randomly pick side.
            if(this.selected[0]!=sand.length-1 && this.selected[0]!=0){
                if(sand[this.selected[0]-1][this.selected[1]-1]==0 && sand[this.selected[0]+1][this.selected[1]-1]==0 ){
                    if(rand.nextBoolean()){
                        swap(this.selected[0], this.selected[1], this.selected[0]+1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]]);
                    }else{
                        swap(this.selected[0], this.selected[1], this.selected[0]-1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]]);
                    }
                }else if(sand[this.selected[0]-1][this.selected[1]-1]==0){
                    swap(this.selected[0], this.selected[1], this.selected[0]-1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]]);
                }else if(sand[this.selected[0]+1][this.selected[1]-1]==0){
                    swap(this.selected[0], this.selected[1], this.selected[0]+1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]]);
                }
            }else{
                if(this.selected[0]!=0){
                    if(sand[this.selected[0]-1][this.selected[1]-1]==0){
                        swap(this.selected[0], this.selected[1], this.selected[0]-1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]]);
                    }
                }else if(sand[this.selected[0]+1][this.selected[1]-1]==0){
                    swap(this.selected[0], this.selected[1], this.selected[0]+1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]]);
                }
            }  
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
                if(i>rowsFilled && i<sand[0].length && sand[u][i] !=0){//changed
                    //Check If spot below is empty
                    this.selected[0] = u;
                    this.selected[1] =i;
                    SandType.moveFromType(sand[u][i],this);
                    //this.sandGravity();
                    
                }
            }
        }
    }
}