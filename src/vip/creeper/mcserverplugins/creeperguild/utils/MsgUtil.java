package vip.creeper.mcserverplugins.creeperguild.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgUtil {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");
    public static final String HEAD_MSG = "§a[CreeperGuild] ";
    public static final String ONLY_PLAYER_CAN_EXECUTE_CMD = "&c命令执行者必须是玩家!";
    public static final String[] CMD_HELP_MSGS = new String[] {
            getLineSegmentMsg()
            , "\'[]\' 内的内容为选填内容, 可以不填!  &e黄字&f为[会主/管理员]专用指令!"
            , "&7- &f/g create <公会名> &b- &f创建公会"
            , "&7- &f/g join <GID> &b- &f加入公会"
            , "&7- &f/g info [GID] &b- &f查看[某/自己]的信息"
            , "&7- &f/g list [页数] &b- &f查看公会列表"
            , "&7- &f/g spawn [GID] &b- &f传送至[自己/指定]公会主城出生点"
            , "&7- &e/g gui approve &b- &e打开公会审批面板"
            , "&7- &e/g alert <消息> &b- &e向会内所有成员广播消息"
            , "&7- &e/g tpaall &b- &e向会内所有成员发送传送到您这的请求"
            , "&7- &e/g setIntro <介绍> &b- &e设置您公会的介绍信息"
            , "&7- &e/g setSpawn &b- &e您所站的位置设置为公会主城出生点"
            , "&7- &e/g kick <玩家名> &b- &e踢出玩家"
            , "&7- &e/g setAdmin <玩家名> &b- &e设置管理员"
            , "&7- &e/g deAdmin <玩家名> &b- &e取消管理员"
            , getLineSegmentMsg()
    };

    public static boolean sendClsMsg(CommandSender cs) {
        if (!PlayerUtil.isValidCs(cs)) {
            return false;
        }

        for (int i = 0; i < 15; i ++) {
            cs.sendMessage("");
        }

        return true;
    }

    public static boolean sendMsg(CommandSender cs, String msg) {
        if (!PlayerUtil.isValidCs(cs) || msg == null) {
            return false;
        }

        cs.sendMessage(HEAD_MSG  + "§b" + ChatColor.translateAlternateColorCodes('&', msg));
        return true;
    }

    public static boolean sendMsgWithoutPrefix(CommandSender cs, String msg) {
        if (!PlayerUtil.isValidCs(cs)) {
            return false;
        }

        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        return true;
    }

    public static boolean sendMsgsWithoutPrefix(CommandSender cs, String[] msgs) {
        if (!PlayerUtil.isValidCs(cs)) {
            return false;
        }

        for (String msg : msgs) {
            sendMsgWithoutPrefix(cs, msg);
        }

        return true;
    }

    public static String getLineSegmentMsg() {
        return "§d§m §d§m §d§m §d§m §d§m §d§m §b§m §b§m §b§m §b§m §b§m §b§m §c§m §c§m §c§m §c§m §c§m §c§m §a§m §a§m §a§m §a§m §a§m §a§m §d§m §d§m §d§m §d§m §d§m §d§m §b§m §b§m §b§m §b§m §b§m §b§m §c§m §c§m §c§m §c§m §c§m §c§m §a§m §a§m §a§m §a§m §a§m §a§m  §d§m §d§m §d§m §d§m §d§m §d§m §b§m §b§m §b§m §b§m §b§m §b§m §c§m §c§m §c§m §c§m §c§m §c§m §a§m §a§m §a§m §a§m §a§m §a§m";
    }

    public static String formatDate(Date date) {
        return SDF.format(date);
    }

    public static String formatDate(long time) {
        return formatDate(new Date(time));
    }

    public static void broadcastMsg(String msg) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static boolean tellRawMsg(Player player, String json) {
        if (!PlayerUtil.isValidCs(player)) {
            return false;
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + json);
        return true;
    }

    public static boolean tellConfirmRawMsg(Player player, String cmd) {
        return tellRawMsg(player, "[\"\",{\"text\":\"[CreeperGuild] \",\"color\":\"green\"},{\"text\":\"点击确认\",\"color\":\"red\",\"italic\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/" + cmd + "\"}}]");
    }
}
