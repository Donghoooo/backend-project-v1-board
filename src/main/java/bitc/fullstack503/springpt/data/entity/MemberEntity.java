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
import java.util.List;
@Entity
@Table (name = "member")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners (AuditingEntityListener.class)
public class MemberEntity
{
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private long seqMember;
  @Column (nullable = false, length = 45, unique = true)
  private String id;
  @Column (nullable = false, length = 45)
  private String pw;
  @Column (nullable = false, length = 45, unique = true)
  private String nickName;
  @CreatedDate
  @Column (nullable = false)
  private String createDate;
  
  @PrePersist
  protected void onCreate ()
  {
    this.createDate = LocalDateTime.now ().format (DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm"));
  }
  
  @OneToMany (mappedBy = "member_nick")
  @ToString.Exclude
  private List<BoardEntity> boards;
}
