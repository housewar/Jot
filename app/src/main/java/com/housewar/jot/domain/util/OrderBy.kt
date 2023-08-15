package com.housewar.jot.domain.util

sealed class OrderBy(val orderDirection: OrderDirection) {
    class Timestamp(orderDirection: OrderDirection): OrderBy(orderDirection)
    class Title(orderDirection: OrderDirection): OrderBy(orderDirection)
}