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


    private long previousValuePlain;
    private long previousValueBalloon;
    private long previousValueCanoe;
    private long previousValuePlank;

    public Desert(char[] path){

        this.path=path;


        previousValuePlain = Integer.MAX_VALUE;
        previousValueBalloon = Integer.MAX_VALUE;
        previousValueCanoe = Integer.MAX_VALUE;
        previousValuePlank = Integer.MAX_VALUE;


        matrixPath = new long[4][path.length];

    }



    public long problem(){

        fillFirst(path[0],0);

        long lowestValue;
        long valueAux;
        for (int i =1; i<path.length;i++){

             previousValuePlain = matrixPath[0][i-1];
             previousValueBalloon = matrixPath[1][i-1];
             previousValueCanoe = matrixPath[2][i-1];
             previousValuePlank = matrixPath[3][i-1];


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
                        matrixPath[1][i] = getBestChoice(lowestValue,previousValueBalloon,i);
                    }else{
                        if (isObstacle(path[i-1]))
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
                        matrixPath[2][i] = getBestChoice(lowestValue,previousValueCanoe,i);
                    }else{
                        if (isObstacle(path[i-1]))
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
                        matrixPath[3][i] = getBestChoice(lowestValue,previousValuePlank,i);
                    }else{
                        if (isObstacle(path[i-1]))
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


        return lowestValue;
    }

    private boolean isObstacle(char obs){
        return (obs == MOUNTAIN || obs == LAKE || obs == PIT);
    }

    //Quando encontra um objeto, calcula o minimo entre continuar com o objeto anterior ou apanhar este novo objeto
    private long getBestChoice(long lowestValue,long previousValue,int i){
        long value1 = previousValue + ENTERING+LEAVING+DEFAULTCOST;
        long value2 = lowestValue+DEFAULTCOST+LEAVING;

        if (isObstacle(path[i-1]))
           value2+=ENTERING;

        return Math.min(value1,value2);
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
