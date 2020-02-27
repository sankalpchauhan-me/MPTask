package me.sankalpchauhan.marsplayassignment;


import androidx.test.espresso.ViewInteraction;
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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner.class)
public class DetailActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void A_ScrollTest() {
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
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.read_more), withText("Read More"),
                        TestUtility.childAtPosition(
                                TestUtility.childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                4),
                        isDisplayed()));
        if (TestUtility.waitUntilVisible((long) 5000, appCompatButton)) {
            appCompatButton.perform(click());
        }

        ViewInteraction abstractScroll = onView(withId(R.id.ll));

        if (TestUtility.waitUntilVisible((long) 5000, appCompatButton)) {
            abstractScroll.perform(scrollTo(), click());
            abstractScroll.check(matches(isDisplayed()));
        }

    }

    @Test
    public void B_UpButtonClickTest() {
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
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.read_more), withText("Read More"),
                        TestUtility.childAtPosition(
                                TestUtility.childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                4),
                        isDisplayed()));
        if (TestUtility.waitUntilVisible((long) 5000, appCompatButton)) {
            appCompatButton.perform(click());
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        TestUtility.childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        TestUtility.childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));

        if (TestUtility.waitUntilVisible((long) 5000, appCompatButton)) {
            appCompatImageButton.perform(click());
        }
    }


}
