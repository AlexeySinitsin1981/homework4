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
            if(!nameFields.contains(s)){
                throw new IllegalArgumentException();
            }
            }
            for (String s : fieldsToOutput) {
                if(!nameFields.contains(s)){
                    throw new IllegalArgumentException();
                }
            }
            for (Field field : fields) {
                for (String s : fieldsToCleanup) {
                    if (field.getType().getName().equals("byte")) {
                        field.setByte(object, (byte) 0);
                    } else if (field.getType().getName().equals("short")) {
                        field.setShort(object, (short) 0);
                    } else if (field.getType().getName().equals("int")) {
                        field.setInt(object, 0);
                    } else if (field.getType().getName().equals("char")) {
                        field.setChar(object, (char) 0);
                    } else if (field.getType().getName().equals("long")) {
                        field.setLong(object, 0);
                    } else if (field.getType().getName().equals("float")) {
                        field.setFloat(object, 0);
                    } else if (field.getType().getName().equals("double")) {
                        field.setDouble(object, 0);
                    } else if (field.getType().getName().equals("boolean")) {
                        field.setBoolean(object, false);
                    } else if (field.getType().getName().equals("object")) {
                        field.set(object, null);
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
                            conversion += field.get(object) + "; ";

                        } else {
                            conversion += field.get(object) + "; ";
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

                System.out.println(get.invoke(object,s));
            }
        }
    }


        public static void main (String[]args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            Animal an = new Animal();

            HashMap<String, String> hm = new HashMap<>();


            hm.put("1", "1");
            hm.put("2", "2");
            hm.put("3", "3");
            hm.put("4", "4");

            Set<String> fieldsToCleanup = new HashSet<>();
            Set<String> fieldsToOutput = new HashSet<>();
            fieldsToCleanup.add("1");
            fieldsToCleanup.add("2");

            fieldsToOutput.add("3");
            fieldsToOutput.add("4");

            cleanup(hm, fieldsToCleanup,fieldsToOutput);
            //hm.values().stream().forEach(System.out::println);


            Class clazz = an.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                fieldsToCleanup.add(field.getName());
            }
            for (Field field : fields) {
                fieldsToOutput.add(field.getName());
            }

            //App.cleanup(an, fieldsToCleanup, fieldsToOutput);


        }
    }




