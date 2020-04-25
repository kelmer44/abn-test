package com.kelmer.abn.foursquare.data.converter


interface Converter<I, O> {
    fun convert(input: I): O
    fun convert(input: List<I>): List<O>{
        return input.map(this::convert)
    }
}



interface BiConverter<I1, I2, O> : Converter<Pair<I1, I2>, O> {
    fun convert(input1: I1, input2: I2): O

    override fun convert(input: Pair<I1, I2>): O {
        val (input1, input2) = input
        return convert(input1, input2)
    }
}