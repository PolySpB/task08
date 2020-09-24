import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Реализация метода void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput).
 * Используем рефлексию для доступа к полям/методам object.
 * В object поля, перечисленные в fieldsToCleanup устанавливаем в значение null.
 * Если поля примитивных типов устанавливаем их значение по умолчанию. Поля, перечисленные в fieldsToOutput выводим в консоль.
 * Если object является реализацией интерфейса Map, то для списка fieldsToCleanup удаляем ключи из мапы.
 * Для fieldsToOutput выводим значения мапы в консоль.
 * При отсутствии в объекте/мапе полей из fieldsToCleanup/fieldsToOutput, выбрасываем IllegalArgumentException.
 */
public class Main {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //Пример для объекта
        TestObject testObject = new TestObject();
        Set<String> objFieldsToCleanup = new HashSet<>();
        Set<String> objFieldsToOutput = new HashSet<>();
        Collections.addAll(objFieldsToCleanup, "field1", "field2", "field3", "field4", "field5",
                "field6", "field7", "field8", "field9", "field10");
        Collections.addAll(objFieldsToOutput, "field1", "field2", "field3", "field4", "field5",
                "field6", "field7", "field8", "field9", "field10");

        ObjectReflection.printAllObjFields(testObject);
        cleanup(testObject, objFieldsToCleanup, objFieldsToOutput);

        //Пример для мапы
        Map<String, String> testMap = new HashMap<>();
        testMap.put("e1", "1");
        testMap.put("e2", "2");
        testMap.put("e3", "3");
        testMap.put("e4", "4");
        testMap.put("e5", "5");
        testMap.put("e6", "6");
        testMap.put("e7", "7");
        testMap.put("e8", "8");
        Set<String> mapFieldsToCleanup = new HashSet<>();
        Set<String> mapFieldsToOutput = new HashSet<>();
        Collections.addAll(mapFieldsToCleanup, "e1", "e2", "e4", "e5", "e6", "e7", "e8");
        Collections.addAll(mapFieldsToOutput, "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8");

        MapReflection.printAllMapFields(testMap);
        cleanup(testMap, mapFieldsToCleanup, mapFieldsToOutput);
    }

    /**
     * Функция очищения полей у object.
     * @param object объект, у котрого будем очищать/выводить поля
     * @param fieldsToCleanup список полей, которые необходимо очистить
     * @param fieldsToOutput список полей, которые необходимо вывести
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (object instanceof Map) {
            MapReflection.cleanupMap(object, fieldsToCleanup, fieldsToOutput);
        }
        else {
            ObjectReflection.cleanupObject(object, fieldsToCleanup, fieldsToOutput);
        }
    }
}
