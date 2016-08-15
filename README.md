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
<video controls="">
  <source src="bunny.mp4" type="video/mp4"></div>
  <source src="bunny.ogg" type="video/ogg"></div>
</video>
```

Will be transformed to:

```
<video controls="">
  <source src="/data/user/0/io.cordova.hellocordova/cache/htmlvideos/video1484775800.ogg" type="video/ogg">
  <source src="/data/user/0/io.cordova.hellocordova/cache/htmlvideos/video545174706.mp4" type="video/mp4">
</video>
```
