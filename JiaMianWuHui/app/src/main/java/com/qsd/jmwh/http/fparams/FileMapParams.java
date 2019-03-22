package com.qsd.jmwh.http.fparams;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileMapParams implements Serializable {
    private ArrayList<MultipartBody.Part> body = new ArrayList<>();


    public ArrayList<MultipartBody.Part> getBody() {
        return body;
    }

    public FileMapParams(ArrayList<File> files) {
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            body.add(multipartBody);
        }


    }

}
