package ua.dp.rename.dniprostreets.core;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpView;

import butterknife.ButterKnife;

public abstract class BaseFragmentM<V extends MvpView, P extends BasePresenter<V>>
        extends MvpFragment<V, P> {

    public static final String EXTRA_KEY = "BaseFragment_EXTRA";

    public static Fragment instantiate(Context context, String fname, Parcelable bundle) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_KEY, bundle);
        return instantiate(context, fname, args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Layout layout = getLayoutFromAnnotation(this.getClass());
        if (layout != null) {
            return inflater.inflate(layout.value(), container, false);
        } else {
            throw new IllegalArgumentException("BaseFragment subclass should have Layout annotation");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    /**
     * Recursively scans class hierarchy searching for {@link Layout} annotation defined.
     * @param clazz class to search for annotation
     * @return defined layout if any or <b>null</b>
     */
    @Nullable
    private Layout getLayoutFromAnnotation(Class clazz) {
        if (clazz == null || clazz.equals(Object.class)) return null;
        //
        Layout layout = (Layout) clazz.getAnnotation(Layout.class);
        if (layout != null) {
            return layout;
        } else {
            return getLayoutFromAnnotation(clazz.getSuperclass());
        }
    }

    protected void informUser(String message) {
        if (getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    protected void informUser(@StringRes int messageResId) {
        informUser(getString(messageResId));
    }

    /** Try hide soft keyboard if visible */
    public static void hideSoftInput(View view) {
        if (view == null) return;
        //
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    /** Try hide soft keyboard based on current fragment's view */
    public void tryHideSoftInput() {
        hideSoftInput(getView());
    }

    protected Parcelable getArgs() {
        return getArguments().getParcelable(EXTRA_KEY);
    }
}
