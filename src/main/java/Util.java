public class Util {
    public boolean countCoordinates(String userInput, int sizeShip){
        String[] coordinates = userInput.split(";");
        if(coordinates.length == sizeShip){
            return false;
        }else{
            System.out.println("Слишком мало координат, введите повторно(Формат x,y;x,y;x,y;x,y");
            return true;
        }
    }
    public boolean validCoordinates(String userInput){
        boolean condition;
        String[] valid = userInput.split(";");
        for(String elements: valid){
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
        if (condition = false){
            return false;
        }else{
            System.out.println("Некорректные координаты.Введенные " +
                    "координаты должны быть целочисленными, в диапазоне от 0 до 9.");
            return true;
        }
    }
}
