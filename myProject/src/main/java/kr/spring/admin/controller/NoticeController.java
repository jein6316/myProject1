package kr.spring.admin.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.admin.service.NoticeService;
import kr.spring.admin.vo.NoticeVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;

@Controller
public class NoticeController {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	NoticeService noticeService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public NoticeVO initCommand() {
		return new NoticeVO();
	}
	
	
	// 게시판 목록
	@RequestMapping("/admin/noticeList.do")
	public ModelAndView process(@RequestParam(value = "pageNum", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyfield", defaultValue = "") String keyfield,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		System.out.println("//map: " + map);

		// 총 글의 갯수 또는 검색된 글의 갯수 구하기
		int count = noticeService.selectRowCount(map);
		System.out.println("//count: " + count);
		if (log.isDebugEnabled()) {
			log.debug("<<count>> : " + count);
		}
		System.out.println("//" + currentPage + "//" + keyfield + "//" + keyword);
		PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, 10, 10, "noticeList.do");
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		System.out.println("//page :" + page);
		System.out.println("//map: " + map);
		List<NoticeVO> list = null;
		if (count > 0) {
			list = noticeService.selectList(map);
			System.out.println("//list : " + list);

			if (log.isDebugEnabled()) {
				log.debug("<<글목록>> : " + list);
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("noticeList");
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("pagingHtml", page.getPagingHtml());
		System.out.println("//mav: " + mav);
		return mav;
	}

	
	//글 등록 폼
	@RequestMapping(value="/admin/noticeWrite.do",method=RequestMethod.GET)
	public String form() {
		return "noticeWrite";
	}
	
	//글 등록 처리
	@RequestMapping(value="/admin/noticeWrite.do",method=RequestMethod.POST)
	public String submit(@Valid NoticeVO noticeVO,
			             BindingResult result,
			             HttpServletRequest request,
			             HttpSession session) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<게시판 글 저장>> : " + noticeVO);
		}
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "noticeWrite";
		}
		
		//회원 번호 셋팅
		MemberVO vo = (MemberVO)session.getAttribute("user");
		noticeVO.setMem_num(vo.getMem_num());

		//글쓰기
		noticeService.insertNotice(noticeVO);
		
