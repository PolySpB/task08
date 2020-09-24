import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Класс для работы с объектом, не являющимся инстансом интерфейса Map.
 */
public class ObjectReflection {
    /**
     * Функция для очистки и выведения значения полей объекта.
     * @param object объект
     * @param fieldsToCleanup список полей, которые необходимо очистить
     * @param fieldsToOutput список полей, которые необходимо вывести
     * @throws IllegalAccessException если у объекта нет полей, указанных в fieldsToCleanup/fieldsToOutput
     */
    public static void cleanupObject(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException {
        if (!objFieldsExist(object, fieldsToCleanup) || !objFieldsExist(object, fieldsToOutput)) {
            throw new IllegalArgumentException();
        } else {
            cleanObjectFields(object, fieldsToCleanup);
            outputObjectFields(object, fieldsToOutput);
        }
    }

    /**
     * Функция очистки полей объекта.
     * Если поля примитивных типов, то устанавливаем их значение по умолчанию.
     * Если поля не примитивных типов, то устанавливаем значение null.
     * @param object объект
     * @param fieldsToCleanup список полей, которые необходимо очистить
     * @throws IllegalAccessException
     */
    private static void cleanObjectFields(Object object, Set<String> fieldsToCleanup) throws IllegalAccessException {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            for (String field : fieldsToCleanup) {
                if (declaredField.getName().equalsIgnoreCase(field)) {
                    declaredField.setAccessible(true);
                    String type = declaredField.getType().toString();
                    if (isPrimitive(type)) {
                        switch (type) {
                            case "boolean":
                                declaredField.set(object, false);
                                break;
                            case "byte":
                                declaredField.set(object, (byte) 0);
                                break;
                            case "char":
                                declaredField.set(object, '\u0000');
                                break;
                            case "short":
                                declaredField.set(object, (short) 0);
                                break;
                            case "int":
                                declaredField.set(object, 0);
                                break;
                            case "long":
                                declaredField.set(object, 0L);
                                break;
                            case "float":
                                declaredField.set(object, 0f);
                                break;
                            case "double":
                                declaredField.set(object, 0d);
                                break;
                        }

                    } else {
                        declaredField.set(object, null);
                    }
                }
            }
        }
    }

    /**
     * Функция, проверяющая является ли тип поля примитивным или нет.
     * @param type проверяемый тип
     * @return true если поле примитивного типа, false если нет
     */
    private static boolean isPrimitive(String type) {
        return  type.equalsIgnoreCase("boolean") ||
                type.equalsIgnoreCase("byte") ||
                type.equalsIgnoreCase("char") ||
                type.equalsIgnoreCase("short") ||
                type.equalsIgnoreCase("int") ||
                type.equalsIgnoreCase("long") ||
                type.equalsIgnoreCase("float") ||
                type.equalsIgnoreCase("double");
    }

    /**
     * Вывод значенией полей объекта.
     * @param object объект
     * @param fieldsToOutput список полей, которые необходимо вывести
     * @throws IllegalAccessException
     */
    private static void outputObjectFields(Object object, Set<String> fieldsToOutput) throws IllegalAccessException {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            for (String field : fieldsToOutput) {
                if (declaredField.getName().equalsIgnoreCase(field)) {
                    declaredField.setAccessible(true);
                    System.out.println(declaredField.getType() + " " + declaredField.getName() + " = " + declaredField.get(object));
                }
            }
        }
        System.out.println("***********************************************************");
    }

    /**
     * Функция, проверяющая, содержит ли объект поля, перечисленные в fieldsToCleanup/fieldsToOutput.
     * @param object объект
     * @param fields список полей
     * @return true если у объекта есть перечисленные поля, false если у объекта нет какого-то из перечисленных полей
     */
    private static boolean objFieldsExist(Object object, Set<String> fields) {
        boolean result = true;
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        List<String> declaredFieldsList = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            declaredFieldsList.add(declaredField.getName());
        }

        for (String field : fields) {
            if (!declaredFieldsList.contains(field)) {
                result = false;
                break;
            }
        }

        return result;
    }

    /**
     * Вывод полей объекта в консоль.
     * @param object объект
     * @throws IllegalAccessException
     */
    public static void printAllObjFields(Object object) throws IllegalAccessException {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            System.out.println(declaredField.getType() + " " + declaredField.getName() + " = " + declaredField.get(object));
        }
        System.out.println("***********************************************************");
    }
}
