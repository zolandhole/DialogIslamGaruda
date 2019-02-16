package com.example.dialogislamgaruda.model;

public class ModelHaji {
    public static final int TEXT_TYPE=0;
    public static final int IMAGE_TYPE=1;
    public static final int AUDIO_TYPE=2;
    public static final int VIDEO_TYPE=3;

    public int type;
    public int data;
    public String text, title;

    public ModelHaji(int type, String text, String title, int data) {
        this.type = type;
        this.text = text;
        this.data = data;
        this.title = title;
    }
}
