package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static String fileName = "src/main/resources/tasks.csv";
    static String[][] tasks;


    public static void main(String[] args) {

        loadTasksFromFile(fileName);
        displayMenu();


        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask();  // metoda utworzona, ale nie zapisuje do pliku
                    break;
/*               case "remove":
                    removeTask();  // metoda do zrobienia
                    break;
 */
                case "list":
                    listTask();  // metoda do sprawdzenia
                    System.out.println(ConsoleColors.GREEN + "File has been loaded successfully.");
                    break;
/*                case "exit" :
                    exitTask();  // metoda do zrobienia
                    System.out.println(ConsoleColors.PURPLE_BOLD + "Till the next time :)");
                    System.exit(0);
                    break;
 */
                default:
                    System.out.println("Please select an option from the following list.");
            }
        }


    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);

        // Pobieram opis zadania
        System.out.println(ConsoleColors.PURPLE_UNDERLINED + "Please add task description:" + ConsoleColors.RESET);
        String taskDescription = scanner.nextLine();

        // Pobieram datę
        System.out.println(ConsoleColors.PURPLE_UNDERLINED + "Please add task due date in format YYYY-MM-DD:" + ConsoleColors.RESET);
        String taskDate = scanner.nextLine();

        // Pobieram informację o ważności zadania
        System.out.println(ConsoleColors.PURPLE_UNDERLINED + "Please provide task importance - true or false:" + ConsoleColors.RESET);
        String taskImportance = scanner.nextLine();

        // Tworzę nową tablicę o jeden element większą i kopiuję dotychczasowe zadania
        String[][] newTasks = new String[tasks.length + 1][];
        for (int i = 0; i < tasks.length; i++) {
            newTasks[i] = tasks[i];

        }
        // Dodaję ostatnie zadanie jako ostatni element w tablicy
        newTasks[tasks.length] = new String[]{taskDescription, taskDate, taskImportance};

        tasks = newTasks;

        System.out.println();

        System.out.println(ConsoleColors.GREEN_BOLD + "Task has been added successfully" + ConsoleColors.RESET);

        scanner.close();

    }

    public static void listTask() {
        System.out.println(ConsoleColors.PURPLE_BOLD + "list" + ConsoleColors.RESET);

        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.println(tasks[i][j] + " ");
            }
            System.out.println();

        }
    }

    public static void loadTasksFromFile(String fileName) {

        try {
            // Tworzę nowy obiekt Path, który reprezentuje ścieżkę do pliku
            Path path = Paths.get(fileName);

            // Liczę ilość linii w pliku
            int lines = Files.readAllLines(path).size();

            tasks = new String[Integer.parseInt(String.valueOf(lines))][];

            // Wczytuję plik linia po linii
            List<String> fileLines = Files.readAllLines(path);

            for (int i = 0; i < fileLines.size(); i++) {

                // Dzielę linie na części po przecinku i zapisuję do tablicy
                String[] parts = fileLines.get(i).split(",");
                tasks[i] = parts;

            }

        } catch (IOException e) {
            System.out.println("Błąd podczas wczytywania pliku: " + e.getMessage());
            e.getStackTrace();

        }
    }

    public static void displayMenu() {

        // Definiuję tablicę opcji dostępnych w menu
        String[] options = {"add", "remove", "list", "exit"};

        // Dodaję tytuł programu
        System.out.println(ConsoleColors.GREEN_BOLD + "Welcome to TaskManager");

        System.out.println();

        // Wyświetlam nagłówek
        System.out.println(ConsoleColors.BLUE_BOLD + "Please select an options: ");

        // Iteruję po tablicę, dodając numerację dla lepszej czytelności
        for (int i = 0; i < options.length; i++) {
            System.out.println(ConsoleColors.BLACK + (i + 1) + ". " + options[i]);

        }
        System.out.println();
    }
}
