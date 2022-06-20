package spring.board.common.security.consts;

import lombok.RequiredArgsConstructor;
import spring.board.exception.auth.InvalidOauthTypeException;

@RequiredArgsConstructor
public enum OauthType {
    GOOGLE("google");

    private final String value;

    public static OauthType get(String type) {
        for (OauthType oauthType : OauthType.values()) {
            if (oauthType.value.equals(type)) {
                return oauthType;
            }
        }

        throw new InvalidOauthTypeException(type + "은 유효한 소셜 로그인 방법이 아닙니다.");
    }
}
