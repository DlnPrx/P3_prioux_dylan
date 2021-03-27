package com.openclassrooms.entrevoisins.service;

import android.view.View;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */

@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    //add new neighbour
    @Test
    public void createNeighbourWithSuccess() {
        Neighbour neighbour = new Neighbour(0, "Jean", "0", "", "", "", false);
        List<Neighbour> neighbours = service.getNeighbours();
        int listeSizeOld = neighbours.size();
        service.createNeighbour(neighbour);
        int listeSizeNew = neighbours.size();
        Assert.assertEquals(listeSizeOld + 1, listeSizeNew);
    }



    //add neighbour to favorite
    @Test
    public void addNeighbourToFavorite() {
        Neighbour neighbourAddToFavorite = service.getNeighbours().get(0);
        neighbourAddToFavorite.setFavorite(true);
        assertTrue(service.getFavoriteNeighbours().contains(neighbourAddToFavorite));
    }

    //remove neighbour from favorite
    @Test
    public void removeNeighbourFromFavorite() {
        Neighbour neighbourAddToFavorite = service.getNeighbours().get(0);
        neighbourAddToFavorite.setFavorite(false);
        assertFalse(neighbourAddToFavorite.getFavorite());
    }
    //
    @Test
    public void onlyFavoriteOnFavList() {
        Neighbour neighbourAddToFavorite = service.getNeighbours().get(0);
        Neighbour noFavoriteNeighbour = service.getNeighbours().get(1);
        neighbourAddToFavorite.setFavorite(true);
        noFavoriteNeighbour.setFavorite(false);
        assertTrue(service.getFavoriteNeighbours().contains(neighbourAddToFavorite));
        assertFalse(service.getFavoriteNeighbours().contains(noFavoriteNeighbour));
    }
}
