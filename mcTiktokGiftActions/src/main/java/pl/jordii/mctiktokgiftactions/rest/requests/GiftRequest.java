// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.rest.requests;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.IOException;
import java.util.Scanner;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import java.net.URL;
import pl.jordii.mctiktokgiftactions.McTiktokGiftActions;
import pl.jordii.mctiktokgiftactions.rest.objects.Gift;
import java.util.Map;
import pl.jordii.mctiktokgiftactions.rest.services.Callback;
import pl.jordii.mctiktokgiftactions.rest.services.GiftRequestService;

public class GiftRequest implements GiftRequestService
{
    @Override
    public void sendRequest(final String name, final Callback<Map<String, Gift>> callback) {
        McTiktokGiftActions.getExecutorService().execute(() -> {
            try {
                URL url = new URL("http://95.216.216.167:1975/gift/" + name);
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet getRequest = new HttpGet(url.toString());
                CloseableHttpResponse response = httpClient.execute(getRequest);
                Scanner scanner = new Scanner(response.getEntity().getContent());
                StringBuilder responseBuilder = new StringBuilder();
                while (scanner.hasNext()) {
                    responseBuilder.append(scanner.nextLine());
                }
                Map<String, Gift> jsonObjects = this.parseInput(responseBuilder.toString());
                scanner.close();
                callback.accept(jsonObjects);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    @Override
    public Map<String, Gift> parseInput(final String json) {
        final Type targetClassType = new TypeToken<Map<String, Gift>>() {}.getType();
        return (Map<String, Gift>)new Gson().fromJson(json, targetClassType);
    }
}
