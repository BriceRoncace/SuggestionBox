package gov.idaho.isp.suggestion.domain;

import gov.idaho.isp.suggestion.util.jpa.CriteriaNumber;
import gov.idaho.isp.suggestion.util.jpa.PredicateReducer;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SuggestionSpec implements Specification<Suggestion> {
  private boolean video;
  private CriteriaNumber videoLength = new CriteriaNumber();
  private List<String> tags = new ArrayList<>();
  private boolean unsuggested;
  private String title;
  private String details;
  
  @Override
  public Predicate toPredicate(Root<Suggestion> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();
    Join<Suggestion, VideoDetails> videoDetails = root.join(Suggestion_.videoDetails);
    
    if (video) {
      predicates.add(cb.or(cb.isNotNull(videoDetails.get(VideoDetails_.seconds)), cb.isNotNull(videoDetails.get(VideoDetails_.posted))));
    }
    
    if (videoLength.isBuildable()) {
      predicates.add(videoLength.constructPredicate(cb, videoDetails.get(VideoDetails_.seconds)));
    }
    
    if (StringUtils.isNotBlank(title)) {
      predicates.add(cb.like(cb.lower(root.get(Suggestion_.title)), "%" + title.toLowerCase() + "%"));
    }

    if (StringUtils.isNotBlank(details)) {
      predicates.add(cb.like(cb.lower(root.get(Suggestion_.details)), "%" + details.toLowerCase() + "%"));
    }
    
    if (unsuggested) {
      predicates.add(cb.isNotNull(root.join(Suggestion_.history)));
    }
    
    if (tags != null && !tags.isEmpty()) {
       predicates.add(matchTags(tags, root, cb));
    }
    
    return PredicateReducer.AND.reduce(cb, predicates);
  }
  
  // see https://stackoverflow.com/a/60579265/225217
  private Predicate matchTags(List<String> tags, Root<Suggestion> root, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();
    Join<Suggestion, String> joinedTag = root.join(Suggestion_.tags);

    for (String tag : tags) {
      predicates.add(cb.like(cb.lower(joinedTag), tag.toLowerCase()));
    }
    return PredicateReducer.OR.reduce(cb, predicates);
  }
  
  public boolean isVideo() {
    return video;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public CriteriaNumber getVideoLength() {
    return videoLength;
  }

  public void setVideoLength(CriteriaNumber videoLength) {
    this.videoLength = videoLength;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public boolean isUnsuggested() {
    return unsuggested;
  }

  public void setUnsuggested(boolean unsuggested) {
    this.unsuggested = unsuggested;
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

  @Override
  public String toString() {
    return "SuggestionSpec{" + "video=" + video + ", videoLength=" + videoLength + ", tags=" + tags + ", unsuggested=" + unsuggested + ", title=" + title + ", details=" + details + '}';
  }
}
