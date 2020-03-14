package gov.idaho.isp.suggestion.domain;

import gov.idaho.isp.suggestion.user.User;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SuggesterImpl implements Suggester {
  private final SuggestionRepository suggestionRepository;

  public SuggesterImpl(SuggestionRepository suggestionRepository) {
    this.suggestionRepository = suggestionRepository;
  }
  
  @Override
  public Optional<Suggestion> suggest(SuggestionSpec spec, Optional<User> user) {
    long count = suggestionRepository.count(spec);
    if (count == 0) {
      return Optional.empty();
    }
    
    Page<Suggestion> page = suggestionRepository.findAll(spec, PageRequest.of(pickRandom(Math.toIntExact(count)), 1));
    Suggestion s = page.getContent().get(0);
    s.getHistory().add(buildSuggestionHistory(user));
    return Optional.of(suggestionRepository.save(s));
  }
  
  private SuggestionHistory buildSuggestionHistory(Optional<User> user) {
    SuggestionHistory sh = new SuggestionHistory();
    sh.setSuggestedOn(LocalDate.now());
    sh.setSuggestedTo(user.orElse(null));
    return sh;
  }
  
  private int pickRandom(int upperBound) {
    return new Random().nextInt(upperBound);
  }
}
