package gov.idaho.isp.suggestion.domain;

public interface Suggester {
  Suggestion suggest(SuggestionSpec spec);
}
