package com.core.op.bindingadapter.image;

//import com.facebook.common.executors.UiThreadImmediateExecutorService;
//import com.facebook.common.references.CloseableReference;
//import com.facebook.datasource.DataSource;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.facebook.imagepipeline.common.ResizeOptions;
//import com.facebook.imagepipeline.core.ImagePipeline;
//import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
//import com.facebook.imagepipeline.image.CloseableImage;
//import com.facebook.imagepipeline.request.ImageRequest;
//import com.facebook.imagepipeline.request.ImageRequestBuilder;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.core.op.lib.command.ReplyCommand;

/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {

    @BindingAdapter(value = {"uri", "onSuccessCommand"}, requireAll = false)
    public static void setImageUri(ImageView imageView,
                                   String uri,
                                   ReplyCommand<GlideDrawable> replyCommand) {
        if (!TextUtils.isEmpty(uri)) {
            if (replyCommand == null)
                Glide.with(imageView.getContext())
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            else
                Glide.with(imageView.getContext())
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                imageView.setImageDrawable(resource);
                                replyCommand.execute(resource);
                            }
                        });
        }
    }
//
//
//    @BindingAdapter(value = {"uri", "placeholderImageRes", "request_width", "request_height", "onSuccessCommand", "onFailureCommand"}, requireAll = false)
//    public static void loadImage(final ImageView imageView, String uri,
//                                 @DrawableRes int placeholderImageRes,
//                                 int width, int height,
//                                 final ReplyCommand<Bitmap> onSuccessCommand,
//                                 final ReplyCommand<DataSource<CloseableReference<CloseableImage>>> onFailureCommand) {
//        imageView.setImageResource(placeholderImageRes);
//        if (!TextUtils.isEmpty(uri)) {
//            ImagePipeline imagePipeline = Fresco.getImagePipeline();
//            ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri));
//            if (width > 0 && height > 0) {
//                builder.setResizeOptions(new ResizeOptions(width, height));
//            }
//            ImageRequest request = builder.build();
//            DataSource<CloseableReference<CloseableImage>>
//                    dataSource = imagePipeline.fetchDecodedImage(request, imageView.getContext());
//            dataSource.subscribe(new BaseBitmapDataSubscriber() {
//                @Override
//                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//                    if (onFailureCommand != null) {
//                        onFailureCommand.execute(dataSource);
//                    }
//                }
//
//                @Override
//                protected void onNewResultImpl(Bitmap bitmap) {
//                    imageView.setImageBitmap(bitmap);
//                    if (onSuccessCommand != null) {
//                        onSuccessCommand.execute(bitmap);
//                    }
//                }
//            }, UiThreadImmediateExecutorService.getInstance());
//        }
//    }

}

