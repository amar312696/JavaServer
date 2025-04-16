import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.*;

public class TinyRouter{
    private static final Map<String,Runnable> routes=new HashMap<>();
    public static void addRoute(String path,Runnable handler){
        routes.put(path,handler);

    }
    public static String dispatch(String path){
        Runnable handler=routes.get(path);
        if(handler!=null){
            ByteArrayOutputStream output=new ByteArrayOutputStream();
            PrintStream oldOut=System.out;
            System.setOut(new PrintStream(output));
            handler.run();
            System.setOut(oldOut);
            return output.toString();

        }else{
            return "404 Not found";
        }
    }
}