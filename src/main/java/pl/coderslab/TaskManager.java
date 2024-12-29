package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static String fileName = "src/main/resources/tasks.csv";
    static String[][] tasks;


    public static void main(String[] args) {

        System.out.println(ConsoleColors.FOREST_GREEN_BOLD + "Welcome to TaskManager" + ConsoleColors.RESET);

        System.out.println();

        loadTasksFromFile(fileName);
        displayMenu();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask();
                    System.out.println(ConsoleColors.GREEN + "New task has been successfully added." + ConsoleColors.RESET);
                    System.out.println();
                    break;
                case "remove":
                    boolean validRemove = false;
                    while (!validRemove) {

                        System.out.println(ConsoleColors.PURPLE + "Please select task number to remove:" + ConsoleColors.RESET);
                        try {
                            int taskNumber = Integer.parseInt(scanner.nextLine());
                            if (taskNumber <= 0) {
                                System.out.println(ConsoleColors.RED + "List element can not be zero or negative!" + ConsoleColors.RESET);
                                continue;
                            }
                            if (taskNumber > tasks.length) {
                                System.out.println(ConsoleColors.RED + "Attempting to access a non-existent list element." + ConsoleColors.RESET);
                                continue;
                            }
                            removeTask(tasks, tasks.length - 1);
                            validRemove = true;
                        } catch (NumberFormatException e) {
                            System.out.println(ConsoleColors.RED + "Incorrect argument was selected." + ConsoleColors.RESET);
                        }
                    }
                    break;
                case "list":
                    listTask();
                    System.out.println(ConsoleColors.GREEN + "File has been successfully loaded.");
                    System.out.println();
                    break;
                case "exit":
                    saveTaskToFile();
                    System.out.println();
                    System.out.println(ConsoleColors.PURPLE_BOLD + "Till the next time :)" + ConsoleColors.RESET);
                    System.exit(0);
                    break;

                default:
                    System.out.println(ConsoleColors.RED +
                            "Please select an option from the following list. The option you selected '" + input + "' does not exist!" + ConsoleColors.RESET);
            }
            displayMenu();
        }

    }

    public static void saveTaskToFile() {

        // Tworzę nowy obiekt Path, który reprezentuję ścieżkę do pliku
        Path path = Paths.get(fileName);

        // Tworzę nową listę, która będzie przechowywać linie do zapisu
        ArrayList<String> linesToWrite = new ArrayList<>();

        // Przechodzę po każdym zadaniu w tablicy i zapisuję do listy używając separatora
        for (String[] task : tasks) {
            String taskLine = String.join(", ", task);
            linesToWrite.add(taskLine);
        }

        try {
            // Zapisuję wszystkie linie do pliku
            Files.write(path, linesToWrite);

            System.out.println(ConsoleColors.GREEN + "Your task list has been successfully updated." + ConsoleColors.RESET);

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error while saving the file.\n" + "Technical details: " + ConsoleColors.RESET + e.getStackTrace());
        }

    }

    public static void removeTask(String[][] tabel, int number) {

        tasks = ArrayUtils.remove(tabel, number);
        System.out.println(ConsoleColors.GREEN + "Task number has been successfully removed." + ConsoleColors.RESET);

    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);

        // Pobieram opis zadania
        System.out.println(ConsoleColors.PURPLE + "Please add task description:" + ConsoleColors.RESET);
        String taskDescription = scanner.nextLine();

        // Pobieram datę
        System.out.println(ConsoleColors.PURPLE + "Please add task due date in format YYYY-MM-DD:" + ConsoleColors.RESET);
        String taskDate = scanner.nextLine();

        // Pobieram informację o ważności zadania
        System.out.println(ConsoleColors.PURPLE + "Please provide task importance - true or false:" + ConsoleColors.RESET);
        String taskImportance = scanner.nextLine();

        // Tworzę nową tablicę o jeden element większą i kopiuję dotychczasowe zadania
        String[][] newTasks = new String[tasks.length + 1][];
        for (int i = 0; i < tasks.length; i++) {
            newTasks[i] = tasks[i];

        }
        // Dodaję ostatnie zadanie jako ostatni element w tablicy
        newTasks[tasks.length] = new String[]{taskDescription, taskDate, taskImportance};
        tasks = newTasks;

    }

    public static void listTask() {
        System.out.println(ConsoleColors.PURPLE + "List of your tasks:" + ConsoleColors.RESET);

        for (int i = 0; i < tasks.length; i++) {
            System.out.print((i + 1) + ": ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
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
            System.out.println(ConsoleColors.RED + "Error while loading the file.\n" +
                    "Technical details: " + ConsoleColors.RESET + e.getMessage());
            e.getStackTrace();

        }
    }

    public static void displayMenu() {

        // Definiuję tablicę opcji dostępnych w menu
        String[] options = {"add", "remove", "list", "exit"};

        // Wyświetlam nagłówek
        System.out.println(ConsoleColors.OCEAN_BLUE_BOLD + "Please select an options: ");

        // Iteruję po tablicę, dodając numerację dla lepszej czytelności
        for (int i = 0; i < options.length; i++) {
            System.out.println(ConsoleColors.BLACK + (i + 1) + ". " + options[i]);

        }
        System.out.println();
    }

}