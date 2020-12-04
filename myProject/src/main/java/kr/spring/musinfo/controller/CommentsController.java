package kr.spring.musinfo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.ModelAndView;

import kr.spring.member.vo.MemberVO;
import kr.spring.musinfo.service.CommentsService;
import kr.spring.musinfo.vo.CommentsVO;
import kr.spring.musinfo.vo.ContentsVO;
import kr.spring.util.PagingUtil;


@Controller
public class CommentsController {
	@Resource
	private CommentsService commentsService;
	private Logger log = Logger.getLogger(this.getClass());
	//자바빈 초기화
	@ModelAttribute
	public CommentsVO initCommand() {
		return new CommentsVO();
	}

	//리뷰 폼 호출
	@RequestMapping(value="/musinfo/write.do",method=RequestMethod.GET)
	public String reviewForm(@RequestParam int mus_num, Model model) {
		CommentsVO commentsVO = new CommentsVO();
		commentsVO.setMus_num(mus_num);
		model.addAttribute("commentsVO", commentsVO);
		model.addAttribute("pageCheck", "reviewpage");
		
		return "reviewWrite";
	}
	//리뷰 등록
	@RequestMapping(value="/musinfo/write.do",method=RequestMethod.POST)
	public String submit(@Valid CommentsVO commentsVO, 
						BindingResult result, 
						HttpServletRequest request, 
						HttpSession session) {
		System.out.println("//리뷰 등록 처리");
		if(log.isDebugEnabled()) {
			log.debug("<<리뷰 저장>>:"+commentsVO);
		}
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "reviewWrite";
		}
		//회원번호 세팅
		MemberVO member=(MemberVO)session.getAttribute("user");
		System.out.println("//member : " + member);
		commentsVO.setMem_num(member.getMem_num());
		//뮤지컬번호 세팅
		commentsVO.setMus_num(commentsVO.getMus_num());
		//리뷰쓰기
		commentsService.insertComments(commentsVO);
		System.out.println("//리뷰쓰기");
		return "redirect:musinfoDetail.do?mus_num="+commentsVO.getMus_num();
	}



	//리뷰 전체보기
		@RequestMapping("/musinfo/reviews.do")
		public ModelAndView process(
				@RequestParam int mus_num,
				@RequestParam(value="pageNum",defaultValue="1") int currentPage,
				@RequestParam(value = "keyfield", defaultValue = "") String keyfield,
				@RequestParam(value = "keyword", defaultValue = "") String keyword){
			System.out.println("//리뷰 전체보기*************");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("keyfield", keyfield);
			map.put("keyword", keyword);
			map.put("mus_num",mus_num);
			int count=commentsService.selectRowCount(map);
			System.out.println("//count: "+ count);
			if(log.isDebugEnabled()) {
				log.debug("<<count>>: "+count);
			}
			PagingUtil page=new PagingUtil(currentPage,count,100,10,"reviews.do");
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
			System.out.println("//page :" + page);
			System.out.println("//map: " + map);
			List<CommentsVO> list=null;
			if(count>0) {
				list=commentsService.selectList(map);
				if(log.isDebugEnabled()) {
					log.debug("<<리뷰 목록>>:"+list);
				}
			}
			System.out.println("//list: "+ list);
			ModelAndView mav=new ModelAndView();
			mav.setViewName("reviews");
			mav.addObject("count",count);
			mav.addObject("list",list);
			mav.addObject("pagingHtml",page.getPagingHtml());
			System.out.println("//mav: "+ mav);
			return mav;
		}
	//리뷰 수정 폼 호출
	@RequestMapping(value="/musinfo/modify.do",method=RequestMethod.GET)
	public String form(@RequestParam int rev_num,@RequestParam int mus_num, Model model) {
		CommentsVO commentsVO=commentsService.selectComments(rev_num,mus_num);
		
		if(log.isDebugEnabled()) {
			log.debug("<<CommentsVO>> : " + commentsVO);
		}
		
		model.addAttribute("commentsVO",commentsVO);
		return "reviewModify";
	}
	//리뷰 수정 처리
	@RequestMapping(value="/musinfo/modify.do",method=RequestMethod.POST)
	public String submitUpdate(@ModelAttribute("commentsVO") @Valid CommentsVO commentsVO, BindingResult result,HttpServletRequest request,HttpSession session,Model model) {	
		if(log.isDebugEnabled()) {
			log.debug("<<리뷰 수정>>"+commentsVO);
		}
		//오류시 폼 호출
		if(result.hasErrors()) {
			return "reviewModify";
		}
		MemberVO member=(MemberVO)session.getAttribute("user");
		System.out.println("//member : " + member);
		commentsVO.setMem_num(member.getMem_num());
		//뮤지컬번호 세팅
		commentsVO.setMus_num(commentsVO.getMus_num());
		//리뷰쓰기
		commentsService.updateComments(commentsVO);

		
		//수정 후 view
		model.addAttribute("message","수정되었습니다.");
		model.addAttribute("url",request.getContextPath()+"/musinfo/musinfoMain.do");
		return "musinfo/result";
	}
	//글 삭제
	@RequestMapping("/musinfo/delete.do")
	public String submitDelete(@RequestParam int rev_num,Model model, HttpServletRequest request) {
		commentsService.deleteComments(rev_num);
		model.addAttribute("message","삭제되었습니다.");
		model.addAttribute("url",request.getContextPath()+"/main/musMain.do");
		return "musinfo/result";

	}
	
	
}
