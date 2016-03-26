import java.util.HashMap;
import java.util.Map;

/**
 * Created by Antonio on 26/03/16.
 */
public class CrossingCost {


     Map<String,Integer> terrain;

     Map<String,Integer> objects;

    public static final int DEFAULTCOST = 1;

    public CrossingCost(){
        init();
    }

    public void init(){

        terrain = new HashMap<>();
        objects = new HashMap<>();

        objects.put("c",4);
        objects.put("p",5);
        objects.put("b",6);

        terrain.put("_",1);
        terrain.put("L",2);
        terrain.put("P",3);
        terrain.put("M",4);
    }






    public int crossingCost(String object,boolean usingObject, boolean hadObject,boolean leavingWithObject){



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
