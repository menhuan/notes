import com.infervision.OnirigiRepileApplication;
import com.infervision.controller.ProfessorControler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/3/27 23:30
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= OnirigiRepileApplication.class)
public class OnirigiRepileApplicationTest {

    @Resource
    ProfessorControler professorControler;

    @Test
    public void test(){
        professorControler.start();
    }


}
