package com.dev.mvvmex.utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RetrofitUtil {

    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(value, MediaType.parse("multipart/form-data"));
    }

    public static MultipartBody.Part getRequestPart(String key, File file) {
        RequestBody fileBody = MultipartBody.create(file, MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData(key, file.getName(), fileBody);
    }

    public static MultipartBody.Part getEmptyRequestPart(String key) {
        RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));

        return MultipartBody.Part.createFormData(key, "", attachmentEmpty);
    }
}