		return "redirect:/admin/noticeList.do";
	}
	
	//공자사항 상세
	@RequestMapping("/admin/noticeView.do")
	public ModelAndView process(@RequestParam int no_num) {
		System.out.println("//공지사항 상세 보기 ");
		if(log.isDebugEnabled()) {
			log.debug("<<글 상세>> : " + no_num);
		}
		//해당 글의 조회수 증가
		noticeService.updateHit(no_num);
		
		NoticeVO notice = noticeService.selectNotice(no_num);
		
		return new ModelAndView("noticeView","notice",notice);
	}
	
	
	//수정 폼 호출
	@RequestMapping(value="/admin/noticeUpdate.do", method=RequestMethod.GET)
	public String form(@RequestParam int no_num, Model model) {
		
		NoticeVO noticeVO = noticeService.selectNotice(no_num);
		
		model.addAttribute("noticeVO", noticeVO);
		
		return "noticeModify";
	}
	
	//글 수정 처리
	@RequestMapping(value="/admin/noticeUpdate.do", method=RequestMethod.POST)
	public String submitUpdate(@Valid NoticeVO noticeVO,
			             BindingResult result,
			             HttpServletRequest request,
			             HttpSession session,
			             Model model) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<글 정보 수정>> : " + noticeVO);
		}
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "noticeModify";
		}
		
		//회원 번호 셋팅
		MemberVO user = (MemberVO)session.getAttribute("user");
		noticeVO.setMem_num(user.getMem_num());
		
		//글 수정
		noticeService.updateNotice(noticeVO);
		
		//View에 표시할 메시지
		model.addAttribute("message", "공지사항 수정 완료!!");
		model.addAttribute("url", 
				request.getContextPath()+"/admin/noticeList.do");
		
		//타일스 설정에 아래 뷰이름이 없으면 단독으로 JSP 호출
		return "redirect:/admin/noticeView.do?no_num="+noticeVO.getNo_num();
	}
	
	//글 삭제 처리
	@RequestMapping("/admin/noticeDelete.do")
	public String submitDelete(@RequestParam int no_num,
			                   Model model,
			                   HttpServletRequest request) {
		
		if(log.isDebugEnabled()) {
			log.debug("<<게시판 글 삭제>> : " + no_num);
		}
		
		//글 삭제
		noticeService.deleteNotice(no_num);
		
		model.addAttribute("message", "공지사항 삭제 완료!!");
		model.addAttribute("url", 
				request.getContextPath()+"/admin/noticeList.do");
		
		return "redirect:/admin/noticeList.do";
	}
	

	//이미지 업로드
	 @RequestMapping(value="/admin/imageUpload.do", method = RequestMethod.POST)
	    public void imageUpload(HttpServletRequest request,
	            HttpServletResponse response, MultipartHttpServletRequest multiFile
	            , @RequestParam MultipartFile upload) throws Exception{
	        // 랜덤 문자 생성
	        UUID uid = UUID.randomUUID();
	        
	        OutputStream out = null;
	        PrintWriter printWriter = null;
	        
	        //인코딩
	        response.setCharacterEncoding("utf-8");
	        response.setContentType("text/html;charset=utf-8");
	        
	        try{
	            
	            //파일 이름 가져오기
	            String fileName = upload.getOriginalFilename();
	            byte[] bytes = upload.getBytes();
	            
	            //이미지 경로 생성
	            String path = "/myProject/src/main/webapp/resources/upload";
	            String ckUploadPath = path + uid + "_" + fileName;
	            File folder = new File(path);
	            
	            //해당 디렉토리 확인
	            if(!folder.exists()){
	                try{
	                    folder.mkdirs(); // 폴더 생성
	                }catch(Exception e){
	                    e.getStackTrace();
	                }
	            }
	            
	            out = new FileOutputStream(new File(ckUploadPath));
	            out.write(bytes);
	            out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화
	            
	            String callback = request.getParameter("CKEditorFuncNum");
	            printWriter = response.getWriter();
	            String fileUrl = "/admin/ckImgSubmit.do?uid=" + uid + "&fileName=" + fileName;  // 작성화면
	            
	        // 업로드시 메시지 출력
	          printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
	          printWriter.flush();
	            
	        }catch(IOException e){
	            e.printStackTrace();
	        } finally {
	          try {
	           if(out != null) { out.close(); }
	           if(printWriter != null) { printWriter.close(); }
	          } catch(IOException e) { e.printStackTrace(); }
	         }
	        
	        return;
	    }
	    
	    /**
	     * cKeditor 서버로 전송된 이미지 뿌려주기
	     * @param uid
	     * @param fileName
	     * @param request
	     * @return
	     * @throws ServletException
	     * @throws IOException
	     */
	    //
	    @RequestMapping(value="/admin/ckImgSubmit.do")
	    public void ckSubmit(@RequestParam(value="uid") String uid
	                            , @RequestParam(value="fileName") String fileName
	                            , HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException{
	        
	        //서버에 저장된 이미지 경로
	        String path = "/myProject/src/main/webapp/resources/upload";
	    
	        String sDirPath = path + uid + "_" + fileName;
	    
	        File imgFile = new File(sDirPath);
	        
	        //사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
	        if(imgFile.isFile()){
	            byte[] buf = new byte[1024];
	            int readByte = 0;
	            int length = 0;
	            byte[] imgBuf = null;
	            
	            FileInputStream fileInputStream = null;
	            ByteArrayOutputStream outputStream = null;
	            ServletOutputStream out = null;
	            
	            try{
	                fileInputStream = new FileInputStream(imgFile);
	                outputStream = new ByteArrayOutputStream();
	                out = response.getOutputStream();
	                
	                while((readByte = fileInputStream.read(buf)) != -1){
	                    outputStream.write(buf, 0, readByte);
	                }
	                
	                imgBuf = outputStream.toByteArray();
	                length = imgBuf.length;
	                out.write(imgBuf, 0, length);
	                out.flush();
	                
	            }catch(IOException e){
	                log.info(e);
	            }finally {
	                outputStream.close();
	                fileInputStream.close();
	                out.close();
	            }
	        }
	    }

	
}

