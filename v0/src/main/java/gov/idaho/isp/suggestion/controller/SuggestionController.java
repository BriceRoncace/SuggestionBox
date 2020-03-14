package gov.idaho.isp.suggestion.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.idaho.isp.suggestion.domain.Suggester;
import gov.idaho.isp.suggestion.domain.Suggestion;
import gov.idaho.isp.suggestion.domain.SuggestionRepository;
import gov.idaho.isp.suggestion.domain.SuggestionSpec;
import gov.idaho.isp.suggestion.user.User;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SuggestionController {
  private final SuggestionRepository suggestionRepository;
  private final Suggester suggester;

  public SuggestionController(SuggestionRepository suggestionRepository, Suggester suggester) {
    this.suggestionRepository = suggestionRepository;
    this.suggester = suggester;
  }
  
  @ModelAttribute
  public Suggestion prepareSuggestion(@PathVariable Optional<Long> id, @RequestAttribute Optional<User> user) {
    return id.map(i -> suggestionRepository.findById(i).orElse(null)).orElse(new Suggestion(user.orElse(null)));
  }
  
  @GetMapping("/suggestions/new")
  public String forwardToSetup(Model m) {
    return "suggestion";
  }
  
  @GetMapping("/suggestions/{id}")
  public String get(Suggestion suggestion, Model m) throws JsonProcessingException {
    m.addAttribute(suggestion);
    return "suggestion";
  }
  
  @GetMapping("/suggestions")
  public String search(SuggestionSpec spec, Model m) {
    m.addAttribute("suggestions", suggestionRepository.findAll(spec));
    return "suggestions";
  }
  
  @PostMapping("/suggest")
  public String suggest(SuggestionSpec spec, @RequestAttribute Optional<User> user, RedirectAttributes ra, Model m) {
    Optional<Suggestion> s = suggester.suggest(spec, user);
    if (s.isPresent()) {
      m.addAttribute("suggestion", s.get());
      return "suggestion";
    }
    else {
      ra.addFlashAttribute("errors", "No suggestions could be found matching the provided criteria.");
      return "redirect:/suggestions";
    }
  }
  
  @PostMapping({"/suggestions", "/suggestions/{id}"})
  public String save(@ModelAttribute Suggestion suggestion, Model m) {
    suggestionRepository.save(removeBlankTags(suggestion));
    m.addAttribute("messages", "Suggestion saved.");
    return "redirect:/suggestions";
  }
  
  @DeleteMapping("/suggestions/{id}")
  public String delete(@ModelAttribute Suggestion suggestion, Model m) {
    suggestionRepository.delete(suggestion);
    m.addAttribute("messages", "Suggestion deleted.");
    return "redirect:/suggestions";
  }
  
  private Suggestion removeBlankTags(Suggestion s) {
    if (s != null) {
      s.getTags().removeIf(StringUtils::isBlank);
    }
    return s;
  }
}
