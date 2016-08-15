
  module.exports = {
    fixVideo: function(success, fail, path) {
      cordova.exec(success, fail, "HTMLVideo", "fixVideo", [path]);
    },
    fixAllVideos: function(success, fail) {
      var errors = 0;

      function done() {
        if (errors > 0) {
          fail();
        } else {
          success();
        }
      }

      var videoNodesProcessed = 0;
      var videoNodes = document.querySelectorAll("video");
      for (var i = 0, len = videoNodes.length; i < len; i++) {
        (function(videoNode) {
          var nodesProcessed = 0;
          var newVideoNode = videoNode.cloneNode(false);
          var sourceNodes = videoNode.querySelectorAll("source[src][type]");
          for (var j = 0, len = sourceNodes.length; j < len; j++) {
            (function(sourceNode) {
              var source = sourceNode.attributes.src.value;
              var type = sourceNode.attributes.type.value;
              cordova.exec(function(result) {
                var newSourceNode = document.createElement("source");
                newSourceNode.setAttribute("src", result);
                newSourceNode.setAttribute("type", type);
                newVideoNode.appendChild(newSourceNode);
                if (++nodesProcessed === sourceNodes.length) {
                  videoNode.parentNode.replaceChild(newVideoNode, videoNode);
                  if (++videoNodesProcessed === videoNodes.length) {
                    done();
                  }
                }
              }, function(error) {
                console.error("error: " + error);
                errors++;
                if (++nodesProcessed === sourceNodes.length) {
                  videoNode.parentNode.replaceChild(newVideoNode, videoNode);
                  if (++videoNodesProcessed === videoNodes.length) {
                    done();
                  }
                }
              }, "HTMLVideo", "fixVideo", [source, location.pathname]);

            })(sourceNodes[j]);
          }
        })(videoNodes[i]);
      }
    }
  };
