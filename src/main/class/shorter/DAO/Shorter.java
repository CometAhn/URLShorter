package shorter.DAO;

import org.springframework.stereotype.Component;

@Component
public class Shorter {
	// php에서 사용하는 방식 java에서 쓰자.
	// shorter값은 비워두고 데이터 입력.
	// 그 데이터의 id값 + 10000000을
	// base_convert(그값,10,36) 한다.
	// 결과 값을 shorter에 저장 후 업데이트

	// 랜덤 문자 생성
	public String makeShorter(int id) {
		id += 10000000;
		return base_convert(Integer.toString(id), 10, 36);
	}

	public static String base_convert(final String inputValue, final int fromBase, final int toBase) {
		if (fromBase < 2 || fromBase > 36 || toBase < 2 || toBase > 36) {
			return null;
		}
		String ret = null;
		try {
			ret = Integer.toString(Integer.parseInt(inputValue, fromBase), toBase);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}
}