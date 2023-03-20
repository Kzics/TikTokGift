// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.rest.services;

import pl.jordii.mctiktokgiftactions.rest.objects.Gift;
import java.util.Map;

public interface GiftRequestService extends RequestService<String, Callback<Map<String, Gift>>>
{
    void sendRequest(final String p0, final Callback<Map<String, Gift>> p1);
    
    Map<String, Gift> parseInput(final String p0);
}
