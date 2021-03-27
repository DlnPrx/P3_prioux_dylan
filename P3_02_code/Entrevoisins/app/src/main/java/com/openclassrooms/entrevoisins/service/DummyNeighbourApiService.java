package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private int mNeighbourIndex;
    private Neighbour mNeighbour;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     *
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        List<Neighbour> favNeighbours = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.getFavorite()) {
                favNeighbours.add(neighbour);
            }
        }
        return favNeighbours;
    }

    @Override
    public void changeNeighbourStatus(Neighbour neighbour) {
        mNeighbourIndex = getNeighbours().indexOf(neighbour);
        mNeighbour = neighbours.get(mNeighbourIndex);

        if (mNeighbour.getFavorite()) {

            mNeighbour.setFavorite(false);



        } else if (!mNeighbour.getFavorite()) {

            mNeighbour.setFavorite(true);


        }

    }


}
