//package egovframework.com.utl.wed.web;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.egovframe.rte.fdl.cryptography.EgovCryptoService;
//import org.egovframe.rte.fdl.property.EgovPropertyService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Base64Utils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import com.google.gson.JsonObject;
//
//import egovframework.com.cmm.service.EgovProperties;
//import egovframework.let.utl.fcc.service.EgovFileUploadUtil;
//import egovframework.let.utl.fcc.service.EgovFormBasedFileUtil;
//import egovframework.let.utl.fcc.service.EgovFormBasedFileVo;
//import egovframework.let.utl.fcc.service.EgovStringUtil;
//
///**
// * 웹에디터 이미지 upload 처리 Controller
// * @author 공통컴포넌트개발팀 한성곤
// * @since 2009.08.26
// * @version 1.0
// * @see
// *
// * <pre>
// * << 개정이력(Modification Information) >>
// *
// *   수정일                수정자          수정내용
// *  -----------   --------  ---------------------------
// *   2009.08.26   한성곤          최초 생성
// *   2017.08.31   장동한          path, physical 파라미터 노출 암호화 처리
// *   2017.12.12   장동한          출력 모듈 경로 변경 취약점 조치
// *   2018.03.07   신용호          URLEncode 처리
// *   2018.08.17   신용호          URL 암호화 보안 추가 조치
// *   
// * </pre>
// */
//@RestController
//public class EgovWebEditorImageController {
//
//    /** 로그설정 */
//	private static final Logger LOGGER = LoggerFactory.getLogger(EgovWebEditorImageController.class);
//	
//    /** 첨부파일 위치 지정  => globals.properties */
//    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath");
//    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
//    private final String extWhiteList = EgovProperties.getProperty("Globals.fileExtensions.Images");    
//
//    /** 첨부 최대 파일 크기 지정 */
//    private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
//
//    public static final String ALGORITM_KEY = EgovProperties.getProperty("Globals.crypto.algoritm");
//    
//    @Resource(name = "propertiesService")
//    protected EgovPropertyService propertyService;
//    
//	/** 암호화서비스 */
//	@Resource(name = "egovARIACryptoService")
//	EgovCryptoService cryptoService;
//    
//    /**
//     * 이미지 Upload(CK에디터)를 처리한다.
//     *
//     * @param request
//     * @param model
//     * @return
//     * @throws Exception
//     */
//    @PostMapping(value="/utl/wed/insertImageCk.do")
//    @ResponseBody
//    public String imageUploadCk(MultipartHttpServletRequest multiRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
//    	
//    	JsonObject jsonObject = new JsonObject();
//    	PrintWriter printWriter = null;
//    	OutputStream outputStream = null;
//    	MultipartFile multipartFile = multiRequest.getFile("upload");
//    	
//    	if(multipartFile != null) {
//    		if(multipartFile.getSize() > 0 && StringUtils.isNotBlank(multipartFile.getName())) {
//    			if(multipartFile.getContentType().toLowerCase().startsWith("image/")) {
//    				try {
//    					List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(multiRequest, uploadDir, maxFileSize, extWhiteList);
//    					if (list.size() > 0) {
//    						EgovFormBasedFileVo vo = list.get(0);	// 첫번째 이미지
//    						
//    						String fileName = vo.getFileName();
//    						String subPath = vo.getServerSubPath();
//    						String physicalName = vo.getPhysicalName();
//    					    String url = request.getContextPath()
//    								    + "/utl/web/imageSrc.do?"
//    								    + "path=" + Base64Utils.encodeToUrlSafeString(cryptoService.encrypt(subPath.getBytes(), ALGORITM_KEY))
//    								    + "&physical=" + Base64Utils.encodeToUrlSafeString(cryptoService.encrypt(physicalName.getBytes(), ALGORITM_KEY))
//    								    + "&contentType=" + Base64Utils.encodeToUrlSafeString(cryptoService.encrypt(vo.getContentType().getBytes(), ALGORITM_KEY));
//    					
//	    					byte[] bytes = multipartFile.getBytes();
//	    					String uploadPath = uploadDir + "/" + subPath;
//	    					
//	    					File uploadFile = new File(uploadPath);
//	    					if(!uploadFile.exists()) {
//	    						uploadFile.mkdirs();
//	    					}
//	    					
//	    					uploadPath = uploadPath + "/" + physicalName;
//	    					outputStream = new FileOutputStream(new File(uploadPath));
//	    					outputStream.write(bytes);
//	    					
//	    					printWriter = response.getWriter();
//	    					response.setContentType("text/html");
//	    					
//	    					jsonObject.addProperty("uploaded", 1);
//	    					jsonObject.addProperty("fileName", fileName);
//	    					jsonObject.addProperty("url", url);
//	    					
//	    					printWriter.println(jsonObject);
//    					}
//    				} catch(IOException e) {
//    					e.printStackTrace();
//    				} finally {
//    					if(outputStream != null) {
//    						outputStream.close();
//    					}
//    					if(printWriter != null) {
//    						printWriter.close();
//    					}
//    				}
//    			}
//    		}
//    	}
//    	
//    	return null;
//    }
//
//    /**
//     * 이미지 view를 제공한다.
//     *
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    @GetMapping(value="/utl/web/imageSrc.do")
//    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		//2017.12.12 - 출력 모듈 경로 변경 취약점 조치
//    	//KISA 보안약점 조치 (2018-10-29, 윤창원)
//		String subPath = new String(cryptoService.decrypt(Base64Utils.decodeFromUrlSafeString(EgovStringUtil.isNullToString(request.getParameter("path"))),ALGORITM_KEY));
//		String physical = new String(cryptoService.decrypt(Base64Utils.decodeFromUrlSafeString(EgovStringUtil.isNullToString(request.getParameter("physical"))),ALGORITM_KEY));
//		String mimeType = new String(cryptoService.decrypt(Base64Utils.decodeFromUrlSafeString(EgovStringUtil.isNullToString(request.getParameter("contentType"))),ALGORITM_KEY));
//		
//		if (subPath.indexOf("..") >= 0 ) throw new Exception("Security Exception - illegal url called.");
//		if (physical.indexOf("..") >= 0 ) throw new Exception("Security Exception - illegal url called.");
//		
//		String ext = "";
//		if ( physical.lastIndexOf(".") > 0 )
//			ext = physical.substring(physical.lastIndexOf(".") + 1,physical.length()).toLowerCase();
//		if ( ext == null ) throw new FileNotFoundException();
//		
//		if ( extWhiteList.indexOf(ext) >= 0 )
//			EgovFormBasedFileUtil.viewFile(response, uploadDir, subPath, physical, mimeType);
//		else
//			throw new FileNotFoundException();
//    }
//}
