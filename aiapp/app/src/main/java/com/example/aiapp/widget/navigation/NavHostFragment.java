package com.example.aiapp.widget.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;

public class NavHostFragment extends Fragment implements NavHost {
    private static final String KEY_GRAPH_ID = "android-support-nav:fragment:graphId";
    private static final String KEY_START_DESTINATION_ARGS =
            "android-support-nav:fragment:startDestinationArgs";
    private static final String KEY_NAV_CONTROLLER_STATE =
            "android-support-nav:fragment:navControllerState";
    private static final String KEY_DEFAULT_NAV_HOST = "android-support-nav:fragment:defaultHost";

    /**
     * Find a {@link NavController} given a local {@link Fragment}.
     *
     * <p>This method will locate the {@link NavController} associated with this Fragment,
     * looking first for a {@link androidx.navigation.fragment.NavHostFragment} along the given Fragment's parent chain.
     * If a {@link NavController} is not found, this method will look for one along this
     * Fragment's {@link Fragment#getView() view hierarchy} as specified by
     * {@link Navigation#findNavController(View)}.</p>
     *
     * @param fragment the locally scoped Fragment for navigation
     * @return the locally scoped {@link NavController} for navigating from this {@link Fragment}
     * @throws IllegalStateException if the given Fragment does not correspond with a
     * {@link NavHost} or is not within a NavHost.
     */
    @NonNull
    public static NavController findNavController(@NonNull Fragment fragment) {
        Fragment findFragment = fragment;
        while (findFragment != null) {
            if (findFragment instanceof NavHostFragment) {
                return ((NavHostFragment) findFragment).getNavController();
            }
            Fragment primaryNavFragment = findFragment.requireFragmentManager()
                    .getPrimaryNavigationFragment();
            if (primaryNavFragment instanceof NavHostFragment) {
                return ((NavHostFragment) primaryNavFragment).getNavController();
            }
            findFragment = findFragment.getParentFragment();
        }

        // Try looking for one associated with the view instead, if applicable
        View view = fragment.getView();
        if (view != null) {
            return Navigation.findNavController(view);
        }
        throw new IllegalStateException("Fragment " + fragment
                + " does not have a NavController set");
    }

    private NavController mNavController;

    // State that will be saved and restored
    private int mGraphId;
    private boolean mDefaultNavHost;

    /**
     * Create a new NavHostFragment instance with an inflated {@link NavGraph} resource.
     *
     * @param graphResId resource id of the navigation graph to inflate
     * @return a new NavHostFragment instance
     */
    @NonNull
    public static NavHostFragment create(@NavigationRes int graphResId) {
        return create(graphResId, null);
    }

    /**
     * Create a new NavHostFragment instance with an inflated {@link NavGraph} resource.
     *
     * @param graphResId resource id of the navigation graph to inflate
     * @param startDestinationArgs arguments to send to the start destination of the graph
     * @return a new NavHostFragment instance
     */
    @NonNull
    public static NavHostFragment create(@NavigationRes int graphResId,
                                                                      @Nullable Bundle startDestinationArgs) {
        Bundle b = null;
        if (graphResId != 0) {
            b = new Bundle();
            b.putInt(KEY_GRAPH_ID, graphResId);
        }
        if (startDestinationArgs != null) {
            if (b == null) {
                b = new Bundle();
            }
            b.putBundle(KEY_START_DESTINATION_ARGS, startDestinationArgs);
        }

        final NavHostFragment result = new NavHostFragment();
        if (b != null) {
            result.setArguments(b);
        }
        return result;
    }

    /**
     * Returns the {@link NavController navigation controller} for this navigation host.
     * This method will return null until this host fragment's {@link #onCreate(Bundle)}
     * has been called and it has had an opportunity to restore from a previous instance state.
     *
     * @return this host's navigation controller
     * @throws IllegalStateException if called before {@link #onCreate(Bundle)}
     */
    @NonNull
    @Override
    public final NavController getNavController() {
        if (mNavController == null) {
            throw new IllegalStateException("NavController is not available before onCreate()");
        }
        return mNavController;
    }

    @CallSuper
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // TODO This feature should probably be a first-class feature of the Fragment system,
        // but it can stay here until we can add the necessary attr resources to
        // the fragment lib.
        if (mDefaultNavHost) {
            requireFragmentManager().beginTransaction()
                    .setPrimaryNavigationFragment(this)
                    .commit();
        }
    }

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = requireContext();

        mNavController = new NavController(context);
        mNavController.getNavigatorProvider().addNavigator(createFragmentNavigator());

        Bundle navState = null;
        if (savedInstanceState != null) {
            navState = savedInstanceState.getBundle(KEY_NAV_CONTROLLER_STATE);
            if (savedInstanceState.getBoolean(KEY_DEFAULT_NAV_HOST, false)) {
                mDefaultNavHost = true;
                requireFragmentManager().beginTransaction()
                        .setPrimaryNavigationFragment(this)
                        .commit();
            }
        }

        if (navState != null) {
            // Navigation controller state overrides arguments
            mNavController.restoreState(navState);
        }
        if (mGraphId != 0) {
            // Set from onInflate()
            mNavController.setGraph(mGraphId);
        } else {
            // See if it was set by NavHostFragment.create()
            final Bundle args = getArguments();
            final int graphId = args != null ? args.getInt(KEY_GRAPH_ID) : 0;
            final Bundle startDestinationArgs = args != null
                    ? args.getBundle(KEY_START_DESTINATION_ARGS)
                    : null;
            if (graphId != 0) {
                mNavController.setGraph(graphId, startDestinationArgs);
            }
        }
    }

    /**
     * Create the FragmentNavigator that this NavHostFragment will use. By default, this uses
     * {@link androidx.navigation.fragment.FragmentNavigator}, which replaces the entire contents of the NavHostFragment.
     * <p>
     * This is only called once in {@link #onCreate(Bundle)} and should not be called directly by
     * subclasses.
     * @return a new instance of a FragmentNavigator
     */
    @NonNull
    protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator() {
        return new FragmentNavigator(requireContext(), getChildFragmentManager(), getId());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        // When added via XML, this has no effect (since this FrameLayout is given the ID
        // automatically), but this ensures that the View exists as part of this Fragment's View
        // hierarchy in cases where the NavHostFragment is added programmatically as is required
        // for child fragment transactions
        frameLayout.setId(getId());
        return frameLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!(view instanceof ViewGroup)) {
            throw new IllegalStateException("created host view " + view + " is not a ViewGroup");
        }
        // When added via XML, the parent is null and our view is the root of the NavHostFragment
        // but when added programmatically, we need to set the NavController on the parent - i.e.,
        // the View that has the ID matching this NavHostFragment.
        View rootView = view.getParent() != null ? (View) view.getParent() : view;
        Navigation.setViewNavController(rootView, mNavController);
    }

    @CallSuper
    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs,
                          @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

//        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavHostFragment);
//        final int graphId = a.getResourceId(R.styleable.NavHostFragment_navGraph, 0);
//        final boolean defaultHost = a.getBoolean(R.styleable.NavHostFragment_defaultNavHost, false);
//
//        if (graphId != 0) {
//            mGraphId = graphId;
//        }
//        if (defaultHost) {
//            mDefaultNavHost = true;
//        }
//        a.recycle();
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle navState = mNavController.saveState();
        if (navState != null) {
            outState.putBundle(KEY_NAV_CONTROLLER_STATE, navState);
        }
        if (mDefaultNavHost) {
            outState.putBoolean(KEY_DEFAULT_NAV_HOST, true);
        }
    }
}