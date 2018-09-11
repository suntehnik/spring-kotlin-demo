package com.dvaletin.demo.providers

import com.dvaletin.demo.configuration.DemoConfigurationProperties
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.FileInputStream


@Component
class FirebaseProvider(@Autowired configurationProperties: DemoConfigurationProperties) {

    final val firebaseDatabase: FirebaseDatabase

    init {
        val serviceAccount = FileInputStream(configurationProperties.serviceAccountJsonPath)
        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(configurationProperties.databaseUrl)
                .build()
        FirebaseApp.initializeApp(options)
        // Despite the fact FirebaseDatabase is implemented as singleton,
        // we should initialize FirebaseApp first and then we can provide database instance
        // to it's consumers. So I use Provider here to make sure no controllers will start
        // before we initialize FirebaseApp.
        firebaseDatabase = FirebaseDatabase.getInstance()
    }
}