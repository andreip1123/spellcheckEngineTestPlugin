package customspelltest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.sync.ecss.extensions.api.SpellCheckingProblemInfo;
import ro.sync.ecss.extensions.api.webapp.SpellcheckingEngine;
import ro.sync.ecss.extensions.api.webapp.WebappSpellchecker;
import ro.sync.exml.workspace.api.util.TextChunkDescriptor;

public class CustomSpellcheckerEngine implements SpellcheckingEngine {

  WebappSpellchecker spellchecker;
  public CustomSpellcheckerEngine(WebappSpellchecker spellchecker) {
    this.spellchecker = spellchecker;
  }

  public List<SpellCheckingProblemInfo> check(List<TextChunkDescriptor> textDescriptors) {
    List<SpellCheckingProblemInfo> checked = null;
    List<SpellCheckingProblemInfo> checkedExtended = new ArrayList<>();
    // Break the infinite loop for one default check.
    spellchecker.setSpellcheckingEngine("de", null);
    try {
      checked = spellchecker.check(textDescriptors);
      checked.forEach(problemInfo ->
        {
          String word = problemInfo.getWord();
          checkedExtended.add(new SpellCheckingProblemInfo(
              problemInfo.getStartOffset(), 
              problemInfo.getEndOffset(), 
              problemInfo.getErrorCode(), 
              problemInfo.getLanguageIsoName(), 
              word,
              Arrays.asList(
                  word + "a", 
                  word + "b", 
                  word + "c"
              )
            )
          );
        }
      );
      spellchecker.setSpellcheckingEngine("de", this);
      return checkedExtended;
    } catch (IOException e) {
      e.printStackTrace();
      spellchecker.setSpellcheckingEngine("de", this);
      return new ArrayList<>();
    }
  }

}
