package com.zkkc.track.moudle.home.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


import com.zkkc.track.base.BaseModel;
import com.zkkc.track.moudle.home.callback.IResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * Created by ShiJunRan on 2019/1/23
 */
public class MainModel<T> extends BaseModel {

//    public void pictureShot(Context context, android.view.View mView, ExecutorService etService, IResult iResult) {
//
//        getPictureShot(context, mView, etService, iResult);
//    }
//
//    /**
//     * 截取视频拍照
//     */
//    android.view.View dView;
//
//    public void getPictureShot(final Context context, android.view.View v, ExecutorService etService, final IResult iResult) {
//        dView = v;
//        etService.execute(new Runnable() {
//            @Override
//            public void run() {
//                dView.setDrawingCacheEnabled(true);
//                dView.buildDrawingCache();
//                Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
//                if (bitmap != null) {
//                    saveGallery(context, bitmap, iResult);
//                }
//            }
//        });
//    }

//    public void saveGallery(Context context, Bitmap bmp, IResult iResult) {
//        // 首先保存图片
//        File filesDir = Environment.getExternalStorageDirectory();
//        File appDir = new File(filesDir, "a_track");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String nowDate = getNowDate();
//        String fileName = nowDate + ".png";
//        File file = new File(appDir, fileName);
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            fos.flush();
//            fos.close();
////            ToastUtil.showShortToast("抓拍成功");
//            Log.d("SJR", "saveToSystemGallery: " + fileName + "----" + filesDir);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            iResult.Failure(e.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            iResult.Failure(e.toString());
//        }
//
//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
//        iResult.Succeed();
//    }
//
//    /**
//     * 获取系统当前时间
//     */
//    private String getNowDate() {
//        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
//        return format;
//    }
}
