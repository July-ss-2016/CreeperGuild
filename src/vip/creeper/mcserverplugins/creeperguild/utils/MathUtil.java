package vip.creeper.mcserverplugins.creeperguild.utils;

/**
 * Created by July on 2018/2/10.
 */
public class MathUtil {
    public static int getRandomIntegerNum(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
