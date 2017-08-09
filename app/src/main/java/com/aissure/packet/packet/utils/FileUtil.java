package com.aissure.packet.packet.utils;

import android.content.Context;

import com.aissure.packet.packet.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/8/9.
 */

public class FileUtil {
    private static FileUtil instance;
    private Context mContext;
    private FileUtil(Context context){
        this.mContext = context;
    }

    public static FileUtil getInstance(Context context){
        if(instance == null){
            synchronized (FileUtil.class){
                if(instance == null){
                    instance = new FileUtil(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void cpHbSound(){
        String hbSoundPath = C.PATH_HB_SOUND;
        int id = R.raw.hb;
        cpRawToSd(hbSoundPath,id);
    }
    public void cpRawToSd(String path,int id){
        File file = new File(path);
        checkDir(file);
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            if(file.exists()){
                return;
            }
            is = mContext.getResources().openRawResource(id);
            fos = new FileOutputStream(file);
            int tmp = -1;
            byte[] buffer = new byte[1024];
            while ((tmp = is.read(buffer))!=-1){
                fos.write(tmp);

            }
            fos.flush();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(is,fos);
        }
    }

    public void close(Closeable... closeables){
        try{
            if(closeables!=null&&closeables.length>0){
                for (Closeable closeable:closeables){
                    if(closeable!=null){
                        closeable.close();
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void checkDir(File file){
        File dir = file.getParentFile();
        if(!dir.exists()){
            dir.mkdirs();
        }
    }
    public void checkFile(File file)throws IOException{
        checkDir(file);
        if(!file.exists()){
            file.createNewFile();
        }
    }
}
