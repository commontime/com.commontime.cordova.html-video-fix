# HTML5 Video fixer plugin

Replaces paths to HTML5 videos in the Android assets with paths to copied files in the cache folder.

**_plugins.htmlVideoFix.fixVideo( success, fail, path );_**
```
plugins.htmlVideoFix.fixVideo(function(path){console.log(path);}, function(error) {console.error(error);}, "file:///android_asset/www/assets/big_buck_bunny.mp4");
```
undefined
/data/data/io.cordova.hellocordova/cache/htmlvideos/big_buck_bunny527194493.mp4
```


**_plugins.htmlVideoFix.fixAllVideos( success, fail );_**
```
plugins.htmlVideoFix.fixAllVideos(function() {
  console.log("done");
}, function() {
  console.error("failed");
});
```

eg.

```
<video controls src="assets/big_buck_bunny.mp4"></video>
```

Will be transformed to:

```
<video controls src="/data/data/io.cordova.hellocordova/cache/htmlvideos/big_buck_bunny805959135.mp4"></video>

```
