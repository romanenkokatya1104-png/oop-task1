import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Компаратор текстовых файлов.
 * Сравнивает два файла построчно или посимвольно.
 */
public class FileComparator {

    private final Path filePath1;
    private final Path filePath2;

    public FileComparator(Path filePath1, Path filePath2) {
        this.filePath1 = filePath1;
        this.filePath2 = filePath2;
    }

    /**
     * Выполняет сравнение файлов в указанном режиме.
     *
     * @param mode режим сравнения (построчный или посимвольный)
     * @throws FileCompareException если не удалось прочитать файлы
     */
    public void compare(ComparisonMode mode) throws FileCompareException {
        List<String> lines1;
        List<String> lines2;

        try {
            lines1 = Files.readAllLines(filePath1);
            lines2 = Files.readAllLines(filePath2);
        } catch (IOException e) {
            throw new FileCompareException(
                "Ошибка чтения файлов: " + e.getMessage(), e
            );
        }

        switch (mode) {
            case LINE_BY_LINE:
                compareLineByLine(lines1, lines2);
                break;
            case CHARACTER_BY_CHARACTER:
                compareByCharacter(lines1, lines2);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный режим: " + mode);
        }
    }

    private void compareLineByLine(List<String> lines1, List<String> lines2) {
        System.out.println("\n--- Результаты построчного сравнения ---");
        int maxLines = Math.max(lines1.size(), lines2.size());
        boolean differencesFound = false;

        for (int i = 0; i < maxLines; i++) {
            String line1 = (i < lines1.size()) ? lines1.get(i) : "<Нет строки>";
            String line2 = (i < lines2.size()) ? lines2.get(i) : "<Нет строки>";

            if (!line1.equals(line2)) {
                differencesFound = true;
                String display1 = truncate(line1, 40);
                String display2 = truncate(line2, 40);
                System.out.printf("Строка %3d | Файл 1: %-45s | Файл 2: %-45s%n",
                    (i + 1), display1, display2);
            }
        }

        if (!differencesFound) {
            System.out.println("Файлы идентичны построчно.");
        } else {
            System.out.println("\nВсего строк в Файле 1: " + lines1.size());
            System.out.println("Всего строк в Файле 2: " + lines2.size());
        }
    }

    private void compareByCharacter(List<String> lines1, List<String> lines2) {
        String text1 = String.join("\n", lines1);
        String text2 = String.join("\n", lines2);

        System.out.println("\n--- Результаты посимвольного сравнения ---");
        if (text1.equals(text2)) {
            System.out.println("Файлы полностью идентичны.");
        } else {
            System.out.println("Файлы различаются.");
            int minLen = Math.min(text1.length(), text2.length());
            for (int i = 0; i < minLen; i++) {
                if (text1.charAt(i) != text2.charAt(i)) {
                    System.out.printf(
                        "Первое расхождение на позиции %d: '%c' vs '%c'%n",
                        (i + 1), text1.charAt(i), text2.charAt(i)
                    );
                    break;
                }
            }
            if (text1.length() != text2.length()) {
                System.out.println("Файлы имеют разную длину.");
            }
        }
    }

    private String truncate(String s, int maxLength) {
        return s.length() > maxLength ? s.substring(0, maxLength) + "..." : s;
    }
}
