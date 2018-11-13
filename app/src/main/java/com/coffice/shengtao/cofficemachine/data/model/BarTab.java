package com.coffice.shengtao.cofficemachine.data.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * FragmentTabHost  使用添加 Tab
 */
public class BarTab {
    //标题
    private String title;
    //绑定的Fragment
    private Class<?> cls;
    //整个底部View
    private View view;
    //Bundle
    private Bundle bundle;
    //图标正常情况
    private Object imageNormal;
    //图标选中情况
    private Object imageSelect;
    //底部菜单的2个布局view
    private ItemView itemView;

    public BarTab(String title, Object select, Object not, Class<?> cls){
        this.title=title;
        this.imageSelect=select;
        this.imageNormal=not;
        this.cls=cls;
    }

    public static class ItemView{
        private ImageView image;
        private TextView text;

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public TextView getText() {
            return text;
        }

        public void setText(TextView text) {
            this.text = text;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Object getImageNormal() {
        return imageNormal;
    }

    public void setImageNormal(Object imageNormal) {
        this.imageNormal = imageNormal;
    }

    public Object getImageSelect() {
        return imageSelect;
    }

    public void setImageSelect(Object imageSelect) {
        this.imageSelect = imageSelect;
    }

    public ItemView getItemView() {
        return itemView;
    }

    public void setItemView(ItemView itemView) {
        this.itemView = itemView;
    }
}
