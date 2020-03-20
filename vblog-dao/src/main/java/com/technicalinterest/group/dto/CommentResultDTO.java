package com.technicalinterest.group.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: CommentResultDTO
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/20 9:54
 * @Version: 1.0
 */
@Data
public class CommentResultDTO {
    private List<CommentDTO> fristCommnetList;

    private Map<Long,List<CommentDTO>> refCommnet;
}
