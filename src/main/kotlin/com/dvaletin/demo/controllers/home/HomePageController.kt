package com.dvaletin.demo.controllers.home

import com.dvaletin.demo.model.Category
import com.dvaletin.demo.model.HomeModel
import com.dvaletin.demo.model.Shop
import com.dvaletin.demo.providers.FirebaseProvider
import com.google.firebase.database.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomePageController(@Autowired private val firebase: FirebaseProvider) {

    private var categories: MutableList<Category> = ArrayList()
    private var shops: MutableList<Shop> = ArrayList()
    private var lock: Any = object {}

    init {
        firebase.firebaseDatabase
                .getReference("/partners_data/for_app/categories_v2")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {
                        throw Exception(error?.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        val newCategories = ArrayList<Category>()
                        snapshot?.children?.forEach { snap ->
                            try {
                                val category = snap.getValue(Category::class.java)
                                category.let { newCategories.add(it) }
                            } catch (e: DatabaseException) {
                                e.printStackTrace()
                            }
                        }
                        synchronized(lock) {
                            categories = newCategories
                        }
                    }
                })

        firebase.firebaseDatabase
                .getReference("/partners_data/top_offers")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {
                        throw Exception(error?.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        val newShops = ArrayList<Shop>()
                        snapshot?.children?.forEach { snap ->
                            try {
                                val shop = snap.getValue(Shop::class.java)
                                shop.let { newShops.add(it) }
                            } catch (e: DatabaseException) {
                                e.printStackTrace()
                            }
                        }
                        synchronized(lock) {
                            shops = newShops
                        }
                    }
                })
    }

    @RequestMapping("/home")
    fun home(): HomeModel {
        synchronized(lock) {
            return HomeModel(shops, categories)
        }
    }
}