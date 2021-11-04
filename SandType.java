public class SandType{
    //https://coolors.co/1f1a20-221d23-4f3824-d1603d-d78d52-ddb967-7b7d26-d0e37f-dceaa0
    private static float RGBToF(int a){
        return (float)((float)a/256);
    }
    public static float[] backgroundColor(){
        return new float[]{RGBToF(220),RGBToF(234),RGBToF(160),1.0f};
    }
    public static void moveFromType(int type,Sand sand){
        switch(type){
            case 1:
            case 2:
            case 3:
            
                //regular Gravity
                sand.sandGravity();
                break;
            case 5:
                sand.structGravity();
                sand.burnout();
                sand.burn();
                break;
            case 6:
                //grow
                sand.grow();
                sand.structGravity();
                break;
            default:
                //no Gravity
                break;
        }
    }
    public static boolean flamable(int type){
        switch(type){
            case 6:
                return true;
            default:
                return false;
        }
    }
    public static float[] colorFromType(int type){
        switch(type){
            case 1:
                return new float[]{RGBToF(221),RGBToF(185),RGBToF(103),1.0f};
            case 2:
                return new float[]{RGBToF(209),RGBToF(96),RGBToF(61),1.0f};
            case 3:
                return new float[]{RGBToF(215),RGBToF(141),RGBToF(82),1.0f};
            case 4:
                return new float[]{RGBToF(79),RGBToF(56),RGBToF(36),1.0f};
            case 5:
                return new float[]{RGBToF(34),RGBToF(29),RGBToF(35),1.0f};
            case 6:
                return new float[]{RGBToF(123),RGBToF(125),RGBToF(38),1.0f};
            default:
                return new float[]{0.0f,0.0f,0.0f,1.0f};
        }
    }
}