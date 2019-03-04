package com.example.dialogislamgaruda.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.dialogislamgaruda.R;
import com.example.dialogislamgaruda.model.ModelHaji;

import java.util.ArrayList;

public class AdapterHaji extends RecyclerView.Adapter {
    private ArrayList<ModelHaji> dataSet;
    private Context mContext;
    private MediaPlayer mPlayer;
    private Boolean statusLagu = false;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ModelHaji.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new TextTypeViewHolder(view);
            case ModelHaji.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
                return new ImageTypeViewHolder(view);
            case ModelHaji.AUDIO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_type, parent, false);
                return new AudioTypeViewHolder(view);
            case ModelHaji.VIDEO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_type, parent, false);
                return new VideoTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int listPosition) {
        final ModelHaji object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case ModelHaji.TEXT_TYPE:
                    ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    break;
                case ModelHaji.IMAGE_TYPE:
                    ((ImageTypeViewHolder) holder).txtType.setText(object.text);
                    ((ImageTypeViewHolder) holder).txtTitle.setText(object.title);
                    ((ImageTypeViewHolder) holder).image.setImageResource(object.data);
                    break;
                case ModelHaji.AUDIO_TYPE:
                    ((AudioTypeViewHolder) holder).txtType.setText(object.text);
                    ((AudioTypeViewHolder) holder).txtTitle.setText(object.title);
                    ((AudioTypeViewHolder) holder).fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!statusLagu){
                                if (mPlayer == null){
                                    mPlayer = MediaPlayer.create(mContext, object.data);
                                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            stopPlayer();
                                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                                                }
                                            });
                                        }
                                    });
                                }
                                mPlayer.start();
                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.ic_pause_black_24dp);
                                statusLagu = true;
                            } else {
                                if (mPlayer != null){
                                    stopPlayer();
                                    ((Activity) mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                                        }
                                    });
                                }
                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                            }
                        }
                    });
                    break;
                case ModelHaji.VIDEO_TYPE:
                    String videoPath = "android.resource://" + mContext.getPackageName() + "/" + object.data;
                    Uri uri = Uri.parse(videoPath);
                    ((VideoTypeViewHolder) holder).videoViewHaji.setVideoURI(uri);
                    MediaController mediaController = new MediaController(mContext);
                    ((VideoTypeViewHolder) holder).videoViewHaji.setMediaController(mediaController);
                    mediaController.setAnchorView(((VideoTypeViewHolder) holder).videoViewHaji);
                    ((VideoTypeViewHolder) holder).txtType.setText(object.text);
                    ((VideoTypeViewHolder) holder).txtTitle.setText(object.title);
                    break;
            }
        }
    }

    private void stopPlayer(){
        if (mPlayer != null){
            mPlayer.release();
            mPlayer = null;
        }
        statusLagu = false;
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataSet.get(position).type) {
            case 0:
                return ModelHaji.TEXT_TYPE;
            case 1:
                return ModelHaji.IMAGE_TYPE;
            case 2:
                return ModelHaji.AUDIO_TYPE;
            case 3:
                return ModelHaji.VIDEO_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        CardView cardView;

        TextTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = itemView.findViewById(R.id.type);
            this.cardView = itemView.findViewById(R.id.card_view);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType, txtTitle;
        ImageView image;

        ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.txtType = itemView.findViewById(R.id.type);
            this.txtTitle = itemView.findViewById(R.id.title);
            this.image = itemView.findViewById(R.id.background);
        }
    }

    public static class AudioTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType, txtTitle;
        FloatingActionButton fab;
        SeekBar seekBar;
        AudioTypeViewHolder(View itemView) {
            super(itemView);
            this.txtType = itemView.findViewById(R.id.type);
            this.txtTitle = itemView.findViewById(R.id.title);
            this.fab = itemView.findViewById(R.id.fab);
            this.seekBar = itemView.findViewById(R.id.seekBar);
        }
    }

    public static class VideoTypeViewHolder extends RecyclerView.ViewHolder {
        TextView txtType, txtTitle;
        VideoView videoViewHaji;
        VideoTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtType = itemView.findViewById(R.id.type);
            this.txtTitle = itemView.findViewById(R.id.title);
            this.videoViewHaji = itemView.findViewById(R.id.videoViewHaji);
        }
    }

    public AdapterHaji(ArrayList<ModelHaji>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
    }

    public void stopMedia(){
        mPlayer.stop();
        mPlayer.release();
    }

}
