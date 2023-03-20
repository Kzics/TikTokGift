// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.rest.objects;

public class Message
{
    public String uniqueId;
    public String userId;
    public String type;
    public MessageData data;
    
    public class MessageData
    {
        public String comment;
    }
}
