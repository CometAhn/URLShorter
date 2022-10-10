package shorter.DAO;

import shorter.Entity.*;
import shorter.Repository.URLShorterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class URLShorterDAO {
    @Autowired
    URLShorterRepository urlRepository;

    // 등록
    public void addshorter(Url u) throws Exception {
        Url newurl = urlRepository.save(u);
    }

    // 데이터 조회
    public Url getdata(String url) throws Exception {
        return urlRepository.findByShorter(url);
    }

}
