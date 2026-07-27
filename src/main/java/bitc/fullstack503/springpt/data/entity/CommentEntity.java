package bitc.fullstack503.springpt.data.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Entity
@Table (name = "comment")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners (AuditingEntityListener.class)
public class CommentEntity
{
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private long seqComment;
  @Column (nullable = false, length = 100)
  private String content;
  @CreatedDate
  @Column (nullable = false)
  private String createDate;
  
  @PrePersist
  protected void onCreate ()
  {
    this.createDate = LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm"));
  }
  
  @Column (nullable = false, columnDefinition = "INT DEFAULT 0")
  private int likeComment;
  @ManyToOne
  @JoinColumn (name = "createName", referencedColumnName = "nickName", nullable = false)
  @ToString.Exclude
  private MemberEntity member_nick;
}
