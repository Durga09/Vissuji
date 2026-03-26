package com.durga.arunachaleswara.utils

class Calculator {

    fun calculateValues(a: Int, b: Int, lambda: (Int, Int) -> Int): Int {
        return lambda(a, b)
    }
}