//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.hc.client5.http.classic.methods.HttpGet;
//import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
//import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
//import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

//6730928576:AAEEIUQNFvRC767VeCuzv5FB5UvDpYLj1SI

public class Main {
    public static void main(String[] args) throws IOException, TelegramApiException {
        new MyTelegramBot("NasaDayPicture_bot",
                "6730928576:AAEEIUQNFvRC767VeCuzv5FB5UvDpYLj1SI");

    }
}
