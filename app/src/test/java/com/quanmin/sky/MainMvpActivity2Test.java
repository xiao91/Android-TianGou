package com.quanmin.sky;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Mockito测试用例
 * @see <a href="https://github.com/mockito/mockito/wiki">Mockito测试官方文档wiki</a>
 * <p>
 * Created by xiao on 2017-06-06.
 */

public class MainMvpActivity2Test {

    @Test
    public void test() {
        // mock creation
        List mockedList = mock(List.class);

        // using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");
        mockedList.clear();

        // selective, explicit, highly readable verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }


}
