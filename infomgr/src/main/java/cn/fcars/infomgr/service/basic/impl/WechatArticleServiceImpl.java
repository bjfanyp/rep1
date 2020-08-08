package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.WechatArticle;
import cn.fcars.infomgr.mapper.basic.WechatArticleMapper;
import cn.fcars.infomgr.service.basic.WechatArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wechatArticleService")
public class WechatArticleServiceImpl implements WechatArticleService {
	@Autowired
	WechatArticleMapper wechatArticleMapper;

	@Override
	public WechatArticle findByID(Integer id) {
		return null;
	}

	@Override
	public WechatArticle findByStringID(String id) throws Exception {
		return wechatArticleMapper.findByStringID(id);
	}

	@Override
	public WechatArticle findByName(String name) {
		return null;
	}

	@Override
	public List<WechatArticle> findAll() {
		return null;
	}

	@Override
	public List<WechatArticle> findByQuery(BaseQuery data) {
		return null;
	}

	@Override
	public PageInfo<WechatArticle> findByPage(BaseQuery data) {
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
	public void add(WechatArticle data) throws Exception {

	}

	@Override
	public void update(WechatArticle data) throws Exception {

	}

	@Override
	public void deleteByID(Integer id) throws Exception {

	}

	@Override
	public int delete(WechatArticle data) throws Exception {
		return 0;
	}
}
