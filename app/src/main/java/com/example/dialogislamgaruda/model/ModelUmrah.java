package com.example.dialogislamgaruda.model;

import android.widget.VideoView;

public class ModelUmrah {
    private String judul, title;
    private int gambar;

    public ModelUmrah(String judul, String title, int gambar) {
        this.judul = judul;
        this.title = title;
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }
}
