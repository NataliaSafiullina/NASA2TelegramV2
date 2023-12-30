import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;


public class Utils {
    // метод получения ссылки url на картинку
    public static String[] getUrl (String nasaUrl){
        // объявлем сущность, которая будет переводить (маппить) полученный ответ JSON в экземпляр нашего класса NASA
        ObjectMapper mapper = new ObjectMapper();

        // создаем http клиент
        CloseableHttpClient client = HttpClients.createDefault();
        // создаем http get запрос
        HttpGet request = new HttpGet(nasaUrl);
        // запускаем запрос на исполнение и получаем ответ, ответ записываем
        try {
            CloseableHttpResponse response = client.execute(request);
            // маппим ответ в объект нашего класса NASA, в итоге в url будет записан адрес фотографии дня
            NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

            // вернем url картинки и лписание
            String[] arrStr = {answer.url.replace("_","\\_"), answer.explanation};
            return arrStr;

        } catch (IOException e) {
            System.out.println("Произошла какая-то ошибка, попробуйте позже.");
        }
        return new String[]{"", ""};
    }
}
