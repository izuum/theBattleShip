import java.util.ArrayList;
import java.util.List;

public class Util {
    public boolean countCoordinates(String userInput, int sizeShip){
        //проверка координат на соответствие количеству палуб корабля
        String[] coordinates = userInput.split(";");
        if(coordinates.length == sizeShip){
            return false;
        }else{
            System.out.println("Слишком мало координат, введите повторно(Формат x,y;x,y;x,y;x,y");
            return true;
        }
    }
    public boolean validCoordinates(String userInput){
        //проверка координат на корректность (целочисленность координаты и нахождение ее в нужном диапазоне)
        boolean condition = true;
        String[] coordinates = userInput.split(";");
        for(String elements: coordinates){
            String elem = elements;
            String[] xy = elem.split(",");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            if ((x%1==0 & y%1==0) & (0 <= x & x <= 9) & (0 <= y & y <= 9)){
                condition =  false;
            }else{
                condition = true;
            }
        }
        if (condition == false){
            return false;
        }else{
            System.out.println("Некорректные координаты.Введенные " +
                    "координаты должны быть целочисленными, в диапазоне от 0 до 9.");
            return true;
        }
    }
    public boolean validShip(String userInput, int sizeShip){
        //проверяет валидность корабля, по вертикали или по горизонтали
        boolean vertical = true;
        boolean gorizontal = true;
        if(sizeShip == 1){ // однопалубный корабль всегда валиден
            vertical = false;
            gorizontal = false;
        }
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        String[] ship = userInput.split(";");
        for(String elements: ship) {
            String elem = elements;
            String[] xy = elem.split(",");
            x.add(Integer.parseInt(xy[0]));
            y.add(Integer.parseInt(xy[1]));
        }
        for (int i = 0; i < sizeShip; i++){ //проверяются коорднаты как в одну сторону так и в другую
            if (((x.get(0) == x.get(i)) & ((y.get(0)+i) == y.get(i))) || ((x.get(0) == x.get(i)) & ((y.get(0)-i) == y.get(i)))){
                gorizontal = false;
            }
            if((x.get(0)+i == x.get(i)) & (y.get(0) == y.get(i)) || (x.get(0)-i == x.get(i)) & (y.get(0) == y.get(i))){
                vertical = false;
            }
        }
        if(vertical == false || gorizontal == false){
            return false;
        }else{
            System.out.println("Неверные координаты для корабля. Корбль должен быть ровным!");
            return true;
        }
    }
    public boolean freeCoordinatesOnField(String userInput, int[][] playerField){
        //проверка на свободность выбранных координат
        boolean condition = true;
        String[] coordinates = userInput.split(";");
        for(String elements: coordinates) {
            String elem = elements;
            String[] xy = elem.split(",");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            if(playerField[x][y] == -1){
                condition = false;
            }else{
                condition = true;
            }
        }
        if(condition == false){
            return false;
        }else{
            System.out.println("Эти координаты заняты, введите другие координаты.");
            return true;
        }
    }
    public boolean oreolOfShip(String userInput, int sizeShip, int[][] playerField){
        //проверяет состояние орелоа вокруг корабля, соседняя клетка либо свободна (-1), либо в клетке уже есть ореол(0),
        //при нахождении в клетке корабля (1), установка коробля в эту клетку невозможна
        boolean condition = true;
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        String[] ship = userInput.split(";");
        for(String elements: ship) {
            String elem = elements;
            String[] xy = elem.split(",");
            x.add(Integer.parseInt(xy[0]));
            y.add(Integer.parseInt(xy[1]));
        }
        for(int i = 0; i < sizeShip; i++){
            if( (playerField[x.get(i)-1][y.get(i)-1] == 0 | playerField[x.get(i)-1][y.get(i)-1] == -1)
                    & (playerField[x.get(i)][y.get(i)-1] == 0 | playerField[x.get(i)][y.get(i)-1] == -1)
                    & (playerField[x.get(i)+1][y.get(i)-1] == 0 | playerField[x.get(i)+1][y.get(i)-1] == -1)
                    & (playerField[x.get(i)+1][y.get(i)] == 0 | playerField[x.get(i)+1][y.get(i)] == -1)
                    & (playerField[x.get(i)+1][y.get(i)+1] == 0 | playerField[x.get(i)+1][y.get(i)+1] == -1)
                    & (playerField[x.get(i)][y.get(i)+1] == 0 | playerField[x.get(i)][y.get(i)+1] == -1)
                    & (playerField[x.get(i)-1][y.get(i)+1] == 0 | playerField[x.get(i)-1][y.get(i)+1] == -1)
                    & (playerField[x.get(i)-1][y.get(i)] == 0 | playerField[x.get(i)-1][y.get(i)] == -1)){
                condition = false;
            }else{
                condition = true;
            }
        }
        if (condition == false){
            return false;
        }else{
            System.out.println("Слишком близко к другому короблю. Введите другие координаты");
            return true;
        }

    }
}
