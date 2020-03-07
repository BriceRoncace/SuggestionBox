package gov.idaho.isp.suggestion.domain;

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
  public Suggestion suggest(SuggestionSpec spec) {
    long count  = suggestionRepository.count(spec);
    Page<Suggestion> page = suggestionRepository.findAll(spec, PageRequest.of(pickRandom(Math.toIntExact(count)), 1));
    
    //todo document this suggestion and save history
    
    System.out.println("randomly picked: " + page.getContent());
    
    return page.getContent().get(0);
  }
  
  private int pickRandom(int upperBound) {
    return new Random().nextInt(upperBound);
  }
}
