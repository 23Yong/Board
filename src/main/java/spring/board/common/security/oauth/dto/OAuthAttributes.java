package spring.board.common.security.oauth.dto;

import lombok.Builder;
import lombok.Getter;
import spring.board.common.security.consts.OauthType;
import spring.board.domain.member.UserAccount;
import spring.board.exception.auth.InvalidOauthTypeException;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String usernameAttributeName, Map<String, Object> attributes) {
        if (OauthType.get(registrationId) == OauthType.GOOGLE)
            return ofGoogle(usernameAttributeName, attributes);
        else
            throw new InvalidOauthTypeException(registrationId + "은 유효한 소셜 로그인 방식이 아닙니다.");
    }

    private static OAuthAttributes ofGoogle(String usernameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    public UserAccount toEntity() {
        return UserAccount.createOauth(nameAttributeKey, name, email, picture, OauthType.GOOGLE);
    }
}
