package com.github.icarohs7.unoxandroidarch.extensions

import android.graphics.Bitmap

/**
 * Split the given bitmap in parts, each not
 * exceeding the [partMaxHeight] height
 */
fun Bitmap.splitIntoSequence(partMaxHeight: Int): Sequence<Bitmap> {
    return sequence {
        var heightLeft = height
        while (heightLeft > partMaxHeight) {
            yield(cutHeight(height - heightLeft, partMaxHeight))
            heightLeft -= partMaxHeight
        }
        yield(cutHeight(height - heightLeft, heightLeft))
    }
}

/**
 * Cut horizontally the given bitmap, returning
 * the new bitmap created
 * @param offset After how many pixels after the start of the
 *              cut will start
 * @param partHeight Height of the cut
 */
fun Bitmap.cutHeight(offset: Int, partHeight: Int): Bitmap {
    return Bitmap.createBitmap(this, 0, offset, width, partHeight)
}