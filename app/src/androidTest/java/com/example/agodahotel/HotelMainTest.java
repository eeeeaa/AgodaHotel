package com.example.agodahotel;

import android.content.ComponentName;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.example.agodahotel.Model.Hotel;
import com.example.agodahotel.Model.HotelAdapter;
import com.google.common.collect.Ordering;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class HotelMainTest {
    /**
     * UI tests:
     * Recycler View:
     *      sorting
     *      filtering
     * **/
    @Rule
    public ActivityTestRule<HotelMain> activityRule
            = new ActivityTestRule<>(HotelMain.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void RecyclerViewClick(){
        //click on an item in recycler view
        onView(withId(R.id.hotel_list_view))
                .inRoot(withDecorView(Matchers.is(activityRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        //check if it open new activity
        intended(hasComponent(HotelDetail.class.getName()));
    }
    @Test
    public void RecyclerViewScroll(){
        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.hotel_list_view);
        int item_count = recyclerView.getAdapter().getItemCount();
        onView(withId(R.id.hotel_list_view))
                .inRoot(withDecorView(Matchers.is(activityRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.scrollToPosition(item_count-1));

    }
    @Test
    public void RecyclerViewSortAlphabetically(){
        /**check if the recycler view sorted properly when clicking the switch**/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open(Gravity.RIGHT));
        onView(withId(R.id.nav_sort_switch)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isSortedAlphabetically()));
        onView(withId(R.id.nav_sort_switch)).check(matches(isChecked())).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(not(isSortedAlphabetically())));
    }
    @Test
    public void RecyclerViewFilterByRating(){
        /**check if the list is properly filter based on different conditions**/
        ArrayList<Integer> con = new ArrayList<>();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open(Gravity.RIGHT));

        con.add(1);// star condition: 1
        onView(withId(R.id.filter_star_one)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isFiltered(con)));

        con.add(2);// star condition: 1,2
        onView(withId(R.id.filter_star_two)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isFiltered(con)));

        con.add(3); // star condition: 1,2,3
        onView(withId(R.id.filter_star_three)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isFiltered(con)));

        con.add(4); // star condition: 1,2,3,4
        onView(withId(R.id.filter_star_four)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isFiltered(con)));

        con.add(5); // star condition: 1,2,3,4,5
        onView(withId(R.id.filter_star_five)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isFiltered(con)));

        //check when no filter applied (should be equals to when apply all filter)
        onView(withId(R.id.filter_star_one)).perform(click());
        onView(withId(R.id.filter_star_two)).perform(click());
        onView(withId(R.id.filter_star_three)).perform(click());
        onView(withId(R.id.filter_star_four)).perform(click());
        onView(withId(R.id.filter_star_five)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isFiltered(con)));

    }

    @Test
    public void RecyclerViewFilterAndSortScenarios(){
        ArrayList<Integer> con = new ArrayList<>();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open(Gravity.RIGHT));

        con.add(1);
        con.add(3);
        // star condition: 1,3
        onView(withId(R.id.filter_star_one)).perform(click());
        onView(withId(R.id.filter_star_three)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isFiltered(con)));
        //check sorting
        onView(withId(R.id.nav_sort_switch)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isSortedAlphabetically()));
        onView(withId(R.id.nav_sort_switch)).check(matches(isChecked())).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(not(isSortedAlphabetically())));
        onView(withId(R.id.filter_star_one)).perform(click());
        onView(withId(R.id.filter_star_three)).perform(click());

        con.clear();
        con.add(4);
        con.add(2);
        // star condition: 4,2
        //try sort before filtering
        onView(withId(R.id.nav_sort_switch)).perform(click());
        onView(withId(R.id.filter_star_four)).perform(click());
        onView(withId(R.id.filter_star_two)).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(isFiltered(con)));
        //check sorting
        onView(withId(R.id.hotel_list_view)).check(matches(isSortedAlphabetically()));
        onView(withId(R.id.nav_sort_switch)).check(matches(isChecked())).perform(click());
        onView(withId(R.id.hotel_list_view)).check(matches(not(isSortedAlphabetically())));
        onView(withId(R.id.filter_star_four)).perform(click());
        onView(withId(R.id.filter_star_two)).perform(click());

    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    private static Matcher<View> isSortedAlphabetically() {
        return new TypeSafeMatcher<View>() {

            private final ArrayList<String> hotel_name = new ArrayList<>();

            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                HotelAdapter hotelAdapter = (HotelAdapter) recyclerView.getAdapter();
                hotel_name.clear();
                hotel_name.addAll(extractNames(hotelAdapter.getHotelList()));
                return Ordering.natural().isOrdered(hotel_name);
            }

            private ArrayList<String> extractNames(ArrayList<Hotel> hotels) {
                ArrayList<String> hotel_names = new ArrayList<>();
                for(Hotel hotel : hotels){
                    hotel_names.add(hotel.getHotel_name());
                }
                return hotel_names;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has items sorted alphabetically: " + hotel_name);
            }
        };
    }
    private static Matcher<View> isFiltered(final ArrayList<Integer> conditions) {
        return new TypeSafeMatcher<View>() {

            private final ArrayList<Hotel> hotel_star = new ArrayList<>();

            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                HotelAdapter hotelAdapter = (HotelAdapter) recyclerView.getAdapter();
                hotel_star.clear();
                hotel_star.addAll(hotelAdapter.getHotelList());
                for(Hotel hotel : hotel_star){
                   if(!conditions.contains(hotel.getStar())){
                       return false;
                   }
                }
                return true;

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has items filtered by: " + conditions + " Got: " + hotel_star);
            }
        };
    }
}