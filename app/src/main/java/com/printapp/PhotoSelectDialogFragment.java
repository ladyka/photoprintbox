package com.printapp;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.printapp.models.Photo;
import com.squareup.picasso.Picasso;

public class PhotoSelectDialogFragment extends DialogFragment{

    Button photoAddButton;
    ImageView selectedPhoto;
    NumberPicker numberPicker;
    Photo photo;
    int count;
    public PhotoSelectDialogFragment() {
    }

    public interface PhotoSelectListener{
        void OnPhotoSelectListener(Photo photo);
    }
    public static PhotoSelectDialogFragment newInstance(Photo photo,String action) {
        Bundle args = new Bundle();
        args.putSerializable("PHOTO",photo);
        args.putString("ACTION",action);
        PhotoSelectDialogFragment fragment = new PhotoSelectDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photo = (Photo) getArguments().getSerializable("PHOTO");

        selectedPhoto = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(this.getActivity()).load(photo.photo_604)
                .placeholder(R.drawable.image_placeholder).into(selectedPhoto);

        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(5);
        numberPicker.setMinValue(0);

        photoAddButton = (Button) view.findViewById(R.id.button);
        switch (getArguments().getString("ACTION")){
            case "ADD":{
                photoAddButton.setText("ДОБАВИТЬ");
                break;
            }
            case "UPDATE":{
                photoAddButton.setText("СОХРАНИТЬ");
                break;
            }

        }
        photoAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.count = numberPicker.getValue();
                PhotoSelectListener photoSelectListener = (PhotoSelectListener) getActivity();
                photoSelectListener.OnPhotoSelectListener(photo);
                dismiss();
            }
        });
    }
}