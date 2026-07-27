SELECT * FROM springpt.member;
SELECT * FROM springpt.board;
SELECT * FROM springpt.comment;

update comment c set like_comment = 1 where seq_comment = 11;

insert into member  (id, pw, nick_name, create_date)
values ('1', '1', '1', now());

insert into board (board_title, create_name, content, create_date, board_cate)
values ('1', '1', '1', now(), '1');

update board set update_date = '2025-02-05 17:22' where seq_board = 11;

update board b set b.board_title = '테스트제목7', b.content = '테스트내용7', b.board_cate = '테스트카테고리4' where b.seq_board = 12;

update comment c set c.content = '1' where c.seq_comment = 1;