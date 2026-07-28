package bitc.fullstack503.springpt.service;

import bitc.fullstack503.springpt.data.entity.BoardEntity;
import bitc.fullstack503.springpt.data.entity.BoardRecommendEntity;
import bitc.fullstack503.springpt.data.repository.BoardRecommendRepository;
import bitc.fullstack503.springpt.data.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardRecommendRepository boardRecommendRepository;

    @Override
    public List<BoardEntity> selectBoardList() {
        return boardRepository.findAllByOrderBySeqBoardDescNotNotice();
    }

    @Override
    public List<String> boardCate() {
        return boardRepository.findDistinctBoardCate();
    }

    @Override
    public List<BoardEntity> indexBoard() {
        return boardRepository.findFirst6ByOrderBySeqBoardDesc();
    }

    @Override
    public List<BoardEntity> selectCateList(String boardCate) {
        return boardRepository.findByBoardCateOrderBySeqBoardDesc(boardCate);
    }

    @Override
    public BoardEntity boardDetail(long seqBoard) {
        return boardRepository.findById(seqBoard).orElseThrow(() -> new RuntimeException("Board not found"));
    }

    @Override
    public void save(BoardEntity board) {
        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void deleteBoard(long seqBoard) {
        boardRepository.deleteById(seqBoard);
    }

    @Transactional
    @Override
    public void boardLikeCount(long seqBoard) {
        boardRepository.updateLikeBoard(seqBoard);
    }

    @Override
    @Transactional // 데이터의 안전한 등록을 위해 트랜잭션 필수!
    public boolean recommendBoard(Long boardId, String memberId) {

        // 1. 이 회원이 이 게시글을 이미 추천했는지 DB에서 유니크 조건으로 확인
        if (boardRecommendRepository.existsByBoardIdAndMemberId(boardId, memberId)) {
            return false; // 이미 추천 기록이 있다면 false 리턴 (차단)
        }

        // 2. 추천한 적이 없다면 새로운 추천 객체 생성
        BoardRecommendEntity recommend = new BoardRecommendEntity(boardId, memberId);

        // 3. DB에 안전하게 영구 저장
        boardRecommendRepository.save(recommend);

        return true; // 추천 성공!
    }
}
