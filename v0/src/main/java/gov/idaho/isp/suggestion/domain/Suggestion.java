package gov.idaho.isp.suggestion.domain;

import gov.idaho.isp.suggestion.user.User;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Suggestion implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
  
  @ManyToOne
  private User author;
  
  private LocalDate created;
  
  private String title;
  
  private String details;
  
  private String url;
  
  @OneToOne(cascade = CascadeType.ALL)
  private VideoDetails videoDetails = new VideoDetails();
 
  @ElementCollection
  @Column(name = "tag")
  private List<String> tags = new ArrayList<>();
  
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "suggestionId")
  private List<SuggestionHistory> history = new ArrayList<>();

  public Suggestion() {
  }
   
  public Suggestion(User author) {
    this.author = author;
    this.created = LocalDate.now();
  } 
   
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public LocalDate getCreated() {
    return created;
  }

  public void setCreated(LocalDate created) {
    this.created = created;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public VideoDetails getVideoDetails() {
    return videoDetails;
  }

  public void setVideoDetails(VideoDetails videoDetails) {
    this.videoDetails = videoDetails;
  }
  
  public boolean isVideo() {
    return videoDetails != null && !videoDetails.isEmpty();
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public List<SuggestionHistory> getHistory() {
    return history;
  }

  public void setHistory(List<SuggestionHistory> history) {
    this.history = history;
  }
  
  @Override
  public String toString() {
    return "Suggestion{" + "id=" + id + ", author=" + author + ", created=" + created + ", title=" + title + ", details=" + details + ", url=" + url + ", videoDetails=" + videoDetails + ", tags=" + tags + '}';
  }
}
