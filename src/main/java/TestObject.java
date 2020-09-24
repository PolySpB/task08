/**
 * Класс для тестирования.
 * Тестируем возможность доступа к полям объекта с использованием рефлексии.
 */
public class TestObject {

    public static int field1 = 8;
    private final String field2 = "field2";
    private volatile boolean field3 = true;
    byte field4 = 4;
    long field5 = 1234567890L;
    public transient float field6 = 123f;
    short field7 = (short) 23;
    private double field8 = 23456.456;
    public char field9 = 's';
    private Car field10 = new Car("car");

    private class Car {
        String name;

        public Car(String name) {
            this.name = name;
        }
    }
}
