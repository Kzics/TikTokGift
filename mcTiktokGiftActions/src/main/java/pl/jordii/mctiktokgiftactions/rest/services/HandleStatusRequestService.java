// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.rest.services;

import pl.jordii.mctiktokgiftactions.rest.requests.StatusType;

public interface HandleStatusRequestService
{
    void sendRequest(final String p0, final StatusType p1, final Callback<String> p2);
}
