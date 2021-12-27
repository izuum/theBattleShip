package util;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static boolean countCoordinates(String userInput, int sizeShip){
        //проверка координат на соответствие количеству палуб корабля
        if(userInput.split(";").length == sizeShip){
            return false;
        }else{
            return true;
        }
    }
    public static boolean validCoordinates(String userInput){
        //проверка координат на корректность (целочисленность координаты и нахождение ее в нужном диапазоне)
        boolean condition = true;
        String[] coordinates = userInput.split(";");
        try{
            for(String elements: coordinates){
                String[] xy = elements.split(",");
                if ((Integer.parseInt(xy[0]) >= 0 && Integer.parseInt(xy[0]) <= 9)
                        && (Integer.parseInt(xy[1]) >= 0 && Integer.parseInt(xy[1]) <= 9)){
                    condition = false;
                }else{
                    throw new IllegalArgumentException();
                }
            }
        }catch (IllegalArgumentException e){
            return true;
        }
        return condition;
    }
    public static boolean validShip(String userInput, int sizeShip){
        //проверяет валидность корабля, по вертикали или по горизонтали
        boolean condition = true;
        if(sizeShip == 1){ // однопалубный корабль всегда валиден
            condition = false;
        }
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        String[] ship = userInput.split(";");
        try{
            for(String elements: ship) {
                String[] xy = elements.split(",");
                x.add(Integer.parseInt(xy[0]));
                y.add(Integer.parseInt(xy[1]));
            }
            for (int i = 0; i < sizeShip; i++){ //проверяются коорднаты как в одну сторону так и в другую
                if ((((x.get(0) == x.get(i)) && (y.get(0)+i == y.get(i))) || ((x.get(0) == x.get(i)) && ((y.get(0)-i) == y.get(i))))
                        || (((x.get(0)+i == x.get(i)) && (y.get(0) == y.get(i))) || ((x.get(0)-i == x.get(i)) && ((y.get(0) == y.get(i)))))){
                    condition = false;
                }else{
                    throw new IllegalArgumentException();
                }
            }
        }catch (IllegalArgumentException e){
            condition = true;
        }
        return condition;
    }
    public static boolean freeCoordinatesOnField(String userInput, int[][] playerField){
        //проверка на свободность выбранных координат
        boolean condition = true;
        String[] coordinates = userInput.split(";");
        try{
            for(String elements: coordinates) {
                String[] xy = elements.split(",");
                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);
                if(playerField[x][y] == -1){
                    condition = false;
                }else{
                    throw new IllegalArgumentException();
                }
            }
        }catch (IllegalArgumentException e){
            condition = true;
        }

        return condition;
    }
    public static boolean oreolOfShip(String userInput, int sizeShip, int[][] playerField){
        //проверяет состояние орелоа вокруг корабля, соседняя клетка либо свободна (-1), либо в клетке уже есть ореол(0),
        //при нахождении в клетке корабля (1), установка коробля в эту клетку невозможна. Также проверяет ореол, если корабль на краю карты.
        boolean condition = true;
        int position = 0;
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        String[] ship = userInput.split(";");
        try{
            for(String elements: ship) {
                String[] xy = elements.split(",");
                x.add(Integer.parseInt(xy[0]));
                y.add(Integer.parseInt(xy[1]));
            }
            for (int i = 1; i < sizeShip; i++) {
                if (((x.get(0) == x.get(i)) && (y.get(0) + i == y.get(i)))/* || ((x.get(0) == x.get(i)) && ((y.get(0) - i) == y.get(i)))*/) {///проверка кораля в обратную сторону(справа налево)
                    position++;
                } else if (((x.get(0) + i == x.get(i)) && (y.get(0) == y.get(i)))/* || ((x.get(0) - i == x.get(i)) && ((y.get(0) == y.get(i))))*/) {///проверка кораля в обратную сторону(справа налево)
                    position--;
                }
            }
            if( position > 0){
                for( int i = 0; i < sizeShip; i++){
                    if((x.get(0) == 0 & y.get(0) == 0) & (y.get(0) == y.get(i) & x.get(0) != x.get(0)+1)){//корабль вертикально в левом верхнем углу
                        if((playerField[x.get(sizeShip)][y.get(i)]==0 || playerField[x.get(sizeShip)][y.get(i)]==-1) &&
                                (playerField[x.get(sizeShip)][y.get(i)+1]==0 || playerField[x.get(sizeShip)][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1)){
                            condition = false;
                        }
                    }else if((x.get(sizeShip-1) == 9) && (y.get(0) == y.get(i))){//кораль вертикально в нижнем левом углу
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(i)+1]==0 || playerField[x.get(0)-1][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1)) {
                            condition = false;
                        }
                    }else if(x.get(sizeShip-1)==9 && y.get(0)==9){//корабль вертикально в нижнем правом углу
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1)){
                            condition = false;
                        }
                    }else if((x.get(0)==0 & y.get(0)==9) & (y.get(0)==y.get(i) & x.get(0)!=x.get(0)+1)) {//корабль вертикально в правом верхнем углу
                        if((playerField[x.get(sizeShip)][y.get(i)]==0 || playerField[x.get(sizeShip)][y.get(i)]==-1) &&
                                (playerField[x.get(sizeShip)][y.get(i)-1]==0 || playerField[x.get(sizeShip)][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1)){
                            condition = false;
                        }
                    }else if((x.get(0)>=1 & x.get(0)<=8) & y.get(0)==0){//корабль вертикально слева
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(i)+1]==0 || playerField[x.get(0)-1][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)+1]==0 || playerField[x.get(0)+sizeShip][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)]==0 || playerField[x.get(0)+sizeShip][y.get(i)]==-1)){
                            condition = false;
                        }
                    }else if(x.get(sizeShip-1)==9 & y.get(0)==y.get(i)){//корабль вертикально снизу
                        if((playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)+1]==0 || playerField[x.get(0)-1][y.get(0)+1]==-1)){
                            condition = false;
                        }
                    }else if((x.get(0)>=1 & x.get(0)<=8) & y.get(0)==9){//корабль вертикально справа
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(0)-1]==0 || playerField[x.get(0)+sizeShip][y.get(0)-1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(0)]==0 || playerField[x.get(0)+sizeShip][y.get(0)]==-1)){
                            condition = false;
                        }
                    }else if(x.get(0)==0 & y.get(0)==y.get(i)){//корабль вертикально сверху
                        if((playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1 ) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)-1]==0 || playerField[x.get(0)+sizeShip][y.get(i)-1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)+1]==0 || playerField[x.get(0)+sizeShip][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(0)]==0 || playerField[x.get(0)+sizeShip][y.get(0)]==-1)){
                            condition = false;
                        }
                    }else{
                        throw new IllegalArgumentException();
                    }
                }
            }
            if (position < 0){
                for(int i = 0; i < sizeShip; i++){
                    if((x.get(0)==0 & y.get(0)==0) & (x.get(0)==x.get(i) & y.get(0)!=y.get(0)+1)){//верхний левый угол по горизонтали
                        if((playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(0)+1][y.get(0)+sizeShip]==0 || playerField[x.get(0)+1][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(0)][y.get(0)+sizeShip]==0 || playerField[x.get(0)][y.get(0)+sizeShip]==-1)){
                            condition = false;
                        }
                    }else if((1 <= x.get(0) & x.get(0) <= 8) & (y.get(0)==0 & y.get(0)!=y.get(i)+1)){//слева по горизонтали
                        if((playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)+1]==0 || playerField[x.get(i)-1][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)+1]==0 || playerField[x.get(i)+1][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1)){
                            condition = false;
                        }
                    }else if((x.get(0)==9 & x.get(i)==x.get(0)) & (y.get(0)==0 & y.get(0)!=y.get(0)+1)){//нижний левый угол по горизонтали
                        if((playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)+1]==0 || playerField[x.get(i)-1][y.get(i)+1]==-1)){
                            condition = false;
                        }
                    }else if((x.get(0)==9 & x.get(i)==9) & y.get(0)!=y.get(0)+1){//нижний по горизонтали
                        if((playerField[x.get(i)][y.get(0)-1]==0 || playerField[x.get(i)][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1) &&
                                (playerField[x.get(i)-1][y.get(0)-1]==0 || playerField[x.get(i)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(0)+sizeShip]==0 || playerField[x.get(i)-1][y.get(0)+sizeShip]==-1)){
                            condition = false;
                        }
                    }else if((x.get(0)==9 & x.get(i)==9) & y.get(0)+sizeShip-1==9){//нижний правый угол по горзонтальный
                        if((playerField[x.get(i)][y.get(0)-1]==0 || playerField[x.get(i)][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)-1]==0 || playerField[x.get(i)-1][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1)){
                            condition = false;
                        }
                    }else if((1 <= x.get(0) & x.get(0) <= 8) & (x.get(0)==x.get(i) & y.get(0)+sizeShip-1==9)){//справа по горизонтали
                        if((playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(i)+1][y.get(0)]==0 || playerField[x.get(i)+1][y.get(0)]==-1) &&
                                (playerField[x.get(i)][y.get(0)-1]==0 || playerField[x.get(i)][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)-1]==0 || playerField[x.get(i)-1][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)-1]==0 || playerField[x.get(i)+1][y.get(i)-1]==-1)){
                            condition = false;
                        }
                    }else if((x.get(0)==0 & x.get(i)==x.get(0)) & y.get(0)+sizeShip-1==9){//правый верхнй угол
                        if((playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)-1]==0 || playerField[x.get(i)+1][y.get(i)-1]==-1)){
                            condition = false;
                        }
                    }else if((x.get(0)==0 & x.get(i)==x.get(0)) & y.get(0)!=y.get(0)+1){//сверху по горизонтали
                        if((playerField[x.get(0)][y.get(0)-1]==0 || playerField[x.get(0)][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(0)+1][y.get(i)-1]==0 || playerField[x.get(0)+1][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(i)+1][y.get(0)+sizeShip]==0 || playerField[x.get(i)+1][y.get(0)+sizeShip]==-1)){
                            condition = false;
                        }
                    }
                }
            }
        }catch (IllegalArgumentException e){
            condition = true;
        }
        System.out.println(position);
        return condition;
    }
    public static void setupShips(String userInput, int sizeShip, int[][] playerField){
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        String[] ship = userInput.split(";");
        for(String elements: ship) {
            String[] xy = elements.split(",");
            x.add(Integer.parseInt(xy[0]));
            y.add(Integer.parseInt(xy[1]));
        }
        for(int i = 0; i < sizeShip; i++){
            if(playerField[x.get(i)][y.get(i)] == -1){
                playerField[x.get(i)][y.get(i)] = 1;
            }

        }
        for(int i = 0; i < 10; i++){///////////////////////////////вывод игрового поля в консоль
            for(int j = 0; j < 10; j++){
                if (playerField[i][j] == 1){
                    System.out.print("\uD83D\uDEE5 ");
                }else if (playerField[i][j] == 0){
                    System.out.print("\uD83D\uDFE6 ");
                }else if (playerField[i][j] == -2){
                    System.out.print("\uD83D\uDFE5 ");
                }else{
                    System.out.print("⬜ ");
                }
            }
            System.out.println();
        }
    }
}
