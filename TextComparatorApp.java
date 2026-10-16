import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Консольное приложение для сравнения текстовых файлов.
 */
public class TextComparatorApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printHeader();

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            if (choice.equals("2")) {
                System.out.println("Выход из программы. До свидания!");
                break;
            }

            if (!choice.equals("1")) {
                System.out.println("Неверный выбор. Попробуйте снова.");
                continue;
            }

            System.out.print("Введите путь к первому файлу: ");
            String path1Str = scanner.nextLine().trim();
            System.out.print("Введите путь ко второму файлу: ");
            String path2Str = scanner.nextLine().trim();

            Path path1 = Path.of(path1Str);
            Path path2 = Path.of(path2Str);

            if (!Files.exists(path1) || !Files.exists(path2)) {
                System.out.println("\n[Ошибка]: Один или оба файла не существуют.");
                continue;
            }

            System.out.println("\nВыберите режим сравнения:");
            System.out.println("1. Построчное сравнение");
            System.out.println("2. Посимвольное сравнение");
            System.out.print("Ваш выбор (1/2): ");
            String modeStr = scanner.nextLine().trim();

            ComparisonMode mode = modeStr.equals("1")
                ? ComparisonMode.LINE_BY_LINE
                : ComparisonMode.CHARACTER_BY_CHARACTER;

            FileComparator comparator = new FileComparator(path1, path2);

            try {
                comparator.compare(mode);
            } catch (FileCompareException e) {
                System.out.println("\n[Ошибка]: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printHeader() {
        System.out.println("   КОМПАРАТОР ТЕКСТОВЫХ ФАЙЛОВ (Java)");
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Сравнить два файла");
        System.out.println("2. Выход");
        System.out.print("Выберите действие (1/2): ");
    }
}
