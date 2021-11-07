
import java.util.Random;
public class Sand{
    private Random rand = new Random();
    public int rowsFilled = 0; 
    public SandObj[][]sand;
    public int[] selected= new int[]{0,0};
    public Sand(SandObj[][] grid){
        this.sand = grid;
        for(int i=0; i<sand.length; i++){
            for(int u=0; u<sand[0].length; u++){
                sand[i][u] = new SandObj(0);
            }
        }
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
            if(sand[x][i].type !=0){
                tmp=i;
            }
        }
        return tmp;
    }
    public void remove(int x,int y){
        this.sand[x][y].type = 0;
    }
    public void removeHighest(int x,int y){
        if(y <= highestPoint(x)){
            this.sand[x][highestPoint(x)].type = 0;
        }
    }
    public void addtype(int x,int y ,int type){
        this.sand[x][y].type = type;
    }
    public void add(int x,int y){
        
            this.sand[x][y].type = 1;
        
            //System.out.println(e);
            //this.sand[x][y] = new SandObj(1);
        
        
    }
    public void add(){
        if(sand.length-rowsFilled >0){
            this.add(rand.nextInt(sand.length-rowsFilled)+rowsFilled,rand.nextInt(sand[0].length));
        }
    }
    public void addcube(int x1,int y1,int radius,int type){
        for(int x = -radius; x<=radius; x++){
            for(int y = -radius; y <=radius; y++){
                try{
                    addtype(x1+x, y1+y, type);
                }catch(Exception e){}
            }
        }
    }
    private void swap(int x1,int y1,int x2,int y2,int type){
        this.sand[x1][y1].type = 0;
        this.sand[x2][y2].type = type;
    }
    private void checkRowFill(){
        int temp=0;
        for(int i=rowsFilled; i<sand[0].length; i++){
            for(int u =0; u<sand.length; u++){
                temp+=sand[u][i].type;
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
            if(!(sand[this.selected[0]-1][this.selected[1]-1].type != 0 || sand[this.selected[0]+1][this.selected[1]-1].type != 0 )){//sides are touching
                if(sand[this.selected[0]][this.selected[1]-1].type == 0 ){
                    swap(this.selected[0], this.selected[1], this.selected[0], this.selected[1]-1,sand[this.selected[0]][this.selected[1]].type);
                }
            }
        }catch(Exception e){}
        
    }
    public void burnout(int time){
        if(rand.nextInt(time) ==1){
            remove(this.selected[0],this.selected[1]);
        }
    }
    
    public void burn(){
        int radius = 1;
        if(rand.nextInt(4)==1){
            for(int x = -radius; x<=radius; x++){
                for(int y = -radius; y <=radius; y++){
                    try{
                        if(SandType.flamable(sand[this.selected[0]+x][this.selected[1]+y].type)){
                            addtype(this.selected[0]+x, this.selected[1]+y, sand[this.selected[0]][this.selected[1]].type);
                        }
                    }catch(Exception e){}
                }
            }
            
        }
    }
    public void conwayair(int type){
        int radius = 1;
        int neighbor =0;
        if(rand.nextInt(4)==1){
            for(int x = -radius; x<=radius; x++){
                for(int y = -radius; y <=radius; y++){
                    try{
                        if(sand[this.selected[0]+x][this.selected[1]+y].type == type){
                            neighbor++;
                        }
                    }catch(Exception e){}
                }
            }
            if(neighbor == 3){
                addtype(this.selected[0],this.selected[1],type);
            }
        }
    }
    public void conway(int type){
        int radius = 1;
        int neighbor =0;
        if(rand.nextInt(4)==1){
            for(int x = -radius; x<=radius; x++){
                for(int y = -radius; y <=radius; y++){
                    try{
                        if(sand[this.selected[0]+x][this.selected[1]+y].type == sand[this.selected[0]][this.selected[1]].type){
                            neighbor++;
                        }
                    }catch(Exception e){}
                }
            }
            if (neighbor<=1 || neighbor >3){
                addtype(this.selected[0],this.selected[1],type);
            }

        }
    }
    public void harden(int type){
        int belowType = sand[this.selected[0]][this.selected[1]-1].type;
        int curType = sand[this.selected[0]][this.selected[1]].type;
        if(rand.nextInt(2048) ==1 && belowType != 0){
            addtype(this.selected[0], this.selected[1], type);
        }
    }
    public void grow(){
        if(rand.nextInt(8) == 1){
            try{
                //System.out.println(rand.nextInt(3)-1);
                if(sand[this.selected[0]][this.selected[1]+1].type == 0 || sand[this.selected[0]][this.selected[1]+1].type == 7){
                    int tmp = this.selected[0]+rand.nextInt(3)-1;
                    int tmpy = this.selected[1]+1;
                    if (sand[tmp][this.selected[1]+1].type == 0 || sand[this.selected[0]][this.selected[1]+1].type == 7){
                        //if(tmp + this.selected[0] >= sand.length || tmp + this.selected[0] <=0){
                           // if(this.selected[1]+1 >= sand[0].length || this.selected[1]+1 <= 0){
                                addtype(tmp,tmpy,sand[this.selected[0]][this.selected[1]].type);
                          //  }
                            
                       // }
                        
                    }
                    
                }
                
            }catch(Exception e){
            }
        }
        
    }
    public void waterGravity(){
        int curX = this.selected[0];
        int curY = this.selected[1];
        int curType = sand[curX][curY].type;
        if(sand[curX][curY-1].type == 0){
            swap(curX,curY,curX,curY-1,curType);
            
        }else{
            //if(rand.nextInt(2) == 1){
            if(sand[curX][curY].xSpeed ==0 ){
                int tmp =0;
                while (tmp ==0){
                    tmp = rand.nextInt(2)-1;
                }
                sand[curX][curY].xSpeed = tmp;
            }
                try{
                    if(sand[curX+1][curY].type == 0){
                        swap(curX,curY, curX+1, curY, curType);
                        
                    }else if (sand[curX-1][curY].type == 0){
                        swap(curX,curY, curX-1, curY, curType);
                    }else{
                        sand[curX][curY].xSpeed *=-1;
                    }
                }catch(Exception e){}
            
            //System.out.println(sand[curX][curY].xSpeed);
        }//}
    }
    /*public void waterGravity(){
        int curX = this.selected[0];
        int curY = this.selected[1];
        int curType = sand[curX][curY].type;
        if(sand[curX][curY-1].type == 0){
            swap(curX,curY,curX,curY-1,curType);
        }else{
            int tmp = rand.nextInt(2)-1;
            try{
                if(sand[curX+tmp][curY-1].type == 0){//check diangles
                    swap(curX,curY,curX+tmp,curY-1,curType);
                }else if(sand[curX-tmp][curY-1].type ==0){//check diangles
                    swap(curX,curY,curX-tmp,curY-1,curType);
                }else
                if(sand[curX+tmp][curY].type == 0){//check diangles
                    swap(curX,curY,curX+tmp,curY,curType);
                }else if(sand[curX-tmp][curY].type ==0){//check diangles
                    swap(curX,curY,curX-tmp,curY,curType);
                }
            }catch(Exception e){}
        }

    }*/
    public void sandGravity(){
        if(sand[this.selected[0]][this.selected[1]-1].type == 0){
            //move down{up matrix inverted}
            swap(this.selected[0], this.selected[1], this.selected[0], this.selected[1]-1,sand[this.selected[0]][this.selected[1]].type);
        }else {
            //both side are open randomly pick side.
            if(this.selected[0]!=sand.length-1 && this.selected[0]!=0){
                if(sand[this.selected[0]-1][this.selected[1]-1].type==0 && sand[this.selected[0]+1][this.selected[1]-1].type==0 ){
                    if(rand.nextBoolean()){
                        swap(this.selected[0], this.selected[1], this.selected[0]+1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]].type);
                    }else{
                        swap(this.selected[0], this.selected[1], this.selected[0]-1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]].type);
                    }
                }else if(sand[this.selected[0]-1][this.selected[1]-1].type==0){
                    swap(this.selected[0], this.selected[1], this.selected[0]-1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]].type);
                }else if(sand[this.selected[0]+1][this.selected[1]-1].type==0){
                    swap(this.selected[0], this.selected[1], this.selected[0]+1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]].type);
                }
            }else{
                if(this.selected[0]!=0){
                    if(sand[this.selected[0]-1][this.selected[1]-1].type==0){
                        swap(this.selected[0], this.selected[1], this.selected[0]-1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]].type);
                    }
                }else if(sand[this.selected[0]+1][this.selected[1]-1].type==0){
                    swap(this.selected[0], this.selected[1], this.selected[0]+1, this.selected[1]-1,sand[this.selected[0]][this.selected[1]].type);
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
                
                if(i>rowsFilled && i<sand[0].length && sand[u][i].type !=0){//changed
                //Check If spot below is empty
                this.selected[0] = u;
                this.selected[1] =i;
                if(sand[u][i].type !=0){
                    SandType.moveFromType(sand[u][i].type,this);
                }
                //this.sandGravity();
                
                }
                
                
            }
        }
    }
}