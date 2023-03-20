// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.model;

import java.util.UUID;

public class Streamer
{
    private UUID playerUuid;
    private String playerName;
    private String tiktokName;
    private StreamStatus streamStatus;
    private int watchersCount;
    private String lastGifter;
    private String lastGift;
    
    public Streamer(final UUID playerUuid, final String playerName, final String tiktokName, final StreamStatus streamStatus, final int watchersCount, final String lastGifter, final String lastGift) {
        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.tiktokName = tiktokName;
        this.streamStatus = streamStatus;
        this.watchersCount = watchersCount;
        this.lastGifter = lastGifter;
        this.lastGift = lastGift;
    }
    
    public String getLastGift() {
        return this.lastGift;
    }
    
    public void setLastGift(final String lastGift) {
        this.lastGift = lastGift;
    }
    
    public String getLastGifter() {
        return this.lastGifter;
    }
    
    public void setLastGifter(final String lastGifter) {
        this.lastGifter = lastGifter;
    }
    
    public UUID getPlayerUuid() {
        return this.playerUuid;
    }
    
    public void setPlayerUuid(final UUID playerUuid) {
        this.playerUuid = playerUuid;
    }
    
    public String getPlayerName() {
        return this.playerName;
    }
    
    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }
    
    public String getTiktokName() {
        return this.tiktokName;
    }
    
    public void setTiktokName(final String tiktokName) {
        this.tiktokName = tiktokName;
    }
    
    public StreamStatus getStreamStatus() {
        return this.streamStatus;
    }
    
    public void setStreamStatus(final StreamStatus streamStatus) {
        this.streamStatus = streamStatus;
    }
    
    public int getWatchersCount() {
        return this.watchersCount;
    }
    
    public void setWatchersCount(final int watchersCount) {
        this.watchersCount = watchersCount;
    }
    
    @Override
    public String toString() {
        return "Streamer{playerUuid=" + this.playerUuid + ", playerName='" + this.playerName + '\'' + ", tiktokName='" + this.tiktokName + '\'' + ", streamStatus=" + this.streamStatus + ", watchersCount=" + this.watchersCount + '}';
    }
}
