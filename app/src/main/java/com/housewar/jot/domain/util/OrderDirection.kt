package com.housewar.jot.domain.util

sealed class OrderDirection{
    object Ascending: OrderDirection()
    object Descending: OrderDirection()
}
