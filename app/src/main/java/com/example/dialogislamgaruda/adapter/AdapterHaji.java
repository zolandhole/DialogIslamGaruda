package com.example.dialogislamgaruda.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dialogislamgaruda.R;
import com.example.dialogislamgaruda.model.ModelHaji;

import java.util.ArrayList;

public class AdapterHaji extends RecyclerView.Adapter {
    private ArrayList<ModelHaji> dataSet;
    private Context mContext;
    private MediaPlayer mPlayer;
    private boolean fabStateVolume = false;

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
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int listPosition) {
        ModelHaji object = dataSet.get(listPosition);
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
                    ((AudioTypeViewHolder) holder).fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (fabStateVolume) {
                                if (mPlayer.isPlaying()) {
                                    mPlayer.stop();
                                    mPlayer.release();
                                }
                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                                fabStateVolume = false;
                            } else {
                                mPlayer = MediaPlayer.create(mContext, R.raw.lagu);
                                mPlayer.setLooping(false);
                                mPlayer.start();
                                ((AudioTypeViewHolder) holder).fab.setImageResource(R.drawable.ic_pause_black_24dp);
                                fabStateVolume = true;
                            }
                        }
                    });
                    break;
            }
        }
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

        TextView txtType;
        FloatingActionButton fab;

        AudioTypeViewHolder(View itemView) {
            super(itemView);
            this.txtType = itemView.findViewById(R.id.type);
            this.fab = itemView.findViewById(R.id.fab);
        }
    }

    public AdapterHaji(ArrayList<ModelHaji>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
    }
}
