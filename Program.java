import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Program {
    static Map<String, List<String>> map = new HashMap<>();
    static String[] array = new String[0];

    public static void main(String[] args) throws Exception {
        requestData();
        addData();
        System.out.println(map);
    }
    public static void requestData() throws Exception {
        System.out.println("Введите: Фамилию, Имя, Отчество; дату рождения (в формате dd.mm.yyyy); номер телефона (только цифры, без разделителей); пол(f или m) |Данные вводить латинскими буквами через пробел.");
        String text;
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            text = bf.readLine();
        } catch (IOException e){
            throw new IOException("Произошла ошибка");
        }

        array = text.split(" ");
        if (array.length != 6){
            throw new Exception("Заполните все параметры");
        }

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date birthdate;
        try {
            birthdate = format.parse(array[3]);
        }catch (ParseException e){
            throw new ParseException("Введите дату рождения в заданном формате", e.getErrorOffset());
        }
        long phone;
        try {
            phone = Long.parseLong(array[4]);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Введите корректный номер телефона");
        }

        String gender = array[5];
        if (!gender.equalsIgnoreCase("f") && !gender.equalsIgnoreCase("m")){
            throw new RuntimeException("Неверно введен пол");
        }

        String fileName = array[0] + ".txt";

        try(FileWriter fileWriter = new FileWriter(fileName)){
            for (String s : array) {
                fileWriter.write("<");
                fileWriter.write(s);
                fileWriter.write(">");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void addData(){
        String data = String.join(", ", array);
        ArrayList<String> list = new ArrayList<>();
        list.add(data);

        if (!map.containsKey(array[0])){
            map.put(array[0], list);
        } else {
            map.get(array[0]).add(data);
        }
    }
}