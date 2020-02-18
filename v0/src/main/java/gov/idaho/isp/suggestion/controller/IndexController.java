package gov.idaho.isp.suggestion.controller;

import gov.idaho.isp.suggestion.domain.SuggestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  private final SuggestionRepository suggestionRepository;

  public IndexController(SuggestionRepository suggestionRepository) {
    this.suggestionRepository = suggestionRepository;
  }
  
  @GetMapping("/")
  public String index(Model m) {
    long suggestionCount = suggestionRepository.count();
    m.addAttribute("suggestionCount", suggestionCount);
    return "index";
  }
}