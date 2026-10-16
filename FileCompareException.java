/**
 * Исключение, выбрасываемое при ошибках сравнения файлов.
 * Проверяемое (checked), так как ошибка ввода-вывода ожидаема.
 */
public class FileCompareException extends Exception {

    public FileCompareException(String message) {
        super(message);
    }

    public FileCompareException(String message, Throwable cause) {
        super(message, cause);
    }
}
