package com.technicalinterest.group.service.dto;

import com.technicalinterest.group.dao.PageBase;
import lombok.Data;

/**
 * @ClassName: AskDTO
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/31 16:49
 * @Version: 1.0
 */
@Data
public class AskDTOParam extends PageBase {

    private String keyWord;

    private Short type;
}
