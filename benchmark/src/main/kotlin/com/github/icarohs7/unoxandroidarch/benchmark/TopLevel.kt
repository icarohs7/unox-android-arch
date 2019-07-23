package com.github.icarohs7.unoxandroidarch.benchmark

/** [Benchmarker.measure] */
fun addToBenchmark(identifier: Any) {
    Benchmarker.measure(identifier)
}

/** [Benchmarker.calculate] */
fun finishBenchmark() {
    Benchmarker.calculate()
}