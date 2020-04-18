package com.technicalinterest.group.service;

import com.technicalinterest.group.dao.PageBase;
import com.technicalinterest.group.service.dto.LikeDTO;
import com.technicalinterest.group.service.dto.ReturnClass;

public interface CollectionService {

	ReturnClass insert(LikeDTO likeDTO);

	ReturnClass del(LikeDTO likeDTO);

	ReturnClass queryListCollection(Short type,PageBase pageBase);
}
