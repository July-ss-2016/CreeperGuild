package vip.creeper.mcserverplugins.creeperguild;

/**
 * Created by July on 2018/1/29.
 */
public class Settings {
    private String placeholderApiChatPrefixHasNot;
    private String placeholderApiChatPrefixHas;
    private String placeholderApiGuildNameHasNot;
    private String placeholderApiGuildNameHas;
    private int createNameMaxLen;
    private int createNameMinLen;
    private int createCostMoney;
    private int setSpawnCostMoney;
    private int maxAdminNum;

    public int getMaxAdminNum() {
        return maxAdminNum;
    }

    public void setMaxAdminNum(int maxAdminNum) {
        this.maxAdminNum = maxAdminNum;
    }

    public int getSetSpawnCostMoney() {
        return setSpawnCostMoney;
    }

    public void setSetSpawnCostMoney(int setSpawnCostMoney) {
        this.setSpawnCostMoney = setSpawnCostMoney;
    }

    public String getPlaceholderApiChatPrefixHasNot() {
        return placeholderApiChatPrefixHasNot;
    }

    public void setPlaceholderApiChatPrefixHasNot(String placeholderApiChatPrefixHasNot) {
        this.placeholderApiChatPrefixHasNot = placeholderApiChatPrefixHasNot;
    }

    public String getPlaceholderApiChatPrefixHas() {
        return placeholderApiChatPrefixHas;
    }

    public void setPlaceholderApiChatPrefixHas(String placeholderApiChatPrefixHas) {
        this.placeholderApiChatPrefixHas = placeholderApiChatPrefixHas;
    }

    public String getPlaceholderApiGuildNameHasNot() {
        return placeholderApiGuildNameHasNot;
    }

    public void setPlaceholderApiGuildNameHasNot(String placeholderApiGuildNameHasNot) {
        this.placeholderApiGuildNameHasNot = placeholderApiGuildNameHasNot;
    }

    public String getPlaceholderApiGuildNameHas() {
        return placeholderApiGuildNameHas;
    }

    public void setPlaceholderApiGuildNameHas(String placeholderApiGuildNameHas) {
        this.placeholderApiGuildNameHas = placeholderApiGuildNameHas;
    }

    public int getCreateNameMinLen() {
        return createNameMinLen;
    }

    public void setCreateNameMinLen(int createNameMinLen) {
        this.createNameMinLen = createNameMinLen;
    }

    public int getCreateNameMaxLen() {
        return createNameMaxLen;
    }

    public void setCreateNameMaxLen(int createNameMaxLen) {
        this.createNameMaxLen = createNameMaxLen;
    }

    public int getCreateCostMoney() {
        return createCostMoney;
    }

    public void setCreateCostMoney(int createCostMoney) {
        this.createCostMoney = createCostMoney;
    }
}
