package gov.idaho.isp.suggestion.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class VideoDetails implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
  private LocalDate posted;
  private Integer seconds;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getPosted() {
    return posted;
  }

  public void setPosted(LocalDate posted) {
    this.posted = posted;
  }

  public Integer getSeconds() {
    return seconds;
  }

  public void setSeconds(Integer seconds) {
    this.seconds = seconds;
  }
  
  public boolean isEmpty() {
    return posted == null && seconds == null;
  }

  @Override
  public String toString() {
    return "VideoDetails{" + "id=" + id + ", posted=" + posted + ", seconds=" + seconds + '}';
  }
}
