package bgroseclose.videogamesearch.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bgroseclose.videogamesearch.Activity.MainActivity;
import bgroseclose.videogamesearch.Models.GameModel;
import bgroseclose.videogamesearch.Models.SearchResult;
import bgroseclose.videogamesearch.R;
import butterknife.BindView;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private Context context;
    private SearchResult result;

    public GameListAdapter(Context context, SearchResult result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View gameListView = inflater.inflate(R.layout.game_list_view, viewGroup, false);
        return new ViewHolder(gameListView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final GameModel game = result.getGames().get(position);
        viewHolder.mGameTitle.setText(game.getGameName());
        Picasso.get()
                .load(game.getImages().getThumbNail())
                .error(R.drawable.ic_broken_image)
                .into(viewHolder.mGameImage);
        viewHolder.mGameListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle(game.getGameName())
                        .setMessage(game.getGameDesc())
                        .setIcon(R.drawable.ic_info)
                        .setNeutralButton(context.getString(R.string.close), null)
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.getSearchCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mGameListView;
        ImageView mGameImage;
        TextView mGameTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mGameListView = itemView.findViewById(R.id.game_list_view);
            mGameImage = itemView.findViewById(R.id.game_list_image);
            mGameTitle = itemView.findViewById(R.id.game_list_text);
        }
    }

}
