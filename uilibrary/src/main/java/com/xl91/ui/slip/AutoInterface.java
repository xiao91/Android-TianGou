package com.xl91.ui.slip;

import android.support.v4.view.PagerAdapter;

/**
 *
 */
public interface AutoInterface {
    void updateIndicatorView(int size);

    void setAdapter(PagerAdapter adapter);

    void startScorll();

    void endScorll();
}
