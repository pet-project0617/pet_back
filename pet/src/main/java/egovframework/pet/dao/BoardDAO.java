package egovframework.pet.dao;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.cop.bbs.service.Board;
import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.pet.vo.BoardTbVO;

/**
 * 게시물 관리를 위한 데이터 접근 클래스
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009.03.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.19  이삼섭          최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 *  </pre>
 */
@Repository("BoardDAO")
public class BoardDAO extends EgovAbstractMapper {


    /**
     * 조건에 맞는 게시물 목록을 조회 한다.
     *
     * @param boardVO
     * @return
     * @throws Exception
     */
    public List<BoardTbVO> selectBoardList(BoardTbVO vo) throws Exception {
	return (List<BoardTbVO>) list("BoardDAO.selectBoardList", vo);
    }

    /**
     * 조건에 맞는 게시물 목록에 대한 전체 건수를 조회 한다.
     *
     * @param boardVO
     * @return
     * @throws Exception
     */
    public int selectBoardListCnt(BoardTbVO vo) throws Exception {
	return (Integer)selectOne("BoardDAO.selectBoardListCnt", vo);
    }
    
    /**
     * 게시물 한 건에 대하여 상세 내용을 조회 한다.
     *
     * @param boardVO
     * @return
     * @throws Exception
     */
    public BoardTbVO selectBoardDetail(BoardTbVO boardVO) throws Exception {
	return (BoardTbVO)selectOne("BoardDAO.selectBoardDetail", boardVO);
    }
    
    /**
     * 게시물 한 건의 조회수를 수정한다
     *
     * @param board
     * @throws Exception
     */
    public void updateViews(BoardTbVO boardVO) throws Exception {
    	update("BoardDAO.updateViews",boardVO);
    }
}
