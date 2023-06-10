package egovframework.pet.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.pet.dao.BoardDAO;
import egovframework.pet.service.BoardService;
import egovframework.pet.vo.BoardTbVO;

/**
 * 게시물 관리를 위한 서비스 구현 클래스
 * @author 공통 서비스 개발팀 한성곤
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
@Service("BoardService")
public class BoardServiceImpl implements BoardService {

	@Resource(name = "BoardDAO")
	private BoardDAO boardDAO;

	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	/**
	 * 조건에 맞는 게시물 목록을 조회 한다.
	 *
	 * @see egovframework.let.cop.bbs.BoardService.service.EgovBBSManageService#selectBoardArticles(egovframework.let.cop.bbs.brd.service.BoardVO)
	 */
	@Override
	public Map<String, Object> selectBoardList(BoardTbVO vo) throws Exception {

		List<BoardTbVO> result = boardDAO.selectBoardList(vo);
		
		int cnt = boardDAO.selectBoardListCnt(vo);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}
	
	/**
	 * 게시물 대하여 상세 내용을 조회 한다.
	 *
	 * @see egovframework.let.cop.bbs.BoardService.service.EgovBBSManageService#selectBoardArticle(egovframework.let.cop.bbs.brd.service.BoardVO)
	 */
	@Override
	public BoardTbVO selectBoardDetail(BoardTbVO vo) throws Exception {
		boardDAO.updateViews(vo);

		return boardDAO.selectBoardDetail(vo);
	}

	/**
	 * 게시물 등록
	 *
	 * @see egovframework.let.cop.bbs.BoardService.service.EgovBBSManageService#selectBoardArticle(egovframework.let.cop.bbs.brd.service.BoardVO)
	 */
	@Override
	public BoardTbVO insertBoard(BoardTbVO vo) throws Exception {
		boardDAO.insertBoard(vo);
		
		return boardDAO.insertBoard(vo);
	}
}
