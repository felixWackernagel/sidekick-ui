package de.wackernagel.android.example.sidekick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SimpleAdapter adapter = new SimpleAdapter();
        adapter.setItems(Arrays.asList(
                new SimpleItem( "Widgets - AspectRatioImageView", AspectRatioImageViewActivity.class ),
                new SimpleItem( "Widgets - CircularRevealView", RevealViewActivity.class ),
                new SimpleItem( "Widgets - IndicatorView", IndicatorActivity.class ),
                new SimpleItem( "Utils - Tooltip", TooltipActivity.class ),
                new SimpleItem( "Utils - ColorFilterUtils", ColorFilterUtilsActivity.class ),
                new SimpleItem( "Utils - Device and Network", DeviceActivity.class ),
                new SimpleItem( "Utils - Drawable Tinting", TintingActivity.class ),
                new SimpleItem( "Helper - Grid Gutter Decoration", GridGutterDecorationActivity.class ),
                new SimpleItem( "Resources - Colors", ColorsActivity.class ) ));

        final RecyclerView recyclerView = ( RecyclerView ) findViewById(R.id.recyclerView);
        recyclerView.setAdapter( adapter );
    }

    private static class SimpleItem {
        final String name;
        final Class<?> activity;

        SimpleItem(String name, Class<?> activity) {
            this.name = name;
            this.activity = activity;
        }
    }

    private static class SimpleAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
        private ArrayList<SimpleItem> items = new ArrayList<>();
        private LayoutInflater inflater = null;

        void setItems( List<SimpleItem> items ) {
            clearItems();
            addItems(items);
        }

        void addItems( List<SimpleItem> items ) {
            this.items.addAll( items );
            notifyItemRangeInserted( items.size(), items.size() );
        }

        void clearItems() {
            int size = items.size();
            this.items.clear();
            notifyItemRangeRemoved(0, size);
        }

        SimpleItem getItem( int position ) {
            return items.get( position );
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if( inflater == null )
                inflater = LayoutInflater.from( parent.getContext() );
            return new SimpleViewHolder(inflater.inflate( R.layout.simple_item, parent, false ) );
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            final int adaptPos = holder.getAdapterPosition();
            holder.text.setText( items.get( position ).name );
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( adaptPos != RecyclerView.NO_POSITION )
                        v.getContext().startActivity(new Intent(v.getContext(), getItem(adaptPos).activity));
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        SimpleViewHolder(View itemView) {
            super(itemView);
            text = ( TextView ) itemView.findViewById(R.id.text);
        }
    }

}
