package com.infervision.model;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.DocxRenderData;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/6/16 15:01
 * @Version 1.0
 */
public class TitleHead {

    @Name("title_name")
    private String titleHead;

    private DocxRenderData segment;

    public String getTitleHead() {
        return titleHead;
    }

    public void setTitleHead(String titleHead) {
        this.titleHead = titleHead;
    }

    public DocxRenderData getSegment() {
        return segment;
    }

    public void setSegment(DocxRenderData segment) {
        this.segment = segment;
    }
}
