package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private NeighbourApiService mApiService;
    private Neighbour mNeighbour;
    private int mNeighbourIndex;

    @BindView(R.id.neighbourAvatar)
    ImageView mNeighbourAvatar;
    @BindView(R.id.neighbourNameAvatar)
    TextView mNeighbourAvatarName;
    @BindView(R.id.cardViewNeighbourName)
    TextView mNeighbourName;
    @BindView(R.id.neighbourPhone)
    TextView mNeighbourPhone;
    @BindView(R.id.neighbourAdress)
    TextView mNeighbourAdress;
    @BindView(R.id.neighbourUrl)
    TextView mNeighbourUrl;
    @BindView(R.id.neighbourAbout)
    TextView mNeighbourAbout;
    @BindView(R.id.floatingActionButtonFav)
    FloatingActionButton mFloatingActionButtonFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // Ajout butterKnifeBind
        ButterKnife.bind(this);
        //configuration du retour
        configureSupportActionBar();
        //configuration du voisin
        configureNeighbour();
        //configuration FAB
        configureFloatingActionButton();
        configureFloatingActionButtonColor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureNeighbour() {

        mApiService = DI.getNeighbourApiService();
        mNeighbour = (Neighbour) getIntent().getParcelableExtra("value");
        mNeighbourIndex = mApiService.getNeighbours().indexOf(mNeighbour);
        //Attribution des widgets
        //avatarName
        mNeighbourAvatarName.setText(mNeighbour.getName().toString());
        //name
        mNeighbourName.setText(mNeighbour.getName().toString());
        //Avatar
        Glide.with(this).load(mNeighbour.getAvatarUrl().toString()).into(mNeighbourAvatar);
        //Adresse
        mNeighbourAdress.setText(mNeighbour.getAddress().toString());
        //Phone
        mNeighbourPhone.setText(mNeighbour.getPhoneNumber().toString());
        //Url
        mNeighbourUrl.setText("www.facebook.fr/" + mNeighbour.getName().toString());
        //about
        mNeighbourAbout.setText(mNeighbour.getAboutMe().toString());

    }

    //Ajoute bouton retour
    private void configureSupportActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //configure le fab
    private void configureFloatingActionButton() {

        mFloatingActionButtonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mApiService.changeNeighbourStatus(mNeighbour);
                configureFloatingActionButtonColor();
                makeToast();

            }

        });


    }

    //configure la couleur du fab
    private void configureFloatingActionButtonColor() {
        if (mApiService.getNeighbours().get(mNeighbourIndex).getFavorite()) {
            mFloatingActionButtonFav.setImageResource(R.drawable.ic_star_yellow_24dp);

        }
        if (!mApiService.getNeighbours().get(mNeighbourIndex).getFavorite()) {
            mFloatingActionButtonFav.setImageResource(R.drawable.ic_star_border_white_24dp);
        }

    }

    //toast de confirmation
    private void makeToast() {
        if (mApiService.getNeighbours().get(mNeighbourIndex).getFavorite()) {
            Toast.makeText(DetailsActivity.this, " Voisin ajouté aux favoris", Toast.LENGTH_SHORT).show();
        }
        if (!mApiService.getNeighbours().get(mNeighbourIndex).getFavorite()) {
            Toast.makeText(DetailsActivity.this, " Voisin retiré des favoris", Toast.LENGTH_SHORT).show();
        }

    }


}
