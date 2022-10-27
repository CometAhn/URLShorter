package shorter;

import shorter.DAO.*;
import shorter.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class URLShorterController {

    final URLShorterDAO dao;

    @Autowired
    Shorter st;

    @Autowired
    public URLShorterController(URLShorterDAO dao) {
        this.dao = dao;
    }

    final String http = "http://localhost/";

    @GetMapping("")
    public String index() {
        return "/index";
    }

    @PostMapping("")
    public String adddata(@RequestParam String input, @RequestParam String custom, Model m) throws Exception {
        Url u = new Url();
        Url r = null;
        Url l = null;
        String randum = null;
        try {
            if (input.equals("")) { // 아무것도 입력 안되어 있으면,
                // 에러 출력 : URL을 입력해주세요.
                m.addAttribute("error", "2");
                return "/index";
            }
            // 데이터 등록 시작
            if (custom.equals("")) { // 커스텀 단축이 아니면,

                // 입력한 주소가 커스텀 주소가 아니고, 데이터베이스에 있다면,
                l = dao.getDataToInput(input);
                if (l != null) {
                    // 데이터 추가하지 말고 등록된 값 뿌려주자.
                    m.addAttribute("shorterurl", http + l.getShorter());
                    return "/index";
                }

                // php에서 사용하던 방식 그대로 사용.
                // 생성
                u.setAddr(input);
                u.setCustomCheck(false);
                dao.addShorter(u);

                // 그리고 생성된 id값으로 Shorter 업데이트
                u.setShorter(st.makeShorter(u.getId()));

                dao.addShorter(u);
            } else { // 커스텀 단축이면
                // 데이터 조회
                r = dao.getDataToShorter(custom);
                // 동일한 값이 db에 있다면
                if (r != null) {
                    // 에러 출력 : 이미 존재하는 커스텀 단축 주소입니다.
                    m.addAttribute("error", "0");
                    return "/index";
                }
                u.setAddr(input);
                u.setShorter(custom);
                u.setCustomCheck(true);
                //데이터 저장.
                dao.addShorter(u);
            }
        } catch (Exception e) {
            // 에러 출력 : 예기치 않는 오류가 발생했습니다.
            m.addAttribute("error", "3");
            e.printStackTrace();
            return "/index";
        }
        m.addAttribute("shorterurl", http + u.getShorter());
        return "/index";
    }

    @GetMapping("/{shorter}")
    public String gotoadr(@PathVariable String shorter, Model m) throws Exception {
        try {
            Url u = dao.getDataToShorter(shorter);

            if (u != null) {
                m.addAttribute("addr", u.getAddr());
                //데이터 업데이트 : 마지막 사용 시간
                dao.update(u);
            } else {
                // 에러 출력 :  존재하지 않는 단축 주소입니다.
                m.addAttribute("error", "1");
                return "/index";
            }
        } catch (Exception e) {
            // 에러 출력 : 예기치 않는 오류가 발생했습니다.
            m.addAttribute("error", "3");
            e.printStackTrace();
            return "/index";
        }
        return "/Control";
    }
}