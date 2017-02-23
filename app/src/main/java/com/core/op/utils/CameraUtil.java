package com.core.op.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.core.op.AppConfig;
import com.core.op.R;
import com.core.op.lib.utils.AppToast;
import com.core.op.lib.utils.FileUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.width;


/**
 * @author op
 * @version 1.0
 * @description
 * @createDate 2016/4/15
 */
public class CameraUtil {

    public static final String CAMERA_DIR = "CAMERA_DIR";
    public static final String CAMERA_TEMP_DIR = "CAMERA_TEMP_DIR";
    private Activity activity;
    private Fragment fragment;

    private OnResultLisetener onResultLisetener;
    private List<String> temps = new ArrayList<>();
    private List<String> pics = new ArrayList<>();

    private List<Integer> request_camera_cut = new ArrayList<>();
    private List<Integer> request_cut_pic = new ArrayList<>();
    private List<Integer> request_photo_cut = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private List<Boolean> isCamers = new ArrayList<>();
    RxPermissions rxPermissions;
    private int picWeith = 100;

    private int picHeight = 100;

    public CameraUtil(Activity context, String type, ImageView... imageViews) {
        this.activity = context;

        rxPermissions = new RxPermissions(context);
        File path = FileUtil.getSaveFolder(AppConfig.FILE_SAVE_PATH + CAMERA_DIR);
        File tempPath = FileUtil.getSaveFolder(AppConfig.FILE_SAVE_PATH + CAMERA_TEMP_DIR);
        for (int i = 0; i < imageViews.length; i++) {
            this.imageViews.add(imageViews[i]);
            temps.add(tempPath.getAbsolutePath() + "/" + type + "_temp" + i);
            pics.add(path.getAbsolutePath() + "/" + type + "_pic" + i);
            request_camera_cut.add(i);
            request_cut_pic.add(10 + i);
            request_photo_cut.add(20 + i);
            isCamers.add(false);
        }
    }

    public CameraUtil(Fragment fragment, String type, ImageView... imageViews) {
        this.fragment = fragment;

        rxPermissions = new RxPermissions(fragment.getActivity());
        File path = FileUtil.getSaveFolder(AppConfig.FILE_SAVE_PATH + CAMERA_DIR);
        File tempPath = FileUtil.getSaveFolder(AppConfig.FILE_SAVE_PATH + CAMERA_TEMP_DIR);
        for (int i = 0; i < imageViews.length; i++) {
            this.imageViews.add(imageViews[i]);
            temps.add(tempPath.getAbsolutePath() + "/" + type + "_temp" + i);
            pics.add(path.getAbsolutePath() + "/" + type + "_pic" + i);
            request_camera_cut.add(i);
            request_cut_pic.add(10 + i);
            request_photo_cut.add(20 + i);
            isCamers.add(false);
        }
    }

    public void setOnResultLisetener(OnResultLisetener onResultLisetener) {
        this.onResultLisetener = onResultLisetener;
    }

    public List<String> getPics() {
        return pics;
    }

    public List<Integer> getRequest_camera_cut() {
        return request_camera_cut;
    }


    public List<Integer> getRequest_cut_pic() {
        return request_cut_pic;
    }

    public List<Integer> getRequest_photo_cut() {
        return request_photo_cut;
    }


    public void startCamera(int index) {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        getFromSysCamera(new File(temps.get(index)), request_camera_cut.get(index));
                    } else {
                        AppToast.show(activity, activity.getString(R.string.app_permission_camer_refuse));
                    }
                });
    }

    public void startPicture(int index) {
        getPicFromPhotos(request_photo_cut.get(index));
    }

    private void startCut(int index) {
        cutPhotos(Uri.fromFile(new File(temps.get(index))), Uri.fromFile(new File(pics.get(index))), request_cut_pic.get(index), picWeith, picHeight);
    }

    private void startPicCut(Uri uri, int index) {
        cutPhotos(uri, Uri.fromFile(new File(pics.get(index))), request_cut_pic.get(index), 100, 100);
    }

    public int getPicWeith() {
        return picWeith;
    }

    public void setPicWeith(int picWeith) {
        this.picWeith = picWeith;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public List<Boolean> getIsCamers() {
        return isCamers;
    }

    private void startImageViews(int index) {
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Bitmap bitmap = BitmapFactory.decodeFile(pics.get(index));
                        imageViews.get(index).setImageBitmap(bitmap);
                    } else {
                        AppToast.show(activity, activity.getString(R.string.app_permission_camer_refuse));
                    }
                });
        isCamers.set(index, true);
        if (onResultLisetener != null) {
            onResultLisetener.success(index, pics.get(index));
        }
    }

    public void result(int requestCode, Intent data) {
        for (int i = 0; i < getRequest_camera_cut().size(); i++) {
            if (getRequest_camera_cut().get(i) == requestCode) {
                if (FileUtil.exists(temps.get(i)))//当点击拍照然后直接返回是判断
                    startCut(i);
                else return;
                break;
            }
            if (data != null && getRequest_photo_cut().get(i) == requestCode) {
                startPicCut(data.getData(), i);
                break;
            }
            if (getRequest_cut_pic().get(i) == requestCode) {
                startImageViews(i);
                break;
            }
        }
    }

    /**
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @description The Uri to check.
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @description Whether the Uri authority is DownloadsProvider.
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());

    }

    /**
     * @description Whether the Uri authority is MediaProvider..
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @description Whether the Uri authority is Google Photos.
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @Description 调系统相机
     */
    private void getFromSysCamera(File file, int requescode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// android.media.action.IMAGE_CAPTURE
        Uri uri = Uri.fromFile(file);// out
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (activity == null) {
            fragment.startActivityForResult(intent, requescode);
        } else {
            activity.startActivityForResult(intent, requescode);
        }
    }

    private void cutPhotos(Uri data, Uri outPutPath, int requestId, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {//4.4系统与5.0系统不一样，相册裁剪判断
//            String url = getPath(data);
//            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
//        } else {
        intent.setDataAndType(data, "image/*");
//        }
        intent.putExtra("output", outPutPath);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);// 输出图片大小
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        if (activity == null) {
            fragment.startActivityForResult(intent, requestId);
        } else {
            activity.startActivityForResult(intent, requestId);
        }
    }

    /**
     * @description
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    private String getPath(final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(activity, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @description
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    private String getDataColumn(Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = activity.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @description
     * @author Andy.fang
     * @createDate
     * @version 1.0
     */
    private void getPicFromPhotos(int requescode) {
//        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        openAlbumIntent.setType("image/*");
//        activity.startActivityForResult(openAlbumIntent, requescode);

        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            if (activity == null) {
                fragment.startActivityForResult(Intent.createChooser(intent, ""),
                        requescode);
            } else {
                activity.startActivityForResult(Intent.createChooser(intent, ""),
                        requescode);
            }
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (activity == null) {
                fragment.startActivityForResult(Intent.createChooser(intent, ""),
                        requescode);
            } else {
                activity.startActivityForResult(Intent.createChooser(intent, ""),
                        requescode);
            }
        }
    }

    public interface OnResultLisetener {
        void success(int index, String path);
    }

}
