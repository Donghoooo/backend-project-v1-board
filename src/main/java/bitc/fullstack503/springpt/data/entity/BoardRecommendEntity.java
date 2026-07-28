package bitc.fullstack503.springpt.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "board_recommend",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_member_board",
                        columnNames = {"board_id", "member_id"}
                )
        }
)

public class BoardRecommendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendId;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "member_id", nullable = false, length = 50)
    private String memberId;

    private LocalDateTime recommendDate = LocalDateTime.now();

    // [중요] 생성자 이름도 클래스 이름과 똑같이 수정 완료!
    public BoardRecommendEntity(Long boardId, String memberId) {
        this.boardId = boardId;
        this.memberId = memberId;
    }
}
