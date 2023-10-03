package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername(); // 이건 최적화
    TeamInfo getTeam();

    interface TeamInfo { // 중첩 구조에서 여기는 최적화 안됨 다 불러옴, left outer join 함
        String getName();
    }

}
