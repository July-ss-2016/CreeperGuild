package vip.creeper.mcserverplugins.creeperguild;

import java.util.Date;

/**
 * Created by July on 2018/2/8.
 */
public class UnapprovedPlayer {
    private String playerName;
    private long applicationTime;

    public UnapprovedPlayer(String playerName, long applicationTime) {
        this.playerName = playerName;
        this.applicationTime = applicationTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public long getApplicationTime() {
        return applicationTime;
    }
}
