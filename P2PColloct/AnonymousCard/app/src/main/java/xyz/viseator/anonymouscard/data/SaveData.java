package xyz.viseator.anonymouscard.data;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by viseator on 2016/12/21.
 * Wudi
 * viseator@gmail.com
 */

public class SaveData {
    public static void writeToFile(Context context, Object object, String fileName) {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readFromFile(Context context, String fileName) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(fileName));
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
