package com.wuxl.retrofit.retrofit.http.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * 图片压缩
 * Created by WUXL on 2016/9/20.
 */
public class BitmapUtil {

    private static final int MAXSEIZE = 1024;

    private static final int W = 480;
    private static final int H = 800;

    /**
     * 将图片进行质量压缩
     *
     * @param bmp       图片
     * @param maxLength 压缩后图片的最大值,单位KB
     * @return
     */
    public static File compressBmpToFile(Bitmap bmp, int maxLength) {
        //临时创建一个文件
        File tempfile = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 50;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > maxLength) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            String newfilepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TEMP/IMG/temp_" + new Date().getTime() + ".jpg";

            tempfile = new File(newfilepath);
            if (!tempfile.getParentFile().exists()) {
                tempfile.getParentFile().mkdirs();
            }
            if (!tempfile.exists()) {
                tempfile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(tempfile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //回收
        bmp.recycle();

        return tempfile;
    }

    public static File compressBmpToFile(Bitmap bmp) {
        return compressBmpToFile(bmp, MAXSEIZE);
    }

    public static File compressBmpToFile(String imgPath) {
        return compressBmpToFile(getBitmap(imgPath), MAXSEIZE);
    }

    public static Bitmap getBitmap(String imgPath){
        return getBitmap(imgPath,W,H);
    }

    /**
     * 获取大小为targetW×targetH的图片
     *
     * @param imgPath 图片路径
     * @param targetW 宽
     * @param targetH 高
     * @return
     */
    public static Bitmap getBitmap(String imgPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, bmOptions);

        return bitmap;
    }
}
