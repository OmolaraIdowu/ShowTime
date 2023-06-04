<img src="https://img.shields.io/badge/made%20with-kotlin-blue.svg?style=plastic"> <img src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=plastic">
<br>

# ShowTime

A simple android app that follows the MVVM (Model-View-ViewModel) architectural pattern. It allows users to browse popular movies and search for movies using The Movie Database (TMDb) API.

## SDK Requirements
- Minimum SDK Requirement - android API 24 (Android 7 - Nougat)
- Target SDK - android API 33 (Android 13 - Tiramisu)

## Installation
 - To run this code, clone this repository using this command

`git clone https://github.com/omolaraidowu/ShowTime.git`
 - Import into android studio
 - Get your API key from [here](https://www.themoviedb.org/) The Movie Database (TMDb) website.
 - Open the **local.properties** file in the root directory of the project.
 - Add the following line to the file: **API_KEY=YOUR_API_KEY**, replacing **YOUR_API_KEY** with your actual API key.
 - Make sure to sync the project in Android Studio after adding the API key to local.properties and run on an android device or emulator.

## APK file

You can download the APK file for this project [here](https://github.com/OmolaraIdowu/ShowTime/blob/master/app/release/Show-Time.apk)

## Architecture 
This app follows the MVVM (Model-View-ViewModel) architectural pattern, which separates the responsibilities of the UI, data, and business logic. Here's a brief overview of the different components:

- **Model:** Represents the data and business logic of the app. It includes data classes such as Movie and MovieResponse, as well as the MovieApiService interface for making API requests.
- **View:** Consists of the UI components, including fragments, activities, and layouts. In this app, the views are implemented using fragments and XML layouts.
- **ViewModel:** Acts as a middle layer between the View and the Model. It holds the UI-related data and exposes it to the View via LiveData objects. The MovieViewModel class handles the logic for fetching popular movies and performing search operations.
- **LiveData:** Provides data observation and propagation. The LiveData objects in the ViewModel allow the View to observe changes in the data and update the UI accordingly.

## Libraries 
 * [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
 * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
 * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
 * [Retrofit](https://square.github.io/retrofit/) and [OkHttp](https://square.github.io/okhttp/) - For handling API requests and networking.
 * **GSON** to parse API response.
 * [Android Material Design Components](https://material.io/develop/android/docs/getting-started)
 * [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) - Concurrency design pattern used on Android to simplify code that executes asynchronously.
 * [Glide](https://bumptech.github.io/glide/) - For loading and displaying movie poster images.
 * [Navigation](https://developer.android.com/guide/navigation/get-started) - Android Jetpack Navigation for navigating between fragments.
 * [ProgressView](https://github.com/skydoves/ProgressView) - A polished and flexible ProgressView, fully customizable with animations.

## Screenshots
<ul>
  <img src="https://github.com/OmolaraIdowu/ShowTime/blob/master/screenshots/ShowTime_1.png"width="300" height="600" alt="Screen 1">
  <img src="https://github.com/OmolaraIdowu/ShowTime/blob/master/screenshots/ShowTime_2.png" width="300" height="600" alt="Screen 2">
  <img src="https://github.com/OmolaraIdowu/ShowTime/blob/master/screenshots/ShowTime_3.png" width="300" height="600" alt="Screen 3">
  <img src="https://github.com/OmolaraIdowu/ShowTime/blob/master/screenshots/ShowTime_4.png" width="300" height="600" alt="Screen 4">
</ul>

## Author

* **OmolaraIdowu**  
 - [LinkedIn](https://www.linkedin.com/in/omolara-idowu-0273661b4/)

