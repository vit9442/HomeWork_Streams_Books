import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


/*
        Задание
        •	Создайте Map<Integer, String> authors. Ключ – id автора, значение – его имя.
        •	Прочитайте весь файл author.csv, записав каждую пару id-name в наш Map authors.
*/
        Map<Integer, String> authors = new HashMap<Integer, String>(); // коллекция авторов
        ArrayList<Book> books = new ArrayList<Book>(); // коллекция книг

/////////// Чтение Авторов из файла csv/author.csv/////////////////
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("csv/author.csv"))) {
            System.out.println("файл author.csv считан");
            bufferedReader.readLine();
            while (bufferedReader.ready()) {
                String str[] = bufferedReader.readLine().split(",");
                authors.put(Integer.parseInt(str[0]), str[1]);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

////////////////////////Чтение книг из файла csv/book.csv/////////////////////////////////
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("csv/book.csv"))) {
            bufferedReader.readLine();
            String s = new String();
            String title = new String();
            while ((s = bufferedReader.readLine()) != null) {
                Book book = new Book();
                // проверка, есть ли в названии книги кавычки
                if (s.contains("\"")) {
                    String[] str = s.split("[\"(*?)\"]");
                    title = str[1];
                    if (str.length > 3) {
                        for (int i = 2; i < str.length - 1; ++i)
                            title += str[i];
                        // System.out.println(title);
                    }
                    int i = 1;
                    String[] strAfterComma = str[2 + (str.length - 3)].split(",");
                    book.setId(Integer.parseInt(str[0].replaceAll(",", "")));
                    book.setTitle(title);
                    book.clearTitle();  // Метод убирает символы, недопустимые в названии файла
                    book.setPrice(Double.parseDouble(strAfterComma[i++]));
                    book.setAmount(Integer.parseInt(strAfterComma[i++]));
                    book.setImage_path(strAfterComma[i++]);
                    book.setAuthor_id(Integer.parseInt(strAfterComma[i++]));

                } else {
                    int i = 0;
                    String[] str = s.split(",");
                    book.setId(Integer.parseInt(str[i++]));
                    book.setTitle(str[i++]);
                    book.clearTitle();  // Метод убирает символы, недопустимые в названии файла
                    book.setPrice(Double.parseDouble(str[i++]));
                    book.setAmount(Integer.parseInt(str[i++]));
                    book.setImage_path(str[i++]);
                    book.setAuthor_id(Integer.parseInt(str[i++]));

                }
                books.add(book);// добавление книги в коллекцию
/////////////////////////////////////////

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
////////////////Запись обложек книг//////////////////////
        for (Book book : books) {
            try (FileInputStream fileInputStream = new FileInputStream("images/" + book.getImage_path())) { //считываем картинки
                byte[] b = fileInputStream.readAllBytes();

                //запись картинок в папку result
                try (FileOutputStream fileOutputStream = new FileOutputStream("result/" + authors.get(book.getAuthor_id()) + " - " + book.getTitle() + ".jpg")) {
                    fileOutputStream.write(b);

                } catch (IOException ex) {
                    System.out.println(ex);
                }

            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        System.out.println("Обложки книг записаны в папку result");
//////////////Дополнительное задание:////////////////////////////////////////////
        //Найдите самую дорогую книгу и самую дешёвую
        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;
        Book bookWithMaxPrice = new Book();
        Book bookWithMinPrice = new Book();

        for (Book book : books) {
            if (maxPrice < book.getPrice()) { // поиск самой дорогой
                maxPrice = book.getPrice();
                bookWithMaxPrice = book;
            }
            if (minPrice > book.getPrice()) { // поиск самой дешёвой
                minPrice = book.getPrice();
                bookWithMinPrice = book;
            }
        }
        // запись самой дорогой и дешёвой книги в файл result/additional task.txt
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result/additional task.txt"))) {

            bufferedWriter.write("Самая дорогая книга - \"" + authors.get(bookWithMaxPrice.getAuthor_id()) + " - " + bookWithMaxPrice.getTitle() + "\", её цена - " + bookWithMaxPrice.getPrice());
            bufferedWriter.write("\nСамая дешёвая книга - \"" + authors.get(bookWithMinPrice.getAuthor_id()) + " - " + bookWithMinPrice.getTitle() + "\", её цена - " + bookWithMinPrice.getPrice());
            System.out.println("Дополнительное задание, записать самую дорогую книгу и самую дешёвую.\nФайл записан в Result/additional task.txt");
        } catch (IOException ex) {
            System.out.println(ex);
        }


    }
}