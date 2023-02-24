package fan.company.springbootjwttoken.repository;

import fan.company.springbootjwttoken.entity.Card;
import fan.company.springbootjwttoken.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

    List<Outcome> findAllByFromCardId(Card card);
}
