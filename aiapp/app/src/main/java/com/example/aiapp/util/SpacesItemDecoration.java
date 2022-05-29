package com.example.aiapp.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author yzw
 * @date 2022/3/21
 * @describe 设置recyclerView上下间距的辅助类
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int count = 0;
    private int left = 0;
    private int top = 0;

    public SpacesItemDecoration(int count, int space) {
        this(count, space, space);
    }

    public SpacesItemDecoration(int count, int horizontal, int vertical) {
        this.count = count;
        this.left = horizontal;
        this.top = vertical;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        //当前条目的下表
        int position = parent.getChildAdapterPosition(view);
        setCount(position, outRect);
    }

    private void setCount(int position, Rect outRect) {
        outRect.left = getLeft(position);
        outRect.right = getRight(position);

        //第一排，上面不能加
        if (position > count - 1) {
            outRect.top = top;
        }
    }

    //最左侧一排不能加左边距
    private int getLeft(int position) {
        if (position % count != 0) {
            return left / 2;
        } else {//最左侧
            return 0;
        }
    }

    //最右侧一排不能加右边距
    private int getRight(int position) {
        if ((position + 1) % count != 0) {
            return left / 2;
        } else {//最右侧
            return 0;
        }
    }

}