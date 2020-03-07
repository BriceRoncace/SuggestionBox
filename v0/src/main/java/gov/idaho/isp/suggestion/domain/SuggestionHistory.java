package gov.idaho.isp.suggestion.domain;

import gov.idaho.isp.suggestion.user.User;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SuggestionHistory implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
  
  @ManyToOne
  private User suggestedTo;
  
  private LocalDate suggestedOn;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getSuggestedTo() {
    return suggestedTo;
  }

  public void setSuggestedTo(User suggestedTo) {
    this.suggestedTo = suggestedTo;
  }

  public LocalDate getSuggestedOn() {
    return suggestedOn;
  }

  public void setSuggestedOn(LocalDate suggestedOn) {
    this.suggestedOn = suggestedOn;
  }

  @Override
  public String toString() {
    return "SuggestionHistory{" + "id=" + id + ", suggestedTo=" + suggestedTo + ", suggestedOn=" + suggestedOn + '}';
  }
}
