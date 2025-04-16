import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.Function;

public class TinyServer{
    public static void start(Function<String,String> dispatcher) throws IOException{

        ServerSocket serverSocket=new ServerSocket(8080);
        System.out.println("server is running on port 8080...");
        
        while(true){
            Socket clientSocket=serverSocket.accept();//accept the incomming tcp connection
             System.out.println("clientSocket"+clientSocket);

             new Thread(()->handleClient(clientSocket,dispatcher)).start();//assign a thread 

        }
    }

    private static void handleClient(Socket socket,Function<String,String> dispatcher){
        try(            
            BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ){
            String line;
            String requestLine=in.readLine();//eg "Get/hello HTTP/1.1"
            String path=requestLine.split(" ")[1];
            String response=dispatcher.apply(path);
            out.write("HTTP/1.1 200 OK\r\n");

                out.write("content-length()"+response.length()+"\r\n");
                out.write("\r\n");
                out.write(response);
                 out.flush();
                socket.close();

            // if(requestLine!=null && requestLine.contains("/hello")){

            //     String response="Hello from custom server";
            //     out.write("HTTP/1.1 200 OK\r\n");

            //     out.write("content-length()"+response.length()+"\r\n");
            //     out.write("\r\n");
            //     out.write(response);


            // }
            // else{
            //     out.write("HTTP/1.1 404 not found \r\n\r\n");
            //     out.flush();
            //     socket.close();
            // }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
