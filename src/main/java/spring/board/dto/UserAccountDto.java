package spring.board.dto;

import spring.board.domain.member.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String userId,
        String email,
        String userPassword,
        String nickname,
        String memo
) {

    public static UserAccountDto of(String userId, String email, String userPassword, String nickname, String memo) {
        return new UserAccountDto(null, null, null, null, userId, email, userPassword, nickname, memo);
    }

    public static UserAccountDto of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String userId, String email, String userPassword, String nickname, String memo) {
        return new UserAccountDto(createdAt, createdBy, modifiedAt, modifiedBy, userId, email, userPassword, nickname, memo);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getUserId(),
                entity.getEmail(),
                entity.getUserPassword(),
                entity.getNickname(),
                entity.getMemo()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo
        );
    }
}
