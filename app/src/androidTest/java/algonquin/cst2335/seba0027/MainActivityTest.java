//package algonquin.cst2335.seba0027;
//
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.Espresso.pressBack;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.action.ViewActions.replaceText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withParent;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.filters.LargeTest;
//import androidx.test.rule.ActivityTestRule;
//import androidx.test.runner.AndroidJUnit4;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class MainActivityTest {
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    /** Test case testing if the password entered is complex enough */
//
//    @Test
//    public void mainActivityTest() {
//        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
//        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());
//
//
//        ViewInteraction materialButton = onView(allOf(withId(R.id.loginbutton)));
//        materialButton.perform(click());
//
//        ViewInteraction textView = onView(allOf(withId(R.id.textView2)));
//        textView.check(matches(withText("You shall not pass!")));
//    }
//
//
//    /** Test case testing if the password entered has an upper case in it */
//    @Test
//    public void testFindMissingUpperCase(){
//        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
//        appCompatEditText.perform(click());
//
//        appCompatEditText.perform(replaceText("password123#$"), closeSoftKeyboard());
//
//        ViewInteraction materialButton = onView(allOf(withId(R.id.loginbutton)));
//        materialButton.perform(click());
//
//        ViewInteraction textView = onView(allOf(withId(R.id.textView2)));
//        textView.check(matches(withText("You shall not pass!")));
//    }
//
//
//    /** Test case testing if the password entered has an digit in it */
//    @Test
//    public void testFindMissingNumber(){
//        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
//        appCompatEditText.perform(click());
//
//        appCompatEditText.perform(replaceText("Password#$"), closeSoftKeyboard());
//
//        ViewInteraction materialButton = onView(allOf(withId(R.id.loginbutton)));
//        materialButton.perform(click());
//
//        ViewInteraction textView = onView(allOf(withId(R.id.textView2)));
//        textView.check(matches(withText("You shall not pass!")));
//    }
//
//    /** Test case testing if the password entered has an Lower case in it */
//    @Test
//    public void testFindMissingLowerCase(){
//        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
//        appCompatEditText.perform(click());
//
//        appCompatEditText.perform(replaceText("PASSWORD123#$"), closeSoftKeyboard());
//
//        ViewInteraction materialButton = onView(allOf(withId(R.id.loginbutton)));
//        materialButton.perform(click());
//
//        ViewInteraction textView = onView(allOf(withId(R.id.textView2)));
//        textView.check(matches(withText("You shall not pass!")));
//    }
//
//    /** Test case testing if the password entered has an upper case in it */
//    @Test
//    public void testFindMissingSpecialCharacter(){
//        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
//        appCompatEditText.perform(click());
//
//        appCompatEditText.perform(replaceText("Password123"), closeSoftKeyboard());
//
//        ViewInteraction materialButton = onView(allOf(withId(R.id.loginbutton)));
//        materialButton.perform(click());
//
//        ViewInteraction textView = onView(allOf(withId(R.id.textView2)));
//        textView.check(matches(withText("You shall not pass!")));
//    }
//    //a digit, an upper case, a lower case, and a special character.
//
//
//    /** Test case testing if the password works */
//    @Test
//    public void testFindPerfectPassword(){
//        ViewInteraction appCompatEditText = onView(withId(R.id.editTextTextPassword));
//        appCompatEditText.perform(click());
//
//        appCompatEditText.perform(replaceText("Password123#$"), closeSoftKeyboard());
//
//        ViewInteraction materialButton = onView(allOf(withId(R.id.loginbutton)));
//        materialButton.perform(click());
//
//        ViewInteraction textView = onView(allOf(withId(R.id.textView2)));
//        textView.check(matches(withText("Your password meets the requirements")));
//    }
//
//    //Test successfully passes
//
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
//}
