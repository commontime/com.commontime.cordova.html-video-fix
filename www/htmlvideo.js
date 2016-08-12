
  module.exports = {
    fixVideo: function(success, fail, path) {
      cordova.exec(success, fail, "HTMLVideo", "fixVideo", [path]);
    },
    fixAllVideos: function(success, fail) {
      var errors = 0;
      var itemsProcessed = 0;

      function done() {
        if (errors > 0) {
          fail();
        } else {
          success();
        }
      }

      var sourceNodes = document.querySelectorAll("video > div[src][type]");
      sourceNodes.forEach(function(sourceNode) {
        var videoNode = sourceNode.parentNode;
        var source = sourceNode.attributes.src.value;
        var type = sourceNode.attributes.type.value;
        videoNode.removeChild(sourceNode);
        cordova.exec(function(result) {
          var newSourceNode = document.createElement("source");
          newSourceNode.setAttribute("src", result);
          newSourceNode.setAttribute("type", type);
          videoNode.appendChild(newSourceNode);
          itemsProcessed++;
          if (itemsProcessed === sourceNodes.length) {
            done();
          }
        }, function(error) {
          console.log("error: " + error);
          errors++;
          itemsProcessed++;
          if (itemsProcessed === sourceNodes.length) {
            done();
          }
        }, "HTMLVideo", "fixVideo", [source]);
      });
    }
  };