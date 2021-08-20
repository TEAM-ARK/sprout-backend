package com.ark.inflearnback.domain.category;

import com.ark.inflearnback.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    /**
     *  TODO : 카테고리를 생성하는 권한에 대한 논의가 필요
     *  - 관리자만 생성가능토록 할 것인가? or 지식 공유자가 강의를 등록할 때 새로운 카테고리를 생성할 수 있도록 할 것인가?
     */

    public Category(String name) {
        this.name = name;
    }

    /**
     * TODO : 카테고리를 생성 후에 부모 자식 카테고리를 연결하는 연관 관계 메서드가 추후에 필요함.
     * ex)
     *  1. 최상위 카테고리인 개발/프로그래밍 카테고리 생성
     *      - 부모 카테고리는 없고, 자식 카테고리는 빈 리스트
     *  2. 개발/프로그래밍 카테고리의 하위 카테고리로 들어갈 웹 개발 카테고리 생성
     *  3. 웹 개발 카테고리의 부모 카테고리를 개발/프로그래밍 카테고리로 지정하면 개발/프로그래밍의 자식 리스트에 웹 개발이 포함되도록하는
     *     연관관계 편의 메서드!
     */
}
