package vip.creeper.mcserverplugins.creeperguild.utils;

import vip.creeper.mcserverplugins.creeperguild.CreeperGuild;

import java.io.*;

public class FileUtil {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static CreeperGuild plugin = CreeperGuild.getInstance();
    public static final String PLUGIN_FOLDER_PATH = plugin.getDataFolder().getAbsolutePath();
    public static final String DATA_FOLDER_PATH = PLUGIN_FOLDER_PATH + File.separator + "data";
    public static final String PLAYERS_FOLDER_PATH = DATA_FOLDER_PATH + File.separator + "players";
    public static final String GUILDS_FOLDER_PATH = DATA_FOLDER_PATH + File.separator + "guilds";
    public static final File PLUGIN_FOLDER_FILE = plugin.getDataFolder();
    public static final File DATA_FOLDER_FILE = new File(DATA_FOLDER_PATH);
    public static final File PLAYERS_FOLDER_FILE = new File(PLAYERS_FOLDER_PATH);
    public static final File GUILDS_FOLDER_FILE = new File(GUILDS_FOLDER_PATH);

    //从jar包复制文件
    public static boolean copyJarResource(final String inPath, final String outPath) {
        try {
            InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(inPath); //读取文件内容
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            StringBuilder sb = new StringBuilder();

            while ((lineText = bufferedReader.readLine()) != null){
                sb.append(lineText).append(LINE_SEPARATOR);
            }

            bufferedReader.close();
            reader.close();
            return writeFile(outPath, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //写文件
    private static boolean writeFile(final String path, final String data) {
        File file = new File(path);

        try {
            FileWriter fw = new FileWriter(file);

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
            }

            fw.write(data);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
