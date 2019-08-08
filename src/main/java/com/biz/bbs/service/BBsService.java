package com.biz.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.bbs.mapper.BBsDao;
import com.biz.bbs.model.BBsDto;
import com.biz.bbs.model.BBsReqDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BBsService {

	@Autowired
	BBsDao bDao;
	
	@Autowired
	AjaxFileService aFService;
	
	
	public List<BBsDto> bbsList(){
		//List<BBsVO> bbsList = bDao.selectAll();
		List<BBsDto> bbsList = bDao.selectAll();
		return bbsList;
	}

	public List<BBsDto> bbsListForFile(){
		List<BBsDto> bbsList = bDao.selectAllForFile();
		
		return bbsList;
	}
	/*
	 *글쓰기를 수행할 떄 file을 같이 upload를 하면
	 *해당하는 글의 bbs_seq를 조회하여 
	 *file table에 저장할 떄 같이 저장을 수행해줘야 한다.
	 *
	 * 	1. 게시글 저장
	 * 	2. 게시글의 bbs_seq를 조회
	 * 	3. 파일정보를 insert할때 해당하는 bbs_seq를 같이 저장
	 * 	4. 게시글을 조회할 때 해당하는 파일들을 같이 조회할 수 있다
	 */
	public int insert(BBsReqDto bbsReqDto) {

		// 게시판 내용 저장
		int ret = bDao.insert(bbsReqDto);
		// 첨부파일 정보 저장
		int file_ret = aFService.insert(bbsReqDto);
		
		return ret;
	}

	public BBsDto getContent(long bbs_seq) {
		
		BBsDto bbsDto = bDao.findBySeqForFile(bbs_seq);
		
		return bbsDto;
	}

	public int update(BBsReqDto bbsReqDto) {

		
		int ret = bDao.update(bbsReqDto);
		
		return 0;
	}

	public int delete(long bbs_seq) {

		//첨부파일 삭제
	//int file_ret = aFService.files_delete(bbs_seq);
		
		//게시글을 삭제하면 두 테이블이 참조 무결성 관계이므로 
		//첨부파일 목록은 자동으로 삭제된다
		int ret = bDao.delete(bbs_seq);
		
		return 0;
	}
	
}
