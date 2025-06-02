package co2103.hw2.hw2;

import co2103.hw2.GenerateTasks;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Task2Test {

    public Class<?> loadClass(String name) {
        ClassLoader classLoader = this.getClass().getClassLoader();

        try {
            Class<?> aClass = classLoader.loadClass(name);
            return aClass;
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    @Test
    public void classesAndFieldsCreated() throws Exception {
        Map<String, String> c = GenerateTasks.getConcepts();

        Class<?> parent = loadClass("co2103.hw2.model." + c.get("Parent"));
        assertNotNull(parent, "Class " + "co2103.hw2.model." + c.get("Parent") + " not found");

        // fields
        assertField(parent, "private", "int", c.get("pf1"));
        assertField(parent, "private", "java.lang.String", c.get("pf2"));
        assertField(parent, "private", "java.util.List", c.get("children"));
        assertField(parent, "private", "co2103.hw2.model." + c.get("Child"), c.get("pf4"));


        Class<?> child = loadClass("co2103.hw2.model." + c.get("Child"));
        assertNotNull(child, "Class " + "co2103.hw2.model." + c.get("Child") + " not found");
        assertField(child, "private", "java.lang.String", c.get("cf1"));
        assertField(child, "private", "java.util.List", c.get("parents"));
        assertField(child, "private", "java.util.List", c.get("grandchildren"));
        assertField(child, "private", "co2103.hw2.model." + c.get("Grandchild"), c.get("cf4"));

        Class<?> grandchild = loadClass("co2103.hw2.model." + c.get("Grandchild"));
        assertNotNull(grandchild, "Class " + "co2103.hw2.model." + c.get("Grandchild") + " not found");
        assertField(grandchild, "private", "int", c.get("gf1"));
        assertField(grandchild, "private", "int", c.get("gf2"));

    }

    private void assertField(Class<?> parent, String modifier, String type, String name) throws Exception {
        Field f = parent.getDeclaredField(name);
        assertNotNull(f);
        assertEquals(type, f.getType().getName());
        assertEquals(Modifier.PRIVATE, f.getModifiers());
    }

}

