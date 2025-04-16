public class App{
    public static void main(String[] args) throws Exception{
        
        TinyRouter.addRoute("/hello",()-> System.out.println("Hello from controller"));
        TinyServer.start((path)->TinyRouter.dispatch(path));
    }
}