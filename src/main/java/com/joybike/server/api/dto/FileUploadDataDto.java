package com.joybike.server.api.dto;

import java.util.Arrays;

/**
 * Created by 58 on 2016/11/18.
 */
public class FileUploadDataDto {

    public FileUploadDataDto(){}

    public FileUploadDataDto(byte[] file) {
        this.file = file;
    }

    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileUploadDataDto{" +
                "file=" + Arrays.toString(file) +
                '}';
    }
}
