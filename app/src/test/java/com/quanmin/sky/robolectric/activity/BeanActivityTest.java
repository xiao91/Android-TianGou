package com.quanmin.sky.robolectric.activity;

import android.content.Context;

import com.quanmin.sky.BuildConfig;
import com.quanmin.sky.R;
import com.quanmin.sky.robolectric.base.XRobolectricTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Robolectric框架测试
 *
 * Created by xiao on 2017-06-05.
 */

@RunWith(XRobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class BeanActivityTest {

    /**
     * View控件测试详情查看<a href="https://github.com/robolectric/robolectric"></a>
     *
     */
    @Test
    public void testTextView() throws Exception {
//        MainMvpActivity activity = Robolectric.setupActivity(MainMvpActivity.class);
//
//        TextView results = (TextView) activity.findViewById(R.id.tv_test);
//        Assert.assertEquals(results.getText().toString(), "测试BaseActivity");
    }

    /**
     * Activity控件点击测试详情查看<a href="http://robolectric.org/writing-a-test/"></a>
     *
     */
    @Test
    public void testClickIntentActivity() {
//        MainMvpActivity activity = Robolectric.setupActivity(MainMvpActivity.class);
//        activity.findViewById(R.id.tv_test).performClick();

//        Intent expectedIntent = new Intent(activity, BeanActivity.class);
//        assertThat(shadowOf(activity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }

    /**
     * 资源测试详情查看<a href="http://robolectric.org/using-qualifiers/"></a>
     *
     */
    @Test
    public void testStringResource() {
        Context application = RuntimeEnvironment.application;

        assertThat(application.getString(R.string.app_name)).isEqualTo("MVP");
    }
}