# Rick and Morty App

## About

*(Please add a brief description of your application here. What does it do? What technologies does it showcase?)*

## Screenshots

<img src="/screenshots/list_view.png" alt="ListView" width="200"/>
<img src="/screenshots/detail_view.png" alt="DetailView" width="200"/>

## Features

*(Please list the key features of your app here. For example:*
*   *Browse a list of Rick and Morty characters.*
*   *View detailed information about each character.*
*   *Search for specific characters.*
*   *Offline support using a local database.)*

## Dependencies

*   **UI**: Jetpack Compose (androidx.compose.*)
*   **Paging**: Jetpack Paging (androidx.paging.*) for handling large datasets.
*   **Local Database**: Room (androidx.room.*)
*   **Dependency Injection**: Hilt (com.google.dagger:hilt-android)
*   **Networking**: Ktor (io.ktor.*) for network requests.
*   **Image Loading**: Coil (io.coil-kt.coil3:coil-compose)
*   **Serialization**: Kotlinx Serialization (org.jetbrains.kotlinx:kotlinx-serialization-json)

## Project Structure

```text
RickAndMortyApp/
├── app/                                 (Application Module)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── io/github/smithjustinn84_netizen/rickandmortyapp/
│   │   │   │       ├── Application.kt         (Custom Application class)
│   │   │   │       ├── MainActivity.kt        (Main entry point Activity)
│   │   │   │       ├── NavGraph.kt            (Jetpack Compose Navigation)
│   │   │   │       ├── characters/            (Character list feature)
│   │   │   │       ├── characterdetail/       (Character detail feature)
│   │   │   │       ├── data/                  (Data Layer - Repositories, Local/Remote Data Sources, Mappers)
│   │   │   │       │   ├── local/             (Room Database, DAOs, Entities)
│   │   │   │       │   ├── remote/            (Network API service, DTOs)
│   │   │   │       │   └── mappers/           (Data model to Domain model mappers)
│   │   │   │       ├── di/                    (Hilt Dependency Injection modules)
│   │   │   │       ├── domain/                (Domain Layer - Use Cases, Models, Repository Interfaces)
│   │   │   │       ├── extensions/            (Kotlin extension functions)
│   │   │   │       └── ui/                    (UI Layer - Composables, ViewModels, Theme)
│   │   │   │           └── theme/             (Compose UI Theme, Colors, Typography)
│   │   │   ├── res/                       (Resources - drawables, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle.kts                   (App module build script)
│
├── build.gradle.kts                       (Project level build script)
└── settings.gradle.kts                    (Project settings)
```