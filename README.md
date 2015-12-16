[![Moviegur](art/feature-graphic.jpg)][1]

[![Download](art/google-play-badge.png)][1]

[![Moviegur]()[2]

# Moviegur

Are you the kind of person that likes to see information on a bunch of now playing, popular, top
rated or upcoming movies?

Have no fear, Moviegur does exactly that. But it doesn't just stop there, oh no! It provides all
those succulent, juicy features through the wonderful use of RxJava, Dagger, Retrofit, etc. all
wrapped in a wonderful architectural sandwich that we all know and love: MVP. It even has tests!

Honestly though, this is a pretty useless application.

### Running & Releasing

This application depends on the following keys and secrets that need to be defined in `local.properties`:

- `tmdb.key` -- TMDB API key.
- `tracking.id.debug` -- Google Analytics Tracker ID for the debug build.
- `tracking.id.release` -- Google Analytics Tracker ID for the release build.
- `crashlytics.key` -- Key for Crashlytics
- `keystore.password` -- Keystore Password
- `keystore.key.alias` -- Keystore Key Alias
- `keystore.key.password` -- Keystore Key Password

Using the new `google-services` gradle plugin requires a `google-services.json` file in the `app` 
folder with all the analytics data. 

If you decide to just outright steal the source code of this application and upload it to the play 
store you're going to need a `moviegur.keystore` file in the root directory.

### Things I still have to do:

- Cleanup the unit testing
- Add instrumentation tests

License
-------

    Copyright 2015 Asad Shah

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: https://play.google.com/store/apps/details?id=com.asadmshah.moviegur
 [2]: https://fat.gfycat.com/OblongMatureBufflehead.webm