package com.infervision.service.impl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.infervision.model.ProfessorContent;
import com.infervision.model.TitleHead;
import com.infervision.service.WordService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/6/16 14:21
 * @Version 1.0
 */
@Service
public class WordServiceImpl implements WordService {


    @Override
    public void toWordByContent(List<ProfessorContent> cons) {
        String file =  this.getClass().getClassLoader().getResource("doc/segment.docx").getPath();
        String story =  this.getClass().getClassLoader().getResource("doc/story.docx").getPath();
        TitleHead head = new TitleHead();
        head.setTitleHead("教授");
        DocxRenderData segment = new DocxRenderData(new File(file), cons );
        head.setSegment(segment);
        XWPFTemplate template = XWPFTemplate.compile(story).render(head);

        try {
            FileOutputStream out = new FileOutputStream("f:/VSCODE\\out.docx");
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
