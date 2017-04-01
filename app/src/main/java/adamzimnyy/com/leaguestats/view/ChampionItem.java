package adamzimnyy.com.leaguestats.view;

import adamzimnyy.com.leaguestats.R;
import adamzimnyy.com.leaguestats.api.RetrofitBuilder;
import adamzimnyy.com.leaguestats.model.realm.Champion;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.List;

public class ChampionItem extends AbstractItem<ChampionItem, ChampionItem.ViewHolder> {
    public String name;
    public String key;
    public String image;
    public boolean pinned;

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChampionItem that = (ChampionItem) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }

    //The unique ID for this type of item
    @Override
    public int getType() {
        return R.id.champion_item_id;
    }

    //The layout to be used for this type of item
    @Override
    public int getLayoutRes() {
        return R.layout.champion_list_item;
    }

    //The logic to bind your data to the root
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        //call super so the selection is already handled for you
        super.bindView(viewHolder, payloads);

        //bind our data
        //set the text for the name
        viewHolder.name.setText(name);
        //set the text for the description or hide
       ImageLoader.getInstance().displayImage(RetrofitBuilder.RIOT_IMAGE + "champion/" + image, viewHolder.image);
        viewHolder.pin.setVisibility(pinned? View.VISIBLE : View.GONE);
    }

    //reset the root here (this is an optional method, but recommended)
    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.name.setText(null);
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    //The viewHolder used for this item. This viewHolder is always reused by the RecyclerView so scrolling is blazing fast
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected CircularImageView image;
        ImageView pin;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.name);
            this.image = (CircularImageView) view.findViewById(R.id.image);
            this.pin = (ImageView) view.findViewById(R.id.pin);
        }
    }
}
