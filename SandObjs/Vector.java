import java.lang.Math;
public class Vector{
    public double x;
    public double y;
    public Vector(){}
    public Vector(double x,double y){
        this.set(x,y);
    }
    public void set(double x,double y){
        this.x = x;
        this.y = y;
    }
    public double getx(){
        return this.x;
    }
    public double gety(){
        return this.y;
    }
    public double getDist(Vector v){
        return Math.sqrt(getRelDist(v));
    }
    public double getRelDist(Vector v){
        return NumJa.square(Math.abs(v.x-this.x))+NumJa.square(Math.abs(v.y-this.y));
    }
    public void add(Vector v){
        this.set(this.x+v.x,this.y+v.y);
    }
    public void subtract(Vector v){
        this.set(this.x-v.x,this.y-v.y);
    }
    public double magnitude(){
        return Math.sqrt(NumJa.square(this.x) +NumJa.square(this.y));
    }
}