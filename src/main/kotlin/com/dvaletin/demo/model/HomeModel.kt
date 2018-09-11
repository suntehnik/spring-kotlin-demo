package com.dvaletin.demo.model

data class HomeModel(
        val shops: MutableList<Shop>,
        val categories: MutableList<Category>
)


// Due to GSON mapping nature we have to use beans with empty constructor
class Shop {
    var id: String = ""
    var name: String = ""
    var cpa: String = ""
    var deep_link: String = ""
    var logo: String = ""
}

class Category {
    var id: String = ""
    var name: String = ""
    var icon: String = ""
    var count_offers: Int = 0
}