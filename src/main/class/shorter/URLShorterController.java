package shorter;

import shorter.DAO.*;
import shorter.Entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/Lib")
public class URLShorterController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()); // 로거 선언

    final URLShorterDAO dao;

    @Autowired
    Encrypt enc;

    @Autowired
    public URLShorterController(URLShorterDAO dao) {
        this.dao = dao;
    }

    @GetMapping("")
    public String index() {

        return "/index";
    }


    @PostMapping("/add")
    public String adddata(@RequestParam String input, @RequestParam String custom) throws Exception {
        Url u = new Url();
        String randum = null;
        try {
            if (custom.equals("")) {
                randum = enc.randnum();
                System.out.println("랜덤값 : " + randum);
                u.setAddr(input);
                u.setShorter(randum);
            } else {
                u.setAddr(input);
                u.setShorter(custom);
            }
            dao.addshorter(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index";
    }


    @GetMapping("/{adr}")
    public String gotoadr(@PathVariable String adr, Model m) throws Exception {

        try {
            Url u = dao.getdata(adr);
            System.out.println("adr값 : " + adr);

            if (u != null) {
                System.out.println("주소 : " + u.getAddr());
                m.addAttribute("addr", u.getAddr());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/Control";
    }
}