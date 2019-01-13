package com.android.collect.library.util.file;


import android.content.Context;
import android.os.Environment;

import com.android.collect.library.util.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {

    public static final String TAG = LogUtils.getLogTag(FileUtil.class);

    /**
     * SD卡是否可用
     *
     * @return
     */
    public static boolean isSdCardUsable() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 通过文件路径获取文件名（带"."）
     *
     * @param path
     * @return
     */
    public static String getFileNameByPath(String path) {
        int count = path.lastIndexOf("/");
        return path.substring(count + 1);
    }

    /**
     * 下载文件
     *
     * @param urlStr    下载链接
     * @param down_path 下载路径
     * @param file_name 下载文件名
     * @param isCover   如果文件已存在：true覆盖，false不下载
     */
    public static void downloadFile(String urlStr, String down_path, String file_name, boolean isCover) {

        File files = new File(down_path);
        if (!files.exists())
            files.mkdirs();
        File file = new File(files, file_name);

        // 如果目标文件已经存在，则删除。产生覆盖旧文件的效果
        if (file.exists()) {
            if (isCover)
                file.delete();
            else
                return;
        }

        OutputStream output = null;

        try {

            file.createNewFile();

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream input = conn.getInputStream();

            output = new FileOutputStream(file);
            // 读取大文件
            byte[] buffer = new byte[1024];
            int read = input.read(buffer);
            while (read != -1) {
                output.write(buffer, 0, read);
                read = input.read(buffer);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
                LogUtils.i(file_name + "文件下载成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存文件
     *
     * @param is
     * @param saveFileDir
     * @param fileName
     * @return
     */
    public static boolean saveFile(InputStream is, String saveFileDir, String fileName) {
        byte[] buf = new byte[1024];
        int len = 0;
        FileOutputStream fos = null;
        try {

            File files = new File(saveFileDir);
            if (!files.exists())
                files.mkdirs();
            File file = new File(files, fileName);

            fos = new FileOutputStream(file);

            if ((len = is.read(buf)) == -1) { // 文件为空
                fos.flush();
                return false;
            } else {
                fos.write(buf, 0, len);
            }

            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            // 如果下载文件成功，第一个参数为文件的绝对路径
            LogUtils.i(TAG, "下载文件成功：-" + saveFileDir + fileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
            return false;
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 保存图片到fileDir目录
     *
     * @param bytes
     * @param fileDir
     * @param name（包含后缀）
     */
    public synchronized static boolean saveByteArrayToFile(byte[] bytes, String fileDir, String name) {

        try {
            File files = new File(fileDir);
            if (!files.exists())
                files.mkdirs();
            File file = new File(files, name);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputstream = new FileOutputStream(file);
            outputstream.write(bytes);
            outputstream.flush();
            outputstream.close();
            return true;

        } catch (FileNotFoundException e) {
            LogUtils.i(TAG, e.getMessage());
            return false;
        } catch (IOException e) {
            LogUtils.i(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 复制文件
     *
     * @param oldPath
     * @param newPath
     */
    public static boolean copyFile(String oldPath, String newPath, String fileName) {
        try {
            int byteRead = 0;
            File oldFile = new File(oldPath);
            File newFileDir = new File(newPath);
            newFileDir.mkdirs();
            File newFile = new File(newPath, fileName);
            if (oldFile.exists() && !newFile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath + fileName);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteRead += byteRead;
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

    /**
     * 把data对象存到名为filename的文件中
     *
     * @param context
     * @param data
     * @param filename
     * @return
     */
    public synchronized static boolean saveDataToFile(Context context, Object data,
                                                      String filename) {
        try {
            context.deleteFile(filename);
            // 创建的文件保存在/data/data/<package name>/files目录
            FileOutputStream f = context.openFileOutput(filename,
                    Context.MODE_PRIVATE);
            ObjectOutputStream obj_out = new ObjectOutputStream(f);
            obj_out.writeObject(data);
            obj_out.close();
            f.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从名为filename的文件中读取对象
     *
     * @param context
     * @param filename
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized static <T> T loadData(Context context, String filename) {
        try {
            FileInputStream f = context.openFileInput(filename);
            ObjectInputStream obj_in = new ObjectInputStream(f);
            T data = (T) obj_in.readObject();
            obj_in.close();
            f.close();
            return data;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 删除filename文件
     *
     * @param context
     * @param filename
     */
    public synchronized static void clearData(Context context, String filename) {
        boolean b = context.deleteFile(filename);
    }

}
