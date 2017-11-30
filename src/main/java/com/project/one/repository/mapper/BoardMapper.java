package com.project.one.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.one.vo.BoardVO;

public interface BoardMapper {
	public int insert(BoardVO board);
	public int selectTotalCount();
	public int searchTitleCount(String keyword);
	public int searchContentCount(String keyword);
	public int searchTitleContentCount(String keyword);
	public int searchWriterCount(String keyword);
	public BoardVO select(int board_num);
	public List<BoardVO> selectList(@Param("startRow")int startRow, @Param("count")int count);
	public List<BoardVO> searchTitle(@Param("startRow")int startRow, @Param("count")int count, @Param("keyword")String keyword);
	public List<BoardVO> searchContent(@Param("startRow")int startRow, @Param("count")int count, @Param("keyword")String keyword);
	public List<BoardVO> searchTitleContent(@Param("startRow")int startRow, @Param("count")int count, @Param("keyword")String keyword);
	public List<BoardVO> searchWriter(@Param("startRow")int startRow, @Param("count")int count, @Param("keyword")String keyword);
	public int updateReadCount(int board_num);
	public int update(BoardVO board);
	public int updateOpen(@Param("open")int open, @Param("board_num")int board_num);
	public int delete(int board_num);
	public int maxList();
	public int replyList(int board_num);
	public int replyLevel(int board_num);
	public int replyRidx(int board_num);
	public int replyExistCheck(@Param("list")int list, @Param("level")int level, @Param("ridx")int ridx);
	public int listCount(int list);
	public int countRidx(@Param("list")int list, @Param("level")int level, @Param("ridx")int ridx);
	public int inputRidx(@Param("list")int list, @Param("level")int level, @Param("ridx")int ridx);
	public int inputMaxRidx(int list);
	public int increaseRidx(@Param("list")int list, @Param("ridx")int ridx);
}