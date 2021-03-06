package org.kosta.madfortaste.user.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.kosta.madfortaste.common.lib.Page;
import org.kosta.madfortaste.user.dao.MemberDao;
import org.kosta.madfortaste.user.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao memberDao;
	
	@Resource(name="memberImg")
	private String path;
	
	@Override
	public Member insertMember(Member member) throws IllegalStateException, IOException {
		MultipartFile imgFile = member.getImgFile();
		if(imgFile != null) {
			File dir = new File(path);	
			if(!dir.exists()) dir.mkdirs();
			if(imgFile.getSize()!=0) {
			String extension = "." + imgFile.getOriginalFilename().split("\\.")[1];
			imgFile.transferTo(new File(path + member.getId() + extension));
			member.setProfileImg(member.getId()+extension);
			}
		}
		return memberDao.insertMember(member);
	}

	@Override
	public Member selectMemberById(String id) {
		return memberDao.selectMemberById(id);
	}

	@Override
	public int selectTotalCount() {
		return memberDao.selectTotalCount();
	}

	@Override
	public List<Member> selectMemberList(int currentPage) {
		Page page = new Page(memberDao.selectTotalCount());
		page.setCurrentPage(currentPage);
		page.setPageSize(10);
		page.setPageGroupSize(5);
		return memberDao.selectMemberList(page);
	}

	@Override
	public void updateMember(Member member) {
		memberDao.updateMember(member);
	}

	@Override
	public void deleteMember(String id) {
		memberDao.deleteMember(id);
	}

	@Override
	public void upExp(String id, int acquiredExp) {
		memberDao.upExp(id, acquiredExp);
	}

	@Override
	public void upPoint(String id, int acquiredPoint) {
		memberDao.upPoint(id, acquiredPoint);
	}

	@Override
	public void downPoint(String id, int lostPoint) {
		memberDao.downPoint(id, lostPoint);
	}

}
