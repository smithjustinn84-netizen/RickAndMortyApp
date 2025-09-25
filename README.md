# 🧪 Rick and Morty App

[![Android](https://img.shields.io/badge/Android-API%2029+-green.svg)](https://developer.android.com/about/versions/android-10.0)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-blue.svg)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## About

A modern Android application built with **Jetpack Compose** that showcases characters from the popular TV series "Rick and Morty". This app demonstrates contemporary Android development practices using **Clean Architecture**, **MVVM pattern**, and the latest Android technologies.

The app fetches data from the [Rick and Morty API](https://rickandmortyapi.com/) and provides offline support through local caching, ensuring a smooth user experience even without internet connectivity.

## Screenshots

<p align="center">
  <img src="screenshots/list_view.png" alt="Character List" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="screenshots/detail_view.png" alt="Character Detail" width="250"/>
</p>

## ✨ Features

- **📱 Modern UI**: Built entirely with Jetpack Compose
- **👥 Character Browsing**: Browse a comprehensive list of Rick and Morty characters
- **📖 Detailed Information**: View detailed information about each character including:
  - Name, status (Alive/Dead/Unknown)
  - Species and gender
  - Origin and last known location
  - Episode appearances
- **🔍 Infinite Scrolling**: Seamless pagination through large datasets
- **💾 Offline Support**: Local database caching for offline viewing
- **🎨 Material Design 3**: Modern design system with dark mode support
- **⚡ Performance Optimized**: Efficient image loading and memory management

## 🛠️ Tech Stack

### Core Technologies
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern declarative UI toolkit
- **[Kotlin](https://kotlinlang.org/)** - Primary programming language
- **[Material Design 3](https://m3.material.io/)** - Design system and theming

### Architecture & Patterns
- **[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)** - Separation of concerns with clear layer boundaries
- **MVVM Pattern** - Model-View-ViewModel architectural pattern
- **Repository Pattern** - Data access abstraction layer

### Libraries & Dependencies
- **📦 Dependency Injection**: [Hilt](https://dagger.dev/hilt/) - Compile-time DI framework
- **📊 Pagination**: [Jetpack Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Efficient data loading
- **💾 Database**: [Room](https://developer.android.com/training/data-storage/room) - Local SQLite database with coroutines support
- **🌐 Networking**: [Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html) - Asynchronous HTTP client
- **🗺️ Navigation**: [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) - Type-safe navigation
- **🖼️ Image Loading**: [Coil](https://coil-kt.github.io/coil/) - Efficient image loading with Compose support
- **🔄 Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) - JSON serialization

### Development Tools
- **Minimum SDK**: API 29 (Android 10)
- **Target SDK**: API 36
- **Kotlin Compiler**: JVM Target 11
- **Build System**: Gradle with Kotlin DSL

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Hedgehog | 2023.1.1 or later
- **JDK**: 11 or higher
- **Android SDK**: API level 29 or higher
- **Git**: For cloning the repository

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/smithjustinn84-netizen/RickAndMortyApp.git
   cd RickAndMortyApp
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and select it

3. **Sync and Build**
   - Wait for Gradle sync to complete
   - Build the project using `Build > Make Project` or `Ctrl+F9`

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift+F10`

## 📊 API Reference

This app uses the [Rick and Morty API](https://rickandmortyapi.com/) to fetch character data:

- **Base URL**: `https://rickandmortyapi.com/api/`
- **Characters Endpoint**: `/character` - Returns paginated character data
- **Character Detail**: `/character/{id}` - Returns specific character details
- **Rate Limiting**: No authentication required, but please be respectful with requests

### Sample API Response
```json
{
  "id": 1,
  "name": "Rick Sanchez",
  "status": "Alive",
  "species": "Human",
  "gender": "Male",
  "origin": {
    "name": "Earth (C-137)"
  },
  "location": {
    "name": "Citadel of Ricks"
  },
  "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
  "episode": ["https://rickandmortyapi.com/api/episode/1", "..."]
}
```

## 🏢 Project Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

```text
📱 Presentation Layer (UI)
├── 🗺️ Navigation (NavGraph)
├── 📱 Screens (Composables)
├── 🧠 ViewModels (State Management)
└── 🎨 UI Theme & Components

💼 Domain Layer (Business Logic)
├── 📊 Models (Domain Entities)
├── ⚙️ Use Cases (Business Rules)
└── 📄 Repository Interfaces

📏 Data Layer (Data Sources)
├── 🗺️ Repositories (Implementation)
├── 🌐 Remote Data Source (API)
├── 💾 Local Data Source (Room)
└── 🔄 Mappers (Data Transformation)
```

### Detailed Project Structure

```text
RickAndMortyApp/
├── app/                                 (Application Module)
│   ├── src/main/java/io/github/smithjustinn84_netizen/rickandmortyapp/
│   │   ├── MainActivity.kt            (🏠 Main Activity)
│   │   ├── NavGraph.kt                (🗺️ Navigation Setup)
│   │   ├── characters/                (👥 Character List Feature)
│   │   │   ├── ui/                    (UI Components)
│   │   │   └── model/                 (Presentation Models)
│   │   ├── characterdetail/           (📖 Character Detail Feature)
│   │   │   ├── ui/                    (UI Components)
│   │   │   └── model/                 (Presentation Models)
│   │   ├── data/                      (📏 Data Layer)
│   │   │   ├── local/                 (💾 Room Database)
│   │   │   ├── remote/                (🌐 API Service)
│   │   │   └── repository/            (🗺️ Repository Implementation)
│   │   ├── domain/                    (💼 Domain Layer)
│   │   │   ├── model/                 (📊 Domain Models)
│   │   │   └── repository/            (📄 Repository Interfaces)
│   │   ├── di/                        (📦 Dependency Injection)
│   │   └── ui/                        (🎨 Shared UI Components)
│   └── build.gradle.kts               (App Build Configuration)
├── screenshots/                       (🖼️ App Screenshots)
├── build.gradle.kts                   (Project Build Configuration)
└── settings.gradle.kts                (Project Settings)
```

## 🧪 Testing

The project includes comprehensive testing:

- **Unit Tests**: Domain layer business logic testing
- **Integration Tests**: Repository and data layer testing  
- **UI Tests**: Compose UI components testing

Run tests with:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 🔧 Development

### Code Style
This project follows [Android's official coding standards](https://developer.android.com/kotlin/style-guide) and uses:
- **Kotlin Code Style**: Official Kotlin coding conventions
- **Detekt**: Static code analysis
- **KtLint**: Kotlin linter (if configured)

### Build Variants
- **Debug**: Development build with logging enabled
- **Release**: Production build with optimizations

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow Clean Architecture principles
- Write unit tests for new features
- Use meaningful commit messages
- Update documentation as needed

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **[Rick and Morty API](https://rickandmortyapi.com/)** - For providing the awesome free API
- **Adult Swim & Dan Harmon** - For creating the amazing Rick and Morty series
- **Android Developer Community** - For excellent documentation and samples
- **Jetpack Compose Team** - For the modern UI toolkit

## 📞 Contact

**Developer**: [@smithjustinn84-netizen](https://github.com/smithjustinn84-netizen)

**Project Link**: [https://github.com/smithjustinn84-netizen/RickAndMortyApp](https://github.com/smithjustinn84-netizen/RickAndMortyApp)

---

<p align="center">
  Made with ❤️ for Android developers
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Wubba%20Lubba%20Dub%20Dub!-💚-green" alt="Rick and Morty"/>
</p>
