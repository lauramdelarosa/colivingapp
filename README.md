# Co-living App

Is a platform to manage coliving buildings

## Platforms

**Android** 
The project can be opened with Android Studio or IntelliJ

## Installation

Clone this repository and import into selected platform

```bash
git clone https://github.com/lauramdelarosa/colivingapp.git
```

## Generating signed apk for Android
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***

## Versions

 Kotlin 1.3.72
 Android Studio 3.6.3
 Gradle 5.6.4-all (Wrapper)  
 **Android Minimal API:** 21  
 **Android Target API:** 29  

## Structure

***Application module***
- Handles all user interface requirements, all snippet classes subscribe to events that viewModel can react to.
- To build the UI, the pattern of "Single Activity" per flow and Fragment per screen is followed.
- The JetPack navigation controller is used to handle navigation in the application.

***Domain module***
- Mainly data classes that represent the data types in the application.

***Data module***
- API calls assigned using the Retrofit client (https://square.github.io/retrofit/) in the repositories using coroutines
- Manage Datasource classes.

***Use case module***
- Use cases are pure Kotlin classes that represent a unit of business logic (also known as interactors). To run use cases on a different thread, we are using Coroutines and the results are modeled based on the ResultData sealed class.
- The ResultData type represents values ​​with two possibilities: a value that is successful or an error;  

## Architecture

MVVM + Clean Arquitecture using coroutines 

There are strong boundaries between layers and the inners ones shouldn't know anything about the outside layer

- Dependencies
The most important dependencies are:
    * Androidx and Google Android Material
    * (https://github.com/bumptech/glide) for images handler
    * (https://github.com/Karumi/Dexter) for permission
    * (https://github.com/intuit/sdp) for dimensions
    

## Naming branches
to collaborate in the project should create a branch specifying what are you going to do.
there are some established prefixes that you will use.
if you are going to :
 * do a new feature your branch should be name :  feature/{name_of_the_feature} - ig: feature/push_notification
 * fix some bug of a feature your branch should be name : fix/{name_of_the_fix}
 * do general code that are not from a specific history so your branch should be name : general/{name_of_the_general} - ig :general/tooltip


