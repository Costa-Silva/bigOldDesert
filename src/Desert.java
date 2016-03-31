import java.util.HashMap;
import java.util.Map;

/**
 * Created by Antonio on 30/03/16.
 */
public class Desert {

    private static final int LEAVING = 1;
    private static final int ENTERING = 1;
    private static final int DEFAULTCOST = 1;
    private static final int BALLOONTIME = 6;
    private static final int PLANKTIME = 5;
    private static final int CANOETIME =4;
    private static final char BALLOON= 'b';
    private static final char CANOE = 'c';
    private static final char PLANK = 'p';
    private static final char PLAIN = '_';
    private static final char LAKE = 'L';
    private static final char PIT = 'P';
    private static final char MOUNTAIN = 'M';
    private char[] path;
    private long matrixPath[][];

    public Desert(char[] path){

        this.path=path;

        matrixPath = new long[4][path.length];

    }



    public long problem(){

        fillFirst(path[0],0);

        long lowestValue;
        long valueAux;
        for (int i =1; i<path.length;i++){

            long previousValuePlain = matrixPath[0][i-1];
            long previousValueBalloon = matrixPath[1][i-1];
            long previousValueCanoe = matrixPath[2][i-1];
            long previousValuePlank = matrixPath[3][i-1];


            lowestValue = Math.min(Math.min(Math.min(previousValueBalloon,previousValuePlank),previousValueCanoe),previousValuePlain);

            char terrain = path[i];

            switch (terrain){

                case PLAIN:

                    if (previousValuePlain!= Integer.MAX_VALUE) {
                        matrixPath[0][i] = previousValuePlain+ DEFAULTCOST;
                    }else{
                        matrixPath[0][i] = lowestValue + DEFAULTCOST+LEAVING;
                    }


                    if (previousValueCanoe!=Integer.MAX_VALUE){
                        matrixPath[2][i] = previousValueCanoe + DEFAULTCOST + ENTERING + LEAVING;
                    }else{
                        matrixPath[2][i] = Integer.MAX_VALUE;
                    }


                    if (previousValueBalloon != Integer.MAX_VALUE) {

                        matrixPath[1][i] = previousValueBalloon + ENTERING+LEAVING+DEFAULTCOST;
                    }else{
                        matrixPath[1][i] = Integer.MAX_VALUE;
                    }

                    if (previousValuePlank!=Integer.MAX_VALUE){
                        matrixPath[3][i] = previousValuePlank + DEFAULTCOST + ENTERING + LEAVING;
                    }else{
                        matrixPath[3][i] = Integer.MAX_VALUE;
                    }

                    break;

                case BALLOON:
                    if (previousValuePlain!= Integer.MAX_VALUE) {
                        matrixPath[0][i] = previousValuePlain+ DEFAULTCOST;
                    }else{
                        matrixPath[0][i] = lowestValue + ENTERING + DEFAULTCOST;
                    }


                    if (previousValueCanoe!=Integer.MAX_VALUE){
                        matrixPath[2][i] = previousValueCanoe + DEFAULTCOST + ENTERING + LEAVING;
                    }else{
                        matrixPath[2][i] = Integer.MAX_VALUE;
                    }


                    if (previousValueBalloon != Integer.MAX_VALUE) {

                        valueAux =(previousValueBalloon + ENTERING+LEAVING+DEFAULTCOST> lowestValue+DEFAULTCOST+LEAVING) ? lowestValue+DEFAULTCOST+LEAVING :previousValueBalloon + ENTERING+LEAVING+DEFAULTCOST;


                        if (path[i-1]== MOUNTAIN || path[i-1]==LAKE || path[i-1]== PIT)
                            valueAux = (previousValueBalloon + ENTERING + LEAVING + DEFAULTCOST > lowestValue + DEFAULTCOST + LEAVING + ENTERING) ? lowestValue + DEFAULTCOST + LEAVING +ENTERING: previousValueBalloon + ENTERING + LEAVING + DEFAULTCOST;
                        matrixPath[1][i] = valueAux;



                    }else{
                        if (path[i-1]== MOUNTAIN || path[i-1]==LAKE || path[i-1]== PIT)
                            matrixPath[1][i] = lowestValue + ENTERING+DEFAULTCOST+LEAVING;
                        else matrixPath[1][i] = lowestValue + DEFAULTCOST+LEAVING;
                    }

                    if (previousValuePlank!=Integer.MAX_VALUE){
                        matrixPath[3][i] = previousValuePlank + DEFAULTCOST + ENTERING + LEAVING;
                    }else{
                        matrixPath[3][i] = Integer.MAX_VALUE;
                    }

                    break;

                case CANOE:
                    if (previousValuePlain!= Integer.MAX_VALUE) {
                        matrixPath[0][i] = previousValuePlain+ DEFAULTCOST;
                    }else{
                        matrixPath[0][i] = lowestValue + ENTERING + DEFAULTCOST;
                    }


                    if (previousValueCanoe!=Integer.MAX_VALUE){

                        valueAux= (previousValueCanoe + ENTERING+LEAVING+DEFAULTCOST> lowestValue+DEFAULTCOST+LEAVING) ? lowestValue+DEFAULTCOST+LEAVING :previousValueCanoe + ENTERING+LEAVING+DEFAULTCOST;


                        if (path[i-1]== MOUNTAIN || path[i-1]==LAKE || path[i-1]== PIT)
                            valueAux = (previousValueCanoe + ENTERING + LEAVING + DEFAULTCOST > lowestValue + DEFAULTCOST + LEAVING + ENTERING) ? lowestValue + DEFAULTCOST + LEAVING+ ENTERING : previousValueCanoe + ENTERING + LEAVING + DEFAULTCOST;

                        matrixPath[2][i] = valueAux;

                    }else{
                        if (path[i-1]== MOUNTAIN || path[i-1]==LAKE || path[i-1]== PIT)
                            matrixPath[2][i] = lowestValue + ENTERING+DEFAULTCOST+LEAVING;
                        else matrixPath[2][i] = lowestValue + DEFAULTCOST+LEAVING;

                    }


                    if (previousValueBalloon != Integer.MAX_VALUE) {

                        matrixPath[1][i] = previousValueBalloon + ENTERING+LEAVING+DEFAULTCOST;
                    }else{
                        matrixPath[1][i] = Integer.MAX_VALUE;
                    }

                    if (previousValuePlank!=Integer.MAX_VALUE){
                        matrixPath[3][i] = previousValuePlank + DEFAULTCOST + ENTERING + LEAVING;
                    }else{
                        matrixPath[3][i] = Integer.MAX_VALUE;
                    }

                    break;
                case PLANK:
                    if (previousValuePlain!= Integer.MAX_VALUE) {
                        matrixPath[0][i] = previousValuePlain+ DEFAULTCOST;
                    }else{
                        matrixPath[0][i] = lowestValue + ENTERING + DEFAULTCOST;
                    }


                    if (previousValueCanoe!=Integer.MAX_VALUE){
                        matrixPath[2][i] = previousValueCanoe + DEFAULTCOST + ENTERING + LEAVING;
                    }else{
                        matrixPath[2][i] = Integer.MAX_VALUE;
                    }


                    if (previousValueBalloon != Integer.MAX_VALUE) {

                        matrixPath[1][i] = previousValueBalloon + ENTERING+LEAVING+DEFAULTCOST;
                    }else{
                        matrixPath[1][i] = Integer.MAX_VALUE;
                    }

                    if (previousValuePlank!=Integer.MAX_VALUE){
                        valueAux = (previousValuePlank + ENTERING+LEAVING+DEFAULTCOST> lowestValue+DEFAULTCOST+LEAVING) ? lowestValue+DEFAULTCOST+LEAVING :previousValuePlank + ENTERING+LEAVING+DEFAULTCOST;


                        if (path[i-1]== MOUNTAIN || path[i-1]==LAKE || path[i-1]== PIT)
                            valueAux = (previousValuePlank + ENTERING+LEAVING+DEFAULTCOST> lowestValue+DEFAULTCOST+LEAVING+ENTERING) ? lowestValue+DEFAULTCOST+LEAVING+ENTERING :previousValuePlank + ENTERING+LEAVING+DEFAULTCOST;

                        matrixPath[3][i] = valueAux;


                    }else{
                        if (path[i-1]== MOUNTAIN || path[i-1]==LAKE || path[i-1]== PIT)
                            matrixPath[3][i] = lowestValue + ENTERING+DEFAULTCOST+LEAVING;
                        else matrixPath[3][i] = lowestValue + DEFAULTCOST+LEAVING;

                    }

                    break;

                case MOUNTAIN:
                    matrixPath[0][i] = Integer.MAX_VALUE;
                    matrixPath[2][i] = Integer.MAX_VALUE;
                    matrixPath[3][i] = Integer.MAX_VALUE;

                    if (previousValueBalloon != Integer.MAX_VALUE) {

                        matrixPath[1][i] = previousValueBalloon + BALLOONTIME;
                    }else{
                        matrixPath[1][i] = Integer.MAX_VALUE;
                    }
                    break;

                case PIT:

                    matrixPath[0][i] = Integer.MAX_VALUE;
                    matrixPath[2][i] = Integer.MAX_VALUE;

                    if (previousValueBalloon != Integer.MAX_VALUE) {

                        matrixPath[1][i] = previousValueBalloon + BALLOONTIME;
                    }else{
                        matrixPath[1][i] = Integer.MAX_VALUE;
                    }

                    if (previousValuePlank != Integer.MAX_VALUE) {
                        matrixPath[3][i]= previousValuePlank+PLANKTIME;

                    }else{
                        matrixPath[3][i] =Integer.MAX_VALUE;
                    }
                    break;
                case LAKE:

                    matrixPath[0][i] = Integer.MAX_VALUE;

                    if (previousValueBalloon != Integer.MAX_VALUE) {

                        matrixPath[1][i] = previousValueBalloon + BALLOONTIME;
                    }else{
                        matrixPath[1][i] = Integer.MAX_VALUE;
                    }

                    if (previousValueCanoe!=Integer.MAX_VALUE){

                        matrixPath[2][i] = previousValueCanoe+CANOETIME;
                    }else{
                        matrixPath[2][i] = Integer.MAX_VALUE;
                    }

                    if (previousValuePlank != Integer.MAX_VALUE) {
                        matrixPath[3][i]= previousValuePlank+PLANKTIME;

                    }else{
                        matrixPath[3][i] = Integer.MAX_VALUE;
                    }
                    break;

            }






        }

        lowestValue = Math.min(Math.min(Math.min(matrixPath[1][path.length-1],matrixPath[2][path.length-1]),matrixPath[3][path.length-1]),matrixPath[0][path.length-1]);


        /*

        for (int k=0;k<4;k++){
            for (int j=0;j<path.length;j++){


                System.out.print(matrixPath[k][j] +" ");
            }
                System.out.println();
        }

        */

        return lowestValue;
    }

    private void fillFirst(char c,int pos) {



        switch (c){

            case PLAIN:
                matrixPath[0][pos] = 1;

                matrixPath[1][pos]=Integer.MAX_VALUE;

                matrixPath[2][pos]=Integer.MAX_VALUE;

                matrixPath[3][pos]=Integer.MAX_VALUE;
                break;
            case BALLOON:
                matrixPath[1][pos] = 2;

                matrixPath[0][pos]=1;

                matrixPath[2][pos]=Integer.MAX_VALUE;

                matrixPath[3][pos]=Integer.MAX_VALUE;
                break;
            case CANOE:
                matrixPath[2][pos] = 2;

                matrixPath[0][pos]=1;

                matrixPath[1][pos]=Integer.MAX_VALUE;

                matrixPath[3][pos]=Integer.MAX_VALUE;
                break;
            case PLANK:
                matrixPath[3][pos] = 2;

                matrixPath[0][pos]=1;

                matrixPath[1][pos]=Integer.MAX_VALUE;

                matrixPath[2][pos]=Integer.MAX_VALUE;
                break;
        }
    }


}
