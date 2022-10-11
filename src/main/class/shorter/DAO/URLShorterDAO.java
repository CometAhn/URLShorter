package shorter.DAO;

import shorter.Entity.*;
import shorter.Repository.URLShorterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class URLShorterDAO {
	@Autowired
	URLShorterRepository urlRepository;

	// 등록
	public void addShorter(Url u) throws Exception {
		// 현재 날짜 구하기용 및 포맷
		LocalDateTime date = LocalDateTime.now();
		String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);
		u.setTimestampt(now);
		Url newurl = urlRepository.save(u);
	}

	// 데이터 조회
	public Url getDataToShorter(String shorter) throws Exception {
		return urlRepository.findByShorter(shorter);
	}

	// 데이터 조회 to url
	public List<Url> getDataToInput(String addr) throws Exception {
		return urlRepository.findByAddr(addr);
	}

	// 마지막 사용 시간
	public void update(Url u) throws Exception {
		// 현재 날짜 구하기용 및 포맷
		LocalDateTime date = LocalDateTime.now();
		String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(date);
		u.setLastUsed(now);
		Url newurl = urlRepository.save(u);
	}
}
