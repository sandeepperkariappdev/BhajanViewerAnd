package com.sbtso.BhajanViewer;

/**
 * Created by sandeepperkari on 8/3/16.
 */

import android.os.AsyncTask;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
/**
 * Created by sandeepperkari on 7/31/16.
 */
public class DownloadFile extends AsyncTask<Void, Void, File>{
    private DbxClientV2 dbxClient;
    private Callback callback;
    private Context context;
    private Exception exception;

    public interface Callback {
        void onDownloadComplete(File result);
        void onError(Exception e);
    }
    DownloadFile( Context context, DbxClientV2 dbxClient, Callback callback){
        this.dbxClient = dbxClient;
        this.callback = callback;
        this.context = context;
    }
    @Override
    protected  File doInBackground(Void... params){
        String FILENAME = "hello_file";
        try{
            File file = new File(context.getFilesDir(),FILENAME);
            Metadata metadata =  dbxClient.files().getMetadata("/SaiBhajanViewer.xml");
            OutputStream outputStream = new FileOutputStream(file);
            if(metadata.getPathLower() != null){
                dbxClient.files().download(metadata.getPathLower()).download(outputStream);
            }
            return file;
        } catch (DbxException e){
            callback.onError(e);
        } catch (IOException e){
            callback.onError(e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(File result) {
        super.onPostExecute(result);
        if (exception != null) {
            callback.onError(exception);
        } else {
            callback.onDownloadComplete(result);
        }
        //Toast.makeText(context, "File downloaded successfully", Toast.LENGTH_SHORT).show();
    }
}

