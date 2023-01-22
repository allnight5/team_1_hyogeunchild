package com.sparta.team_1_hyogeunchild.persistence.entity;

import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "seller")
@DiscriminatorValue(value = "Seller")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Seller extends User {
    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    private String introduce;
    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Category> categories;

    // 문제? -> Seller 인스턴스가 +1 되는거에요. USER1 -> SELLER / USER2 이자 Seller < / Buyer로도 주고 Seller 로도 주면 로그인 부분이 좀 문제가 있을 수가 있다.<
    // 우리의 로직은 대부분 UserName < 더 문제의 여지가 있다. < unique 를 풀면 < Seller 새 회원이름 다오 하면 노상관.
    // Buyer <기본. -> Seller 로 등업이 되면, Buyer였던 이 사람의 유저아이디를 삭제하고 Seller로만 남길건지?
    // 새 객체 만드는거 문제 없다. 근데 userName이 중복이다. < ID는 다름
    // 펭귄(부모클래스) 뒤뚱뒤뚱걷고, 날개+다리두개 -> 뽀로로(자식클래스) + 안경을 특이한거 쓴다 // 황제펭귄(자식클래스) + 황제다 <
    @Builder
    public Seller(String username, String password, UserRoleEnum role, String storeName, String introduce) {
        super(username, password, role);
        this.storeName = storeName;
        this.introduce = introduce;
    }
}
