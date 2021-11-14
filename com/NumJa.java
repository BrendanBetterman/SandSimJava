package com;
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
    public static int getState(int a,int b, int c, int d){
        return a*8+b*4+c*2+d*1;
    }
    public static int[] aveTwo(int[] a,int[] b){
        int[] tmp = new int[]{(a[0]+b[0])/2,(a[1]+b[1])/2};
        return tmp;
    }
    public static int[][] marchingSquares(SandObj[][] sand){
        int[] a;
        int[] b;
        int[] c;
        int[] d;
        ArrayList<int[]> out = new ArrayList<int[]>();
        for(int x=0; x<sand.length-1; x++){
            for(int y=0;y<sand[0].length-1;y++){
                a = new int[]{x, y};
                b = new int[]{x+1, y};
                c = new int[]{x, y+1};
                d = new int[]{x, y};
                int state = getState(sand[x][y].type, sand[x+1][y].type, sand[x+1][y+1].type, sand[x][y+1].type);
                switch(state){
                    case 1:
                        out.add(aveTwo(c,d));
                        break;
                    case 2:
                        out.add(aveTwo(b, c));
                        break;
                    case 3:
                        out.add(aveTwo(b,d));
                        break;
                    case 4:
                        out.add(aveTwo(a,b));
                        break;
                    case 5:
                    out.add(aveTwo(a,d));
                    out.add(aveTwo(b,c));
                        break;
                    case 6:
                    out.add(aveTwo(a,c));
                        break;
                    case 7:
                    out.add(aveTwo(a,d));
                        break;
                    case 8:
                    out.add(aveTwo(a,d));
                        break;
                    case 9:
                    out.add(aveTwo(a,c));
                        break;
                    case 10:
                    out.add(aveTwo(a,b));
                    out.add(aveTwo(c,d));
                        break;
                    case 11:
                    out.add(aveTwo(a,b));
                        break;
                    case 12:
                    out.add(aveTwo(b,d));
                        break;
                    case 13:
                    out.add(aveTwo(b,c));
                        break;
                    case 14:
                    out.add(aveTwo(c,d));
                        break;
                    default:
                        break;
                }
            }
        }
        int[][] temp = new int[out.size()][2];
        for (int i =0;i<out.size();i++){
            temp[i] = out.get(i);
        }
        //System.out.println(stringArray(temp));
        return temp;
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