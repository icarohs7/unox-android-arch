package androidx.databinding

/**
 * Workaround for [robolectric#3789](https://github.com/robolectric/robolectric/issues/3789)
 */
class DataBinderMapperImpl internal constructor() : MergedDataBinderMapper() {
    init {
        addMapper(DataBinderMapperImpl())
    }
}