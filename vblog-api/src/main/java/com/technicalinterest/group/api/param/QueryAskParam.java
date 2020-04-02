package com.technicalinterest.group.api.param;

import lombok.Data;

/**
 * @ClassName: QueryAskParam
 * @Author: shuyu.wang
 * @Description:
 * @Date: 2020/3/31 17:31
 * @Version: 1.0
 */
@Data
public class QueryAskParam extends PageBaseParam {

    private String keyWord;
}
