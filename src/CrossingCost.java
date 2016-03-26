import java.util.HashMap;
import java.util.Map;

/**
 * Created by Antonio on 26/03/16.
 */
public class CrossingCost {


     Map<Character,Integer> terrain;

     Map<Character,Integer> objects;

    public static final int DEFAULTCOST = 1;

    private static final int BALLOONTIME = 6;
    private static final int PLANKTIME = 5;
    private static final int CANOETIME =4;

    public CrossingCost(){
        init();
    }

    public void init(){

        terrain = new HashMap<Character,Integer>();
        objects = new HashMap<Character,Integer>();

        objects.put('c',CANOETIME);
        objects.put('p',PLANKTIME);
        objects.put('b',BALLOONTIME);

        terrain.put('_',1);
        terrain.put('L',2);
        terrain.put('P',3);
        terrain.put('M',4);
    }






    public int crossingCost(char object,boolean usingObject, boolean hadObject,boolean leavingWithObject){


        if (usingObject) {

            return objects.get(object);
        }

        int crossCost=DEFAULTCOST;

        if (hadObject)
            crossCost++;
        if (leavingWithObject)
            crossCost++;

        return crossCost;



    }
}
