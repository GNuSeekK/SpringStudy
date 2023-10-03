package study.datajpa.repository;

public class UsernameOnlyDto {

    private final String username;

    public UsernameOnlyDto(String username) { // 파라미터 명을 보고 생성함 Projection
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
