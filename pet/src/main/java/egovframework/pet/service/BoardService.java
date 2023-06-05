package egovframework.pet.service;

import java.util.Map;

import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.pet.vo.BoardTbVO;

/**
 * 게시물 관리를 위한 서비스 인터페이스  클래스
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
public interface BoardService {

	/**
	 * 게시물 목록을 조회한다
	 * @return
	 * 
	 * @param Board
	 * @exception Exception Exception
	 */
	public Map<String, Object> selectBoardList(BoardTbVO vo)
	  throws Exception;
	
	/**
	 * 게시물 대하여 상세 내용을 조회 한다.
	 * @return
	 * 
	 * @param boardVO
	 * @exception Exception Exception
	 */
	public BoardTbVO selectBoardDetail(BoardTbVO boardVO)
	  throws Exception;

}