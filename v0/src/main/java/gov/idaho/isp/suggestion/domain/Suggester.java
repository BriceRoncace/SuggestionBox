package gov.idaho.isp.suggestion.domain;

import gov.idaho.isp.suggestion.user.User;
import java.util.Optional;

public interface Suggester {
  Optional<Suggestion> suggest(SuggestionSpec spec, Optional<User> user);
}
