
public class ChunkBorders{
    private void checkEdge(Sand sand1,Sand sand2,int x){
        for(int i=0; i <sand1.sand[0].length-1; i++){// only edges
            if(sand1.sand[x][i] == 1){//if sand
                if(i>1){
                    if(sand1.sand[x][i-1] ==0){
                        //should be handed in sand
                        //sand1.remove(x,i);
                       // sand1.add(x,i-1);
                    }else if(sand2.sand[sand1.sand.length-x-1][i-1] == 0){
                        sand1.remove(x,i);
                        sand2.add(sand1.sand.length-x-1,i-1);
                    }else{
                        //break;
                    }
                }
                
            }
        }
    }
    public void update(Sand sand1,Sand sand2, Sand sand3){
        checkEdge(sand1,sand2,sand1.sand.length-1);
        checkEdge(sand2,sand1,0);
        checkEdge(sand2,sand3,sand2.sand.length-1);
        checkEdge(sand3,sand2,0);
    }
}