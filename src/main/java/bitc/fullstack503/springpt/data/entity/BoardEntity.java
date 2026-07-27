package bitc.fullstack503.springpt.data.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Entity
@Table (name = "board")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners (AuditingEntityListener.class)
public class BoardEntity
{
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private long seqBoard;
  @Column (nullable = false, length = 45)
  private String boardTitle;
  @Column (nullable = false, length = 500)
  private String content;
  @Column (nullable = false)
  private String createDate;
  private String updateDate;
  
  @PrePersist
  protected void onCreate ()
  {
    this.createDate = LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm"));
    if (this.updateDate == null || this.updateDate.isBlank ())
    {
      this.updateDate = this.createDate;
    }
  }
  
  @PreUpdate
  protected void onUpdate ()
  {
    this.updateDate = LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm"));
  }
  
  @Column (nullable = false, columnDefinition = "INT DEFAULT 0")
  private int likeBoard;
  @Column (nullable = false, length = 45)
  private String boardCate;
  @ManyToOne
  @JoinColumn (name = "createName", referencedColumnName = "nickName", nullable = false)
  @ToString.Exclude
  private MemberEntity member_nick;
}
