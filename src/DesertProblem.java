import java.util.HashMap;
import java.util.Map;

/**
 * Created by Antonio on 26/03/16.
 */
public class DesertProblem {

    private boolean gotObject;
    private char[] path;

    public DesertProblem(char[] path){
        this.path=path;
        this.gotObject=false;
    }

    public void inutilReferences(){

        Map<String,Integer> terrain;

        Map<String,Integer> objects;
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

    public int getMinimumCost(){

        CrossingCost crossingCost = new CrossingCost();


        int totalPathCost=0;


        int currentSpot=path.length;

        while (currentSpot>=0){

            if (path[currentSpot]=='_'){


            }

        }






        return 0;
    }





}
