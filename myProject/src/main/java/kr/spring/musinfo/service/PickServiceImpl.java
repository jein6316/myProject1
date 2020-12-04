package kr.spring.musinfo.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.spring.musinfo.dao.PickMapper;
import kr.spring.musinfo.vo.PickVO;

@Service("pickService")
public class PickServiceImpl implements PickService{
	@Resource
	PickMapper pickMapper;

	@Override
	public void insertPick(PickVO pickVO) {
		pickMapper.insertPick(pickVO);
	}

	@Override
	public void deletePick(PickVO pickVO) {
		pickMapper.deletePick(pickVO);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return pickMapper.selectRowCount(map);
	}

}