package vip.creeper.mcserverplugins.creeperguild;

/**
 * Created by July on 2018/2/5.
 */
public enum GuildPlayerPermission {
    MEMBER("会员"), ADMIN("管理员"), OWNER("会主");

    private String chineseName;

    GuildPlayerPermission(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
