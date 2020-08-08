package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.FeedBack;
import cn.fcars.infomgr.mapper.basic.FeedbackMapper;
import cn.fcars.infomgr.service.basic.FeedbackService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackMapper feedbackMapper;

	@Override
	public FeedBack findByID(Integer id) {
		return null;
	}

	@Override
	public FeedBack findByStringID(String id) throws Exception {
		return null;
	}

	@Override
	public FeedBack findByName(String name) {
		return null;
	}

	@Override
	public List<FeedBack> findAll() {
		return null;
	}

	@Override
	public List<FeedBack> findByQuery(BaseQuery data) {
		return null;
	}

	@Override
	public PageInfo<FeedBack> findByPage(BaseQuery data) {
		return null;
	}

	@Override
	public Integer check(String name) {
		return null;
	}

	@Override
	public boolean checkExist(String id, String name) {
		return false;
	}

	@Override
	public void add(FeedBack data) throws Exception {
		feedbackMapper.add(data);
	}

	@Override
	public void update(FeedBack data) throws Exception {

	}

	@Override
	public void deleteByID(Integer id) throws Exception {

	}

	@Override
	public int delete(FeedBack data) throws Exception {
		return 0;
	}
}
