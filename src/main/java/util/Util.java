package util;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static boolean countCoordinates(String userInput, int sizeShip) {
        //проверка координат на соответствие количеству палуб корабля
        boolean condition = true;
        String[] coordinates = userInput.split(";");
        if(coordinates.length == sizeShip) {
            for (String elements : coordinates) {
                String[] xy = elements.split(",");
                if (xy.length == 2) {
                    condition = false;
                } else {
                    condition = true;
                }
            }
        }
        return condition;
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
    public static boolean oreolOfShip(String userInput, int sizeShip, int[][] playerField) throws InterruptedException {
        //проверяет состояние орелоа вокруг корабля, соседняя клетка либо свободна (-1), либо в клетке уже есть ореол(0),
        //при нахождении в клетке корабля (1), установка корабля в эту клетку невозможна. Также проверяет ореол, если корабль на краю карты.
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
            for (int i = 0; i < sizeShip; i++) {
                if (((x.get(0) == x.get(i)) && (y.get(0) + i == y.get(i)))/* || ((x.get(0) == x.get(i)) && ((y.get(0) - i) == y.get(i)))*/) {///проверка кораля в обратную сторону(справа налево)
                    position=10;//горизонтальный
                } else if (((x.get(0) + i == x.get(i)) && (y.get(0) == y.get(i)))/* || ((x.get(0) - i == x.get(i)) && ((y.get(0) == y.get(i))))*/) {///проверка кораля в обратную сторону(справа налево)
                    position=-10;//вертикальный
                }else{
                    position=1;
                }
            }
            if( position == -10){
                for( int i = 0; i < sizeShip; i++){
                    if((x.get(0) == 0 & y.get(0) == 0) & (y.get(0) == y.get(i) & x.get(0) != x.get(0)+1)){//корабль вертикальный в левом верхнем углу
                        if((playerField[x.get(0)+sizeShip][y.get(i)]==0 || playerField[x.get(0)+sizeShip][y.get(i)]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)+1]==0 || playerField[x.get(0)+sizeShip][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(sizeShip-1) == 9) && (y.get(i) == 0)){//корабль вертикальный в нижнем левом углу
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(i)+1]==0 || playerField[x.get(0)-1][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1)) {
                            condition = false;
                            break;
                        }
                    }else if(x.get(sizeShip-1)==9 && y.get(i)==9){//корабль вертикальный в нижнем правом углу
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(0)==0 & y.get(0)==9) & (y.get(0)==y.get(i) & x.get(0)!=x.get(0)+1)) {//корабль вертикальный в правом верхнем углу
                        if((playerField[x.get(0)+sizeShip][y.get(i)]==0 || playerField[x.get(0)+sizeShip][y.get(i)]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)-1]==0 || playerField[x.get(0)+sizeShip][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(0)>=1 & x.get(0)<=8) & y.get(0)==0){//корабль вертикальный слева
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(i)+1]==0 || playerField[x.get(0)-1][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)+1]==0 || playerField[x.get(0)+sizeShip][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)]==0 || playerField[x.get(0)+sizeShip][y.get(i)]==-1)){
                            condition = false;
                            break;
                        }
                    }else if(x.get(sizeShip-1)==9 & y.get(0)==y.get(i)){//корабль вертикальный снизу
                        if((playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)+1]==0 || playerField[x.get(0)-1][y.get(0)+1]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(0)>=1 & x.get(0)<=8) & y.get(i)==9){//корабль вертикальный справа
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(0)-1]==0 || playerField[x.get(0)+sizeShip][y.get(0)-1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(0)]==0 || playerField[x.get(0)+sizeShip][y.get(0)]==-1)){
                            condition = false;
                            break;
                        }
                    }else if(x.get(0)==0 & y.get(0)==y.get(i)){//корабль вертикальный сверху
                        if((playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1 ) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)-1]==0 || playerField[x.get(0)+sizeShip][y.get(i)-1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)+1]==0 || playerField[x.get(0)+sizeShip][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(0)]==0 || playerField[x.get(0)+sizeShip][y.get(0)]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((1 <= x.get(0) & x.get(0) <=8) & (x.get(0)!=x.get(0)+1 & y.get(0)==y.get(i))){//корабль вертикальный в любом другом месте поля
                        if((playerField[x.get(0)-1][y.get(i)]==0 || playerField[x.get(0)-1][y.get(i)]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)+1]==0 || playerField[x.get(0)-1][y.get(0)+1]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)][y.get(i)+1]==0 || playerField[x.get(i)][y.get(i)+1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)]==0 || playerField[x.get(0)+sizeShip][y.get(i)]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)-1]==0 || playerField[x.get(0)+sizeShip][y.get(i)-1]==-1) &&
                                (playerField[x.get(0)+sizeShip][y.get(i)+1]==0 || playerField[x.get(0)+sizeShip][y.get(i)+1]==-1)) {
                            condition = false;
                            break;
                        }
                    }else{
                        throw new IllegalArgumentException();
                    }
                }
            }else if (position == 10){
                for(int i = 0; i < sizeShip; i++){
                    if((x.get(0)==0 & y.get(0)==0) & (x.get(0)==x.get(i) & y.get(0)!=y.get(0)+1)){//корабль горизонтальный в верхнем левом углу
                        if((playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(0)+1][y.get(0)+sizeShip]==0 || playerField[x.get(0)+1][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(0)][y.get(0)+sizeShip]==0 || playerField[x.get(0)][y.get(0)+sizeShip]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(0)==9 & x.get(0)==x.get(i)) & (y.get(0)==0 & y.get(0)!=y.get(0)+1)){//корабль горизонтальный в нижнем левом углу
                        if((playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)+1]==0 || playerField[x.get(i)-1][y.get(i)+1]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(0)==9 & x.get(i)==9) & y.get(0)+sizeShip-1==9){//корабль горизонтальный в нижнем правом углу
                        if((playerField[x.get(i)][y.get(0)-1]==0 || playerField[x.get(i)][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)-1]==0 || playerField[x.get(i)-1][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(0)==0 & x.get(0)==x.get(i)) & y.get(0)+sizeShip-1==9){//корабль горизонтальный в правом верхнем углу
                        if((playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(i)][y.get(i)-1]==0 || playerField[x.get(i)][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)-1]==0 || playerField[x.get(i)+1][y.get(i)-1]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((1 <= x.get(0) & x.get(0) <= 8) & (y.get(0)==0 & y.get(0)!=y.get(i)+1)){//корабль горизонтальный слева
                        if((playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)+1]==0 || playerField[x.get(i)-1][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)+1]==0 || playerField[x.get(i)+1][y.get(i)+1]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(0)==9 & x.get(i)==9) & y.get(0)!=y.get(0)+1){//корабль горизонтальный в снизу
                        if((playerField[x.get(i)][y.get(0)-1]==0 || playerField[x.get(i)][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1) &&
                                (playerField[x.get(i)-1][y.get(0)-1]==0 || playerField[x.get(i)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(0)+sizeShip]==0 || playerField[x.get(i)-1][y.get(0)+sizeShip]==-1)){
                            condition = false;
                            break;
                        }

                    }else if((1 <= x.get(0) & x.get(0) <= 8) & (x.get(0)==x.get(i) & (y.get(0)+sizeShip-1==9))){//корабль горизонтальный справа
                        if((playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(i)+1][y.get(0)]==0 || playerField[x.get(i)+1][y.get(0)]==-1) &&
                                (playerField[x.get(i)][y.get(0)-1]==0 || playerField[x.get(i)][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)-1]==0 || playerField[x.get(i)-1][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)-1]==0 || playerField[x.get(i)+1][y.get(i)-1]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((x.get(0)==0 & x.get(0)==x.get(i)) & y.get(0)!=y.get(0)+1){//корабль горизонтальный сверху
                        if((playerField[x.get(0)][y.get(0)-1]==0 || playerField[x.get(0)][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)+1][y.get(i)]==0 || playerField[x.get(i)+1][y.get(i)]==-1) &&
                                (playerField[x.get(0)+1][y.get(i)-1]==0 || playerField[x.get(0)+1][y.get(i)-1]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(i)+1][y.get(0)+sizeShip]==0 || playerField[x.get(i)+1][y.get(0)+sizeShip]==-1)){
                            condition = false;
                            break;
                        }
                    }else if((1 <= x.get(0) & x.get(0) <= 8) && (x.get(0)==x.get(i) & y.get(0)!=y.get(0)+1)){//корабль горизонтальный в любом другом месте поля
                        if((playerField[x.get(0)][y.get(0)-1]==0 || playerField[x.get(0)][y.get(0)-1]==-1) &&
                                (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                                (playerField[x.get(0)+1][y.get(0)-1]==0 || playerField[x.get(0)+1][y.get(0)-1]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1) &&
                                (playerField[x.get(i)-1][y.get(i)]==0 || playerField[x.get(i)-1][y.get(i)]==-1) &&
                                (playerField[x.get(i)][y.get(0)+sizeShip]==0 || playerField[x.get(i)][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(i)-1][y.get(0)+sizeShip]==0 || playerField[x.get(i)-1][y.get(0)+sizeShip]==-1) &&
                                (playerField[x.get(i)+1][y.get(0)+sizeShip]==0 || playerField[x.get(i)+1][y.get(0)+sizeShip]==-1)){
                            condition = false;
                            break;
                        }
                    }else{
                        throw new IllegalArgumentException();
                    }
                }
            }else if(position==1){//однопалубный корабль
                if((playerField[x.get(0)-1][y.get(0)]==0 || playerField[x.get(0)-1][y.get(0)]==-1) &&
                        (playerField[x.get(0)-1][y.get(0)+1]==0 || playerField[x.get(0)-1][y.get(0)+1]==-1) &&
                        (playerField[x.get(0)-1][y.get(0)-1]==0 || playerField[x.get(0)-1][y.get(0)-1]==-1) &&
                        (playerField[x.get(0)][y.get(0)-1]==0 || playerField[x.get(0)][y.get(0)-1]==-1) &&
                        (playerField[x.get(0)][y.get(0)+1]==0 || playerField[x.get(0)][y.get(0)+1]==-1) &&
                        (playerField[x.get(0)+1][y.get(0)]==0 || playerField[x.get(0)+1][y.get(0)]==-1) &&
                        (playerField[x.get(0)+1][y.get(0)-1]==0 || playerField[x.get(0)+1][y.get(0)-1]==-1) &&
                        (playerField[x.get(0)+1][y.get(0)+1]==0 || playerField[x.get(0)+1][y.get(0)+1]==-1)){
                    condition = false;
                }else{
                    throw new IllegalArgumentException();
                }
            }
        }catch (IllegalArgumentException e){
            Thread.sleep(2000);
            System.out.println("Слишком близко к другому короблю. Введите другие координаты");
        }
        return condition;
    }
    public static void setupShips(String userInput, int sizeShip, int[][] playerField){
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        int position = 0;
        String[] ship = userInput.split(";");
        for(String elements: ship) {
            String[] xy = elements.split(",");
            x.add(Integer.parseInt(xy[0]));
            y.add(Integer.parseInt(xy[1]));
        }
        for(int i = 0; i < sizeShip; i++){
            playerField[x.get(i)][y.get(i)] = 1;
            }
        for (int i = 0; i < sizeShip; i++) {
            if (((x.get(0) == x.get(i)) && (y.get(0) + i == y.get(i)))) {
                position=10;//горизонтальный
            } else if (((x.get(0) + i == x.get(i)) && (y.get(0) == y.get(i)))) {
                position=-10;//вертикальный
            }else{
                position=1;
            }
        }
        if( position == -10){
            for( int i = 0; i < sizeShip; i++) {
                if ((x.get(0) == 0 & y.get(0) == 0) & (y.get(0) == y.get(i) & x.get(0) != x.get(0) + 1)) {//корабль вертикальный в левом верхнем углу
                    playerField[x.get(0) + sizeShip][y.get(i)] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i) + 1] = 0;
                    playerField[x.get(i)][y.get(i) + 1] = 0;
                } else if ((x.get(sizeShip - 1) == 9) && (y.get(i) == 0)) {//корабль вертикальный в нижнем левом углу
                    playerField[x.get(0) - 1][y.get(i)] = 0;
                    playerField[x.get(0) - 1][y.get(i) + 1] = 0;
                    playerField[x.get(i)][y.get(i)+1] = 0;
                } else if (x.get(sizeShip - 1) == 9 && y.get(i) == 9) {//корабль вертикальный в нижнем правом углу
                    playerField[x.get(0) - 1][y.get(i)] = 0;
                    playerField[x.get(0) - 1][y.get(0) - 1] = 0;
                    playerField[x.get(i)][y.get(i) - 1] = 0;
                } else if ((x.get(0) == 0 & y.get(0) == 9) & (y.get(0) == y.get(i) & x.get(0) != x.get(0) + 1)) {//корабль вертикальный в правом верхнем углу
                    playerField[x.get(0) + sizeShip][y.get(i)] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i) - 1] = 0;
                    playerField[x.get(i)][y.get(i) - 1] = 0;
                } else if ((x.get(0) >= 1 & x.get(0) <= 8) & y.get(0) == 0) {//корабль вертикальный слева
                    playerField[x.get(0) - 1][y.get(i)] = 0;
                    playerField[x.get(0) - 1][y.get(i) + 1] = 0;
                    playerField[x.get(i)][y.get(i) + 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i) + 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i)] = 0;
                } else if (x.get(sizeShip - 1) == 9 & y.get(0) == y.get(i)) {//корабль вертикальный снизу
                    playerField[x.get(i)][y.get(i) - 1] = 0;
                    playerField[x.get(i)][y.get(i) + 1] = 0;
                    playerField[x.get(0) - 1][y.get(0) - 1] = 0;
                    playerField[x.get(0) - 1][y.get(i)] = 0;
                    playerField[x.get(0) - 1][y.get(0) + 1] = 0;
                } else if ((x.get(0) >= 1 & x.get(0) <= 8) & y.get(i) == 9) {//корабль вертикальный справа
                    playerField[x.get(0) - 1][y.get(i)] = 0;
                    playerField[x.get(0) - 1][y.get(0) - 1] = 0;
                    playerField[x.get(i)][y.get(i) - 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(0) - 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(0)] = 0;
                } else if (x.get(0) == 0 & y.get(0) == y.get(i)) {//корабль вертикальный сверху
                    playerField[x.get(i)][y.get(i) - 1] = 0;
                    playerField[x.get(i)][y.get(i) + 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i) - 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i) + 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(0)] = 0;
                } else if ((1 <= x.get(0) & x.get(0) <= 8) & (x.get(0) != x.get(0) + 1 & y.get(0) == y.get(i))) {//корабль вертикальный в любом другом месте поля
                    playerField[x.get(0) - 1][y.get(i)] = 0;
                    playerField[x.get(0) - 1][y.get(0) - 1] = 0;
                    playerField[x.get(0) - 1][y.get(0) + 1] = 0;
                    playerField[x.get(i)][y.get(i) - 1] = 0;
                    playerField[x.get(i)][y.get(i) + 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i)] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i) - 1] = 0;
                    playerField[x.get(0) + sizeShip][y.get(i) + 1] = 0;
                }
            }
        }else if (position == 10){
            for(int i = 0; i < sizeShip; i++){
                if((x.get(0)==0 & y.get(0)==0) & (x.get(0)==x.get(i) & y.get(0)!=y.get(0)+1)){//корабль горизонтальный в верхнем левом углу
                    playerField[x.get(i)+1][y.get(i)] = 0;
                    playerField[x.get(0)+1][y.get(0)+sizeShip] = 0;
                    playerField[x.get(0)][y.get(0)+sizeShip] = 0;
                }else if((x.get(0)==9 & x.get(0)==x.get(i)) & (y.get(0)==0 & y.get(0)!=y.get(0)+1)){//корабль горизонтальный в нижнем левом углу
                    playerField[x.get(i)-1][y.get(i)] = 0;
                    playerField[x.get(i)][y.get(0)+sizeShip] = 0;
                    playerField[x.get(i)-1][y.get(i)+1] = 0;
                }else if((x.get(0)==9 & x.get(i)==9) & y.get(0)+sizeShip-1==9){//корабль горизонтальный в нижнем правом углу
                    playerField[x.get(i)][y.get(0)-1] = 0;
                    playerField[x.get(i)-1][y.get(i)-1] = 0;
                    playerField[x.get(i)-1][y.get(i)] = 0;
                }else if((x.get(0)==0 & x.get(i)==0) & y.get(0)+sizeShip-1==9){//корабль горизонтальный в правом верхнем углу
                    playerField[x.get(i)+1][y.get(i)] = 0;
                    playerField[x.get(i)][y.get(0)-1] = 0;
                    playerField[x.get(i)+1][y.get(i)-1] = 0;
                }else if((1 <= x.get(0) & x.get(0) <= 8) & (y.get(0)==0 & y.get(0)!=y.get(i)+1)){//корабль горизонтальный слева
                    playerField[x.get(i)-1][y.get(i)] = 0;
                    playerField[x.get(i)+1][y.get(i)] = 0;
                    playerField[x.get(i)-1][y.get(i)+1] = 0;
                    playerField[x.get(i)+1][y.get(i)+1] = 0;
                    playerField[x.get(i)][y.get(0)+sizeShip] = 0;
                }else if((x.get(0)==9 & x.get(i)==9) & y.get(0)!=y.get(0)+1){//корабль горизонтальный в снизу
                    playerField[x.get(i)][y.get(0)-1] = 0;
                    playerField[x.get(i)][y.get(0)+sizeShip] = 0;
                    playerField[x.get(i)-1][y.get(i)] = 0;
                    playerField[x.get(i)-1][y.get(0)-1] = 0;
                    playerField[x.get(i)-1][y.get(0)+sizeShip] = 0;
                }else if((1 <= x.get(0) & x.get(0) <= 8) & (x.get(0)==x.get(i) & (y.get(0)+sizeShip-1==9))){//корабль горизонтальный справа
                    playerField[x.get(i)+1][y.get(i)] = 0;
                    playerField[x.get(i)-1][y.get(i)] = 0;
                    playerField[x.get(0)][y.get(0)-1] = 0;
                    playerField[x.get(0)-1][y.get(0)-1] = 0;
                    playerField[x.get(0)+1][y.get(0)-1] = 0;
                }else if((x.get(0)==0 & x.get(0)==x.get(i)) & y.get(0)!=y.get(0)+1){//корабль горизонтальный сверху
                    playerField[x.get(0)][y.get(0)-1] = 0;
                    playerField[x.get(i)+1][y.get(i)] = 0;
                    playerField[x.get(0)+1][y.get(i)-1] = 0;
                    playerField[x.get(i)][y.get(0)+sizeShip] = 0;
                    playerField[x.get(i)+1][y.get(0)+sizeShip] = 0;
                }else if((1 <= x.get(0) & x.get(0) <= 8) && (x.get(0)==x.get(i) & y.get(0)!=y.get(0)+1)){//корабль горизонтальный в любом другом месте поля
                    playerField[x.get(0)][y.get(0)-1] = 0;
                    playerField[x.get(0)-1][y.get(0)-1] = 0;
                    playerField[x.get(0)+1][y.get(0)-1] = 0;
                    playerField[x.get(i)-1][y.get(i)] = 0;
                    playerField[x.get(i)+1][y.get(i)] = 0;
                    playerField[x.get(i)][y.get(0)+sizeShip] = 0;
                    playerField[x.get(i)-1][y.get(0)+sizeShip] = 0;
                    playerField[x.get(i)+1][y.get(0)+sizeShip] = 0;
                }
            }
        }else if(position==1){//однопалубный корабль
            playerField[x.get(0)-1][y.get(0)] = 0;
            playerField[x.get(0)-1][y.get(0)+1] = 0;
            playerField[x.get(0)-1][y.get(0)-1] = 0;
            playerField[x.get(0)][y.get(0)-1] = 0;
            playerField[x.get(0)][y.get(0)+1] = 0;
            playerField[x.get(0)+1][y.get(0)] = 0;
            playerField[x.get(0)+1][y.get(0)-1] = 0;
            playerField[x.get(0)+1][y.get(0)+1] = 0;
            }
        for(int i = 0; i < 10; i++){///////////////////////////////вывод игрового поля в консоль
            for(int j = 0; j < 10; j++){
                if (playerField[i][j] == 1){
                    System.out.printf("\uD83D\uDEF3\uFE0F");
                }else if (playerField[i][j] == 0){
                    System.out.printf("\uD83D\uDFE6");
                }else if (playerField[i][j] == -2){
                    System.out.printf("\uD83D\uDFE5");
                }else{
                    System.out.printf("\uD83D\uDFEB");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void printCompletePlayerField(int[][] playerField) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Твое поле готово!");
        for(int i = 0; i < 10; i++){///////////////////////////////вывод игрового поля в консоль
            for(int j = 0; j < 10; j++){
                if (playerField[i][j] == 1){
                    System.out.printf("\uD83D\uDEF3\uFE0F");
                }else if (playerField[i][j] == 0){
                    System.out.printf("\uD83D\uDFE6");
                }else if (playerField[i][j] == -2){
                    System.out.printf("\uD83D\uDFE5");
                }else{
                    System.out.printf("\uD83D\uDFEB");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
