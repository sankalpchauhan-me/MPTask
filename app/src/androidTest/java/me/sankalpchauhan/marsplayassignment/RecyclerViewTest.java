package me.sankalpchauhan.marsplayassignment;


import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.sankalpchauhan.marsplayassignment.view.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class RecyclerViewTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,
            true);

    @Before
    public void waitToLoad() {

    }

    @Test
    public void testCaseForRecyclerViewClick() {
        ViewInteraction element = onView(allOf(TestUtility.childAtPosition(
                allOf(withId(R.id.rvEntry),
                        TestUtility.childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)),
                0),
                isDisplayed()));
        if (TestUtility.waitUntilVisible((long) 5000, element)) {
            element.perform(click());
            element.check(matches(isDisplayed()));
        }
    }

    @Test
    public void testCaseForRecyclerViewScroll() {
        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.rvEntry);
        int itemCount = recyclerView.getAdapter().getItemCount();

        ViewInteraction rc = onView(ViewMatchers.withId(R.id.rvEntry))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityRule.getActivity().getWindow().getDecorView())));
        if (TestUtility.waitUntilVisible((long) 5000, rc)) {
            rc.perform(RecyclerViewActions.scrollToPosition(itemCount - 1));
        }
    }


}
