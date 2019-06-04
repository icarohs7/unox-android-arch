package androidx.databinding

import com.github.icarohs7.unoxandroidarch.DataBinderMapperImpl

/**
 * Workaround for [robolectric#3789](https://github.com/robolectric/robolectric/issues/3789)
 */
class DataBinderMapperImpl internal constructor() : MergedDataBinderMapper() {
    init {
        addMapper(DataBinderMapperImpl())
    }
}