// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.rest.services;

public interface Callback<T>
{
    void accept(final T p0);
    
    default void onFailure(final Throwable cause) {
        cause.printStackTrace();
    }
}
