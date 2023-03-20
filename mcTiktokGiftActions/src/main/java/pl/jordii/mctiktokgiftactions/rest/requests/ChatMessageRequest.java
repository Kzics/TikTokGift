// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.rest.requests;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import pl.jordii.mctiktokgiftactions.McTiktokGiftActions;
import pl.jordii.mctiktokgiftactions.rest.objects.Message;
import pl.jordii.mctiktokgiftactions.rest.services.Callback;
import pl.jordii.mctiktokgiftactions.rest.services.ChatMessageRequestService;

public class ChatMessageRequest implements ChatMessageRequestService
{
    @Override
    public void sendRequest(final String name, final Callback<Map<String, Message>> callback) {
        McTiktokGiftActions.getExecutorService().execute(() -> {
            try {
                URL url = new URL("http://95.216.216.167:1975/messages/" + name);
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet getRequest = new HttpGet(url.toString());
                CloseableHttpResponse response = httpClient.execute(getRequest);
                Scanner scanner = new Scanner(response.getEntity().getContent());
                StringBuilder responseBuilder = new StringBuilder();
                while (scanner.hasNext()) {
                    responseBuilder.append(scanner.nextLine());
                }
                Map<String, Message> jsonObjects = this.parseInput(responseBuilder.toString());
                scanner.close();
                callback.accept(jsonObjects);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    @Override
    public Map<String, Message> parseInput(final String json) {
        final Type targetClassType = new TypeToken<Map<String, Message>>() {}.getType();
        return (Map<String, Message>)new Gson().fromJson(json, targetClassType);
    }
}
