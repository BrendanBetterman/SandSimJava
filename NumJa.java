
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
    public static int[][] findHCluster(SandObj[][] sand,int rowsFilled){
        ArrayList<int[]> clust = new ArrayList<int[]>();
        int clustIndex =0;
        int lastType = 0;//changed
        for(int i=rowsFilled; i<sand.length; i++){//rows
			for(int u=0; u<sand[0].length; u++){//collumns
                if(u==0){//if on edge
                    //don't index before
                    if(sand[i][u].type!=0){
                        clust.add(new int[]{u,0,i,sand[i][u].type});
                        lastType= sand[i][u].type;
                        clustIndex++;
                    }
                }else{
                    //Find if there is sand 
                    //Check front and back to see if it can cluster
                    if(sand[i][u].type!=0){
                        if(sand[i][u-1].type != sand[i][u].type && lastType !=0 && sand[i][u].type != 0){
                            clust.add(new int[]{u,0,i,sand[i][u].type});
                            lastType= sand[i][u].type;
                            clustIndex++;
                        }
                        if(sand[i][u-1].type == sand[i][u].type && sand[i][u].type != 0){
                            lastType = sand[i][u].type;
                            clust.set(clustIndex-1,new int[]{clust.get(clustIndex-1)[0],u-clust.get(clustIndex-1)[0],i,lastType});
                        }
                        
                    }
                }
            }
        }
        
        int[][] temp = new int[clustIndex][4];
        for (int i =0;i<clustIndex;i++){
            temp[i] = clust.get(i);
        }
        //System.out.println(stringArray(temp));
        return temp;
    }
}