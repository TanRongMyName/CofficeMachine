package com.coffice.shengtao.cofficemachine.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.pictureframe.base.ImageLoaderManager;
import com.coffice.shengtao.cofficemachine.pictureframe.base.ImageLoaderOptions;

/**
 * 万能视频的 viewhoder 一列数据的绑定
 */
public class BaseRecyclerHoder extends RecyclerView.ViewHolder {
    private SparseArray<View>views;
    private Context context;
    public BaseRecyclerHoder(Context context,@NonNull View itemView) {
        super(itemView);
        this.context=context;
        views=new SparseArray<>(8);
    }
    /**
     * 取得一个RecyclerHolder对象
     * @param context 上下文
     * @param itemView 子项
     * @return 返回一个RecyclerHolder对象
     */
    public static BaseRecyclerHoder getRecyclerHolder(Context context,View itemView){
        return new BaseRecyclerHoder(context,itemView);
    }
    public SparseArray<View> getViews(){
        return this.views;
    }

    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     * @param viewId 控件的id
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if (view == null ){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 设置字符串
     */
    public BaseRecyclerHoder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    public TextView getTextView(int viewId){
        return getView(viewId);
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHoder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     77      * 设置图片
     78      */
    public BaseRecyclerHoder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public BaseRecyclerHoder setImageByUrl(final int viewId, final String url){
        //Picasso.with(context).load(url).into((ImageView) getView(viewId));
        //        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        //        ImageLoader.getInstance().displayImage(url, (ImageView) getView(viewId));
        //使用 异步执行加载 图片
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ImageLoaderOptions op=new ImageLoaderOptions.Builder(getView(viewId),url).imageRadiusDp(12).build();
//                ImageLoaderManager.getInstance().showImage(op);
//            }
//        }).start();
        ImageLoaderOptions op=new ImageLoaderOptions.Builder(getView(viewId),url).imageRadiusDp(12).build();
        ImageLoaderManager.getInstance().showImage(op);
        return this;
    }
}
