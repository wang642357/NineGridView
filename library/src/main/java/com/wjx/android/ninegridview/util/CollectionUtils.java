package com.wjx.android.ninegridview.util;

import java.util.Collection;

import androidx.annotation.RestrictTo;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * 作者：wangjianxiong 创建时间：2021/5/11
 */
@RestrictTo(LIBRARY_GROUP)
public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> coll) {
        return coll == null || coll.size() == 0;
    }

    public static <T> boolean isNotEmpty(Collection<T> coll) {
        return !isEmpty(coll);
    }

}
