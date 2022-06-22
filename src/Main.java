import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        String str1 = ("Привет");
        String str2 = ("Меня зовут Юрий");
        String str3 = ("Я живу в Москве");
        String str4 = ("i love Java");

        LinkedList<String> bio = new LinkedList<>();
        bio.add(str1);
        bio.add(str2);
        bio.add(str3);
        bio.add(str4);

        bio.remove(2);

        System.out.println(bio);



        ArrayList<String> strings = new ArrayList<>();
        strings.add("Привет");
        strings.add("меня");
        strings.add("зовут");
        strings.add("массив");

        strings.remove(0);

        System.out.println(strings);


    }
}