package fan.company.springbootjwttoken.repository;

import fan.company.springbootjwttoken.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    boolean existsByUsernameAndNumber(String username, Long number);

    boolean existsByNumber(Long number);

    Card findByNumber(Long number);



}
