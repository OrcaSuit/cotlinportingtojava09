package ceti.co.copyproj1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PhotoFragment extends Fragment {
    private final static String ARG_URI = "uri";
    private static String uri = "";
    ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = getView().findViewById(R.id.imageView);

        if(getArguments() != null){
            uri = getArguments().getString(ARG_URI);
        }

        Glide.with(this).load(uri).into(imageView);
    }



    public static PhotoFragment newInstance(String uri) {

        Bundle args = new Bundle();
        args.putString(ARG_URI, uri);

        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
