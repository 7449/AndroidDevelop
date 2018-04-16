package com.example.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.album.Album;
import com.album.AlbumImageLoader;
import com.album.model.AlbumModel;
import com.album.model.FinderModel;
import com.album.ui.annotation.FrescoType;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.common.ImageLoaderUtils;
import com.example.ExampleAlbumListener;
import com.example.R;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleAlbumFragment extends BaseFragment {


    private ExampleAlbumListener simpleAlbumListener;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    XRecyclerViewAdapter<AlbumModel> mAdapter;

    @Override
    protected void initCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 3));
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_example_album)
                        .onXBind((holder, position, model) -> ImageLoaderUtils.display(holder.getImageView(R.id.item_album_iv), model.getPath()))
        );

        simpleAlbumListener = new ExampleAlbumListener(container.getContext()) {
            @Override
            public void onAlbumActivityBackPressed() {
                super.onAlbumActivityBackPressed();
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }

            @Override
            public void onAlbumResources(@NonNull List<AlbumModel> list) {
                super.onAlbumResources(list);
                mAdapter.removeAll();
                mAdapter.addAllData(list);
            }
        };
        Album
                .getInstance()
                .setAlbumImageLoader(new GlideImageLoader())
                .setAlbumListener(simpleAlbumListener)
                .start(container.getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Album.getInstance().setAlbumImageLoader(null);
        simpleAlbumListener = null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_album_example;
    }


    private static class GlideImageLoader implements AlbumImageLoader {

        private RequestOptions requestOptions;

        GlideImageLoader() {
            requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .centerCrop();
        }

        @Override
        public void displayAlbum(@NonNull ImageView view, int width, int height, @NonNull AlbumModel albumModel) {
            Glide
                    .with(view.getContext())
                    .load(albumModel.getPath())
                    .apply(requestOptions.override(width, height))
                    .into(view);
        }

        @Override
        public void displayAlbumThumbnails(@NonNull ImageView view, @NonNull FinderModel finderModel) {
            Glide
                    .with(view.getContext())
                    .load(finderModel.getThumbnailsPath())
                    .apply(requestOptions)
                    .into(view);
        }

        @Override
        public void displayPreview(@NonNull ImageView view, @NonNull AlbumModel albumModel) {
            Glide
                    .with(view.getContext())
                    .load(albumModel.getPath())
                    .apply(requestOptions)
                    .into(view);
        }

        @Override
        public ImageView frescoView(@NonNull Context context, @FrescoType int type) {
            return null;
        }

    }
}
