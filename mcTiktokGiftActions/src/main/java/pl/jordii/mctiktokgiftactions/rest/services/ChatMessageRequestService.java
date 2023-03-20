// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.rest.services;

import pl.jordii.mctiktokgiftactions.rest.objects.Message;
import java.util.Map;

public interface ChatMessageRequestService extends RequestService<String, Callback<Map<String, Message>>>
{
    void sendRequest(final String p0, final Callback<Map<String, Message>> p1);
    
    Map<String, Message> parseInput(final String p0);
}
