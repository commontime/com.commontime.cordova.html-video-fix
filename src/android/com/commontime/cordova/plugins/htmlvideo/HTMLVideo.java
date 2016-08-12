package com.commontime.cordova.plugins.htmlvideo;

import android.content.res.AssetManager;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HTMLVideo extends CordovaPlugin {

    private static final String TAG = "HTMLVideo";
    private static final String FIX_VIDEO = "fixVideo";
    private static final String HTMLVIDEOS = "htmlvideos";
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
            // New thread
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String videoPath = args.getString(0);
                        AssetManager am = cordova.getActivity().getAssets();
                        InputStream is = am.open("www" + videoPath );
                        File cacheDir = cordova.getActivity().getCacheDir();
                        File outputDir = new File(cacheDir, HTMLVIDEOS);
                        File outputFile = File.createTempFile("video", ".mp4", outputDir);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        IOUtils.copy(is, fos);
                        callbackContext.success(outputFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callbackContext.error(e.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callbackContext.error(e.getMessage());
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
