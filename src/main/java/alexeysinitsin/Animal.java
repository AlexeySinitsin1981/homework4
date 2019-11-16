package alexeysinitsin;

import java.util.HashMap;

public class Animal {
     byte b = 5;
     short s = 55;
     char c = 'b';
     int i = 555;
     long l = 5555l;
     float f = 223;
     double d = 32.3;
     boolean bool = true;
     Object o = new Object();



     @Override
     public String toString() {
          return "Animal{" + "b=" + b + ", s=" + s +
                  ", c=" + c +
                  ", i=" + i +
                  ", l=" + l +
                  ", f=" + f +
                  ", d=" + d +
                  ", bool=" + bool +
                  ", o=" + o +
                  '}';
     }
}
