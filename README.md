# HTML5 Video fixer plugin

Replaces paths to HTML5 videos in the Android assets with paths to copied files in the cache folder.

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
<video controls="" src="assets/big_buck_bunny.mp4"></video>
```

Will be transformed to:

```
<video controls="" src="/data/data/io.cordova.hellocordova/cache/htmlvideos/big_buck_bunny805959135.mp4"></video>

```
