package codinginterview.arraystring;

import java.util.HashMap;
import java.util.Map;

/**
 * 문제 설명
 * : 문자열에 포함된 문자들이 전부 유일한지를 검사하는 알고리즘을 구현하라.
 * 제약사항
 * - 다른 자료구조를 사용할 수 없는 상황이며 배열과 해시테이블만 사용 가능하다.
 *
 */
public class Quiz1 {
	public static void main(String[] args) {
		// TODO : JUnit으로 테스트 케이스도 만들자.
		String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers = "0123456789";
		String notUniqueStr = "hello world!";
		String notUniqueStr2 = "안녕하세요.";
		String notUniqueStr3 = "반갑습니다.반갑습니다. ";
		Quiz1 quiz1 = new Quiz1();
		System.out.println(quiz1.isUniqueChar(alpha));
		System.out.println(quiz1.isUniqueChar(numbers));
		System.out.println(quiz1.isUniqueChar(notUniqueStr2));
		System.out.println(quiz1.isUniqueChar(notUniqueStr3));
	}
	public boolean isUniqueChar(String str) {
		boolean result = true;
		Map<Character, Boolean> charMap = new HashMap<Character, Boolean>(str.length() / 2);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (charMap.containsKey(c)) {
				result = false;
				break;
			} else {
				charMap.put(c, true);
			}
		}
		charMap.clear();
		return result;
	}
}
