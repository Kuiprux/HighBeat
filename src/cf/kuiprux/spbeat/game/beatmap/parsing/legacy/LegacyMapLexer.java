package cf.kuiprux.spbeat.game.beatmap.parsing.legacy;

import java.util.ArrayList;
import java.util.List;

public class LegacyMapLexer {
	
	//�� ���� �߶� ��ȯ
	public List<List<Token>> separateRawMap(String rawMap) {
		List<List<Token>> tokenList = new ArrayList<>();

		String[] lineList = rawMap.split(System.lineSeparator());
		for (int l = 0; l < lineList.length; l++) {
			String line = lineList[l];

			List<Token> localTokenList = new ArrayList<>();

			char[] array = line.toCharArray();
			String buffer = "";
			for (int i = 0; i < array.length; i++) {
				char c = array[i];

				//�ּ� ��ū
				if (buffer.equals("//")) {
					localTokenList.add(new Token(TokenType.ANNOTATION, buffer));
					buffer = "";
				}
				else if (c == '=') {
					if (buffer != "") {
						localTokenList.add(new Token(TokenType.IDENTIFIER, buffer));
						buffer = "";
					}
					localTokenList.add(new Token(TokenType.EQUALS, "="));
				}
				else if (c == LegacyMapParser.LOCAL_VARIABLE) {
					if (buffer != "") {
						localTokenList.add(new Token(TokenType.IDENTIFIER, buffer));
						buffer = "";
					}
					localTokenList.add(new Token(TokenType.LOCAL_VARIABLE, c + ""));
				}
				else if (c == LegacyMapParser.BEAT_SEPARATOR) {
					if (buffer != "") {
						localTokenList.add(new Token(TokenType.IDENTIFIER, buffer));
						buffer = "";
					}
					localTokenList.add(new Token(TokenType.BEAT_SEPARATOR, c + ""));
				}
				else if (c == LegacyMapParser.BLANK) {
					if (buffer != "") {
						localTokenList.add(new Token(TokenType.IDENTIFIER, buffer));
						buffer = "";
					}
					localTokenList.add(new Token(TokenType.BLANK, c + ""));
				}
				else if (c == '��' || c == '��' || c == '��' || c == '��') {
					if (buffer != "") {
						localTokenList.add(new Token(TokenType.IDENTIFIER, buffer));
						buffer = "";
					}
					localTokenList.add(new Token(TokenType.HOLD_SLIDER, c + ""));
				}
				else if (isNumberChar(c)) {
					if (buffer != "") {
						localTokenList.add(new Token(TokenType.IDENTIFIER, buffer));
						buffer = "";
					}
					localTokenList.add(new Token(TokenType.TIME_CHARACTER, c + ""));
				}
				else{
					buffer += c;
				}
			}

			//������ �� �߰�
			if (buffer != "") {
				localTokenList.add(new Token(TokenType.IDENTIFIER, buffer));
				buffer = "";
			}

			tokenList.add(localTokenList);
		}
		
		return tokenList;
	}
	
	private boolean isNumberChar(char c) {
		for (char character : LegacyMapParser.NUMBERS) {
			if (character == c)
				return true;
		}
		
		return false;
	}
	
	public class Token {
		
		private TokenType tokenType;
		private String value;
		
		public Token(TokenType tokenType, String value) {
			this.tokenType = tokenType;
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public TokenType getTokenType() {
			return tokenType;
		}
		
		@Override
		public String toString() {
			return getTokenType().name() + " = " + value;
		}
	}
	
	public enum TokenType {
		IDENTIFIER, EQUALS, BEAT_SEPARATOR, BLANK, TIME_CHARACTER, ANNOTATION, LOCAL_VARIABLE, HOLD_SLIDER;
	}
}