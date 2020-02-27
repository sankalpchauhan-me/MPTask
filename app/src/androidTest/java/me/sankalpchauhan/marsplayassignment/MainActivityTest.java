package me.sankalpchauhan.marsplayassignment;


import android.util.Log;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import me.sankalpchauhan.marsplayassignment.view.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void A_ItemClickTest() {
        //"androidx.constraintlayout.widget.ConstraintLayout"
        findById(R.id.rvEntry, "androidx.constraintlayout.widget.ConstraintLayout", 1, 0);
    }

    @Test
    public void B_ReadMoreClickTest() {
        ViewInteraction cardView = onView(
                allOf(TestUtility.childAtPosition(
                        allOf(withId(R.id.rvEntry),
                                TestUtility.childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        1)),
                        0),
                        isDisplayed()));
        ViewInteraction readMoreButton = onView(
                allOf(withId(R.id.read_more), withText("Read More"),
                        TestUtility.childAtPosition(
                                TestUtility.childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                4),
                        isDisplayed()));

        if (TestUtility.waitUntilVisible((long) 5000, cardView)) {
            cardView.perform(click())
                    .check(matches(isDisplayed()));

        }
        if (TestUtility.waitUntilVisible((long) 5000, readMoreButton)) {
            readMoreButton.check(matches(isDisplayed()));
            readMoreButton.perform(click());
        }
    }

    @Test
    public void C_FullActivityTest() {
        Log.d("C_FullActivityTest", "Large Test May take upto a minute");
        ViewInteraction cardView = onView(
                allOf(TestUtility.childAtPosition(
                        allOf(withId(R.id.rvEntry),
                                TestUtility.childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        1)),
                        0),
                        isDisplayed()));
        if (TestUtility.waitUntilVisible((long) 5000, cardView)) {
            cardView.perform(click());
            cardView.check(matches(isDisplayed()));
        }

        ViewInteraction readMoreButton = onView(
                allOf(withId(R.id.read_more), withText("Read More"),
                        TestUtility.childAtPosition(
                                TestUtility.childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                4),
                        isDisplayed()));
        if (TestUtility.waitUntilVisible((long) 5000, readMoreButton)) {
            readMoreButton.check(matches(isDisplayed()));
            readMoreButton.perform(click());
        }

        ViewInteraction upButton = onView(
                allOf(withContentDescription("Navigate up"),
                        TestUtility.childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        TestUtility.childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));

        if (TestUtility.waitUntilVisible((long) 5000, readMoreButton)) {
            upButton.check(matches(isDisplayed()));
            upButton.perform(click());
        }


        ViewInteraction cardView2 = onView(
                allOf(TestUtility.childAtPosition(
                        allOf(withId(R.id.rvEntry),
                                TestUtility.childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        1)),
                        4),
                        isDisplayed()));

        if (TestUtility.waitUntilVisible((long) 5000, readMoreButton)) {
            cardView2.perform(click());
            cardView2.check(matches(isDisplayed()));
        }


        ViewInteraction readMoreButton2 = onView(
                allOf(withId(R.id.read_more), withText("Read More"),
                        TestUtility.childAtPosition(
                                TestUtility.childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                4),
                        isDisplayed()));

        if (TestUtility.waitUntilVisible((long) 5000, readMoreButton)) {
            readMoreButton2.check(matches(isDisplayed()));
            readMoreButton2.perform(click());
        }

        ViewInteraction upButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        TestUtility.childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        TestUtility.childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        if (TestUtility.waitUntilVisible((long) 5000, upButton2)) {
            upButton2.check(matches(isDisplayed()));
            upButton2.perform(click());
        }
    }

    static ViewInteraction findById(int itemId, String className, int position1, int position2) {
        ViewInteraction element = onView(allOf(TestUtility.childAtPosition(
                allOf(withId(itemId),
                        TestUtility.childAtPosition(
                                withClassName(is(className)),
                                1)),
                0),
                isDisplayed()));
        if (TestUtility.waitUntilVisible((long) 5000, element)) {
            element.perform(click());
        }
        return element;
    }
}
