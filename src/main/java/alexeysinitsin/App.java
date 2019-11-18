package alexeysinitsin;


import java.lang.reflect.*;
import java.util.*;


public class App {
    static void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class clazz = object.getClass();

        if (!(object instanceof Map)) {

            Field[] fields = clazz.getDeclaredFields();
            HashSet<String> nameFields = new HashSet<>();
            for (Field field : fields) {
                nameFields.add(field.getName());
            }
            for (String s : fieldsToCleanup) {
                if (!nameFields.contains(s)) {
                    throw new IllegalArgumentException();
                }
            }
            for (String s : fieldsToOutput) {
                if (!nameFields.contains(s)) {
                    throw new IllegalArgumentException();
                }
            }
            for (Field field : fields) {
                for (String s : fieldsToCleanup) {
                    if (field.getName().equals(s)) {
                        switch (field.getType().getName()) {
                            case "byte":
                                field.setByte(object, (byte) 0);
                                break;
                            case "short":
                                field.setShort(object, (short) 0);
                                break;
                            case "char":
                                field.setChar(object, (char) 0);
                                break;
                            case "int":
                                field.setInt(object, 0);
                                break;
                            case "long":
                                field.setLong(object, 0);
                                break;
                            case "float":
                                field.setFloat(object, (float) 0);
                                break;
                            case "double":
                                field.setDouble(object, 0);
                                break;
                            case "boolean":
                                field.setBoolean(object, false);
                                break;
                            default:
                                field.set(object, null);

                        }
                    }
                }
            }


            String[] primitive = {"byte", "short", "int", "char", "long", "float", "double", "boolean"};
            List<String> list = Arrays.asList(primitive);
            String conversion = "";

            for (Field field : fields) {
                for (String s : fieldsToOutput) {
                    if (field.getName().equals(s)) {
                        if (list.contains(s)) {
                            conversion += field.get(object) + " ";

                        } else {
                            conversion += field.get(object) + " ";
                        }
                    }
                }
            }
            System.out.println(conversion);
        }else{
            Method remove = clazz.getMethod("remove", Object.class);
            Method contains = clazz.getMethod("containsKey", Object.class);

            for (String s : fieldsToCleanup) {
                if(!((boolean)contains.invoke(object, s))){
                    throw new IllegalArgumentException();
                }
            }
            for (String s : fieldsToOutput) {
                if(!((boolean)contains.invoke(object, s))){
                    throw new IllegalArgumentException();
                }
            }

            for (String s : fieldsToCleanup) {
                remove.invoke(object, s);

            }
            Method get = clazz.getMethod("get", Object.class);

            for (String s : fieldsToOutput) {
                System.out.println(get.invoke(object, s));
            }
        }
    }
}





