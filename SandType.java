public class SandType{
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
            case 4:
            case 5:
                //regular Gravity
                sand.regularGravity();
                break;
            case 6:
                //grow
                break;
            default:
                //no Gravity
                break;
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