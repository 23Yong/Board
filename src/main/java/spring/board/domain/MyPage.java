package spring.board.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class MyPage {

    @Id @GeneratedValue
    @Column(name = "my_page_id")
    private Long id;
}
