package gov.idaho.isp.suggestion.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long>, JpaSpecificationExecutor<Suggestion> {
  @Query("select distinct t from Suggestion s join s.tags t order by lower(t)")
  List<String> findAllTags();
}
