
import java.util.ArrayList;
import java.util.Random;
public class NumJa{
    public static float[] lerp(float[]a,float[] b, float f){
		for (int i=0; i<a.length; i++){
			a[i] = lerp(a[i],b[i],f);
		}
		return a;
	}
	public static float lerp(float a, float b, float f)
	{
		return a + f * (b - a);
	}
    public static float[] randFloatArray(Random rand,int len){
		float[] temp = new float[len];
		for (int i=0; i<len; i++){
			temp[i] = rand.nextFloat();
		}
		return temp;
	}/**/
    
    public static String stringArray(int[] o){
        String out = "[";
		for(int i =0; i<o.length; i++){
            out+=String.valueOf(o[i]) +", ";
		}
		return out + "]\n";
	}
	public static String stringArray(int[][] o){
        String out ="[";
		for(int i =0; i<o.length; i++){
			out += stringArray(o[i]);
		}
		return out + "]\n";
	}
    public static String stringArray(float[] o){
        String out = "[";
		for(int i =0; i<o.length; i++){
            out+=String.valueOf(o[i]) +", ";
		}
		return out + "]\n";
	}
    public static String stringArray(float[][] o){
        String out ="[";
		for(int i =0; i<o.length; i++){
			out += stringArray(o[i]);
		}
		return out + "]\n";
	}
    public static double square(double a){
        return a*a;
    }
    public static int[][] findHCluster(int[][] sand,int rowsFilled){
        ArrayList<int[]> clust = new ArrayList<int[]>();
        int clustIndex =0;
        for(int i=rowsFilled; i<sand.length; i++){//rows
			for(int u=0; u<sand[0].length; u++){//collumns
                if(u==0){//if on edge
                    //don't index before
                    if(sand[i][u]==1){
                        
                        clust.add(new int[]{u,0,i});
                        clustIndex++;
                    }
                }else{
                    //Find if there is sand 
                    //Check front and back to see if it can cluster
                    if(sand[i][u]==1){
                        if(sand[i][u-1]==0){
                            
                            clust.add(new int[]{u,0,i});
                            clustIndex++;
                        }else{
                            //System.out.println("cluster found");
                            clust.set(clustIndex-1,new int[]{clust.get(clustIndex-1)[0],u-clust.get(clustIndex-1)[0],i});
                        }
                    }
                }
            }
        }
        
        int[][] temp = new int[clustIndex][3];
        for (int i =0;i<clustIndex;i++){
            temp[i] = clust.get(i);
        }
        return temp;
    }
}