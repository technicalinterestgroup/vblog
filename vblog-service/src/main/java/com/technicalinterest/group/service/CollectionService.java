package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.Collection;
import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.service.dto.LikeDTO;
import com.technicalinterest.group.service.dto.ReturnClass;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

public interface CollectionService {

	ReturnClass insert(LikeDTO likeDTO);

	ReturnClass del(LikeDTO likeDTO);

	ReturnClass queryListCollection(PageBase pageBase);
}
