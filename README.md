<h1 align="center"> Kotlin Trending Repositories</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=23"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">  
Android App that lists trending Kotlin Github repositories
</p>

<p align="center">
<img src="/screenshots/Trending_1.png" width="200"/> <img src="/screenshots/Trending_2.png" width="200"/> <img src="/screenshots/Trending_3.png" width="200"/> <img src="/screenshots/Trending_5.gif" width="200"/>
</p>

## Download
Go to the [Releases](https://github.com/skydoves/Pokedex/releases) to download the lastest APK.

## API
Since there is no official API for Trending Repositories (it is one of the internal GitHub API’s),
<br />
I have decided to use [GitHub Search API](https://developer.github.com/v3/search/#search-repositories) and sort the repositories by their stars.

## Architecture
This app is based on MVVM architecture and a repository pattern.

## Tech stack
- Minimum SDK level 23
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- Dagger-Hilt (alpha) for dependency injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Navigation Component - handle everything needed for in-app navigation.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.


