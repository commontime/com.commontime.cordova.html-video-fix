
  module.exports = {
    fixVideo: function(success, fail, path) {
      cordova.exec(success, fail, "HTMLVideoFix", "fixVideo", [path]);
    },
    fixAllVideos: function(success, fail) {
	  var videoNodesProcessed = 0;
	  var errors = 0;
	  function done() {
		  if( errors == 0 ) {
			success();  
		  } else {
			fail();
		  }		  
	  }
      var videoNodes = document.querySelectorAll("video");
      for (var i = 0, len = videoNodes.length; i < len; i++) {
        (function(videoNode) {
          plugins.htmlVideoFix.fixVideo(function(path){
            videoNode.src = path;
            if (++videoNodesProcessed === videoNodes.length) {
			  done();	
			}
          }, function(){
            console.error(arguments);
            errors++;
            if (++videoNodesProcessed === videoNodes.length) {
			  done();	
			}
          }, videoNode.src );
        })(videoNodes[i]);
      }
    }
  };

