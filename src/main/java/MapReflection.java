import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Класс для работы с объектом интерфейса Map.
 */
public class MapReflection {
    /**
     * Фнукция для очистки и выведения значения полей объекта интерфейса Map.
     * Для списка fieldsToCleanup удаляем ключи из мапы. Для fieldsToOutput выводим значения мапы в консоль.
     * @param object объект интерфеса Map
     * @param fieldsToCleanup список ключей, которые необходимо удалить из мапы
     * @param fieldsToOutput список ключей, значения которых необходимо вывести
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException если у мапы нет полей, указанных в fieldsToCleanup/fieldsToOutput
     */
    public static void cleanupMap(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!mapFieldsExist(object, fieldsToCleanup) ||
            !mapFieldsExist(object, fieldsToOutput)) {
            throw new IllegalArgumentException();
        }

        // Удаляем элементы мапы. При помощи рефлексии вызываем метод "remove" интерфейса Map.
        Class<?> aClass = object.getClass();
        Method methodRemove = aClass.getDeclaredMethod("remove", Object.class);
        methodRemove.setAccessible(true);
        for (String field : fieldsToCleanup) {
             methodRemove.invoke(object, field);
        }

        // Выводим элементы мапы. При помощи рефлексии вызываем метод "get" интерфейса Map.
        Method methodGet = aClass.getDeclaredMethod("get", Object.class);
        methodGet.setAccessible(true);
        for (String field : fieldsToOutput) {
            System.out.println(methodGet.invoke(object, field));
        }
    }

    /**
     * Функция, проверяющая, содержит ли мапа ключи, перечисленные в fieldsToCleanup/fieldsToOutput.
     * @param object объект интерфейса Map
     * @param fields список ключей
     * @return true если у мапы есть перечисленные ключи, false если у мапы нет какого-то из перечисленных ключей
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static boolean mapFieldsExist(Object object, Set<String> fields) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        boolean result = true;
        Class<?> aClass = object.getClass();
        Method methodContainsKey = aClass.getDeclaredMethod("containsKey", Object.class);
        methodContainsKey.setAccessible(true);

        for (String field : fields) {
            result = (boolean) methodContainsKey.invoke(object, field);
            if (!result) {
                break;
            }
        }

        return result;
    }

    /**
     * Вывод мапы в консоль.
     * @param testMap мапа
     */
    public static void printAllMapFields(Map<String, String> testMap) {
        for (Map.Entry entry : testMap.entrySet()) {
            System.out.println("K=" + entry.getKey() + " = V=" + entry.getValue());
        }
        System.out.println("***********************************************************");
    }
}
