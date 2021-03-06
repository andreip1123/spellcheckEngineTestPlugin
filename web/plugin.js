(function () {
  // load custom.css file
  var cssFile;
  cssFile = goog.dom.createDom('link');
  cssFile.rel = "stylesheet";
  cssFile.type = "text/css";
  cssFile.href = "../plugin-resources/customspelltest/custom.css";
  goog.dom.appendChild(document.head, cssFile);

  goog.events.listenOnce(workspace, sync.api.Workspace.EventType.BEFORE_EDITOR_LOADED, function(e) {
    console.log('Custom spellchecker engine plugin loaded successfully!');
  });
})();
