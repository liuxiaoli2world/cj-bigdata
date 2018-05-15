package com.lgsc.cjbd.book.scheduled;



import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lgsc.cjbd.book.model.Content;
import com.lgsc.cjbd.book.service.ContentService;




/**
 * 执行定时任务，每周四下午 16:40 减少新闻的点击量
 * 
 * @author 罗天宇
 *
 */
@Component
public class ContentScheduled {
    
	@Autowired
	private ContentService contentService;
	
	private static final Logger logger = LogManager.getLogger(ContentScheduled.class);
    
	@Scheduled(cron = "0 40 16 ? * 4") //每周四下午 16:40 减少新闻的点击量
	public void executeDownPv() {

	    logger.info("每个星期三中午12点定时任务：减少新闻的点击量为原来的四分之一 ");
	    List<Content> notices =  contentService.selectByPvRank("notice");
	    List<Content> headlines =  contentService.selectByPvRank("headline");
	    for (Content content : headlines) {
			content.setPv(content.getPv()/4);
			contentService.updateByPrimaryKey(content);
		}
	    for (Content content : notices) {
			content.setPv(content.getPv()/4);
			contentService.updateByPrimaryKey(content);
		}
	    
		
	}

}
