package pl.edu.agh.virtualassistant.chat;

import android.content.res.AssetManager;
import android.content.res.Resources;
import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.*;

public class AssetsResolver {
    private static final String botName = "alice";

    public static String init(Resources resources, File cacheDir) {
        AssetManager assets = resources.getAssets();
        File jayDir = new File(cacheDir.toString() + "/" + botName + "/bots/" + botName);
        boolean b = jayDir.mkdirs();

        if (jayDir.exists()) {
            try {
                for (String dir : assets.list(botName)) {
                    for (String file : assets.list(botName + "/" + dir)) {
                        File f = new File(jayDir.getPath() + "/" + dir + "/" + file);

                        if (f.exists()) {
                            continue;
                        }

                        InputStream in = assets.open(botName + "/" + dir + "/" + file);
                        File outFile = new File(jayDir.getPath() + "/" + dir + "/" + file);
                        outFile.getParentFile().mkdirs();
                        outFile.createNewFile();
                        OutputStream out = new FileOutputStream(jayDir.getPath() + "/" + dir + "/" + file);

                        copyFile(in, out);
                        in.close();
                        out.flush();
                        out.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //get the working directory
        MagicStrings.root_path = cacheDir.toString() + "/" + botName;
        AIMLProcessor.extension =  new PCAIMLProcessorExtension();
        return MagicStrings.root_path;
    }

    //copying the file
    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

}
