package kr.spring.musinfo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.spring.musinfo.dao.CommentsMapper;
import kr.spring.musinfo.service.CommentsService;
import kr.spring.musinfo.vo.CommentsVO;
         //빈 이름
@Service("commentsService")
public class CommentsServiceImpl implements CommentsService{
	@Resource
	CommentsMapper commentsMapper;
			@Override
			public void insertComments(CommentsVO commentsVO) {
				// TODO Auto-generated method stub
				commentsMapper.insertComments(commentsVO, 0);
			}

			@Override
			public CommentsVO selectComments(int rev_num) {
				// TODO Auto-generated method stub
				return commentsMapper.selectComments(rev_num);
			}

			@Override
			public void updateComments(CommentsVO commentsVO) {
				// TODO Auto-generated method stub
				commentsMapper.updateComments(commentsVO);
			}

			@Override
			public void deleteComments(int rev_num) {
				// TODO Auto-generated method stub
				commentsMapper.deleteComments(rev_num);
			}


}
