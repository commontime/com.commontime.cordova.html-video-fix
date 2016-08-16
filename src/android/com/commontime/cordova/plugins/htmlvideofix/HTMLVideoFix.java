package com.commontime.cordova.plugins.htmlvideofix;

import android.content.res.AssetManager;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class HTMLVideoFix extends CordovaPlugin {

    private static final String TAG = "HTMLVideoFix";
    private static final String FIX_VIDEO = "fixVideo";
    private static final String HTMLVIDEOS = "htmlvideos";
    private static final String INFINITY = "infinity";
    private File[] deleteMe;

    @Override
    protected void pluginInitialize() {
        cleanUp();
    }

    private void cleanUp() {
        File cacheDir = cordova.getActivity().getCacheDir();
        File outputDir = new File(cacheDir, HTMLVIDEOS);
        outputDir.mkdirs();
        try {
            FileUtils.cleanDirectory(outputDir);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals(FIX_VIDEO)) {
            final String videoPath = args.getString(0);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    String ext = FilenameUtils.getExtension(videoPath);
                    String name = FilenameUtils.getBaseName(videoPath);
                    URI uri = URI.create(videoPath.toString());
                    AssetManager am = cordova.getActivity().getAssets();
                    String filePath = uri.toString().replace("file:///" + INFINITY, "");
                    try {
                        InputStream is = am.open("www" + filePath);
                        File cacheDir = cordova.getActivity().getCacheDir();
                        File outputDir = new File(cacheDir, HTMLVIDEOS);
                        File outputFile = File.createTempFile(name, "." + ext, outputDir);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        IOUtils.copy(is, fos);
                        callbackContext.success(outputFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        cleanUp();
    }
}
