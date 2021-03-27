
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.DetailsActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */

@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private DetailsActivity mDetailsActivity;
    private NeighbourApiService mNeighbourApiService;
    private DummyNeighbourApiService mDummyNeighbourApiService;
    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.list_neighbours))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * when we click on item, DetailsActivity is launched
     */
    @Test
    public void transactionToDetailsActivity() {

        //check if element of DetailsActivity doesn't exist
        onView(withId(R.id.activityDetailsConstraintLayout)).check(doesNotExist());
        //When perform a click on item open DetailsActivity
        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(0, click()));
        //check if element of DetailsActivity exist
        onView(withId(R.id.activityDetailsConstraintLayout)).check(matches(isDisplayed()));
        //press back button
        pressBack();
        //check if element of DetailsActivity doesn't exist
        onView(withId(R.id.activityDetailsConstraintLayout)).check(doesNotExist());
    }

    /**
     * name isn't null on DetailsActivity
     */
    @Test
    public void nameUserIsNotEmptyOnDetailsActivity() {

        (onView(withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.cardViewNeighbourName)).check(matches(withText("Caroline")));
    }

    /**
     * favoriteList contain only favorite neighbour
     * add 3, check if fav list = 3
     */
    @Test
    public void onlyFavoriteOnFavoriteTab() {

        (onView(withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(0, click()));
        (onView(withId(R.id.floatingActionButtonFav))).perform((click()));
        pressBack();
        (onView(withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(1, click()));
        (onView(withId(R.id.floatingActionButtonFav))).perform((click()));
        pressBack();
        (onView(withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(2, click()));
        (onView(withId(R.id.floatingActionButtonFav))).perform((click()));
        pressBack();
        swipeLeft();
        onView(withId(R.id.list_favorite_neighbours)).check(withItemCount(3));
    }
}