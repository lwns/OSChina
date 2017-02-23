package com.core.op.bindingadapter.common.itemviews;


import com.core.op.bindingadapter.common.BaseItemViewSelector;
import com.core.op.bindingadapter.common.ItemView;

/**
 * An {@link com.core.op.bindingadapter.common.ItemViewSelector} that selects item views by
 * delegating to each item. Items must implement {@link ItemViewModel}. <b>Important note:</b> If
 * you plan to use this in a {@link android.widget.ListView}, you <em>must</em> subclass this and
 * implement {@link com.core.op.bindingadapter.common.ItemViewSelector#viewTypeCount()} to return the number of different types of
 * views possible in your list.
 */
public class ItemViewModelSelector<T extends ItemViewModel> extends BaseItemViewSelector<T> {
    @Override
    public void select(ItemView itemView, int position, T item) {
        item.itemView(itemView);
    }
}
