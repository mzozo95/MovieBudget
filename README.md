# MovieBudget application

This project is a simple Android application with JavaRX thread handling which showcases the **MVVM** & **MVP** pattern with dependency injection and Network communication with a minimal UI.

- For **MVVM + ViewBinding**, + **ViewModel tests** check out branch: *mvvm-rx-viewbinding* / *master*
- For **MVVM**, check out branch: *mvvm-rx*
- For **MVP**, check out branch: *mvp-rx*


The app has 2 screens, the first has some unique prefetch behaviour by design:

1. **Search page:** which has a text input, where the users can type a word which is a full or a part of a movie title. After clicking on search button, the application fetches 3 pages of matching movies from the "TheMovieDb" and shows it in a list *- listitem: title, price, cover image, checkbox -*  to the user, ordered by price, and highest price first. On the result list the user can mark movies as favorites (with a tick mark) and these favorites should be remembered and indicated in case the movie is presented again. The user can also remove movie(s) from favorites by unticking the item from the search wiew.

2. **Favorite page:** which lists the favorite movies in a list, ordered by title.

The MovieBudget application uses themoviedb.org API to fetch information about movies or series.
Request your api key here: https://developers.themoviedb.org/3

Add your api key to the machine's `gradle.properties` file:

On Windows: `C:/Users/{username}/.gradle/gradle.properties` file:

```
themoviedbApiKey = "*** your api key ***"
```

After, the key can be used as:

```
BuildConfig.API_KEY
```

Used libraries: 
- AndroidX
- Retrofit
- OkHttp3
- Gson
- Chuck network debug
- RxJava
- Room
- Dagger2
- ButterKnife
- Glide
- Mockito
- Junit

Sample image of the application: 

![sample image](images/sample_framed2.png)
