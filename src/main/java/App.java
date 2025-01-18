import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        CreateApp createApp = new CreateApp();

        Javalin app = createApp.getApp();

        app.start(8080);
    }
}
