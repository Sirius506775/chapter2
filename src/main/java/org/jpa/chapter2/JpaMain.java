package org.jpa.chapter2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        //엔티티 매니저 팩토리 - 생성
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("chapter2");
        //엔티티 매니저 - 생성
        EntityManager em = emf.createEntityManager();
        //트랜잭션 - 획득
        EntityTransaction tx = em.getTransaction(); //트랜잭션을 시작하기 위해 EntityManager에서 Transaction API를 받아온다.

        try{
            //jpa는 항상 트랜잭션 안에서 데이터를 변경하여야한다. 그렇지 않다면 예외가 발생한다.
            tx.begin(); //트랜잭션 - 시작
            logic(em); //Transaction API를 사용해서 비즈니즈 로직 실행
            tx.commit(); //로직이 정상 동작하면 트랜잭션을 커밋

        }catch(Exception e){
           tx.rollback(); //예외가 발생하면 트랜잭션 롤백
        }finally {
            em.close(); //엔티티 매니저 - 종료
        }
            emf.close(); //엔티티 매니저 팩토리 - 종료
    }

    //비즈니스 로직
    private static void logic(EntityManager em) {

        String id = "id1";
        Member member =  new Member();
        member.setId(id);
        member.setUsername("Brain");
        member.setAge(2);

        //register
        em.persist(member); //persist() 메소드로 엔티티 저장

        //modify
        member.setAge(22); //JPA는 어떤 엔티티가 변경되었는지 추적 가능하기 때문에 값만 변경해도 update SQL를 생성

        //findOne
        Member findMemember = em.find(Member.class, id); //조회할 엔티티의 타입과 매핑한 @id값으로 엔티티 한 개를 조회
        System.out.println("findMemember: " + findMemember.getUsername() +  ", age:" + findMemember.getAge());

        //findAll
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList(); //여기서 Member는 테이블이 아닌 엔티티 객체이다.
        System.out.println("members.size: " + members.size());
        //필요한 데이터만 조회하기 위해 검색 조건이 포함된 JPQL을 사용한다.
        //em.createQuery() 메소드를 실행해서 쿼리 객체를 생성한 후, 쿼리 객체의 getResultList() 메소드를 호출
        //JPQL은 SQL을 추상화한 객체지향 쿼리 언어이며, DB 테이블을 대상으로하는 SQL과 달리 엔티티 객체를 대상으로 쿼리를 날린다.(EX. 클래스와 필드를 대상으로 쿼리를 날림)
        //JPQL은 db 테이블을 전혀 알지 못하며, JPA는 이 JPQL을 분석해서 적절한 sql을 만들어 사용한다.


        //delete
        em.remove(member);

    }
}
