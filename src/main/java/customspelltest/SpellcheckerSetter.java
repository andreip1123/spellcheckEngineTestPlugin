package customspelltest;

import java.net.URL;
import java.util.Map;

import ro.sync.ecss.extensions.api.webapp.AuthorDocumentModel;
import ro.sync.ecss.extensions.api.webapp.WebappSpellchecker;
import ro.sync.ecss.extensions.api.webapp.access.EditingSessionOpenVetoException;
import ro.sync.ecss.extensions.api.webapp.access.WebappEditingSessionLifecycleListener;
import ro.sync.ecss.extensions.api.webapp.access.WebappPluginWorkspace;
import ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;

public class SpellcheckerSetter implements WorkspaceAccessPluginExtension {

  private WebappSpellchecker spellchecker;

  public boolean applicationClosing() {
    return false;
  }

  public void applicationStarted(StandalonePluginWorkspace pluginWorkspaceAccess) {
    
    WebappPluginWorkspace ws = (WebappPluginWorkspace) pluginWorkspaceAccess;
    ws.addEditingSessionLifecycleListener(new WebappEditingSessionLifecycleListener() {
      @Override
      public void editingSessionAboutToBeStarted(String docId, String licenseeId, URL systemId,
          Map<String, Object> options) throws EditingSessionOpenVetoException {
        // Nothing to do here.
      }
      
      @Override
      public void editingSessionStarted(String sessionId, AuthorDocumentModel documentModel) {
        spellchecker = documentModel.getSpellchecker();
        spellchecker.setSpellcheckingEngine("de", new CustomSpellcheckerEngine(spellchecker));
      }
    });
  }

}
